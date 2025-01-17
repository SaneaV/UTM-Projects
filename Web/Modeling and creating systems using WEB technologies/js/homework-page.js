var submitForms = document.querySelectorAll('.submit-homework');

submitForms.forEach(function (submitForm) {
    submitForm.querySelector('input[type="file"]').addEventListener('change', function () {
        var submitButton = submitForm.querySelector('input[type="submit"]');
        if (this.files.length > 0) {
            submitButton.removeAttribute('disabled');
        } else {
            submitButton.setAttribute('disabled', 'disabled');
        }
    });
});

document.getElementById('internsHomeworkPage').addEventListener('click', function() {
    window.location.href = 'homework-mentor-page.php'; 
});

var closeButtons = document.querySelectorAll(".close-button");

closeButtons.forEach(function (button) {
    button.addEventListener("click", function () {
        var id = this.parentElement.querySelectorAll(".homework-id");
        var title = this.parentElement.querySelectorAll("#homework-title");

        if (id.length > 0) {
            var firstNewsElement = id[0];
            var idText = firstNewsElement.textContent;
        }
        if (id.length > 0) {
            var firstNewsElement = title[0];
            var titleText = firstNewsElement.textContent;
        }
        var confirmDelete = confirm("Are you sure you want to delete the homework with the name: " + titleText + "?");
    if (confirmDelete) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "../script/delete-homework-script.php", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    alert("Homework deleted successfully");
                    location.reload();
                }
            };
            xhr.send("id=" + idText);
        }
    });
});