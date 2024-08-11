"use strict";

import { baseUrl } from "./modules/base-url.js";
import { loadAddress } from "./ph-address-selector.js";
import { addressContent, errorContent, displayTitle } from "./modules/html-content.js";
import { showAppearanceChoices } from "./modules/appearance-type.js";
import { toast, alert, openConfirmDialog } from "./modules/popups.js";
import { visitorTableObject } from "./modules/table-object.js";
import DialogDetails from "./modules/DialogDetails.js";

const idEl = $("#id");
const addressContainer = $("#address");
const courtesyTitleEl = $("#courtesyTitle");
const firstNameEl = $("#firstName");
const middleInitEl = $("#middleInitial");
const lastNameEl = $("#lastName");
const positionEl = $("#position");
const agencyEl = $("#agency");
const entityType = "Visitor";


//state
let isEdit = false;

const dataTable = await $("#visitors").DataTable(visitorTableObject(`${baseUrl}/visitors`));

/*BUTTON LISTENER */
$("#visitors").on("click", "a.btn-new", function (event) {
  event.preventDefault();
  let id = $(this).data("key");
  showAppearanceChoices(baseUrl, id);
});

$("#visitors").on("click", "a.btn-edit", function (event) {
  event.preventDefault();
  isEdit = true;
  displayTitle(isEdit, entityType);
  let id = $(this).data("key");
  editVisitor(id);
});

$("#visitors").on("click", "a.btn-delete", function (event) {
  event.preventDefault();
  deleteVisitor($(this).data("key"));
});

$("#addButton").on("click", function (event) {
  event.preventDefault();
  displayTitle(isEdit, entityType);
  loadAddress(null);
  $(addressContent).prependTo(addressContainer);
  $("#visitorModal").modal("show");
});

$("#closeModalButton").on("click", function (event) {
  event.preventDefault();
  resetVisitorModal();
});

$("#saveButton").on("click", function (event) {
  event.preventDefault();
  submitForm();
});

/* FUNCTIONS */

function submitForm() {
  const barangayEl = $("#address #barangay-text");
  const municipalityEl = $("#address #city-text");
  const provinceEl = $("#address #province-text");
  const regionEl = $("#address #region-text");

  let visitor = {
    id: idEl.val(),
    courtesyTitle: {
      title: courtesyTitleEl.val(),
    },
    firstName: firstNameEl.val(),
    middleInitial: middleInitEl.val() ? middleInitEl.val() : "N/A",
    lastName: lastNameEl.val(),
    position: {
      title: positionEl.val(),
    },
    agency:
      agencyEl.val() === "N/A" || agencyEl.val() === ""
        ? null
        : {
            name: agencyEl.val(),
          },
    address: {
      barangay: barangayEl.val()
        ? {
            code: barangayEl.attr("code"),
            name: barangayEl.val(),
          }
        : null,
      municipality: {
        code: municipalityEl.attr("code"),
        name: municipalityEl.val(),
      },
      province: {
        code: provinceEl.attr("code"),
        name: provinceEl.val(),
      },
      region: {
        code: regionEl.attr("code"),
        name: regionEl.val(),
      },
    },
  };

  submitFormToServer(visitor);
}

async function editVisitor(id) {
  let fullUrl = `${baseUrl}/visitors/${id}`;
  await fetch(fullUrl)
    .then((response) => {
      if (response.status !== 302) {
        return response.json().then((data) => {
          throw new Error(data.message);
        });
      }
      return response.json();
    })
    .then((data) => {
      $(addressContent).prependTo(addressContainer);
      idEl.val(data.id);
      courtesyTitleEl.val(data.courtesyTitle.title);
      firstNameEl.val(data.firstName);
      middleInitEl.val(data.middleInitial);
      lastNameEl.val(data.lastName);
      positionEl.val(data.position.title);
      agencyEl.val(data.agency.name);

      loadAddress(data.address);
      $("#visitorModal").modal("show");
    })
    .catch((error) => {
      alert("Error", error.message, "error");
    });
}

async function submitFormToServer(visitor) {
  const fullUrl = `${baseUrl}/visitors${isEdit ? "/" + visitor.id : ""}`;
  await fetch(fullUrl, {
    method: isEdit ? "PUT" : "POST",
    headers: {
      "Content-Type": "application/json",
    },

    body: JSON.stringify(visitor),
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
      const fullName = `${data.firstName} ${data.middleInitial === "N/A" ? "" : data.middleInitial + " "}${visitor.lastName}`;
      toast.fire({
        icon: "success",
        title: `${fullName}  has been ${isEdit ? "updated" : "saved"}!`,
      });
      $("#visitorModal").modal("hide");
      resetVisitorModal();
      dataTable.ajax.reload();
    })
    .catch((error) => {
      if ($("#errorContainer").length === 0) {
        const modalBody = $("div.modal-body");
        $(errorContent(error)).prependTo(modalBody);
      }
    });
}

function resetVisitorModal() {
  idEl.val("");
  courtesyTitleEl.val("");
  firstNameEl.val("");
  middleInitEl.val("");
  lastNameEl.val("");
  positionEl.val("");
  agencyEl.val("");
  emptyAddressContainer();
  if ($("#errorContainer").length > 0) {
    $("#errorContainer").remove();
  }
  isEdit = false;

  $("h5.modal-title").remove();
}

function emptyAddressContainer() {
  if (addressContainer.length > 0) {
    addressContainer.empty();
  }
}

async function deleteVisitor(id) {
  const dialogDetails = new DialogDetails("Are you sure?", "You won't be able to revert this!", "warning", "Yes, delete it!");

  const result = await openConfirmDialog(dialogDetails);

  if (result.isConfirmed) {
    let fullUrl = `${baseUrl}/visitors/${id}`;

    await fetch(fullUrl, {
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
