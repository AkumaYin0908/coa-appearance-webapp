"use strict";

import { showModalMessage, showModal, hideModal } from "./modules/modal.js";
import { baseUrl } from "./modules/baseUrl.js";
import showMessage from "./modules/message-holder.js";
import { loadAddress } from "./ph-address-selector.js";
import { newButton, actionButtons, addressContent, errorContent } from "./modules/htmlContent.js";

let fullUrl = `${baseUrl}/visitors`;

const modalHeader = $("div.modal-header");
const modalBody = $("div.modal-body");
const addressContainer = $("#visitorForm #address");
export let visitorId ;
const name = $("#name");
const position = $("#position");
const agency = $("#agency");

let barangay = null;
let municipality = null;
let province = null;
let region = null;

//state
let isEdit = false;

const toast = Swal.mixin({
  toast: true,
  position: "top-end",
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.onmouseenter = Swal.stopTimer;
    toast.onmouseleave = Swal.resumeTimer;
  },
});

const renderDataTable = $("#visitors").DataTable({
  responsive: true,
  ajax: {
    url: fullUrl,
    dataSrc: "",
  },
  columns: [
    { data: "id" },
    { data: "name" },
    { data: "position.title" },
    { data: "agency.name" },
    {
      data: "address",
      render: function (data, type, row) {
        return `${data.barangay == undefined ? "" : data.barangay.name + ","} ${data.municipality.name}, ${data.province.name}`;
      },
    },
    {
      data: "id",
      render: function (data, type, row) {
        visitorId = data;
        return `<div id = "actionButton">
        ${newButton(data)}
        ${actionButtons(data)}
        </div>`;
      },
    },
  ],
  processing: true,
});

$("#visitors").on("click", "a.btn-edit", function (event) {
  event.preventDefault();
  let id = $(this).attr("id");
  isEdit = true;
  displayTitle(isEdit);
  editVisitor(id);
});

$("#visitors").on("click", "a.btn-delete", function (event) {
  event.preventDefault();
  let id = $(this).attr("id");
  deleteVisitor(id);
});


const submitForm = function () {
  barangay = $("#address #barangay-text");
  municipality = $("#address #city-text");
  province = $("#address #province-text");
  region = $("#address #region-text");

  let visitor = {
    id: visitorId.val(),
    name: name.val(),
    position: {
      title: position.val(),
    },
    agency: {
      name: agency.val(),
    },
    address: {
      barangay: barangay.val()
        ? {
            code: barangay.attr("code"),
            name: barangay.val(),
          }
        : null,
      municipality: {
        code: municipality.attr("code"),
        name: municipality.val(),
      },
      province: {
        code: province.attr("code"),
        name: province.val(),
      },
      region: {
        code: region.attr("code"),
        name: region.val(),
      },
    },
  };

  console.log(visitor);

  submitFormToServer(visitor);
};

function editVisitor(id) {
  let fullUrl = `${baseUrl}/visitors/${id}`;

  fetch(fullUrl)
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

      visitorId.val(id);
      name.val(data.name);
      position.val(data.position.title);
      agency.val(data.agency.name);

      loadAddress(data.address);
      $("#visitorModal").modal("show");
    })
    .catch((error) => {
      Swal.fire({
        title: "Error",
        text: error.message,
        icon: "error",
      });
    });
}

function submitFormToServer(visitor) {
  let fullUrl = `${baseUrl}/visitors${isEdit ? "/" + visitor.id : ""}`;
  console.log(fullUrl);
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
      window.location.href = `${baseUrl}/visitor-page`;
    })
    .catch((error) => {
      if ($("#errorContainer").length === 0) {
        $(errorContent(error)).prependTo(modalBody);
      }
    })
    .finally(() => (isEdit = false));
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
  name.val("");
  position.val("");
  agency.val("");
  emptyAddressContainer();
  if ($("#errorContainer").length > 0) {
    $("#errorContainer").remove();
  }

  $("h5.modal-title").remove();
  isEdit = false;
});

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
          Swal.fire({
            title: "Deleted!",
            text: data.message,
            icon: "success",
          });
          window.location.href = `${baseUrl}/visitor-page`;
        })
        .catch((error) => {
          Swal.fire({
            title: "Error",
            text: error.message,
            icon: "error",
          });
        });
    }
  });
}
