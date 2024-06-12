"use strict";

import { showMessage, showModal, hideModal } from "./modules/modal.js";
import { baseUrl } from "./modules/baseUrl.js";

$(document).ready(function () {
  let fullUrl = `${baseUrl}/appearances`;

  new DataTable("#history", {
    ajax: fullUrl,
    columns: [
      { data: "visitor" },
      { data: "dateIssued" },
      { data: "dateFrom" },
      { data: "dateTo" },
      { data: "purpose" },
      { data: "reference" },
    ],
  });
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
