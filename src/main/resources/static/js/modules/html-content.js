import { baseUrl } from "./base-url.js";

export const addressContent = ` <div class="row text-on-input mt-3 m-1 border pt-3">
<label class="text-secondary border-label">Address</label>
<div class="col mb-3">
  <select name="region" class="form-control" id="region"></select>
  <input type="hidden" class="form-control" name="region_text" id="region-text" required placeholder="Region" />
</div>
<div class="col mb-3">
  <select name="province" class="form-control" id="province"></select>
  <input type="hidden" class="form-control" name="province_text" id="province-text" required placeholder="Province" />
</div>
<div class="col mb-3">
  <select name="city" class="form-control" id="city"></select>
  <input type="hidden" class="form-control" name="city_text" id="city-text" required placeholder="City" />
</div>
<div class="col mb-3">
  <select name="barangay" class="form-control" id="barangay"></select>
  <input type="hidden" class="form-control" name="barangay_text" id="barangay-text" placeholder="Barangay(ignore if not applicable)" />
</div>
</div>`;

export const newButton = function (data) {
  return ` <a data-key = ${data} class = "btn-new btn btn-sm btn-success"><span class = "label-new">New</span>
        </a>`;
};
export const updateButton = function (data) {
  return `<a data-key = ${data} class = "btn-edit btn btn-sm btn-warning"><span class="material-symbols-outlined">edit</span>
        </a>`;
};

export const deleteButton = function (data) {
  return ` <a data-key = ${data} class = "btn-delete btn btn-sm btn-danger"><span class="material-symbols-outlined">delete</span>
        </a>`;
};

// export const printButton = function (data) {
//   return ` <a data-key = ${data} class = "btn-print btn btn-sm btn-success"><span class="material-symbols-outlined">print</span>
//         </a>`;
// };

export const historyButton = function (data) {
  return ` <a data-key = ${data} class = "btn-history btn btn-sm btn-info"><span class="material-symbols-outlined">history</span>
        </a>`;
};

export const setActiveButton = function (data, row) {
  return ` <a data-key = ${data} class = "btn-assign btn btn-sm btn-${row.inCharge ? "danger" : "success"}"><span class = "label-new">${row.inCharge ? "Revoke" : "Assign"}</span>
  </a>`;
};

export const exportButtons = `<button type="button" id="exportToPdf" class="btn btn-sm btn-success">PDF</button>
<button type="button" id="exportToExcel" class="btn btn-sm btn-success">Excel</button>`;

export const printButton = `<button type="button" id="printButton" class="btn btn-sm btn-success" disabled>Print</button>`;

export const editButton = `<button type="button" id="editButton" class="btn btn-sm btn-success" disabled>Edit</button>`;

export const addButton = `<button type="button" id="addButton" class="btn btn-sm btn-success">Add</button>`;

export const errorContent = function (error) {
  return `<div id = "errorContainer" class="col-xs-auto">
                    <div class="alert alert-danger col-xs-auto">
                      ${error}
                    </div>
                </div>`;
};

export const appearanceDetails = function (appearance) {
  return `<div id ="appearanceDetails" >
      <table class = "table table-bordered border-primary">
        <tbody>
          <tr>
            <td class = "col-1 text-start">Travel Order</td>
            <td class = "col-2">${appearance.reference}</td>
          </tr>
          <tr>
            <td class = "col-1 text-start">Date Issued</td>
            <td class = "col-2">${moment(appearance.dateIssued).format("LL")}</td>
          </tr>
          <tr>
            <td class = "col-1 text-start">Date From</td>
            <td class = "col-2">${moment(appearance.dateFrom).format("LL")}</td>
          </tr>
          <tr>
            <td class = "col-1 text-start">Date to</td>
            <td class = "col-2">${moment(appearance.dateTo).format("LL")}</td>
          </tr>
            <tr>
            <td class = "col-1 text-start">Purpose</td>
            <td class = "col-2">${appearance.purpose.description}</td>
          </tr>
        </tbody>
      </table>
    </div>`;
};

export const visitorDetails = function (visitor) {
  const address = visitor.address;
  return ` <div class="row mb-4">
                  <div class="col-lg-12 col-md-12 col-sm-12">
                    <p id="visitorId" style="display: none">${visitor.id}</p>
                    <h2 class="text-light" id="name">${visitor.courtesyTitle.title}. ${visitor.firstName} ${visitor.middleInitial === "N/A" ? " " : visitor.middleInitial + " "}${visitor.lastName}</h2>
                  </div>
                  <div class="col">
                    <h6 class="text-secondary mb-0" id="position">${visitor.position.title}</h6>
                    ${visitor.agency == null ? "" : `<h6 class="text-secondary" id="agency">${visitor.agency.name}</h6>`}
                    <h6 class="text-secondary" id="address">${address.barangay != null ? address.barangay.name + ", " : ""}${address.municipality.name}, ${address.province.name}</h6>
                  </div>`;
};

export const appearanceButtonContainer = `<div class="button-container hide container p-3">
  <div class="d-flex justify-content-center">
    <div class="row">
      <div class="col-auto">
        <button id="proceedButton" class="btn btn-primary">Proceed</button>
      </div>

      <div class="col">
        <button id="cancelButton" class="btn btn-danger cancel-button">Cancel</button>
      </div>
    </div>
  </div>
</div>`;

export function displayTitle(isEdit, entityType) {
  if ($("h5.modal-title").length === 0) {
    const modalHeader = $("div.modal-header");
    $(`<h5 class="modal-title text-light">${isEdit ? "Edit" : "New"} ${entityType}</h5>`).prependTo(modalHeader);
  }
}
