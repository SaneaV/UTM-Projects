var closeButtons = document.querySelectorAll(".close-button");

closeButtons.forEach(function (button) {
    button.addEventListener("click", function () {
        var id = this.parentElement.querySelectorAll(".news-id");
        var title = this.parentElement.querySelectorAll(".news-title");

        if (id.length > 0) {
            var firstNewsElement = id[0];
            var idText = firstNewsElement.textContent;
        }
        if (id.length > 0) {
            var firstNewsElement = title[0];
            var titleText = firstNewsElement.textContent;
        }
        var confirmDelete = confirm("Are you sure you want to delete the news with the name: " + titleText + "?");
    if (confirmDelete) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "../script/delete-topic-script.php", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    alert("News deleted successfully");
                    location.reload();
                }
            };
            xhr.send("id=" + idText);
        }
    });
});