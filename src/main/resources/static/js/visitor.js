
$(document).ready(function(){
$("#visitorButton").addClass("active");

let addModalMessage=$("#addModalMessageHolder").text();
    if(addModalMessage != ""){
        $("#addVisitorModal").modal('show');
    }
let editModalMessage=$("#editModalMessageHolder").text();
    if(editModalMessage != ""){
        $("#editVisitorModal").modal('show');
    }

$(".btn-delete").on("click",function(event){
    event.preventDefault();
    link=$(this);

    visitorName=link.attr("visitorName");
    $("#btnYes").attr("href",link.attr("href"));
    $("#confirmText").html("Do you want to delete <strong>" + visitorName + "\<\/strong\>?");
    $("#deleteVisitorModal").modal('show');


});

$("#addVisitorButton").on("click",function(event){
    event.preventDefault();
    $("#addVisitorModal").modal('show');
});

$("#addModalCloseButton").on("click",function(event){
    event.preventDefault();
    $("#addModalDiv").hide();
     $("#visitorId").val("");
     $("#visitorName").val("");
     $("#visitorPosition").val("");
     $("#visitorAgency").val("");
});


$("#editModalCloseButton").on("click",function(event){
    event.preventDefault();
    $("#editModalDiv").hide();

});
$(".btn-edit").on("click",function(event){
    event.preventDefault();
    let visitor=$(this);

    $("#editVisitorId").val(visitor.attr("visitorId"));
    $("#editVisitorName").val(visitor.attr("visitorName"));
    $("#editVisitorPosition").val(visitor.attr("visitorPosition"));
    $("#editVisitorAgency").val(visitor.attr("visitorAgency"));


    $("#editVisitorModal").modal('show');
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