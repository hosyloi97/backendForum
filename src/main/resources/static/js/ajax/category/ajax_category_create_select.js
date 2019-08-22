$(document).ready(function () {
    findAllNameMenuCategory();
    findAllNameBigCategory();
    findAllNameSmallCategory();
});

function findAllNameBigCategory() {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/big-category",
        success: function (bigCategories) {
            const listSize = Object.keys(bigCategories).length;
            if (bigCategories.check == "fail") {
                alert("Category isEmpty! Name not found!");
                return;
            }
            if (listSize > 0) {

                let contentRow = '';
                bigCategories.map(function (bigCategory) {
                    contentRow += `
                       <option value ="${bigCategory.id}" >${bigCategory.name}</option>
                    `;
                });
                $("#big-category-value").html(contentRow);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

function findAllNameMenuCategory() {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/menu",
        success: function (menus) {
            const listSize = Object.keys(menus).length;
            if (menus.check == "fail") {
                alert("menu isEmpty! Name not found!");
                return;
            }
            if (listSize > 0) {
                let contentRow = '';
                menus.map(function (menu) {
                    contentRow += `
                       <option value="${menu.id}">${menu.name}</option>
                    `;
                });
                $("#menu-value").html(contentRow);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}
function findAllNameSmallCategory() {
    $.ajax({
        type: "GET",
        headers: {
            "Secret": tokenHeader_value,
        },
        url: "api/v1/public/small-category",
        success: function (smallCategories) {
            const listSize = Object.keys(smallCategories).length;
            if (smallCategories.check == "fail") {
                alert("Small Category isEmpty! Name not found!");
                return;
            }
            if (listSize >= 0) {
                let contentRow = '';
                smallCategories.map(function (smallCategory) {
                    contentRow += `
                       <option value="${smallCategory.id}">${smallCategory.name}</option>
                    `;
                });
                $("#small-category-value").html(contentRow);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errMess(jqXHR, textStatus, errorThrown);
        }
    });
}

