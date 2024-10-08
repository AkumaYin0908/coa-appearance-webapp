/**
 * __________________________________________________________________
 *
 * Phillipine Address Selector
 * __________________________________________________________________
 *
 * MIT License
 *
 * Copyright (c) 2020 Wilfred V. Pine
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @package Phillipine Address Selector
 * @author Wilfred V. Pine <only.master.red@gmail.com>
 * @copyright Copyright 2020 (https://dev.confired.com)
 * @link https://github.com/redmalmon/philippine-address-selector
 * @license https://opensource.org/licenses/MIT MIT License
 */

// var my_handlers = {
//   // fill province
//   fill_provinces: function () {
//     //selected region
//     var region_code = $(this).val();

//     // set selected text to input
//     var region_text = $(this).find("option:selected").text();
//     let region_input = $("#region-text");
//     region_input.val(region_text);
//     region_input.attr("code", region_code);
//     //clear province & city & barangay input
//     $("#province-text").val("");
//     $("#city-text").val("");
//     $("#barangay-text").val("");

//     //province
//     let dropdown = $("#province");
//     dropdown.empty();
//     dropdown.append(
//       '<option selected="true" disabled>Choose State/Province</option>'
//     );
//     dropdown.prop("selectedIndex", 0);

//     //city
//     let city = $("#city");
//     city.empty();
//     city.append('<option selected="true" disabled></option>');
//     city.prop("selectedIndex", 0);

//     //barangay
//     let barangay = $("#barangay");
//     barangay.empty();
//     barangay.append('<option selected="true" disabled></option>');
//     barangay.prop("selectedIndex", 0);

//     // filter & fill
//     var url = "ph-json/province.json";
//     $.getJSON(url, function (data) {
//       var result = data.filter(function (value) {
//         return value.region_code == region_code;
//       });

//       result.sort(function (a, b) {
//         return a.province_name.localeCompare(b.province_name);
//       });

//       $.each(result, function (key, entry) {
//         dropdown.append(
//           $("<option></option>")
//             .attr("value", entry.province_code)
//             .text(entry.province_name)
//         );
//       });
//     });
//   },
//   // fill city
//   fill_cities: function () {
//     //selected province
//     var province_code = $(this).val();

//     // set selected text to input
//     var province_text = $(this).find("option:selected").text();
//     let province_input = $("#province-text");
//     province_input.val(province_text);
//     province_input.attr("code", province_code);
//     //clear city & barangay input
//     $("#city-text").val("");
//     $("#barangay-text").val("");

//     //city
//     let dropdown = $("#city");
//     dropdown.empty();
//     dropdown.append(
//       '<option selected="true" disabled>Choose city/municipality</option>'
//     );
//     dropdown.prop("selectedIndex", 0);

//     //barangay
//     let barangay = $("#barangay");
//     barangay.empty();
//     barangay.append('<option selected="true" disabled></option>');
//     barangay.prop("selectedIndex", 0);

//     // filter & fill
//     var url = "ph-json/city.json";
//     $.getJSON(url, function (data) {
//       var result = data.filter(function (value) {
//         return value.province_code == province_code;
//       });

//       result.sort(function (a, b) {
//         return a.city_name.localeCompare(b.city_name);
//       });

//       $.each(result, function (key, entry) {
//         dropdown.append(
//           $("<option></option>")
//             .attr("value", entry.city_code)
//             .text(entry.city_name)
//         );
//       });
//     });
//   },
//   // fill barangay
//   fill_barangays: function () {
//     // selected barangay
//     var city_code = $(this).val();

//     // set selected text to input
//     var city_text = $(this).find("option:selected").text();
//     let city_input = $("#city-text");
//     city_input.val(city_text);
//     city_input.attr("code", city_code);
//     //clear barangay input
//     $("#barangay-text").val("");

//     // barangay
//     let dropdown = $("#barangay");
//     dropdown.empty();
//     dropdown.append('<option selected="true">Choose barangay</option>');
//     dropdown.prop("selectedIndex", 0);

//     // filter & Fill
//     var url = "ph-json/barangay.json";
//     $.getJSON(url, function (data) {
//       var result = data.filter(function (value) {
//         return value.city_code == city_code;
//       });

//       result.sort(function (a, b) {
//         return a.brgy_name.localeCompare(b.brgy_name);
//       });

//       $.each(result, function (key, entry) {
//         dropdown.append(
//           $("<option></option>")
//             .attr("value", entry.brgy_code)
//             .text(entry.brgy_name)
//         );
//       });
//     });
//   },

//   onchange_barangay: function () {
//     // set selected text to input
//     var barangay_code = $(this).val();
//     var barangay_text = $(this).find("option:selected").text();
//     let barangay_input = $("#barangay-text");
//     barangay_input.val(barangay_text);
//     barangay_input.attr("code", barangay_code);
//   },
// };

// $(function () {
//   // events
//   $("#region").on("change", my_handlers.fill_provinces);
//   $("#province").on("change", my_handlers.fill_cities);
//   $("#city").on("change", my_handlers.fill_barangays);
//   $("#barangay").on("change", my_handlers.onchange_barangay);

//   // load region
//   let dropdown = $("#region");
//   dropdown.empty();
//   dropdown.append('<option selected="true" disabled>Choose Region</option>');
//   dropdown.prop("selectedIndex", 0);
//   const url = "ph-json/region.json";
//   // Populate dropdown with list of regions
//   $.getJSON(url, function (data) {
//     $.each(data, function (key, entry) {
//       dropdown.append(
//         $("<option></option>")
//           .attr("value", entry.region_code)
//           .text(entry.region_name)
//       );
//     });
//   });
// });

