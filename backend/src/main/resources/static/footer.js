document.addEventListener("DOMContentLoaded", function () {
    fetch("/public/components/footer.html")
        .then(response => response.text())
        .then(data => {
            document.getElementById("footer-content").innerHTML = data;
        })
        .catch(error => console.error("Error loading header:", error));
});
