"use strict";
//import { datePickerSetting } from "./modules/date.js";
import { datePicker } from "./modules/datepicker.js";
import { visitorDetails, deleteButton, appearanceButtonContainer } from "./modules/htmlContent.js";
import { baseUrl } from "./modules/baseUrl.js";
import { appearanceDetails } from "./modules/htmlContent.js";
import { toast, alert } from "./modules/alerts.js";

const visitorDetailContainer = $(".visitor-details");
const dateIssued = $("#dateIssued");
const dateFrom = $("#dateFrom");
const dateTo = $("#dateTo");
const purpose = $("#purpose");
const reference = $("#reference");
const inputSection = $(".input-section");
const appearances = [];
const isSingle = appearanceType === "single";
const visitorId = visitor.id;
const postUrl = `${baseUrl}/visitors/${visitorId}/appearances`;
const appearanceTypeInput = $("#appearanceType");

appearanceTypeInput.val(appearanceType);

//displaying visitor details
$(visitorDetails(visitor)).prependTo(visitorDetailContainer);

if (appearanceType === "single") {
  inputSection.append(appearanceButtonContainer);
}

datePicker();

$("#proceedButton").on("click", function (event) {
  event.preventDefault();
  if (isSingle) {
    showAppearanceDetail(getInputs());
  } else {
    if (appearances.length == 0) {
      alert("Error", "No appearance has been added yet!", "error");
    } else {
      submitFormToServer(`${postUrl}?appearanceType=consolidated`, appearances);
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
  if (appearances.length == 0) {
    $(".button-container").remove();
  }
});

// $("#appearanceForm").submit(function(event){
//    event.preventDefault();
// });

//displaying appearance details through modal/alert
async function showAppearanceDetail(appearance) {
  const result = Swal.fire({
    title: `Check the details before ${isSingle ? "printing" : "adding"}!`,
    html: appearanceDetails(appearance),
    showCancelButton: true,
    confirmButtonText: isSingle ? "Print" : "Add",
  });
  try {
    if (result.isConfirmed) {
      if (isSingle) {
        appearances.push(appearance);
        await submitFormToServer(`${postUrl}?appearanceType=single`, appearances);
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
        $("#appearances tbody").append(row);
        appearances.push(appearance);

        if (!$(".button-container").length) {
          inputSection.append(appearanceButtonContainer);
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
    dateIssued: dateIssued.val(),
    dateFrom: dateFrom.val(),
    dateTo: dateTo.val(),
    purpose: {
      description: purpose.val(),
    },
    reference: reference.val() != "" ? reference.val() : "N/A",
  };

  return appearance;
}

//saving objects to database
async function submitFormToServer(url, object) {
  await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(object),
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
      console.log(data);
      toast.fire({
        icon: "success",
        title: `New appearance${data.length > 1 ? "s" : ""} for ${data[0].visitor.name} has been saved!`,
      });
    });
}

$(".btn-add").on("click", function (event) {
  event.preventDefault();
  showAppearanceDetail(getInputs());
  console.log(appearances);
});
