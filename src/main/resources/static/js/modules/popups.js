export const toast = Swal.mixin({
  toast: true,
  position: "top-end",
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.onmouseenter = Swal.stopTimer;
    toast.onmouseleave = Swal.resumeTimer;
  },
});

export const alert = function (title, message, icon) {
  return Swal.fire({
    title: title,
    text: message,
    icon: icon,
  });
};

export const openConfirmDialog = async function (dialogDetails) {
  const confirmDialog = await Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger",
    },
  });

  return await confirmDialog.fire({
    title: dialogDetails.title,
    text: dialogDetails.text,
    icon: dialogDetails.icon,
    showCancelButton: true,
    confirmButtonText: dialogDetails.confirmButtonText,
    cancelButtonText: dialogDetails.cancelButtonText,
  });
};
