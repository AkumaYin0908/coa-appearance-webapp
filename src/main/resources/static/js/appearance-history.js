$(document).ready(function(){


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

    $("#editModalCloseButton").on("click",function(event){
        event.preventDefault();
        $("#editModalDiv").hide();

    });



    $(".btn-edit").on("click",function(event){
        event.preventDefault();
        let appearance=$(this);

        $("#editAppearanceId").val(appearance.attr("appearanceId"));
        $("#editAppearanceDateIssued").val(appearance.attr("appearanceDateIssued"));
        $("#editAppearanceDateFrom").val(appearance.attr("appearanceDateFrom"));
        $("#editAppearanceDateTo").val(appearance.attr("appearanceDateTo"));
        $("#editAppearancePurpose").val(appearance.attr("appearancePurpose"));

        $("#editAppearanceModal").modal('show');
    });


    $("#editAppearanceDateIssued").datepicker(
        $.extend({
            altFormat : "yy-mm-dd",
            dateFormat: "MM dd, yy"
        })
    );

    $(function(){
        let dateFormat="MM dd, yy",
        dateFrom = $("#editAppearanceDateFrom")
        .datepicker({
            dateFormat:"MM dd, yy",
            altFormat : "yy-mm-dd",
            changeMonth: true,
            changeYear : true,
            numberOfMonths: 1
    }).on("change", function(){
        dateTo.datepicker("option", "minDate", getDate(this));
    }),
     dateTo=$("#editAppearanceDateTo").datepicker({
            dateFormat:"MM dd, yy",
            altFormat : "yy-mm-dd",
            changeMonth: true,
            changeYear : true,
            numberOfMonths: 1
    });

     function getDate(element){
        var date;
        try{
            date=$.datepicker.parseDate(dateFormat,element.value);
        }catch(error){
            date=null;
        }
        return date;
    }

    });


    $("#btnClear").on("click", function(event){
        event.preventDefault();

        $("#searchPurpose").text("");

        $("#month").text("Select Month");

        $("#year").text("Select Year");

        visitorId=$(this).attr("visitorId");

        window.location.href ="/appearances/" + visitorId + "/appearance-history";
    });


 $("#pageSize").on("change",function(event){
        event.preventDefault();
        $("#searchForm").submit();
    });

    $("#month").on("change",function(event){
        event.preventDefault();
        $("#searchForm").submit();
    });

    $("#year").on("change",function(event){
        event.preventDefault();
        $("#searchForm").submit();
    });

    let checkPrint = $(".chk-print");

   checkPrint.on("click",function(){
    if($(this).is(":checked")){
           $("#btnPrintChecked").show();
           console.log(checkPrint.val());
       }else{
           $("#btnPrintChecked").hide();
       }
   });


    $("#btnPrintChecked").on("click",function(event){
        $("#appearances").submit();
    });
});

