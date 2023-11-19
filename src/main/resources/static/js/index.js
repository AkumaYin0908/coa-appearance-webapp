$(document).ready(function(){

    let buttons = $("li a");

    buttons.on("click",function(){
        let clickedButton=$(this);

        //remove the 'active' class from the other button
        buttons.not(clickedButton).removeClass("active");

        //toggle 'active' class on the clicked button
        clickedButton.toggleClass("active");



    });

    $("#logoutButton").on("click", function(event){
        event.preventDefault();
        $("#confirmText").html("Are you sure you want to logout?");
        $("#confirmLogout").modal('show');

    });

    $("#logoutButtonModal").on("click",function(event){
                event.preventDefault();
                document.logoutForm.submit();
            });


});