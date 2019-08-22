$(document).ready(function () {
    clickBtnTagChangeSubmit();
});

//============ CREATE TAG ========================

function createTag() {
    $('#btn-create-tag').click(function () {

        const nameTag = $('#name-tag-value').val();
        const tag = {
            "name": nameTag
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url:  "api/v1/admin/tag",
            data: JSON.stringify(tag),
            cache: false,
            timeout: 300000,
            success: function (data) {
                alert("CREATE SUCCESS : " + data.name);
                $('#btn-create-tag').prop("disabled", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert("CREATE ERROR ");
                $('#btn-create-tag').prop("disabled", true);
            }
        })
    });
}

// ============ FIND TAG BY ID ===================

function findTagById(id) {

    $.ajax({
        type: "GET",
        dataType: "json",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/tag/find-by-id?id=" + id,
        timeout: 30000,
        success: function (result) {
            updateTag(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

//============ UPDATE TAG ========================
function updateTag(data) {

    $('#name-tag-value').val(data.name);


    $('#btn-create-tag').click(function () {
        data.name = $('#name-tag-value').val();
        console.log(data);
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url: "api/v1/admin/tag",
            data: JSON.stringify(data),
            timeout: 30000,
            success: function () {
                alert('UPDATE SUCCESS');
                $('#btn-create-tag').prop("disabled", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert("UPDATE ERROR ");
                $('#btn-create-tag').prop("disabled", true);

            }
        });
    });
}

function clickBtnTagChangeSubmit() {
    const urlCreatePartner = window.location.href;
    var str = urlCreatePartner.split("=");
    const id = str[str.length - 1];    if ((id - 1) >= 0) {
        findTagById(id)
    } else createTag();

}

