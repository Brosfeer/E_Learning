$(document).ready(function () {
    $.ajax({
        url: 'onClickJava',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data.data.length > 0) {
                var userDetails = data.data[0];
                $('#userName').text(userDetails.userName);
                $('#email').text(userDetails.email);
                $('#address').text(userDetails.address);
            }
        }
    });
});
$(document).ready(function () {
    $.ajax({
        url: 'Coursses',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.data.length > 0) {
                data.data.forEach(function (userDetails, index) {
                    $('#User_Name' + index).text(userDetails.User_Name);
                    $('#E_mail' + index).text(userDetails.E_mail);
                    $('#Courssetitle' + index).text(userDetails.Courssetitle);
                    $('#teacher_name' + index).text(userDetails.teacher_name);
                    $('#coursseDiscription' + index).text(userDetails.coursseDiscription);
                    $('#coursseDuration' + index).text(userDetails.coursseDuration);
                });
            }
        }
    });
});