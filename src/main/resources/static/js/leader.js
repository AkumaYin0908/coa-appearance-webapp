"use strict";

import { baseUrl } from "./modules/base-url.js";
import { toast, alert } from "./modules/alerts.js";
import { leaderTableObject } from "./modules/table-object.js";

import { displayTitle, errorContent } from "./modules/html-content.js";

const idEl = $("#id");
const nameEl = $("#name");
const positionEl = $("#position");
const inChargeEl = $("#inCharge");
const entityType = "Leader";
//state
let isEdit = false;

const renderDataTable = await $("#leaders").DataTable(leaderTableObject(`${baseUrl}/leaders`));
/*BUTTON LISTENER */
$("#addButton").on("click", async function (event) {
  event.preventDefault();
  const data = await checkInchargeLeader();
  inChargeEl.text(data?.inCharge ? "Inactive" : "Active");
  displayTitle(isEdit, entityType);
  $("#leaderModal").modal("show");
});
$("#saveButton").on("click", function (event) {
  submitForm();
});

$("#leaders").on("click", "a.btn-edit", function (event) {
  event.preventDefault();
  isEdit = true;
  displayTitle(isEdit, entityType);
  let id = $(this).data("key");
  editLeader(id);
});

$("#leaders").on("click", "a.btn-assign", async function (event) {
  event.preventDefault();
  let row = $(this).closest("tr");
  let name = renderDataTable.row(row).data().name;
  let inCharge = renderDataTable.row(row).data().inCharge;
  let id = $(this).data("key");
  try {
    const data = await checkInchargeLeader();
    const result = await displayDialog(inCharge, name);

    if (result.isConfirmed) {
      if (data?.inCharge && !inCharge) {
        await assignLeader(data.id, false);
        await assignLeader(id, true);
      } else if (inCharge) {
        await assignLeader(id, false);
      } else {
        await assignLeader(id, true);
      }
      await alert("Success", `${name} assigned as Leader!`, "success");
      renderDataTable.ajax.reload();
    }
  } catch (error) {
    console.log(error);
    alert("Error", error.message, "error");
  }
});

/* FUNCTIONS */
async function displayDialog(inCharge, name) {
  const confirmDialog = await Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger",
    },
  });

  return await confirmDialog.fire({
    title: `${inCharge ? "Revoke" : "Assign"} ${name} as a leader`,
    text: "Are you sure about this?",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "Yes",
    cancelButtonText: "No",
  });
}
async function assignLeader(id, bool) {
  const response = await fetch(`${baseUrl}/leaders/${id}/${bool}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    return response.json().then((data) => {
      throw new Error(data.message);
    });
  }

  return await response.json();
}
async function submitForm() {
  let leader = {
    id: idEl.val(),
    name: nameEl.val(),
    position: positionEl.val(),
    inCharge: inChargeEl.text() === "Active" ? true : false,
  };
  await submitFormToServer(leader);
}

async function submitFormToServer(leader) {
  const fullUrl = `${baseUrl}/leaders${isEdit ? "/" + leader.id : ""}`;
  await fetch(fullUrl, {
    method: isEdit ? "PUT" : "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(leader),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((data) => new Error(data.message));
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);
      toast.fire({
        icon: "success",
        title: `${data.name} has been ${isEdit ? "updated" : "saved"}!`,
      });
      $("#leaderModal").modal("hide");
      resetLeaderModal();
      renderDataTable.ajax.reload();
    })
    .catch((error) => {
      if ($("#errorContainer").length === 0) {
        const modalBody = $("div.modal-body");
        $(errorContent(error)).prependTo(modalBody);
      }
    });
}

async function editLeader(id) {
  let fullUrl = `${baseUrl}/leaders/${id}`;
  await fetch(fullUrl)
    .then((response) => {
      if (response.status !== 302) {
        return response.json().then((data) => new Error(data.message));
      }
      return response.json();
    })
    .then((data) => {
      idEl.val(data.id);
      nameEl.val(data.name);
      positionEl.val(data.position);
      inChargeEl.text(data.inCharge ? "Active" : "Inactive");
      $("#leaderModal").modal("show");
    })
    .catch((error) => alert("Error", error.message, "error"));
}

async function checkInchargeLeader() {
  const response = await fetch(`${baseUrl}/leaders?inCharge=true`);
  if (response.status === 302) {
    return response.json();
  }
  return null;
}

function resetLeaderModal() {
  idEl.val("");
  nameEl.val("");
  positionEl.val("");
  inChargeEl.text("");
  isEdit = false;
  if ($("#errorContainer").length > 0) {
    $("#errorContainer").remove();
  }
  $("h5.modal-title").remove();
}

// import { showMessage, hideModal } from "./modules/modal.js";

// $(document).ready(function () {
//   $("#leaderButton").addClass("active");

//   showMessage("addModalMessageHolder", "addLeaderModal");

//   showMessage("editModalMessageHolder", "editLeaderModal");

//   $(".btn-delete").on("click", function (event) {
//     event.preventDefault();
//     let link = $(this);

//     let leaderName = link.attr("leaderName");
//     $("#btnYes").attr("href", link.attr("href"));
//     $("#confirmText").html(
//       "Do you want to delete <strong>" + leaderName + "</strong>?"
//     );
//     $("#deleteLeaderModal").modal("show");
//   });

//   $("#addLeaderButton").on("click", function (event) {
//     event.preventDefault();
//     $("#leaderInCharge").prop("checked", true);

//     $("#addLeaderModal").modal("show");
//   });

//   const clearAddModal = function () {
//     $("#leaderId").val("");
//     $("#leaderName").val("");
//     $("#leaderPosition").val("");
//   };

//   hideModal("addModalCloseButton", "addModalDiv", clearAddModal);

//   hideModal("editModalCloseButton", "editModalDiv");

//   $(".btn-edit").on("click", function (event) {
//     event.preventDefault();

//     let leader = $(this);

//     $("#editLeaderId").val(leader.attr("leaderId"));
//     $("#editLeaderName").val(leader.attr("leaderName"));
//     $("#editLeaderPosition").val(leader.attr("leaderPosition"));

//     let inCharge = leader.attr("leaderInCharge") === "true";
//     $("#editLeaderInCharge").prop("checked", inCharge);

//     $("#editLeaderModal").modal("show");
//   });

//   $("#btnClear").on("click", function (event) {
//     event.preventDefault();
//     $("#searchName").text("");

//     window.location.href = "/settings/leaders";
//   });

//   $("#pageSize").on("change", function (event) {
//     event.preventDefault();
//     $("#searchForm").submit();
//   });
// });
