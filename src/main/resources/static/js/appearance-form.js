
    $(document).ready(function(){



    function setPrint(){
        const closePrint = () => {
            document.body.removeChild(this);
        };

        this.contentWindow.onbeforeunload = closePrint;
        this.contentWindow.onafterprint = closePrint;
        this.contentWindow.print();
    }

    $('#dateIssued').datepicker(
        $.extend({
            defaultDate: new Date(),
            altFormat : "yy-mm-dd",
            dateFormat: "MM dd, yy"
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
    }).datepicker("setDate",dateFrom.datepicker("getDate")).on("change", function(){
       dateFrom.datepicker("option","maxDate",getDate(this));
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
});