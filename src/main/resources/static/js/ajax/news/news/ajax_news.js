$(document).ready(function () {

    findAllPageNewsNumber();
    searchNewsByName(1);
    findAllNewsStatusFalse();
});

//==================================page=====================================

function pageNews(size) {
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

function findAllPageNewsNumber() {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/news/size",
        success: function (size) {

            findAllNewsBySize(1);
            pageNews(size);
            $('.page').click(function () {
                const page = $(this).attr("name");
                for (let i = 1; i <= size; i++) {
                    var id = "#_" + i;
                    $(id).removeClass("active");
                }
                $(this).addClass("active");
                findAllNewsBySize(page);
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

function findAllPageNewsByNameNumber() {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/news/name/size",
        success: function (size) {
            pageNews(size);
            $('.page').click(function () {
                    const page = $(this).attr("name");
                    searchNewsByName(page);
                }
            );
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

//============ FIND ALL PRODUCT ========================
function findAllNewsBySize(page) {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/news/page?page=" + page,
        success: function (news) {
            displayOnTable(news);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    })
}



function displayOnTable(newses) {
    $("#column-news").html(
        "<td>STT </td>" +
        "<td>Title </td>" +
        "<td>Time </td>" +
        "<td>Like</td>" +
        "<td>User</td>" +
        "<td>Action</td>"
    );
    const listSize = Object.keys(newses).length;
    if (listSize > 0) {

        let contentRow;
        newses.map(function (news) {
            contentRow += `
                         <tr>
                         <td> ${news.id} </td>
                         <td> ${news.title} </td>
                         <td> ${news.time} </td>
                         <td> ${news.like} </td>
                         <td> ${news.appUser.email} </td>
                         <td>
                              <span name="${news.id}" class="delete-news" style="cursor: pointer;color: red">Xóa</span>&nbsp;
                        </td>
                        </tr>
                       `;
        });
        $("#row-news").html(contentRow);
//===== delete =======
        deleteNews();
    }
}

//============ Delete NEWS ========================
function deleteNews() {
    $('.delete-news').click(function () {
        const id = $(this).attr("name");
        console.log("id-news " + id);
        $.ajax({
            type: "PUT",
            headers: {
                "Authorization": tokenHeader_admin_value,
            },
            contentType: "application/json",
            url: "api/v1/user/news/delete?id=" + id,
            timeout: 30000,
            success: function () {
                alert('DELETE SUCCESS');
                location.href = "news";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errMess(jqXHR, textStatus, errorThrown);
            }
        });
    });
}

//=========================== SEARCH BY NAME ===================================
function searchNewsByName(page) {
    $("#input-search").keypress(function (event) {
        const keycode = event.keycode ? event.keycode : event.which;
        if (keycode == 13) {
            const nameProduct = $('#input-search').val();
            $.ajax({
                type: "GET",
                headers: {
                    "Secret": tokenHeader_value,
                },
                url: "api/v1/public/news/search?name=" + nameProduct + "&page=" + page,
                success: function (products) {
                    findAllPageNewsByNameNumber();
                    displayOnTable(products);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    errMess(jqXHR, textStatus, errorThrown);
                }
            })
        }
    });
}



