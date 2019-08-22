$(document).ready(function () {
    findAllMenu();

});

function findAllMenu() {
    //============ Get All Menu ========================
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/menu",
        success: function (menus) {
            $("#column-menu").html(
                "<td> STT</td>" +
                "<td> Name</td>" +
                "<td> Description</td>" +
                "<td> Action</td>"
            );
            const listSize = Object.keys(menus).length;
            if (menus.check == "fail") {
                alert("Menu isEmpty! Name not found!");
                return;
            }
            if (listSize > 0) {

                let contentRow = '';


                menus.map(function (menu) {
                    contentRow += `
                        <tr>
                        <td> ${menu.id} </td>
                        <td> ${menu.name} </td>
                        <td> ${menu.description} </td>
                        <td>
                              <a href="update-menu?id=${menu.id}" name="${menu.id}"  style="cursor: pointer;color: #4285F4">Sửa</a>&nbsp;
                              <span name="${menu.id}" class="delete-menu" style="cursor: pointer;color: red">Xóa</span>&nbsp;
                        </td>
                        </tr>
                    `;
                });
                $("#row-menu").html(contentRow);
                $(".body-main .table-responsive tr td").css({
                    "max-width": "260px",
                    "overflow": "-webkit-paged-y"
                });
                //============ delete =============
                deleteMenu();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    })
}

//============ Delete Menu =================
function deleteMenu() {

    $('.delete-menu').click(function () {
        const id = $(this).attr("name");
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            url: "api/v1/admin/menu/delete?id=" + id,
            timeout: 30000,
            success: function () {
                alert('DELETE SUCCESS');
                return;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("DELETE FAIL");
                errMess(jqXHR, textStatus, errorThrown);
            }
        });
    });

}
