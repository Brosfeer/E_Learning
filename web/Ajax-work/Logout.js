$('#signout-button').click(function () {
    // Make an AJAX request to delete the session
    $.ajax({
        url: 'checksession',
        method: 'Get',
        success: function (data) {
            // Session deleted successfully
            if (data == "oky") {
                alert("You logeed out");
                window.location.href = 'index.html'; // Redirect to the index page

            }
        },
        error: function () {
            // Handle error case
            console.log('Error deleting session');
        }
    });
});