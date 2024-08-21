"use strict";

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
