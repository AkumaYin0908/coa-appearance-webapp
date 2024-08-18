export const datePickerSetting = {
  dateFormat: "MM dd, yy",
  altFormat: "yy-mm-dd",
  changeMonth: true,
  changeYear: true,
  numberOfMonths: 1,
};

export function getLongDate(date) {
  const dateFormatSetting = { year: "numeric", month: "long", day: "numeric" };
  return new Date(date).toLocaleDateString("EN-US", dateFormatSetting);
}
