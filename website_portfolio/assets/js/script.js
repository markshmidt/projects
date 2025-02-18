
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

