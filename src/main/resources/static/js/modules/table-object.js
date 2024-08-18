import { baseUrl } from "./base-url.js";
import { setActiveButton, newButton, updateButton, deleteButton, historyButton, exportButtons, addButton, printButton,editButton } from "./html-content.js";
import { getLongDate } from "./date.js";

let toolBar = $(`<div></div>`);

function getToolBar(buttons) {
  toolBar.html(buttons);
  return toolBar;
}



export const visitorTableObject = function (url) {
  return {
    responsive: true,
    layout: {
      topStart: getToolBar(`${addButton} ${exportButtons}`),
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
        targets: [0, 1, 2],
      },
      {
        className: "dt-head-center",
        targets: "_all",
      },
    ],
    columns: [
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
          ${updateButton(data)}
          ${deleteButton(data)}
          ${historyButton(data)}
          </div>`;
        },
        width: "15%",
      },
    ],
    processing: true,
  };
};

export const leaderTableObject = function (url) {
  return {
    responsive: true,
    layout: {
      topStart: getToolBar(`${addButton}${exportButtons}`),
    },
    ajax: {
      url: url,
      dataSrc: "",
    },
    columnDefs: [
      {
        className: "dt-head-center",
        targets: "_all",
      },
    ],
    columns: [
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
          ${updateButton(data)}
          ${deleteButton(data)}
          </div>`;
        },
        width: "15%",
      },
    ],
    processing: true,
  };
};

export const appearanceTableObject = function (url) {
  return {
    responsive: true,
    select: {
      style: "multi",
    },
    layout: {
      topStart: getToolBar(`${exportButtons} ${editButton} ${printButton}`),
    },
    ajax: {
      url: url,
      dataSrc: "",
    },
    columnDefs: [
      {
        className: "dt-head-center",
        targets: "_all",
      },
    ],
    columns: [
      {
        data: "dateIssued",
        render: function (data, type, row) {
          return getLongDate(data);
        },
      },
      {
        data: "dateFrom",
        render: function (data, type, row) {
          return getLongDate(data);
        },
      },
      {
        data: "dateTo",
        render: function (data, type, row) {
          return getLongDate(data);
        },
      },
      { data: "purpose.description" },
    ],
  };
};
