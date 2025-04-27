
document.addEventListener("DOMContentLoaded", () => {
    Promise.all([
        fetch("includes/header.html").then(r => r.text()).then(html => {
            document.getElementById("header-placeholder")
                .insertAdjacentHTML("beforebegin", html);
        }),
        fetch("includes/footer.html").then(r => r.text()).then(html => {
            document.body.insertAdjacentHTML("beforeend", html);
        })
    ])
        .then(() => {
            initHeaderToggle();
        })
        .catch(console.error);
});

//scrolling header
function initHeaderToggle() {
    const header = document.querySelector("header");
    const showBtn = document.getElementById("show-header-btn");
    let lastScrollTop = 0;
    let headerVisible = true;

    window.addEventListener("scroll", () => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

        if (scrollTop > lastScrollTop && scrollTop > 100 && headerVisible) {
            header.style.transform = "translateY(-100%)";
            showBtn.classList.add('visible');
            header.style.opacity   = "0";
            showBtn.style.display  = "block";
            headerVisible = false;
        }
        lastScrollTop = Math.max(0, scrollTop);
    });

    showBtn.addEventListener("click", () => {
        header.style.transform = "translateY(0)";
        header.style.opacity   = "1";
        showBtn.style.display  = "none";
        showBtn.classList.remove('visible');
        headerVisible = true;
    });
}
