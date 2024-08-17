"use strict";

import { baseUrl } from "./modules/base-url.js";
import { appearanceTableObject } from "./modules/table-object.js";
import { visitorDetails } from "./modules/html-content.js";

const visitorDetailContainer = $(".visitor-details");
const dataTable = await $("#appearances").DataTable(appearanceTableObject(`${baseUrl}/visitors/${visitor.id}/appearances`));


$(visitorDetails(visitor)).prependTo(visitorDetailContainer);

// import { showMessage, hideModal } from "./modules/modal.js";
// import { datePickerSetting } from "./modules/date.js";

// $(document).ready(function () {
//   showMessage("editModalMessageHolder", "editVisitorModal");

//   $(".btn-delete").on("click", function (event) {
//     event.preventDefault();
//     link = $(this);

//     visitorName = link.attr("visitorName");
//     $("#btnYes").attr("href", link.attr("href"));
//     $("#confirmText").html(
//       "Do you want to delete <strong>" + visitorName + "</strong>?"
//     );
//     $("#deleteVisitorModal").modal("show");
//   });

//   hideModal("editModalCloseButton", "editModalDiv");

//   $(".btn-edit").on("click", function (event) {
//     event.preventDefault();
//     let appearance = $(this);

//     $("#editAppearanceId").val(appearance.attr("appearanceId"));
//     $("#editAppearanceDateIssued").val(appearance.attr("appearanceDateIssued"));
//     $("#editAppearanceDateFrom").val(appearance.attr("appearanceDateFrom"));
//     $("#editAppearanceDateTo").val(appearance.attr("appearanceDateTo"));
//     $("#editAppearancePurpose").val(appearance.attr("appearancePurpose"));

//     $("#editAppearanceModal").modal("show");
//   });

//   $("#editAppearanceDateIssued").datepicker(
//     $.extend({
//       altFormat: "yy-mm-dd",
//       dateFormat: "MM dd, yy",
//     })
//   );

//   $(function () {
//     let dateFormat = "MM dd, yy",
//       dateFrom = $("#editAppearanceDateFrom")
//         .datepicker(datePickerSetting)
//         .on("change", function () {
//           dateTo.datepicker("option", "minDate", getDate(this));
//         }),
//       dateTo = $("#editAppearanceDateTo").datepicker(datePickerSetting);

//     function getDate(element) {
//       let date;
//       try {
//         date = $.datepicker.parseDate(dateFormat, element.value);
//       } catch (error) {
//         date = null;
//       }
//       return date;
//     }
//   });

//   $("#btnClear").on("click", function (event) {
//     event.preventDefault();

//     $("#searchPurpose").text("");

//     $("#month").text("Select Month");

//     $("#year").text("Select Year");

//     visitorId = $(this).attr("visitorId");

//     window.location.href = "/appearances/" + visitorId + "/appearance-history";
//   });

//   $("#pageSize").on("change", function (event) {
//     event.preventDefault();
//     $("#searchForm").submit();
//   });

//   $("#month").on("change", function (event) {
//     event.preventDefault();
//     $("#searchForm").submit();
//   });

//   $("#year").on("change", function (event) {
//     event.preventDefault();
//     $("#searchForm").submit();
//   });

//   let checkPrint = $(".chk-print");

//   checkPrint.on("click", function () {
//     if ($(this).is(":checked")) {
//       $("#btnPrintChecked").show();
//       console.log(checkPrint.val());
//     } else {
//       $("#btnPrintChecked").hide();
//     }
//   });

//   $("#btnPrintChecked").on("click", function (event) {
//     $("#appearances").submit();
//   });
// });
