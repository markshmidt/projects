import { useEffect } from 'react';

export const useHeaderScroll = () => {
  useEffect(() => {
    const header = document.querySelector("header");
    const showBtn = document.getElementById("show-header-btn");
    let lastScrollTop = 0;
    let headerVisible = true;

    const onScroll = () => {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

      if (scrollTop > lastScrollTop && scrollTop > 100 && headerVisible) {
        header.style.transform = "translateY(-100%)";
        header.style.opacity = "0";
        showBtn.classList.add('visible');
        showBtn.style.display = "block";
        headerVisible = false;
      } else if (scrollTop < lastScrollTop && !headerVisible) {
        header.style.transform = "translateY(0)";
        header.style.opacity = "1";
        showBtn.classList.remove('visible');
        showBtn.style.display = "none";
        headerVisible = true;
      }

      lastScrollTop = Math.max(0, scrollTop);
    };

    window.addEventListener("scroll", onScroll);
    return () => window.removeEventListener("scroll", onScroll);
  }, []);
};
