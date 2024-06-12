"use strict";

import { showModalMessage, showModal, hideModal } from "./modules/modal.js";
import { baseUrl } from "./modules/baseUrl.js";
import showMessage from "./modules/message-holder.js";

let fullUrl = `${baseUrl}/visitors`;

showModal("addVisitorButton", "addVisitorModal");

const renderDataTable = function () {
  return new DataTable("#visitors", {
    ajax: {
      url: fullUrl,
      dataSrc: "",
    },
    columns: [
      { data: "id" },
      { data: "name" },
      { data: "position.title" },
      { data: "agency.name" },
      {
        data: "address",
        render: function (data, type, row) {
          return `${
            data.barangay == undefined ? "" : data.barangay.name + ","
          } ${data.municipality.name}, ${data.province.name}`;
        },
      },
      {
        data: "id",
        render: function (data, type, row) {
          return `<div>
            <a type = "button" class = "btn-new btn btn-sm btn-success"
        id = "${data}"><span class = "label-new">New</span>
        </a>
        <a type = "button" class = "btn-edit btn btn-sm btn-warning"
        id = "${data}"><span class="material-symbols-outlined">edit</span>
        </a>
        <a type = "button" class = "btn-delete btn btn-sm btn-danger"
        id = "${data}"><span class="material-symbols-outlined">delete</span>
        </a></div>`;
        },
      },
    ],
    processing: true,
  });
};

renderDataTable();

const getVisitor = function () {
  let visitor = {
    name: $("#name").val(),
    position: {
      title: $("#position").val(),
    },
    agency: {
      name: $("#agency").val(),
    },
    address: {
      barangay: $("#barangay-text").val()
        ? {
            code: $("#barangay-text").attr("code"),
            name: $("#barangay-text").val(),
          }
        : null,
      municipality: {
        code: $("#city-text").attr("code"),
        name: $("#city-text").val(),
      },
      province: {
        code: $("#province-text").attr("code"),
        name: $("#province-text").val(),
      },
      region: {
        code: $("#region-text").attr("code"),
        name: $("#region-text").val(),
      },
    },
  };
  return visitor;
};

// function submitForm() {
//   let visitor = {};

//   visitor["name"] = $("#name").val();
//   visitor["position"] = {};
//   visitor["position"]["title"] = $("#position").val();

//   visitor["agency"] = {};
//   visitor["agency"]["name"] = $("#agency").val();

//   visitor["address"] = {};
//   visitor["address"]["barangay"] =
//     $("#barangay").val() === "Choose barangay"
//       ? null
//       : {
//           code: $("#barangay-text").attr("code"),
//           name: $("#barangay-text").val(),
//         };

//   visitor["address"]["municipality"] = {
//     code: $("#city-text").attr("code"),
//     name: $("#city-text").val(),
//   };

//   visitor["address"]["province"] = {
//     code: $("#province-text").attr("code"),
//     name: $("#province-text").val(),
//   };

//   visitor["address"]["region"] = {
//     code: $("#region-text").attr("code"),
//     name: $("#region-text").val(),
//   };

//   submitFormToServer(visitor);
// }

// $("#region").on("click", function () {
//   console.log($("#region-text").attr("code"));
// });

function submitFormToServer(visitor) {
  let body = $(document.body);

  let url = `${baseUrl}/visitors`;

  $.ajax({
    type: "POST",
    contentType: "application/json",
    url: url,
    data: JSON.stringify(visitor),
    dataType: "json",
    success: function () {
      body.insertAdjacentHTML("afterbegin", showMessage("Success"));
      $("#messageHolder").removeClass("alert-danger").addClass("alert-success");
      setTimeout(function () {
        window.location.href = url;
      }, 2000);
    },
    error: function (e) {
      body.insertAdjacentHTML("afterbegin", showMessage(e));
      $("#messageHolder").removeClass("alert-success").addClass("alert-danger");
    },
  });
}

// async function submitVisitor(visitor) {
//   let fullUrl = `${baseUrl}/visitors`;

//   try {
//     const response = await fetch(fullUrl, {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//       },

//       body: JSON.stringify(visitor),
//     });

//     const result = await response.json();

//   } catch (error) {

//   }
// }

$("#btnAdd").on("click", function () {
  submitFormToServer(getVisitor());
});
