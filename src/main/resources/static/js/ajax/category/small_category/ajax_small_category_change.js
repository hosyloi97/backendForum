$(document).ready(function () {
    clickBtnSmallChangeSubmit();

});

//============ Create Big Category ========================
function createSmallCategory() {
    let idBigCategory = '';
    $('#big-category-value').click(function () {
        idBigCategory = $(this).val();
    });

    $('#btn-create-small-category').click(function () {

        const nameSmallCategory = $("#name-small-category").val();

        console.log(nameSmallCategory + " - " + idBigCategory);
        const smallCategory = {
            "name": nameSmallCategory,
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url: "api/v1/admin/small-category?big-id=" + idBigCategory,
            data: JSON.stringify(smallCategory),
            cache: false,
            timeout: 300000,
            contentType: "application/json",
            success: function (data) {
                alert("CREATE SUCCESS : " + data.name);
                $("#btn-create-small-category").prop("disabled", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert("CREATE ERROR :");

            }
        })
    });
}

//============ Find Big Category By Id ===================

function findSmallCategoryById(id) {

    $.ajax({
        type: "GET",
        dataType: "json",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/small-category/find-by-id?id=" + id,
        timeout: 30000,
        success: function (result) {
            updateSmallCategory(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

// ============ UPDATE Medium Category ========================
function updateSmallCategory(data) {
    $('#name-small-category').val(data.name);
    $("#big-category-value").val(data.bigCategory.name);
    $("#big-category-value").prop("disabled", true);
    $('#btn-create-small-category').click(function () {
        data.name = $('#name-small-category').val();
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url: "api/v1/admin/small-category",
            data: JSON.stringify(data),
            timeout: 30000,
            success: function (result) {
                alert('UPDATE SUCCESS : ' + result.name);
                return;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert('UPDATE FAIL');
            }
        });
    });
}

function clickBtnSmallChangeSubmit() {
    const urlCreateCategory = window.location.href;
    console.log(urlCreateCategory);
    var str = urlCreateCategory.split("=");
    const id = str[str.length - 1];
    if ((id - 1) >= 0) {
        findSmallCategoryById(id)
    } else createSmallCategory();
}
