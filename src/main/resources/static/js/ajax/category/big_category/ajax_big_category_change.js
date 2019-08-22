$(document).ready(function () {
    clickBtnBigChangeSubmit();

});


//============ Create Big Category ========================
function createBigCategory() {
    let idMenu = '';
    $('#menu-value').click(function () {
        idMenu = $(this).val();
    });
    $('#btn-create-big-category').click(function () {
        const nameBigCategory = $("#name-big-category").val();
        const desBigCategory = $("#des-big-category").val();
        console.log(nameBigCategory);
        const bigCategory = {
            "name": nameBigCategory,
            "description": desBigCategory
        };
        console.log(idMenu);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url: "api/v1/admin/big-category?menu-id=" + idMenu,
            data: JSON.stringify(bigCategory),
            cache: false,
            timeout: 300000,
            success: function (data) {
                alert("SUCCESS : " + data.name);
                $("#btn-create-big-category").prop("disabled", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert("CREATE ERROR ");
            }
        })
    });
}

// ============ Find Big Category By Id ===================

function findBigCategoryById(id) {
    $.ajax({
        type: "GET",
        dataType: "json",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/big-category/find-by-id?id=" + id,
        timeout: 30000,
        success: function (result) {
            updateBigCategory(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

//============ UPDATE Big Category ========================
function updateBigCategory(data) {
    $('#name-big-category').val(data.name);
    $("#menu-value").val(data.menu.name);
    $("#menu-value").prop("disabled", true);
    $('#btn-create-big-category').click(function () {
        data.name = $('#name-big-category').val();
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "api/v1/admin/big-category",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            data: JSON.stringify(data),
            timeout: 30000,
            success: function () {
                alert('SUCCESS');
                $("#btn-create-big-category").prop("disabled", true);

            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert("UPDATE ERROR ");
                $("#btn-create-big-category").prop("disabled", true);

            }
        });
    });
}

function clickBtnBigChangeSubmit() {
    const urlCreateCategory = window.location.href;
    console.log(urlCreateCategory);
    var str = urlCreateCategory.split("=");
    const id = str[str.length - 1];
    if ((id - 1) >= 0) {
        findBigCategoryById(id)
    } else createBigCategory();

}
