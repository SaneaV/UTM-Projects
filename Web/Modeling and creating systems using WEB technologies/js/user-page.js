document.addEventListener("DOMContentLoaded", function () {
    const profileImage = document.getElementById("profileImage");
    const photoUpload = document.getElementById("photoUpload");

    photoUpload.addEventListener("change", function () {
        const file = photoUpload.files[0];
        if (file) {
            const reader = new FileReader();

            reader.onload = function (event) {
                profileImage.src = event.target.result;
            };

            reader.readAsDataURL(file);
        }
    });
});
