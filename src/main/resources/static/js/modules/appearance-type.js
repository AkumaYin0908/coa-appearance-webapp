
export const showAppearanceChoices = function (baseUrl,id) {
  Swal.fire({
    title: "Select appearance type",
    input: "select",
    inputOptions: {
      single: "Single Appearance",
      consolidated: "Consolidated Appearance",
    },
    inputPlaceholder: "Select type of appearance",
    showCancelButton: true,
    inputValidator: (value) => {
      if (value === "single") {
       window.location.href = `${baseUrl}/visitors/${id}/appearance-form?appearanceType=single`
      } else if (value === "consolidated") {
        window.location.href = `${baseUrl}/visitors/${id}/appearance-form?appearanceType=consolidated`
      }
    },
  });
};
