"use strict";

$(document).ready(function () {
  let message = $("#message");

  if (message != "") {
    $("#messageHolder").fadeToggle(10000);
  }
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
});
