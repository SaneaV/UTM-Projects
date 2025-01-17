document.getElementById("year").innerHTML = new Date().getFullYear();

var scrollToTopButton = document.getElementById("scroll-to-top");
var addNewButton = document.getElementById("add-button")

window.onscroll = function () {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        if (typeof (addNewButton) != 'undefined' && addNewButton != null) {
            addNewButton.style.bottom = "60px";
        }
        scrollToTopButton.style.display = "block";
    } else {
        if (typeof (addNewButton) != 'undefined' && addNewButton != null) {
            addNewButton.style.bottom = "20px";
        }
        scrollToTopButton.style.display = "none";
    }
};

scrollToTopButton.onclick = function () {
    document.documentElement.scrollTop = 0;
};

function openPopup() {
    var overlay = document.getElementById("overlay");
    var popup = document.getElementById("popup");
    overlay.style.display = "flex";
    popup.style.display = "flex";
}

function closePopup() {
    var overlay = document.getElementById("overlay");
    overlay.style.display = "none";
}