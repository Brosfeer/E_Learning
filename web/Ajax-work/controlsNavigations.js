$(".navigate").click(function () {
    var element = $(this);
    var identifier = element.data('id');

    // Perform different events based on the identifier
    switch (identifier) {
        case 'home':
            window.location.href = ("index.html");
            break;
        case 'about':
            // Event for the 'about' element
            $("#parent").load("about.html");
//                    console.log('Clicked About');
            break;
        case 'courses':
            // Event for the 'courses' element
            $("#parent").load("courses.html");
//                    console.log('Clicked Courses');
            break;
        case 'ourteam':
            // Event for the 'ourteam' element
            $("#parent").load("team.html");
//                    console.log('Clicked Team');
            break;
        case 'contact':
            // Event for the 'contact' element
            $("#parent").load("contact.html");
//                    console.log('Clicked Contact');
            break;
            case 'login':
            // Event for the 'Login' element
            $("#parent").load("Login.html");
//                    console.log('Clicked Login');
            break;
            // Add more cases for other identifiers
    }
});