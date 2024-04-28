export async function showMessage(messageHolderId, modalId) {
  let modalMessage = $(`#${messageHolderId}`).text();
  if (modalMessage != "") {
    $(`#${modalId}`).modal("show");
  }
}

export async function showModal(buttonId, modalId) {
  $(`#${buttonId}`).on("click", function (event) {
    event.preventDefault();
    $(`#${modalId}`).modal("show");
  });
}

export async function hideModal(buttonId, modalId, fn = () => {}) {
  $(`#${buttonId}`).on("click", function (event) {
    event.preventDefault();
    $(`#${modalId}`).hide();
    fn();
  });
}
