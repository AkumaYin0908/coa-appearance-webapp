"use strict";

import { baseUrl } from "./modules/base-url.js";

let buttons = $("li a");

buttons.on("click", function () {
  let clickedButton = $(this);

  //remove the 'active' class from the other button
  buttons.not(clickedButton).removeClass("active");

  //toggle 'active' class on the clicked button
  clickedButton.toggleClass("active");
});

$(".sub-button").on("click", function () {
  $(this).next(".sub-menu").slideToggle();
  $(this).find(".dropdown").toggleClass("rotate");
});

$("#dashboardButton").prop("href",`${baseUrl}/dashboard`);
$("#visitorButton").prop("href",`${baseUrl}/visitor-page`);
$("#leaderButton").prop("href",`${baseUrl}/settings/leader-page`);