$(document).ready(function(){
    $("#dashboardButton").addClass("active");

    let addModalMessage=$("#addModalMessageHolder").text();
    if(addModalMessage != ""){
        $("#addVisitorModal").modal("show");
    }

    let changeModalMessage =$("#changeModalMessageHolder").text();
    if(changeModalMessage != ""){
        $("#changeLeaderModal").modal("show");
    }

    $("#changeLeaderButton").on("click",function(event){
        event.preventDefault();
        $("#changeLeaderModal").modal("show")

    });


    $("#changeModalCloseButton").on("click",function(event){
        event.preventDefault();
        $("#changeModalDiv").hide();
        $("#currentLeaderName").val("");
        $("#leaderName").val("");

    });



    $("#addVisitorButton").on("click",function(event){
        event.preventDefault();
        $("#addVisitorModal").modal("show");
    });

    $("#addModalCloseButton").on("click",function(event){
        event.preventDefault();
        $("#addModalDiv").hide();
         $("#visitorId").val("");
         $("#visitorName").val("");
         $("#visitorPosition").val("");
         $("#visitorAgency").val("");
    });

});