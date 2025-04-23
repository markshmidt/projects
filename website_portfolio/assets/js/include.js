document.addEventListener("DOMContentLoaded", () => {
    fetch("includes/header.html")
        .then(response => response.text())
        .then(data => {
            const headerPlaceholder = document.getElementById("header-placeholder");
            if (headerPlaceholder) {
                headerPlaceholder.innerHTML = data;
            } else {
                console.error('Element with ID "header-placeholder" not found.');
            }
        })
        .catch(error => console.error("Error loading header:", error));

    fetch("includes/footer.html")
        .then(response => response.text())
        .then(data => {
            const footerPlaceholder = document.getElementById("footer-placeholder");
            if (footerPlaceholder) {
                footerPlaceholder.innerHTML = data;
            } else {
                console.error('Element with ID "footer-placeholder" not found.');
            }
        })
        .catch(error => console.error("Error loading footer:", error));
});
