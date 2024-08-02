"use strict";
//import { datePickerSetting } from "./modules/date.js";
import { datePicker } from "./modules/datepicker.js";
import { visitorDetails, deleteButton, appearanceButtonContainer } from "./modules/htmlContent.js";
import { baseUrl } from "./modules/baseUrl.js";
import { appearanceDetails } from "./modules/htmlContent.js";
import { toast, alert } from "./modules/alerts.js";

const visitorDetailContainer = $(".visitor-details");
const dateIssuedEl = $("#dateIssued");
const dateFromEl = $("#dateFrom");
const dateToEl = $("#dateTo");
const purposeEl = $("#purpose");
const referenceEl = $("#reference");
const inputSection = $(".input-section");
const appearanceTable = $("#appearances tbody");
const appearances = [];
const isSingle = appearanceType === "single";
const visitorId = visitor.id;
const url = `${baseUrl}/visitors/${visitorId}/appearances`;
const postUrls = [`${url}?appearanceType=single`, `${url}?appearanceType=consolidated`];
const fullName = `${visitor.firstName}${visitor.middleInitial === "N/A" ? " " : visitor.middleInitial}${visitor.lastName}`;
//const appearanceTypeInput = $("#appearanceType");

$(appearanceButtonContainer).appendTo(inputSection);

if (appearanceType === "single") {
  $(".button-container").toggleClass("hide");
}

//displaying visitor details
$(visitorDetails(visitor)).prependTo(visitorDetailContainer);

datePicker();

$("#proceedButton").on("click", async function (event) {
  event.preventDefault();
  if (isSingle) {
    showAppearanceDetail(getInputs());
  } else {
    if (appearances.length == 0) {
      alert("Error", "No appearance has been added yet!", "error");
    } else {
      await submitFormToServer(postUrls[1], appearances);
    }
  }
});

$("#appearances").on("click", "a.btn-delete", function (event) {
  const id = $(this).parent().parent().attr("id");
  const index = appearances.findIndex((appearance) => appearance.dateFrom === id);

  if (index !== -1) {
    appearances.splice(index, 1);
    $(this).parent().parent().remove();
  }

  console.log("Index of the deleted row: ", index);
  console.log(appearances);
  if (appearances.length === 0) {
    $(".button-container").toggleClass("hide");
  }
});

// $("#appearanceForm").submit(function(event){
//    event.preventDefault();
// });

async function checkAppearanceIfAlreadyExist() {
  const response = await fetch(`${url}?dateFrom=${dateFromEl.val()}`);

  if (response.status === 302) {
    throw new Error(`Appearance issued on ${dateFromEl.val()} already exist!`);
  } else if (!response.ok) {
    throw new Error(`Unexpected error occured, ${response.status}`);
  }
}

//displaying appearance details through modal/alert
async function showAppearanceDetail(appearance) {
  try {
    const result = await Swal.fire({
      title: `Check the details before ${isSingle ? "printing" : "adding"}!`,
      html: appearanceDetails(appearance),
      showCancelButton: true,
      confirmButtonText: isSingle ? "Print" : "Add",
    });

    if (result.isConfirmed) {
      await checkAppearanceIfAlreadyExist();
      validateDates();

      if (isSingle) {
        appearances.push(appearance);
        await submitFormToServer(postUrls[0], appearances);
      } else {
        let reference = appearance.reference;
        let dateFrom = appearance.dateFrom;
        let dateTo = appearance.dateTo;
        let purpose = appearance.purpose.description;

        let row = `<tr id = "${dateFrom}">;
                    <td>${reference}</td>
                    <td>${dateFrom}</td>
                    <td>${dateTo}</td>
                    <td>${purpose}</td>
                    <td>${deleteButton(dateFrom)}`;
        appearanceTable.append(row);
        appearances.push(appearance);

        if ($(".button-container").addClass("hide").length) {
          $(".button-container").toggleClass("hide");
        }
      }
    }
  } catch (error) {
    alert("Error", error.message, "error");
  }
}

//transforming inputted data into JSON object
function getInputs() {
  let appearance = {
    visitor: visitor,
    dateIssued: dateIssuedEl.val(),
    dateFrom: dateFromEl.val(),
    dateTo: dateToEl.val(),
    purpose: {
      description: purposeEl.val(),
    },
    reference: referenceEl.val() != "" ? referenceEl.val() : "N/A",
  };

  return appearance;
}

async function showCertificate(appearanceType, templateNo, object) {
  try {
    const response = await fetch(`${baseUrl}/${appearanceType}-certificate/${templateNo}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(object),
    });

    if (!response.ok) {
      return response.json().then((data) => {
        throw new Error(data.message);
      });
    }
  } catch (error) {
    alert("Error", error.message, "error");
  }
}

//saving objects to database
async function submitFormToServer(url, object) {
  const response = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(object),
  });

  await showCertificate(
    appearanceType,
    1,
    response.json().then((data) => {
      appearanceType === "single" ? data[0] : data;
    }),
  );

  if (!response.ok) {
    return response.json().then((data) => {
      throw new Error(data.message);
    });
  } else {
    const data = response.json();
    toast.fire({
      icon: "success",
      title: `New appearance${data.length > 1 ? "s" : ""} for ${fullName} has been saved!`,
    });

    clearFields();
  }
}

$(".btn-add").on("click", function (event) {
  event.preventDefault();
  showAppearanceDetail(getInputs());
  console.log(appearances);
});

function validateDates() {
  let dateIssued = new Date(dateIssuedEl.val());
  let dateFrom = new Date(dateFromEl.val());
  let dateTo = new Date(dateToEl.val());

  if (dateIssued < dateFrom || dateIssued < dateTo) {
    throw new Error(`Date inputted in "From" field should be earlier than or equal to  ${dateIssuedEl.val()}`);
  } else if (dateFrom > dateIssued) {
    throw new Error(`Date inputted in "From" field should be earlier or equal to the date inputted in "To" field!`);
  }
}

function clearFields() {
  purposeEl.val("");
  referenceEl.val("");
  datePicker();
  appearances.length = 0;
  console.log(appearanceTable.length);
  if (appearanceTable.length > 1) {
    appearanceTable.empty();
    $(".button-container").toggleClass("hide");
  }
}
