$(document).ready(function () {

// making ajax for getting the data from the courses servlet 
    $.ajax({
        url: 'Coursses',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            var signedInCourses = response.data || [];
            var unsignedCourses = response.Restdata || [];
            var unsignedCourses2 = response.data2 || [];
            
            if (signedInCourses.length > 0) {
                displayCourses(signedInCourses);
            }

            if (unsignedCourses.length > 0) {
                displayUnsignedCourses(unsignedCourses);
            }

            if (unsignedCourses2.length > 0) {
                displayCourses(unsignedCourses2);
            }
        }
    });
    function displayCourses(courses) {
        courses.forEach(function (course, index) {
            $('#User_Name' + index).text(course.User_Name);
            $('#E_mail' + index).text(course.E_mail);
            $('#title' + index).text(course.Courssetitle);
            $('#course' + index).attr('data-id',course.CourseId);
            $('#teacher_name' + index).text(course.teacher_name);
            $('#Description' + index).text(course.coursseDiscription);
            $('#Duration' + index).text(course.coursseDuration);
        });
    }

    function displayUnsignedCourses(courses) {
        courses.forEach(function (course, index) {
            $('#title' + index).text(course.Courssetitle);
            $('#teacher_name' + index).text(course.teacher_name);
            $('#Description' + index).text(course.coursseDiscription);
            $('#Duration' + index).text(course.coursseDuration);
        });
    }
//        success: function (response) {
//            var data = response.data;
//
//            if (!data) {
//                data = response.data2;
//            }
//
//            if (data && data.length > 0) {
//                data.forEach(function (userDetails, index) {
//                    $('#User_Name' + index).text(userDetails.User_Name);
//                    $('#E_mail' + index).text(userDetails.E_mail);
//                    $('#title' + index).text(userDetails.Courssetitle);
//                    $('#teacher_name' + index).text(userDetails.teacher_name);
//                    $('#Description' + index).text(userDetails.coursseDiscription);
//                    $('#Duration' + index).text(userDetails.coursseDuration);
//                });
//            }
//        }
    $('.available_course').click(function () {
//        alert("from available courses");
        var course = $(this);
        var courseId = course.data("id");
        alert("The ID of the course: " + courseId);
        // Send AJAX request with courseId
        $.ajax({
            url: 'CourseProInsertion',
            method: 'POST',
            data: {"courseId": courseId},
            success: function (response) {

//                alert("AJAX call successful");
                console.log(response)
                $('.container1').append(course);
                course.removeClass('available_course').unbind("click");
//                 Handle success response
                course.attr("onclick", goToCoursePage(this));
            },
            error: function (xhr, status, error) {
                alert("AJAX call failed");
                // Handle error response
            }
        });
    });
    function goToCoursePage(clickedElement) {
        $("#parent").load("Lesson.html");
    }

    $(function () {
        $('#login-show').click(function () {
            $('#login-modal').fadeIn().css("display", "flex");
        });
    });
    $(function () {
        $('.close-modal').click(function () {
            $('#login-modal').fadeOut();
        });
    }
    );
});
function validateForm(event) {
    event.preventDefault();
    var password = document.getElementById("changedPass").value;
    var confirmPassword = document.getElementById("confirmchangedPass").value;
    if (password !== confirmPassword) {
        document.getElementById("error-message").textContent = "Passwords do not match.";
    } else {
        document.getElementById("error-message").textContent = "";
        // Perform further actions, such as submitting the form to the server
        changePas();
    }
}
function changePas() {
    var formData = {
        newPass: $("#changedPass").val(),
        confirmNewPass: $("#confirmchangedPass").val()
    };
    $.ajax({
        url: 'UpdatePass',
        type: 'POST',
        data: formData,
        success: function (data) {

            alert("Pass  " + data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("There was an error");
        }
    });
}
$("#passChange").submit(validateForm);
