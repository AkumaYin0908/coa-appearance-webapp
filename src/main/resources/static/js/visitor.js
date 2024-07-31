"use strict";

import { baseUrl } from "./modules/baseUrl.js";
import { loadAddress } from "./ph-address-selector.js";
import { newButton, editButton, deleteButton, addressContent, errorContent } from "./modules/htmlContent.js";
import { showAppearanceChoices } from "./modules/appearance-type.js";
import { fetchVisitor } from "./modules/visitor-manager.js";
import { toast, alert } from "./modules/alerts.js";

let fullUrl = `${baseUrl}/visitors`;

const modalHeader = $("div.modal-header");
const modalBody = $("div.modal-body");
const addressContainer = $("#visitorForm #address");
const courtesyTitleEl = $("#courtesyTitle");
const firstNameEl = $("#firstName");
const middleInitEl = $("#middleInitial");
const lastNameEl = $("#lastName");

const positionEl = $("#position");
const agencyEl = $("#agency");

let visitorId = 0;

//state
let isEdit = false;

const renderDataTable = await $("#visitors").DataTable({
  responsive: true,
  language: {
    lengthMenu: "Show _MENU_ entries",
  },
  ajax: {
    url: fullUrl,
    dataSrc: "",
  },
  columnDefs: [
    {
      visible: false,
      targets: [0, 1, 2, 3],
    },
  ],
  columns: [
    { data: "id" },
    { data: "firstName" },
    { data: "middleInitial" },
    { data: "lastName" },
    {
      data: null,
      render: function (data, type, row, meta) {
        return `${row.firstName}${row.middleInitial === "N/A" ? " " : row.middleInitial}${row.lastName}`;
      },
      // width : "20%"
    },
    { data: "position.title" },
    { data: "agency.name" },
    {
      data: "address",
      render: function (data, type, row) {
      console.log(data);
        return `${data.barangay == null ? "" : data.barangay.name + ","} ${data.municipality.name}, ${
          data.province.name
        }`;
      },
    },
    {
      data: "id",
      render: function (data, type, row) {
        return `<div id = "actionButton">
        ${newButton(data)}
        ${editButton(data)}
        ${deleteButton(data)}
        </div>`;
      },
    },
  ],
  processing: true,
});

$("#visitors").on("click", "a.btn-new", function () {
  let id = $(this).data("key");
  showAppearanceChoices(baseUrl, id);
});

$("#visitors").on("click", "a.btn-edit", function (event) {
  event.preventDefault();
  isEdit = true;
  displayTitle(isEdit);
  editVisitor($(this).data("key"));
});

$("#visitors").on("click", "a.btn-delete", function (event) {
  event.preventDefault();
  deleteVisitor($(this).data("key"));
});

function submitForm() {
  const barangayEl = $("#address #barangay-text");
  const municipalityEl = $("#address #city-text");
  const provinceEl = $("#address #province-text");
  const regionEl = $("#address #region-text");

  let visitor = {
    id: visitorId,
    courtesyTitle: {
      title: courtesyTitleEl.val(),
    },
    firstName: firstNameEl.val(),
    middleInitial: middleInitEl.val() ? middleInitEl.val() : "N/A",
    lastName: lastNameEl.val(),
    position: {
      title: positionEl.val(),
    },
    agency: {
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

function editVisitor(id) {
  fetchVisitor(id)
    .then((data) => {
      $(addressContent).prependTo(addressContainer);
      console.log(data);
      visitorId = id;
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

function submitFormToServer(visitor) {
console.log(visitor);
  const fullUrl = `${baseUrl}/visitors${isEdit ? "/" + visitor.id : ""}`;
  fetch(fullUrl, {
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
      toast.fire({
        icon: "success",
        title: `${data["name"]}  has been ${isEdit ? "updated" : "saved"}!`,
      });
      $("#visitorModal").modal("hide");
      resetVisitorModal();
      renderDataTable.ajax.reload();
    })
    .catch((error) => {
      if ($("#errorContainer").length === 0) {
        $(errorContent(error)).prependTo(modalBody);
      }
    });
}

$("#addVisitorButton").on("click", function (event) {
  event.preventDefault();
  displayTitle(isEdit);
  console.log(isEdit);
  loadAddress(null);
  $(addressContent).prependTo(addressContainer);
  $("#visitorModal").modal("show");
});

$("#closeModalButton").on("click", function (event) {
  event.preventDefault();
  resetVisitorModal();
});

function resetVisitorModal() {
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
$("#saveButton").on("click", function (event) {
  submitForm();
});

$("#visitorForm").submit(function (event) {
  event.preventDefault();
});

function displayTitle(isEdit) {
  if ($("h5.modal-title").length === 0) {
    $(`<h5 class="modal-title text-light">${isEdit ? "Edit" : "New"} Visitor</h5>`).prependTo(modalHeader);
  }
}

function emptyAddressContainer() {
  if (addressContainer.length > 0) {
    addressContainer.empty();
  }
}

function deleteVisitor(id) {
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#d33",
    cancelButtonColor: "#3085d6",
    confirmButtonText: "Yes, delete it!",
  }).then((result) => {
    if (result.isConfirmed) {
      let fullUrl = `${baseUrl}/visitors/${id}`;

      fetch(fullUrl, {
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
          renderDataTable.ajax.reload();
        })
        .catch((error) => {
          alert("Error", error.message, "error");
        });
    }
  });
}
