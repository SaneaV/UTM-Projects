$(document).ready(function() {
    $("#feedbackForm").submit(function(event) {
        event.preventDefault();

        var formData = $(this).serialize();

        $.ajax({
            type: "POST",
            url: $(this).attr("action"),
            data: formData,
            success: function (data) {
                if (data === "success") {
                    alert("Feedback successfully added!");
                    $('#feedbackText').val('');
                } else {
                    alert("Feedback addition failed.");
                }
            },
            error: function (xhr, status, error) {
                alert("AJAX error: " + error);
            }
        });
    });
});


document.getElementById('myFeedbacks').addEventListener('click', function() {
    window.location.href = '../page/feedback-about-me-page.php'; 
});