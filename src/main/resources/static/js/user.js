

$(document).ready(function(){

    $(".btn-delete").on("click", function(event){
        event.preventDefault();
        link =$(this);

        userName= link.attr("userName");
        $("#btnYes").attr("href",link.attr("href"));
        $("#confirmText").html("Do you want to delete <strong>" + userName + "\<\/strong>?");
        $("#deleteUserModal").modal("show");
    });



    $("#addUserButton").on("click",function(event){
        event.preventDefault();
        $("#addUserModal").modal("show");
    });

    $(".btn-edit").on("click",function(event){
        user=$(this);
        $("#editUserId").val(user.attr("userId"));
        $("#editUserName").val(user.attr("userName"));
        $("#editName").val(user.attr("name"))
        $("#editUserPosition").val(user.attr("position"));

        let active=user.attr("active") === "true";
        $("#editUserActive").prop("checked",active);

       var userRoles=JSON.parse(user.attr("data-user-roles"));





        $(".role-list input[type=checkbox]").each(function() {
            let checkBox = $(this);
            let roleName = checkBox.attr("name");

            // Check if the roleName exists in userRoles array
            let roleExists = userRoles.some(role=> role.name === roleName);

            // Set the checkbox state based on the result
            checkBox.prop("checked", roleExists);
        });


        $("#editUserModal").modal("show");
    });

    let addModalMessage = $("#addModalMessageHolder").text();

    if(addModalMessage != ""){
        $("#addUserModal").modal("show");
    }

});