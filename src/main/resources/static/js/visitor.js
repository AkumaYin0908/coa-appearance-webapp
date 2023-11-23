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
        $("#keyword").text("");

        window.location ="[[@{/visitors}]]";
    });

$("#pageSize").on("change",function(){
    $("#searchForm").submit();
});

/*
$("#addVisitorButton").on("click",function(event){
    $("#myModal").modal('show');

}); */

});