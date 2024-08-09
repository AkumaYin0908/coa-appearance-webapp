import { baseUrl } from "./base-url.js";
import { setActiveButton, newButton, editButton, deleteButton } from "./html-content.js";

let toolBar = $("<div></div>");
toolBar.html(`<button type="button" id="addButton" class="btn btn-sm btn-success">Add</button>
<button type="button" id="exportToPdf" class="btn btn-sm btn-success">PDF</button>
<button type="button" id="exportToExcel" class="btn btn-sm btn-success">Excel</button>
<button type="button" id="addVisitorButton" class="btn btn-sm btn-success">Print</button>`);

export const visitorTableObject = function (url) {
  return {
    responsive: true,
    layout: {
      topStart: toolBar,
    },
    //   language: {
    //     lengthMenu: "Show _MENU_ entries",
    //   },
    ajax: {
      url: url,
      dataSrc: "",
    },
    columnDefs: [
      {
        visible: false,
        targets: [0, 1, 2, 3],
      },
    ],
    columns: [
      { data: "id" },
      { data: "firstName" },
      { data: "middleInitial" },
      { data: "lastName" },
      {
        data: null,
        render: function (data, type, row, meta) {
          return `${row.firstName} ${row.middleInitial === "N/A" ? "" : row.middleInitial + " "}${row.lastName}`;
        },
        width: "15%",
      },
      { data: "position.title" },
      {
        data: "agency",
        render: function (data, type, row) {
          return `${row.agency == null ? "N/A" : data.name}`;
        },
      },
      {
        data: "address",
        render: function (data, type, row) {
          return `${data.barangay == null ? "" : data.barangay.name + ","} ${data.municipality.name}, ${data.province.name}`;
        },
      },
      {
        data: "id",
        render: function (data, type, row) {
          return `<div id = "actionButton">
          ${newButton(data)}
          ${editButton(data)}
          ${deleteButton(data)}
          </div>`;
        },
        width: "12%",
      },
    ],
    processing: true,
  };
};

export const leaderTableObject = function (url) {
  return {
    responsive: true,
    layout: {
      topStart: toolBar,
    },
    ajax: {
      url: url,
      dataSrc: "",
    },
    columns: [
      { data: "id" },
      { data: "name" },
      { data: "position" },
      {
        data: "inCharge",
        render: function (data, type, row) {
          return `${data ? "Active" : "Inactive"}`;
        },
      },
      {
        data: "id",
        render: function (data, type, row) {
          return `<div id = "actionButton">
          ${setActiveButton(data, row)}
          ${editButton(data)}
          ${deleteButton(data)}
          </div>`;
        },
        width: "15%",
      },
    ],
    processing: true,
  };
};
