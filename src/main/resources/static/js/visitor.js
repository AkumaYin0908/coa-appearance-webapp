$(document).ready(function(){
$("#visitorButton").addClass("active");

$("#btnDelete").on("click",function(event){
    event.preventDefault();
    link=$(this);

    visitorName=link.attr("visitorName");
    $("#btnYes").attr("href",link.attr("href"));
    $("#confirmText").html("Do you want to delete <strong>" + visitorName + "\<\/strong\>?");
    $("#confirmModal").modal('show');


});

$("#btnClear").on("click", function(event){
        event.preventDefault();
        $("#searchName").text("");

        window.location.href ="/visitors";
    });

$("#pageSize").on("change",function(event){
    event.preventDefault();
    $("#searchForm").submit();
});



});