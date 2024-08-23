"use strict";

import { baseUrl } from "./modules/base-url.js";
import { appearanceHistoryTableObject, leaderNamesTableObject } from "./modules/table-object.js";
import { loadAddress } from "./ph-address-selector.js";
import { addressContent, errorContent, displayTitle, displayCurrentLeader, settingsButton } from "./modules/html-content.js";
import { toast, alert, openConfirmDialog } from "./modules/popups.js";

const dataTable = await $("#appearance").DataTable(appearanceHistoryTableObject(`${baseUrl}/appearances/DESC`));
const leaderNameDataTable = $("#leaderNames").DataTable(leaderNamesTableObject(`${baseUrl}/leaders/names`));
// $("#container").css("display", "block");
// leaderNameDataTable.columns.adjust().draw();

const idEl = $("#id");
const addressContainer = $("#address");
const courtesyTitleEl = $("#courtesyTitle");
const firstNameEl = $("#firstName");
const middleInitEl = $("#middleInitial");
const lastNameEl = $("#lastName");
const positionEl = $("#position");
const agencyEl = $("#agency");

const leaderNameSection = $(".leader-name-div");
const settingsButtonSection = $(".leader-section");
const entityType = "Visitor";

const leader = await checkInchargeLeader();
leaderNameSection.append(displayCurrentLeader(leader));

if (leader !== null) {
  settingsButtonSection.append(settingsButton);
}

/*BUTTON LISTENER */
$("#addButton").on("click", function (event) {
  event.preventDefault();
  displayTitle(false, entityType);
  loadAddress(null);
  $(addressContent).prependTo(addressContainer);
  $("#visitorModal").modal("show");
});

$("#closeModalButton").on("click", function (event) {
  event.preventDefault();
  resetVisitorModal();
});

$("#saveButton").on("click", async function (event) {
  event.preventDefault();
  await getInputs();
});

$("#changeLeaderButton").on("click", function (event) {
  event.preventDefault();
  if ($("#leaderModal h5.modal-title").length === 0) {
    const modalHeader = $("#leaderModal div.modal-header");
    $(`<h5 class="modal-title text-light">Change ${entityType}</h5>`).prependTo(modalHeader);
  }
  $("#leaderModal").modal("show");
});

leaderNameDataTable.on("select deselect", function (event) {
  event.preventDefault();
  $("#selectButton").prop("disabled", !leaderNameDataTable.row({ selected: true }).count());
});

$("#selectButton").on("click", async function (event) {
  try {
    const currentLeader = await checkInchargeLeader();
    const selectedLeader = leaderNameDataTable.row({ selected: true }).data();

    await assignLeader(currentLeader.id, false);
    await assignLeader(selectedLeader.id, true);
    alert("Changed!", `${selectedLeader.name} assigned as Leader!`, "success");
    $(".leader-name-div span").text(selectedLeader.name);
    $("#leaderModal").modal("hide");
    leaderNameDataTable.row({ selected: true }).deselect();
  } catch (error) {
    alert("Error", error.message, "error");
  }
});

/* FUNCTIONS */

async function checkInchargeLeader() {
  const response = await fetch(`${baseUrl}/leaders?inCharge=true`);
  if (response.status === 302) {
    return response.json();
  }
  return null;
}

async function getInputs() {
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

  await submitFormToServer(visitor);
}

async function submitFormToServer(visitor) {
  const fullUrl = `${baseUrl}/visitors}`;
  await fetch(fullUrl, {
    method: "POST",
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
        title: `${fullName}  has been saved!`,
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
  $("h5.modal-title").remove();
}

function emptyAddressContainer() {
  if (addressContainer.length > 0) {
    addressContainer.empty();
  }
}
