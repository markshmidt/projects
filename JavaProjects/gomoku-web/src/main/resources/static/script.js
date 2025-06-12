document.addEventListener("DOMContentLoaded", () => {
    const modeSelector = document.getElementById("modeSelector");
    const aiBtn = document.getElementById("modeAiBtn");
    const pvpBtn = document.getElementById("modePvpBtn");

    if (modeSelector) {
        modeSelector.style.display = "flex";
    }
    if (aiBtn) aiBtn.addEventListener("click", () => {
        window.location.href = window.location.pathname.replace(/[^/]*$/, '') + 'ai';
    });

    if (pvpBtn) pvpBtn.addEventListener("click", () => {
        window.location.href = window.location.pathname.replace(/[^/]*$/, '') + 'pvp';
    });
    const rulesModal = document.getElementById("rulesModal");
    const aboutModal = document.getElementById("aboutModal");

    const rulesBtn = document.getElementById("nav-rules");
    const aboutBtn = document.getElementById("nav-about");

    const closeBtns = document.querySelectorAll(".closeModal");

    if (rulesBtn && rulesModal) {
        rulesBtn.addEventListener("click", () => {
            rulesModal.classList.remove("hidden");
        });
    }

    if (aboutBtn && aboutModal) {
        aboutBtn.addEventListener("click", () => {
            aboutModal.classList.remove("hidden");
        });
    }

    closeBtns.forEach(btn => {
        btn.addEventListener("click", () => {
            rulesModal.classList.add("hidden");
            aboutModal.classList.add("hidden");
        });
    });
});
