$(document).ready(function(){

    $(".btn-delete").on("click", function(event){
        event.preventDefault();
        link =$(this);

        userName= link.attr("userName");
        $("#btnYes").attr("href",link.attr("href"));
        $("#confirmText").html("Do you want to delete <strong>" + userName + "\<\/strong>?");
        $("#deleteUserModal").modal("show");
    });

});