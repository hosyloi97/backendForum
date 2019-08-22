$(document).ready(function () {
    findAllTagPage(1);
    findAllPageTagNumber();
});
//==================================page=============================.unbind('click')
function pageTag(size) {
    let contentRow = '';
    for (let i = 1; i <= size; i++) {
        contentRow += `<li><a href="#" class="page" id="_${i}" name="${i}" >${i}</a></li>`;
    }
    $(".pagination").html(
        `<li><a href="#" class="prev" id="prev">&laquo</a></li>`
        + contentRow +
        `<li><a href="#" class="next" id="next">&raquo;</a></li>`
    );
    $("#_1").addClass("active");
}

function findAllPageTagNumber() {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/tag/size",
        success: function (size) {
            console.log(size);
            pageTag(size);
            $('.page').click(function () {
                const page = $(this).attr("name");
                for (let i = 1; i <= size; i++) {
                    var id = "#_" + i;
                    $(id).removeClass("active");
                }
                $(this).addClass("active");
                findAllTagPage(page);
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}


function findAllTagPage(page) {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/tag/page?page="+page ,
        success: function (tags) {

            $("#column-tag").html(
                "<td> STT</td>" +
                "<td> Name </td>" +
                "<td> Action</td>"
            );

            const listSize = Object.keys(tags).length;

            if (tags.check == "fail") {
                alert("Product isEmpty! Name not found!");
                return;
            }

            if (listSize > 0) {

                let contentRow = '';

                tags.map(function (tag) {
                    contentRow += `
                        <tr>
                        <td> ${tag.id} </td>
                        <td> ${tag.name} </td>
                       
                        <td>
                              <a href="update-tag?id=${tag.id}" name="${tag.id}"   style="cursor: pointer;color: #4285F4">Chỉnh sửa</a>&nbsp;
                              <span name="${tag.id}" class="delete-tag" style="cursor: pointer;color: red">Xóa</span>&nbsp;
                        </td>
                        </tr>
                    `;
                })

                $("#row-tag").html(contentRow);

                //===== delete =======
                deleteTag();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

//============ Delete PRODUCT ========================
function deleteTag() {
    $('.delete-tag').click(function () {
        const id = $(this).attr("name");
        console.log("id-tag " + id);
        $.ajax({
            type: "PUT",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            contentType: "application/json",
            url: "api/v1/admin/tag/delete?id=" + id,
            timeout: 30000,
            success: function () {
                alert('DELETE SUCCESS');
                return;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
                alert('DELETE FAIL');
            }
        });
    });
}
