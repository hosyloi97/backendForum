function errMess(jqXHR, textStatus, errorThrown) {
    console.log('jqXHR:');
    console.log(jqXHR);
    console.log('textStatus:');
    console.log(textStatus);
    console.log('errorThrown:');
    console.log(errorThrown);
}

const tokenHeader_value = "thangNaoDungTromApiLamCho";
const tokenHeader_admin_value = sessionStorage.getItem("token");




