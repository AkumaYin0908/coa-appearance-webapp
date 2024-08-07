import { datePickerSetting } from "./date.js";
const dateIssued = $("#dateIssued");

export const datePicker = function(){
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

}