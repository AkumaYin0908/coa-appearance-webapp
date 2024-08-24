import { baseUrl } from "./base-url.js";
import { setActiveButton, newButton, updateButton, deleteButton, historyButton, exportButtons, addButton, printButton, editButton, removeButton } from "./html-content.js";
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
          return `${row.agency === null ? "N/A" : data.name}`;
        },
      },
      {
        data: "address",
        render: function (data, type, row) {
          return row.address === null ? "N/A" : `${data.barangay == null ? "" : data.barangay.name + ","} ${data.municipality.name}, ${data.province.name}`;
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
      topStart: getToolBar(`${addButton} ${exportButtons}`),
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
      topStart: getToolBar(`${exportButtons} ${editButton} ${printButton} ${removeButton}`),
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

export const appearanceHistoryTableObject = function (url) {
  return {
    pageLength: 10,
    responsive: true,
    ordering: false,
    layout: {
      topStart: null,
      topEnd: null,
      bottomStart: null,
    },
    ajax: {
      url: url,
      dataSrc: "",
    },

    columnDefs: [
      {
        visible: false,
        targets: [1],
      },
    ],
    columns: [
      {
        data: "dateIssued",
        render: function (data, type, row) {
          return getLongDate(data);
        },
      },
      { data: "visitor" },
      {
        data: null,
        render: function (data, type, row) {
          const visitor = row.visitor;
          return `${visitor.courtesyTitle.title === "Mr" || visitor.courtesyTitle.title === "Ms" ? "" : visitor.courtesyTitle.title + "."} ${visitor.firstName} ${visitor.middleInitial === "N/A" ? "" : visitor.middleInitial + " "}${visitor.lastName}`;
        },
        width: "15%",
      },
      {
        data: null,
        render: function (data, type, row) {
          const visitor = row.visitor;
          const address = visitor.address;
          const fullAddress = `${address.barangay == null ? "" : address.barangay.name + ","} ${address.municipality.name}, ${address.province.name}`;
          const agency = visitor.agency;

          return `${agency == null ? fullAddress : agency.name}`;
        },
      },
      { data: "formattedDateRange" },
      { data: "purpose.description" },
    ],
  };
};

export const leaderNamesTableObject = function (url) {
  return {
    responsive: true,
    paging: false,
    ordering: false,
    scrollY: 300,
    scrollX: true,
    scroller: true,
    select: true,
    layout: {
      topStart: null,
      topEnd: null,
      bottomStart: null,
      bottomEnd: null,
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
        data: "name",
      },
    ],
  };
};
