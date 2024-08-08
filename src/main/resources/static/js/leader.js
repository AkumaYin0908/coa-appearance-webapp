"use strict";

import { baseUrl } from "./modules/base-url.js";
import { toast, alert } from "./modules/alerts.js";
import { leaderTableObject } from "./modules/table-object.js";
import { loadStatus } from "./modules/status-selector.js";
import { displayTitle, errorContent } from "./modules/html-content.js";

const idEl = $("#id");
const nameEl = $("#name");
const positionEl = $("#position");
const inChargeEl = $("#inCharge-text");

//state
let isEdit = false;

const renderDataTable = await $("#leaders").DataTable(leaderTableObject(`${baseUrl}/leaders`));
/*BUTTON LISTENER */
$("#addButton").on("click", async function (event) {
  event.preventDefault();
  loadStatus(await checkInchargeLeader());
  displayTitle(isEdit, "Leader");
  $("#leaderModal").modal("show");
});
$("#saveButton").on("click", function (event) {
  submitForm();
});

/* FUNCTIONS */

async function submitForm() {
  let leader = {
    id: idEl.val(),
    name: nameEl.val(),
    position: positionEl.val(),
    inCharge: inChargeEl.val() === "active" ? true : false,
  };
  console.log(inChargeEl.val());
  console.log(inChargeEl.val() === "active");

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

async function checkInchargeLeader() {
  try {
    const response = await fetch(`${baseUrl}/leaders?inCharge=true`);

    if (!response.ok) {
      throw new Error(`Unexpected error occured, ${response.status}`);
    }

    console.log(response.status === 302);
    return response.status === 302;
  } catch (error) {
    (error) => alert("Error", error.message, "error");
  }
}

function resetLeaderModal() {
  idEl.val("");
  nameEl.val("");
  positionEl.val("");
  loadStatus();
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
