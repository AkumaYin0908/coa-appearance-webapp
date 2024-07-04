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
let url = null;
let visitorId = visitor.id;
console.log(appearanceType);
//displaying visitor details
$(visitorDetails(visitor)).prependTo(visitorDetailContainer);

if(appearanceType === "single"){
   inputSection.append(appearanceButtonContainer);
}

datePicker();

$("#proceedButton").on("click", function (event) {
  event.preventDefault();
  if (isSingle) {
    submitForm();
  } else {
    if (appearances.length == 0) {
      alert("Error", "No appearance has been added yet!", "error");
    } else {
      url = `${baseUrl}/visitors/${visitorId}/appearances/consolidated`;
      submitFormToServer(appearances, url);
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
  if (appearances.length == 0 ) {
    $(".button-container").remove();
  }
});

// $("#appearanceForm").submit(function(event){
//    event.preventDefault();
// });
 


//displaying appearance details through modal/alert
function showAppearanceDetail(appearance) {
  Swal.fire({
    title: `Check the details before ${isSingle ? "printing" : "adding"}!`,
    html: appearanceDetails(appearance),
    showCancelButton: true,
    confirmButtonText: isSingle ? "Print" : "Add",
  })
    .then((result) => {
      if (result.isConfirmed) {
        if (isSingle) {
          url = `${baseUrl}/visitors/${visitorId}/appearances`;
          submitFormToServer(appearance,url);
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
    })
    .catch((error) => {
      alert("Error", error.message, "error");
    });
}

//transforming inputted data into JSON object and passing it to showAppearanceDetailModal() method
function submitForm() {
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

  showAppearanceDetail(appearance);
}

//saving objects to database
function submitFormToServer(object, url) {
  fetch(url, {
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
      toast.fire({
        icon: "success",
        title: `New appearance${appearances.length > 0 ? "s" : ""} for ${data.visitor.name} has been saved!`,
      });
    });
}

$(".btn-add").on("click", function (event) {
  event.preventDefault();
  submitForm();
  console.log(appearances);
});
