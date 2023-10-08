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
        confirmPassword:$("#confirm_password").val()
    };
    $.ajax({
        url: 'GetCon',
        type: 'POST',
        data: formData,
        success: function (data) {
            if (data.toString() === "Exit") {
                document.getElementById("error-message").textContent = "E_mail not available create another one";
            } else if(!data.null){
                document.getElementById("error-message").textContent = data.toString();
            }else{
     alert('The name is: ' + data);
//                window.location.replace('osamapage.html');
            }

        },
        error: function () {
            alert('Error');
        }
    });
}

$('#signup-form').submit(validateForm);


//Check for the Sign In 
function validateSigninForm(event) {
    event.preventDefault();
    var name = document.getElementById("username").value;
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
        user_name: $("#username").val(),
        password: $("#password").val()
    };
    $.ajax({
        url: 'CheckSignIn',
        type: 'POST',
        data: formData,
        success: function (data) {
            if (!data.null) {
                alert(data.toString());
//                window.location.replace('osamapage.html');
            } else if (data === "flase") {
//                $("#error-message").textContent = "Check You Name Or Password.";
                document.getElementById("error-message").textContent = "Check You Name or Password";
//                alert("Not Exit");
            } else {
                alert("There is error");
            }
        },
        error: function () {
            alert('Error');
        }
    });
}
$("#signin-form").submit(validateSigninForm);