export default function showMessage(message) {
  return `<div
      id="messageHolder"
      class="alert alert-dismissible fade show text-center message mt-3"
      role="alert">
      <span id="message">${message}</span>
      <button
        type="button"
        class="btn-close"
        data-bs-dismiss="alert"
        aria-label="Close">
        <span aria-hidden="true"></span>
      </button>
    </div>`;
}
