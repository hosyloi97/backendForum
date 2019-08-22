$(document).ready(function () {
    clickBtnMenuChangeSubmit();

});

//============ Create Menu ========================
function createMenu() {
    $('#btn-create-menu').click(function () {
        const nameMenu = $("#name-menu").val();
        console.log(nameMenu);
        const menu = {
            "name": nameMenu,
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url: "api/v1/admin/menu",
            data: JSON.stringify(menu),
            cache: false,
            timeout: 300000,
            success: function (data) {
                alert("CREATE SUCCESS : " + data.name);
                $("#btn-create-menu").prop("disabled", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert("CREATE ERROR ");
            }
        })
    });
}

//============ Find Menu By Id ===================

function findMenuById(id) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/menu/find-by-id?id=" + id,
        timeout: 30000,
        success: function (result) {
            console.log(result);
            updateMenu(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

// ============ UPDATE MENU========================
function updateMenu(data) {

    $('#name-menu').val(data.name);
    $('#btn-create-menu').click(function () {
        data.name = $('#name-menu').val();
        console.log(data);
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url: "api/v1/admin/menu",
            data: JSON.stringify(data),
            timeout: 30000,
            success: function (result) {
                alert('UPDATE SUCCESS' + result.name);
                return;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert("UPDATE ERROR ");
            }
        });
    });
}

function clickBtnMenuChangeSubmit() {
    const urlCreateCategory = window.location.href;
    console.log(urlCreateCategory);
    var str = urlCreateCategory.split("=");
    const id = str[str.length - 1];
    if ((id - 1) >= 0) {
        findMenuById(id)
    } else createMenu();

}
