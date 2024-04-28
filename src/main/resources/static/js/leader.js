"use strict";

import { showMessage, hideModal } from "./modules/modal.js";

$(document).ready(function () {
  $("#leaderButton").addClass("active");

  showMessage("addModalMessageHolder", "addLeaderModal");

  showMessage("editModalMessageHolder", "editLeaderModal");

  $(".btn-delete").on("click", function (event) {
    event.preventDefault();
    let link = $(this);

    let leaderName = link.attr("leaderName");
    $("#btnYes").attr("href", link.attr("href"));
    $("#confirmText").html(
      "Do you want to delete <strong>" + leaderName + "</strong>?"
    );
    $("#deleteLeaderModal").modal("show");
  });

  $("#addLeaderButton").on("click", function (event) {
    event.preventDefault();
    $("#leaderInCharge").prop("checked", true);

    $("#addLeaderModal").modal("show");
  });

  const clearAddModal = function () {
    $("#leaderId").val("");
    $("#leaderName").val("");
    $("#leaderPosition").val("");
  };

  hideModal("addModalCloseButton", "addModalDiv", clearAddModal);

  hideModal("editModalCloseButton", "editModalDiv");

  $(".btn-edit").on("click", function (event) {
    event.preventDefault();

    let leader = $(this);

    $("#editLeaderId").val(leader.attr("leaderId"));
    $("#editLeaderName").val(leader.attr("leaderName"));
    $("#editLeaderPosition").val(leader.attr("leaderPosition"));

    let inCharge = leader.attr("leaderInCharge") === "true";
    $("#editLeaderInCharge").prop("checked", inCharge);

    $("#editLeaderModal").modal("show");
  });

  $("#btnClear").on("click", function (event) {
    event.preventDefault();
    $("#searchName").text("");

    window.location.href = "/settings/leaders";
  });

  $("#pageSize").on("change", function (event) {
    event.preventDefault();
    $("#searchForm").submit();
  });
});
