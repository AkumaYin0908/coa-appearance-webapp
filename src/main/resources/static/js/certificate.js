
$(document).ready(function(){
    window.print();


   window.addEventListener("afterprint", () => self.close);
});