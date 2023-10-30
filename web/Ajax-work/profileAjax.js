$(document).ready(function () {

// making ajax for getting the data from the courses servlet 
    $.ajax({
        url: 'Coursses',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            var signedInCourses = response.data || [];

            if (signedInCourses.length > 0) {
                displayCourses(signedInCourses);
            }
        }
    });

    //making ajax request for getting all courses and display it
    $.ajax({
        url: 'NewCoursses',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            var signedInCourses = response.data || [];

            if (signedInCourses.length > 0) {
//                displayCourses(signedInCourses);
                signedInCourses.forEach(function (course, index) {
                    $('#User_Name' + index).text(course.User_Name);
                    $('#E_mail' + index).text(course.E_mail);
                    $('#co_title ' + index).text(course.Courssetitle);
                    $('#co_course' + index).attr('data-id', course.courseId);
                    $('#co_courseImg'+index).attr('src', course.courseImg);
                    $('#co_teacher_name' + index).text(course.teacher_name);
                    $('#co_Description' + index).text(course.coursseDiscription);
                    $('#co_Duration' + index).text(course.coursseDuration);
//                    alert("The Course Id  is   "+course.courseId);
                });
            }
        }
    });

    //      function  for displaing signed courses at div of user's courses
    function displayCourses(courses) {
        courses.forEach(function (course, index) {
            $('#User_Name' + index).text(course.User_Name);
            $('#E_mail' + index).text(course.E_mail);
            $('#title ' + index).text(course.Courssetitle);
            $('#course' + index).attr('data-id', course.courseId);
            $('#courseImg' + index).attr('src', course.courseImg);
            $('#course' + index).show();
            $('#teacher_name' + index).text(course.teacher_name);
            $('#Description' + index).text(course.coursseDiscription);
            $('#Duration' + index).text(course.coursseDuration);
        });
    }
    // for moving the courses from new courses to your courses
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

$('.available_co').click(function () {
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