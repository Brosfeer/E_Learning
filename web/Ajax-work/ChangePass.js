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