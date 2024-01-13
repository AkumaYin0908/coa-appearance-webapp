
    $(document).ready(function(){

//    $("#btnPrint").on("click", function(event){
//        event.preventDefault();
//
//        $(this).submit();
//
//        link=$(this).attr("href");
//
//        loadOtherPage(link);
//
//
//    });
//
//    function loadOtherPage(link){
//        $("<iframe>")
//            .hide()
//            .attr("src",link)
//            .appendTo("body");
//    }


    $("#dateIssued").datepicker(
        $.extend({
            defaultDate: new Date(),
            altFormat : "yy-mm-dd",
            dateFormat: "MM dd, yy",
            changeMonth: true,
            changeYear : true,
            numberOfMonths: 1
        })
    ).datepicker("setDate", new Date());

    $(function(){
        let dateFormat="MM dd, yy",
        dateFrom = $("#dateFrom")
        .datepicker({
            dateFormat:"MM dd, yy",
            altFormat : "yy-mm-dd",
            changeMonth: true,
            changeYear : true,
            numberOfMonths: 1
    }).datepicker("setDate",$("#dateIssued").datepicker("getDate")).on("change", function(){
        dateTo.datepicker("option", "minDate", getDate(this));
    }),
     dateTo=$("#dateTo").datepicker({
            dateFormat:"MM dd, yy",
            altFormat : "yy-mm-dd",
            changeMonth: true,
            changeYear : true,
            numberOfMonths: 1
    }).datepicker("setDate",dateFrom.datepicker("getDate"));
//    .on("change", function(){
//       dateFrom.datepicker("option","maxDate",getDate(this));});

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

//    var appearances = [];
//    $("#btnAddRow").on("click",function(event){
//        event.preventDefault();
//        addRow();
//    });
//
//
//    function addRow(){
//
//    var dateIssued=$("#dateIssued").val();
//    var visitorName = $("#visitorName").text();
//    var visitorPosition = $("#visitorPosition").text();
//    var visitorAgency = $("#visitorAgency").text();
//    var dateFrom = $("#dateFrom").val();
//    var dateTo = $("#dateTo").val();
//    var purpose = $("#purposeTextArea").val();
//     $("#table tbody").append(
//     "<tr>" +
//     "<td>" + dateIssued + "</td>" +
//     "<td>" + visitorName + "</td>" +
//     "<td>" + visitorPosition + "</td>" +
//     "<td>" + visitorAgency + "</td>" +
//     "<td>" + dateFrom + "</td>" +
//     "<td>" + dateTo + "</td>" +
//     "<td>" + purpose + "</td>" +
//     "</tr>"
//     );
//
//     appearances.push({
//        'name' : visitorName,
//        'position' : visitorPosition,
//        'agency' : visitorAgency,
//        'dateIssued' : dateIssued,
//        'dateFrom' : dateFrom,
//        'dateTo' : dateTo,
//        'purpose' : purpose
//     });
//    }
//
//    $("#btnSaveAll").on("click", function(event){
//        event.preventDefault();
//        saveAll();
//    });
//
//    function saveAll(){
//     $.ajax({
//     url : "/appearances/saveAll",
//     type: "POST",
//     contentType : "application/json",
//     data : JSON.stringify(appearances),
//     success : function(){
//        $("#table").find("tr:gt(0)").remove();
//        appearance =[];
//        window.location.href="/dashboard";
//
//
//     },
//     error : function(){
//        alert("Error");
//     }
//     });
//    }


});