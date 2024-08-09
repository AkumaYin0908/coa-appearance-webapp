export function loadStatus(inCharge) {
  const dropdown = $("#inCharge");
  dropdown.append('<option value="Active">Active</option>');
  dropdown.append('<option value="Inactive">Inactive</option>');

  dropdown.prop("selectedIndex", inCharge ? 1 : 0);
}
