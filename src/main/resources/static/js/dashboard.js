"use strict";

import { showMessage, showModal, hideModal } from "./modules/modal.js";

$(document).ready(function () {
  $("#dashboardButton").addClass("active");

  showMessage("addModalMessageHolder", "addVisitorModal");

  showMessage("changeModalMessageHolder", "changeLeaderModal");

  showModal("changeLeaderButton", "changeLeaderModal");

  const clearChangeModal = function () {
    $("#currentLeaderName").val("");
    $("#leaderName").val("");
  };

  hideModal("changeModalCloseButton", "changeModalDiv", clearChangeModal);

  showModal("addVisitorButton", "addVisitorModal");

  const clearAddModal = function () {
    $("#visitorId").val("");
    $("#visitorName").val("");
    $("#visitorPosition").val("");
    $("#visitorAgency").val("");
  };

  hideModal("addModalCloseButton", "addModalDiv", clearAddModal);
});
