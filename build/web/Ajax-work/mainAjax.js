$('#signup-form').submit(validateForm);
$("#signin-form").submit(validateSigninForm);



function validateForm(event) {
    event.preventDefault();

    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm_password").value;

    if (password !== confirmPassword) {
        document.getElementById("error-message").textContent = "Passwords do not match.";
    } else {
        document.getElementById("error-message").textContent = "";
        // Perform further actions, such as submitting the form to the server
        submitForm();
    }
}
function submitForm() {
    var formData = {
        name: $("#username").val(),
        email: $("#email").val(),
        age: $("#user_age").val(),
        address: $("#user_address").val(),
        phoneNo: $("#Phone_No").val(),
        password: $("#password").val(),
        confirmPassword: $("#confirm_password").val()
    };
    $.ajax({
        url: 'GetCon',
        type: 'POST',
        data: formData,
        success: function (data) {
//            alert("The responese    " + data);
            if (data === "Exit") {
//                 alert("The Message   "+data);
                $("#error-message").html("E_mail not available create another one");
            } else if (data === "new") {
//                alert('The name is: ' + data);
                $("#parent").load("osamapage.html");
                
            } else {
//                alert('The name is: ' + data);
                $("#error-message").html(data.toString());
//                $("#parent").load("osamapage.html");
//                window.location.href = "osamapage.html";
            }
        },
        error: function () {
            alert('Error');
        }
    });
}




//Check for the Sign In 
function validateSigninForm(event) {
    event.preventDefault();
    var name = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    if (name === null) {
        document.getElementById("error-message").textContent = "Enter Your Name.";
    } else if (password === null) {
        document.getElementById("error-message").textContent = "Enter Your Password.";
    } else {
        document.getElementById("error-message").textContent = "";
        // Perform further actions, such as submitting the form to the server
        signInForm();
    }
}
function signInForm() {
    var formData = {
        email: $("#email").val(),
        password: $("#password").val()
    };
    $.ajax({
        url: 'CheckSignIn',
        type: 'POST',
        data: formData,
        success: function (data) {
            if (!data.null && data.length !== 0) {
                if (!data.null && data == "success") {
                    $("#parent").load("osamapage.html");
//                    document.getElementById("parent").innerHTML = "osamapage.html";
//                    window.location.replace('osamapage.html');
//                alert("Data ready");
                } else if (data === "flase") {
                    $("#error-message").textContent = "Check You Name Or Password.";
                    window.location.replace('SignUp.html');
                    alert("Not Exit");
                } else {
                    document.getElementById("error-message").textContent = data.toString();
                }
            }
        },
        error: function () {
            alert('Error');
        }
    });
}