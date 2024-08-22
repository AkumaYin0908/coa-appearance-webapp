"use strict";

import { baseUrl } from "./modules/base-url.js";
import { appearanceTableObject } from "./modules/table-object.js";
import { visitorDetails, displayTitle, errorContent } from "./modules/html-content.js";
import { showCertificate } from "./modules/certificate-generator.js";
import { datePicker } from "./modules/date-picker.js";
import { openConfirmDialog, toast,alert } from "./modules/popups.js";
import DialogDetails from "./modules/DialogDetails.js";

const visitorDetailContainer = $(".visitor-details");
const idEl = $("#id");
const referenceEl = $("#reference");
const dateIssuedEl = $("#dateIssued");
const dateFromEl = $("#dateFrom");
const dateToEl = $("#dateTo");
const purposeEl = $("#purpose");
const dataTable = await $("#appearances").DataTable(appearanceTableObject(`${baseUrl}/visitors/${visitor.id}/appearances`));
const entityType = "Appearance";
$(visitorDetails(visitor)).prependTo(visitorDetailContainer);
datePicker();

$("#return").prop("href",`${baseUrl}/visitor-page`);

/*EVENT LISTENER */
dataTable.on("select deselect", function () {
  let selectedRows = dataTable.rows({ selected: true }).count();
  console.log(selectedRows);
  $("#editButton").prop("disabled", selectedRows === 0 || selectedRows > 1);
  $("#removeButton").prop("disabled", selectedRows === 0 || selectedRows > 1);
  $("#printButton").prop("disabled", selectedRows === 0);
   const appearances = dataTable.rows({ selected: true }).data().toArray();
   console.log(appearances);
});

$("#printButton").on("click", async function (event) {
  event.preventDefault();
  const appearances = dataTable.rows({ selected: true }).data().toArray();
  let appearanceType = appearances.length > 1 ? "consolidated" : "single";
  const dateIssued = moment(new Date()).format("YYYY-MM-DD");
  appearances[0].dateIssued = dateIssued;
  await showCertificate(appearanceType, 1, appearances);
});

$("#editButton").on("click", async function (event) {
  event.preventDefault();
  const selectedRows = dataTable.rows({ selected: true }).count();
  if (selectedRows === 1) {
    displayTitle(true, entityType);
    const appearance = dataTable.row({ selected: true }).data();
    editAppearance(appearance);
    console.log(appearance);
  }
});

$("#saveButton").on("click", async function (event) {
  event.preventDefault();
  await submitFormToServer(getInputs());
});

$("#removeButton").on("click", async function (event) {
  event.preventDefault();
  const selectedRows = dataTable.rows({ selected: true }).count();
  if (selectedRows === 1) {
    const appearance = dataTable.row({ selected: true }).data();
    await deleteAppearance(appearance.id);
  }
});

/**FUNCTIONS */
async function editAppearance(appearance) {
  idEl.val(appearance.id);
  dateIssuedEl.val(moment(appearance.dateIssued).format("LL"));
  dateFromEl.val(moment(appearance.dateFrom).format("LL"));
  dateToEl.val(moment(appearance.dateTo).format("LL"));
  purposeEl.val(appearance.purpose.description);
  referenceEl.val(appearance.reference);
  $("#appearanceModal").modal("show");
}

//transforming inputted data into JSON object
function getInputs() {
  let appearance = {
    id: idEl.val(),
    visitor: visitor,
    dateIssued: moment(new Date(dateIssuedEl.val())).format("YYYY-MM-DD"),
    dateFrom: moment(new Date(dateFromEl.val())).format("YYYY-MM-DD"),
    dateTo: moment(new Date(dateToEl.val())).format("YYYY-MM-DD"),
    purpose: {
      description: purposeEl.val(),
    },
    reference: referenceEl.val() != "" ? referenceEl.val() : "N/A",
  };

  return appearance;
}

async function submitFormToServer(appearance) {
  const url = `${baseUrl}/appearances/${appearance.id}`;
  await fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(appearance),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((data) => {
          console.log(data);
          throw new Error(data.message);
        });
      }
      return response.json();
    })
    .then((data) => {
      toast.fire({
        icon: "success",
        title: "Appearance has been updated!",
      });
      $("#appearanceModal").modal("hide");
      resetAppearanceModal();
      dataTable.ajax.reload();
    })
    .catch((error) => {
      if ($("#errorContainer").length === 0) {
        const modalBody = $("div.modal-body");
        $(errorContent(error)).prependTo(modalBody);
      }
    });
}

async function deleteAppearance(id) {
  const dialogDetails = new DialogDetails("Are you sure?", "You won't be able to revert this!", "warning");

  const result = await openConfirmDialog(dialogDetails);

  if (result.isConfirmed) {
    const url = `${baseUrl}/appearances/${id}`;

    await fetch(url, {
      method: "DELETE",
    })
      .then((response) => {
        if (!response.ok) {
          return response.json().then((data) => {
            throw new Error(data.message);
          });
        }
        return response.json();
      })
      .then((data) => {
        alert("Deleted!", data.message, "success");
        dataTable.ajax.reload();
      })
      .catch((error) => {
        alert("Error", error.message, "error");
      });
  }
}

function resetAppearanceModal() {
  purposeEl.val("");
  referenceEl.val("");
  datePicker;
}
