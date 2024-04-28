"use strict";

import { showMessage, showModal, hideModal } from "./modules/modal.js";

$(document).ready(function () {
  $("#visitorButton").addClass("active");

  showMessage("addModalMessageHolder", "addVisitorModal");

  showMessage("editModalMessageHolder", "editVisitorModal");

  $(".btn-delete").on("click", function (event) {
    event.preventDefault();
    let link = $(this);

    let visitorName = link.attr("visitorName");
    $("#btnYes").attr("href", link.attr("href"));
    $("#confirmText").html(
      "Do you want to delete <strong>" + visitorName + "</strong>?"
    );
    $("#deleteVisitorModal").modal("show");
  });

  showModal("addVisitorButton", "addVisitorModal");

  const clearAddModal = function () {
    $("#visitorId").val("");
    $("#visitorName").val("");
    $("#visitorPosition").val("");
    $("#visitorAgency").val("");
  };

  hideModal("addModalCloseButton", "addModalDiv", clearAddModal);
  hideModal("editModalCloseButton", "editModalDiv");

  $(".btn-edit").on("click", function (event) {
    event.preventDefault();
    let visitor = $(this);

    $("#editVisitorId").val(visitor.attr("visitorId"));
    $("#editVisitorName").val(visitor.attr("visitorName"));
    $("#editVisitorPosition").val(visitor.attr("visitorPosition"));
    $("#editVisitorAgency").val(visitor.attr("visitorAgency"));

    $("#editVisitorModal").modal("show");
  });

  $("#btnClear").on("click", function (event) {
    event.preventDefault();
    $("#searchName").text("");

    window.location.href = "/visitors";
  });

  $("#pageSize").on("change", function (event) {
    event.preventDefault();
    $("#searchForm").submit();
  });
});
