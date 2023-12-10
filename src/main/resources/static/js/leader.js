$(document).ready(function(){

    let addModalMessage = $("#addModalMessageHolder").text();
        if(addModalMessage != ""){
            $("#addLeaderModal").modal("show");
        }


    let editModalMessage = $("#editModalMessageHolder").text();
        if(editModalMessage != ""){
            $("#editLeaderModal").modal("show");
        }

    $(".btn-delete").on("click", function(event){
        event.preventDefault();
        link=$(this);

        leaderName=link.attr("leaderName");
        $("#btnYes").attr("href",link.attr("href"));
        $("#confirmText").html("Do you want to delete <strong>" + leaderName + "\<\/strong\>?");
        $("#deleteLeaderModal").modal("show");

    });

    $("#addLeaderButton").on("click", function(event){
        event.preventDefault();
        $("#leaderInCharge").prop("checked", true);

        $("#addLeaderModal").modal("show");
    });

    $("#addModalCloseButton").on("click", function(event){
        event.preventDefault();

        $("#addModalDiv").hide();
        $("#leaderId").val("");
        $("#leaderName").val("");
        $("#leaderPosition").val("");

    });


    $("#editModalCloseButton").on("click", function(event){
        event.preventDefault();
        $("#editModalDiv").hide();

    });

    $(".btn-edit").on("click", function(event){
        event.preventDefault();

        let leader=$(this);

        $("#editLeaderId").val(leader.attr("leaderId"));
        $("#editLeaderName").val(leader.attr("leaderName"))
        $("#editLeaderPosition").val(leader.attr("leaderPosition"));

        let inCharge = leader.attr("leaderInCharge") === 'true';
        $("#editLeaderInCharge").prop("checked",inCharge);

        $("#editLeaderModal").modal("show");
    });


    $("#btnClear").on("click", function(event){
        event.preventDefault();
        $("#searchName").text("");

        window.location.href="/settings/leaders";
    });


   $("#pageSize").on("change",function(event){
       event.preventDefault();
       $("#searchForm").submit();
   });

});