var my_handlers = {
  // fill province
  fill_provinces: function (selectedProvince = null) {
    var region_code = $(this).val();
    var region_text = $(this).find("option:selected").text();
    let region_input = $("#region-text");
    region_input.val(region_text);
    region_input.attr("code", region_code);

    $("#province-text").val("");
    $("#city-text").val("");
    $("#barangay-text").val("");

    let dropdown = $("#province");
    dropdown.empty();
    dropdown.append(
      '<option selected="true" disabled>Choose State/Province</option>'
    );
    dropdown.prop("selectedIndex", 0);

    let city = $("#city");
    city.empty();
    city.append('<option selected="true" disabled></option>');
    city.prop("selectedIndex", 0);

    let barangay = $("#barangay");
    barangay.empty();
    barangay.append('<option selected="true" disabled></option>');
    barangay.prop("selectedIndex", 0);

    var url = "ph-json/province.json";
    $.getJSON(url, function (data) {
      var result = data.filter(function (value) {
        return value.region_code == region_code;
      });

      result.sort(function (a, b) {
        return a.province_name.localeCompare(b.province_name);
      });

      $.each(result, function (key, entry) {
        dropdown.append(
          $("<option></option>")
            .attr("value", entry.province_code)
            .text(entry.province_name)
        );
      });

      if (selectedProvince) {
        dropdown.val(selectedProvince);
        dropdown.trigger("change");
      } else {
        dropdown.prop("selectedIndex", 0);
      }
    });
  },
  // fill city
  fill_cities: function (selectedCity = null) {
    var province_code = $(this).val();
    var province_text = $(this).find("option:selected").text();
    let province_input = $("#province-text");
    province_input.val(province_text);
    province_input.attr("code", province_code);

    $("#city-text").val("");
    $("#barangay-text").val("");

    let dropdown = $("#city");
    dropdown.empty();
    dropdown.append(
      '<option selected="true" disabled>Choose city/municipality</option>'
    );
    dropdown.prop("selectedIndex", 0);

    let barangay = $("#barangay");
    barangay.empty();
    barangay.append('<option selected="true" disabled></option>');
    barangay.prop("selectedIndex", 0);

    var url = "ph-json/city.json";
    $.getJSON(url, function (data) {
      var result = data.filter(function (value) {
        return value.province_code == province_code;
      });

      result.sort(function (a, b) {
        return a.city_name.localeCompare(b.city_name);
      });

      $.each(result, function (key, entry) {
        dropdown.append(
          $("<option></option>")
            .attr("value", entry.city_code)
            .text(entry.city_name)
        );
      });

      if (selectedCity) {
        dropdown.val(selectedCity);
        dropdown.trigger("change");
      } else {
        dropdown.prop("selectedIndex", 0);
      }
    });
  },
  // fill barangay
  fill_barangays: function (selectedBarangay = null) {
    var city_code = $(this).val();
    var city_text = $(this).find("option:selected").text();
    let city_input = $("#city-text");
    city_input.val(city_text);
    city_input.attr("code", city_code);

    $("#barangay-text").val("");

    let dropdown = $("#barangay");
    dropdown.empty();
    dropdown.append('<option selected="true">Choose barangay</option>');
    dropdown.prop("selectedIndex", 0);

    var url = "ph-json/barangay.json";
    $.getJSON(url, function (data) {
      var result = data.filter(function (value) {
        return value.city_code == city_code;
      });

      result.sort(function (a, b) {
        return a.brgy_name.localeCompare(b.brgy_name);
      });

      $.each(result, function (key, entry) {
        dropdown.append(
          $("<option></option>")
            .attr("value", entry.brgy_code)
            .text(entry.brgy_name)
        );
      });

      if (selectedBarangay) {
        dropdown.val(selectedBarangay);
      } else {
        dropdown.prop("selectedIndex", 0);
      }
    });
  },

  onchange_barangay: function () {
    var barangay_code = $(this).val();
    var barangay_text = $(this).find("option:selected").text();
    let barangay_input = $("#barangay-text");
    barangay_input.val(barangay_text);
    barangay_input.attr("code", barangay_code);
  },
};

export function loadAddress(address = null) {
  $(function () {
    $("#region").on("change", function () {
      my_handlers.fill_provinces.call(
        this,
        address === null ? null : address.province.code
      );
    });

    $("#province").on("change", function () {
      my_handlers.fill_cities.call(
        this,
        address === null ? null : address.municipality.code
      );
    });

    $("#city").on("change", function () {
      my_handlers.fill_barangays.call(
        this,
        address === null || address.barangay == null
          ? null
          : address.barangay.code
      );
    });

    $("#barangay").on("change", my_handlers.onchange_barangay);

    // load region
    let dropdown = $("#region");
    dropdown.empty();
    dropdown.append('<option selected="true" disabled>Choose Region</option>');
    dropdown.prop("selectedIndex", 0);
    const url = "ph-json/region.json";
    $.getJSON(url, function (data) {
      $.each(data, function (key, entry) {
        dropdown.append(
          $("<option></option>")
            .attr("value", entry.region_code)
            .text(entry.region_name)
        );
      });
      if (address !== null) {
        dropdown.val(address.region.code);
        dropdown.trigger("change");
      }
    });
  });
}

