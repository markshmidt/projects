
document.addEventListener('DOMContentLoaded', () => {
    const timelineItems = document.querySelectorAll('.timeline-item');

    timelineItems.forEach(item => {
        item.addEventListener('click', () => {
            // Toggle the "expanded" state
            const isExpanded = item.getAttribute('data-expanded') === 'true';
            item.setAttribute('data-expanded', !isExpanded);

            // Toggle visibility of the 'click here' text
            const clickHereText = item.querySelector('.click-here');
            if (clickHereText) {
                clickHereText.style.display = isExpanded ? 'block' : 'none';
            }

            // Collapse other items
            timelineItems.forEach(otherItem => {
                if (otherItem !== item) {
                    otherItem.setAttribute('data-expanded', 'false');
                    const otherClickHereText = otherItem.querySelector('.click-here');
                    if (otherClickHereText) {
                        otherClickHereText.style.display = 'block';
                    }
                }
            });
        });
    });
});

window.addEventListener("scroll", function () {
    let header = document.querySelector("header");
    if (window.scrollY > 50) {
        header.classList.add("header-scrolled");
    } else {
        header.classList.remove("header-scrolled");
    }
});


//slide animation
document.addEventListener("DOMContentLoaded", function() {
    const canvas = document.getElementById("matrixCanvas");
    const ctx = canvas.getContext("2d");

    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    const letters = "01";
    const fontSize = 20;
    const columns = Math.floor(canvas.width / fontSize);

    // array for drops and its speed
    const drops = [];
    for (let i = 0; i < columns; i++) {
        drops[i] = {
            y: 1,
            speed: Math.random() * 5 + 5
        };
    }

    const colors = [
        "#00b7ff", "#0099ff", "#3366ff",
        "#6633ff", "#9933ff", "#cc33ff", "#aa00ff"
    ];

    let fadeOut = false;
    let canvasOpacity = 1;

    function drawMatrix() {
        ctx.fillStyle = `rgba(0, 0, 0, ${fadeOut ? 0.00 : 0.1})`;
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.font = `${fontSize}px monospace`;

        for (let i = 0; i < drops.length; i++) {
            const text = letters[Math.floor(Math.random() * letters.length)];
            const color = colors[Math.floor(Math.random() * colors.length)];

            ctx.fillStyle = color;
            ctx.shadowBlur = 10;
            ctx.shadowColor = color;

            ctx.fillText(text, i * fontSize, drops[i].y * fontSize);

            ctx.shadowBlur = 0;
            ctx.shadowColor = "transparent";

            drops[i].y += drops[i].speed;


            if (drops[i].y * fontSize > canvas.height && Math.random() > 0.975) {
                drops[i].y = 0;
                drops[i].speed = Math.random() * 3 + 2;
            }
        }

        if (fadeOut) {
            canvasOpacity -= 0.02;
            canvas.style.opacity = canvasOpacity;
            if (canvasOpacity <= 0) {
                clearInterval(matrixInterval);
                document.getElementById('preloader').style.display = 'none';
            }
        }
    }

    const matrixInterval = setInterval(drawMatrix, 20);

    setTimeout(() => {
        fadeOut = true;
        document.getElementById('main').style.opacity = '1';
    }, 500);
});
function typeText(element, text, speed = 50) {
    let index = 0;
    element.textContent = '';

    function addLetter() {
        if (index < text.length) {
            element.textContent += text[index];
            index++;
            setTimeout(addLetter, speed);
        }
    }

    addLetter();
}