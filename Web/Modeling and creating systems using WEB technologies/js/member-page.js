var closeButtons = document.querySelectorAll(".close-button");

closeButtons.forEach(function (button) {
    button.addEventListener("click", function () {
        var username = this.parentElement.getAttribute("data-username");
        var confirmDelete = confirm("Are you sure you want to delete the user with the username: " + username + "?");
    if (confirmDelete) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "../script/delete-member-script.php", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    alert("User deleted successfully");
                    location.reload();
                }
            };
            xhr.send("username=" + username);
        }
    });
});

function openEditPopup(editButton) {
    var memberItem = editButton.parentElement;
    var username = memberItem.getAttribute("data-username");
    var firstname = memberItem.getAttribute("data-firstname");
    var lastname = memberItem.getAttribute("data-lastname");
    var role = memberItem.getAttribute("data-role");
    var speciality = memberItem.getAttribute("data-speciality");

    document.getElementById("old-username").value = username;
    document.getElementById("username").value = username;
    document.getElementById("firstname").value = firstname;
    document.getElementById("lastname").value = lastname;
    document.getElementById("role").value = role;
    document.getElementById("speciality").value = speciality;
    document.getElementById("password").disabled = true;
    document.getElementById("submit").textContent = "Update Member";
    document.getElementById("popup-name").textContent = "Update Member";

    document.getElementById("overlay").style.display = "flex";
    document.getElementById("popup").style.display = "flex";
    document.getElementById("members-form").action = "../script/update-member-script.php";
}

function openAddPopup() {
    document.getElementById("username").value = "";
    document.getElementById("firstname").value = "";
    document.getElementById("lastname").value = "";
    document.getElementById("role").value = "";
    document.getElementById("speciality").value = "";
    document.getElementById("password").disabled = false;
    document.getElementById("submit").textContent = "Add Member"
    document.getElementById("popup-name").textContent = "Add Member";

    document.getElementById("overlay").style.display = "flex";
    document.getElementById("popup").style.display = "flex";
    document.getElementById("members-form").action = "../script/add-member-script.php";
}