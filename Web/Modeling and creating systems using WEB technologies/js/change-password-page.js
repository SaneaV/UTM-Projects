document.getElementById("passwordForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const formData = new FormData(document.getElementById("passwordForm"));
    
    fetch("../script/change-password-script.php", {
        method: "POST",
        body: formData,
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            const statusMessage = document.getElementById("statusMessage");
            statusMessage.style.color = "red";
            statusMessage.textContent = data.error;
        } else if (data.success) {
            const statusMessage = document.getElementById("statusMessage");
            statusMessage.style.color = "green";
            statusMessage.textContent = data.success;        
        }
    })
    .catch(error => {
        const statusMessage = document.getElementById("statusMessage");
        statusMessage.style.color = "red";
        statusMessage.textContent = "An error occurred.";
    });
});
