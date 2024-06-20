"use strict";
import { datePickerSetting } from "./modules/date.js";
import { visitorId } from "./visitor.js";

$(document).ready(function () {
  $("#dateIssued")
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
      dateTo = $("#dateTo")
        .datepicker(datePickerSetting)
        .datepicker("setDate", dateFrom.datepicker("getDate"));

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

  // $("#appearanceDetailsButton").on("click", function (event) {
  //   event.preventDefault();
  //   let dateIssued = $("#dateIssued").val();
  //   let dateFrom = $("#dateFrom").val();
  //   let dateTo = $("#dateTo").val();
  //   let purpose = $("#purposeTextArea").val();

  //   $("#dateIssuedDetail").text(dateIssued);
  //   $("#dateFromDetail").text(dateFrom);
  //   $("#dateToDetail").text(dateTo);
  //   $("#purposeDetail").text(purpose);

  //   $("#appearanceDetailsModal").modal("show");
  // });
});
