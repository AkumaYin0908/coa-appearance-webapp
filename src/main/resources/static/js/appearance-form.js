"use strict";
import { datePickerSetting } from "./modules/date.js";
import { visitorDetails } from "./modules/htmlContent.js";
import { baseUrl } from "./modules/baseUrl.js";
import { appearanceDetails } from "./modules/htmlContent.js";
import { toast } from "./modules/alerts.js";

const visitorDetailContainer = $(".visitor-details");
const dateIssued = $("#dateIssued");
const dateFrom = $("#dateFrom");
const dateTo = $("#dateTo");
const purpose = $("#purpose");
const reference = $("#reference");
const appearances = [];
const isSingle = appearanceType === "single";
let url = null;
let visitorId = visitor.id;

//displaying visitor details
$(visitorDetails(visitor)).prependTo(visitorDetailContainer);



$("#proceedButton").on("click", function (event) {
  event.preventDefault();
  if (isSingle) {
    submitForm();
  } else {
    if (appearances.length == 0) {
      Swal.fire({
        title: "Error",
        text: "No appearance has been added yet!",
        icon: "error",
      });
    } else {
      url= `${baseUrl}/visitors/${visitorId}/appearances/consolidated`;
      submitFormToServer(appearances,url);
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
});


//displaying appearance details through modal/alert
function showAppearanceDetailModal(appearance) {
  Swal.fire({
    title: `Check the details before ${isSingle ? "printing" : "adding"}!`,
    html: appearanceDetails(appearance),
    showCancelButton: true,
    confirmButtonText: isSingle ? "Print" : "Add",
  })
    .then((result) => {
      if (result.isConfirmed) {
        if (isSingle) {
          
          url= `${baseUrl}/visitors/${visitorId}/appearances`;
          submitFormToServer(appearance);
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
                      <td><a type ="button" class = "btn-delete btn btn-sm btn-danger"><span class="material-symbols-outlined">delete</span></a>`;
          $("#appearances tbody").append(row);
          appearances.push(appearance);
        }
      }
    })
    .catch((error) => {
      Swal.fire({
        title: "Error",
        text: error.message,
        icon: "error",
      });
    });
}

//for datepicker (dateIssued,dateFrom,dateTo)
dateIssued
  .datepicker(
    $.extend({
      defaultDate: new Date(),
      altFormat: "yy-mm-dd",
      dateFormat: "MM dd, yy",
      changeMonth: true,
      changeYear: true,
      numberOfMonths: 1,
    })
  )
  .datepicker("setDate", new Date());

$(function () {
  let dateFormat = "MM dd, yy",
    dateFrom = $("#dateFrom")
      .datepicker(datePickerSetting)
      .datepicker("setDate", $("#dateIssued").datepicker("getDate"))
      .on("change", function () {
        dateTo.datepicker("option", "minDate", getDate(this));
      }),
    dateTo = $("#dateTo").datepicker(datePickerSetting).datepicker("setDate", dateFrom.datepicker("getDate"));

  function getDate(element) {
    let date;
    try {
      date = $.datepicker.parseDate(dateFormat, element.value);
    } catch (error) {
      date = null;
    }
    return date;
  }
});


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

  showAppearanceDetailModal(appearance);
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
        title: `New appearance for ${data.visitor.name} has been saved!`,
      });
    });
}

$(".btn-add").on("click", function (event) {
  event.preventDefault();
  submitForm();
  console.log(appearances);
});
