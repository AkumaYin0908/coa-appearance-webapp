import { baseUrl } from "./baseUrl.js";

export const addressContent = `
                <div class="form-group">
                  <div class="md-form form-floating mb-3">
                    <select
                      name="region"
                      class="form-control"
                      id="region"></select>
                    <input
                      type="hidden"
                      class="form-control"
                      name="region_text"
                      id="region-text"
                      required
                      placeholder="Region" />
                    <label class="text-secondary" for="region">Region</label>
                  </div>
                </div>

                <div class="form-group">
                  <div class="md-form form-floating mb-3">
                    <select
                      name="province"
                      class="form-control"
                      id="province"></select>
                    <input
                      type="hidden"
                      class="form-control"
                      name="province_text"
                      id="province-text"
                      required
                      placeholder="Province" />
                    <label class="text-secondary" for="province"
                      >Province</label
                    >
                  </div>
                </div>

                <div class="form-group">
                  <div class="md-form form-floating mb-3">
                    <select name="city" class="form-control" id="city"></select>
                    <input
                      type="hidden"
                      class="form-control"
                      name="city_text"
                      id="city-text"
                      required
                      placeholder="City" />
                    <label class="text-secondary" for="city"
                      >City/Municipality</label
                    >
                  </div>
                </div>

                <div class="form-group">
                  <div class="md-form form-floating mb-3">
                    <select
                      name="barangay"
                      class="form-control"
                      id="barangay"></select>
                    <input
                      type="hidden"
                      class="form-control"
                      name="barangay_text"
                      id="barangay-text"
                      placeholder="Barangay" />
                    <label class="text-secondary" for="barangay"
                      >Barangay</label
                    >
                  </div>
                </div>`;

export const newButton = function (data) {
  return ` <a data-key = ${data} class = "btn-new btn btn-sm btn-success"><span class = "label-new">New</span>
        </a>`;
};
export const editButton = function (data) {
  return `<a data-key = ${data} class = "btn-edit btn btn-sm btn-warning"><span class="material-symbols-outlined">edit</span>
        </a>`;
};

export const deleteButton = function (data) {
  return ` <a data-key = ${data} class = "btn-delete btn btn-sm btn-danger"><span class="material-symbols-outlined">delete</span>
        </a>`;
};

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
            <td class = "col-2">${appearance.dateIssued}</td>
          </tr>
          <tr>
            <td class = "col-1 text-start">Date From</td>
            <td class = "col-2">${appearance.dateFrom}</td>
          </tr>
          <tr>
            <td class = "col-1 text-start">Date to</td>
            <td class = "col-2">${appearance.dateTo}</td>
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
                    <h2 class="text-light" id="name">${visitor.name}</h2>
                  </div>
                  <div class="col">
                    <h6 class="text-secondary mb-0" id="position">${visitor.position.title}</h6>
                    <h6 class="text-secondary" id="agency">${visitor.agency.name}</h6>
                    <h6 class="text-secondary" id="address">${
                      address.barangay != null ? address.barangay.name + ", " : ""
                    }${address.municipality.name}, ${address.province.name}</h6>
                  </div>`;
};

export const appearanceButtonContainer = `<div class="button-container container p-3">
  <div class="d-flex justify-content-center">
    <div class="row">
      <div class="col-auto">
        <button type ="submit" id="proceedButton" class="btn btn-primary">Proceed</button>
      </div>

      <div class="col">
        <button id="cancelButton" class="btn btn-danger cancel-button">Cancel</button>
      </div>
    </div>
  </div>
</div>`;
