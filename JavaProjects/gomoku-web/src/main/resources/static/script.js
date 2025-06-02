document.addEventListener("DOMContentLoaded", () => {
    const welcomeTitle = document.getElementById("welcomeTitle");
    const playButton = document.getElementById("playButton");
    const modeSelector = document.getElementById("modeSelector");
    const aiBtn = document.getElementById("modeAiBtn");
    const pvpBtn = document.getElementById("modePvpBtn");

    // Start the game
    playButton.onclick = () => {
        playButton.style.display = "none";
        welcomeTitle.style.display = "none";
        modeSelector.style.display = "block";
    };

    // Going to the chosen game mode
    aiBtn.onclick = () => window.location.href = "ai.html";
    pvpBtn.onclick = () => window.location.href = "pvp.html";
});