$(document).ready(function () {

    $.ajax({
        url: 'GetingLessonData',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
// storing the data recieved from the servlet in variables
            var firstLesson = data[0];
            var secondLesson = data[1];
            var thirdtLesson = data[2];
            var youtubeUrl = firstLesson.videoUrl;
//            var youtubeUrl = "https://youtu.be/DAl3dbTnAgw?si=jxXot_hJBhdBvwn0";
            // Extract the video ID from the YouTube URL
            var videoId = getYoutubeVideoId(youtubeUrl);
//            alert(videoId);
// Construct the embed URLaq
            var embedUrl = "https://www.youtube.com/embed/" + videoId;
//            alert(embedUrl);
// Populate the HTML elements with the values
            $("#courseTitle").text(firstLesson.courseTitle);
            $("#courseDescription").text(firstLesson.courseDescription);
            $("#courseDuration").text(firstLesson.courseDuration);
            $("#lessonTitle").text(firstLesson.lessonTitle);
            $("#lessonTitle1").text(secondLesson.lessonTitle);
            $("#lessonTitle2").text(thirdtLesson.lessonTitle);
            $("#lessonContent").text(firstLesson.lessonContent);
            $("#videoIframe").attr("src", embedUrl);
//            $("#videoUrl")[0].load();
            $("#teacherName").text(firstLesson.teacherName);


            // trigerr the title click
            $('.lessonTitle').click(function () {
                var title = $(this);
                var titleId = title.data("id");
//                alert("The ID of the course: " + titleId);
                if (titleId === 1) {
                    var youtubeUrl = firstLesson.videoUrl;
                    // Extract the video ID from the YouTube URL
                    var videoId = getYoutubeVideoId(youtubeUrl);
//                      Construct the embed URL
                    var embedUrl = "https://www.youtube.com/embed/" + videoId;
                    $("#lessonContent").text(firstLesson.lessonContent);
                    $("#videoIframe").attr("src", embedUrl);
//                    $("#videoSource").attr("src", embedUrl);
//                    $("#videoUrl")[0].load();
                    $("#teacherName").text(firstLesson.teacherName);
                } else if (titleId === 2) {
                    var youtubeUrl = secondLesson.videoUrl;
                    // Extract the video ID from the YouTube URL
                    var videoId = getYoutubeVideoId(youtubeUrl);
//                      Construct the embed URL
                    var embedUrl = "https://www.youtube.com/embed/" + videoId;

                    $("#lessonContent").text(secondLesson.lessonContent);
                    $("#videoIframe").attr("src", embedUrl);
//                    $("#videoSource").attr("src", embedUrl);
//                    $("#videoUrl")[0].load();
                    $("#teacherName").text(secondLesson.teacherName);
                } else if (titleId === 3) {
                    var youtubeUrl = thirdtLesson.videoUrl;
                    // Extract the video ID from the YouTube URL
                    var videoId = getYoutubeVideoId(youtubeUrl);
//                      Construct the embed URL
                    var embedUrl = "https://www.youtube.com/embed/" + videoId;
                    $("#lessonContent").text(thirdtLesson.lessonContent);
                    $("#videoIframe").attr("src", embedUrl);
//                    $("#videoSource").attr("src", embedUrl);
//                    $("#videoUrl")[0].load();
                    $("#teacherName").text(thirdtLesson.teacherName);
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("The error  " + errorThrown.toString());
        }

    });
// Function to extract the video ID from the YouTube URL
    function getYoutubeVideoId(url) {
        var regex = /[?&]v=([^&#]+)/;
        var match = url.match(regex);
        return match ? match[1] : "";
    }
});