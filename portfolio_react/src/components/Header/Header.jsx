import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';
import { useEffect, useRef } from 'react';
import { useHeaderScroll } from './headerScroll.js';


const Header = () => {
    const headerRef = useRef(null);
    const showBtnRef = useRef(null);
  
    useEffect(() => {
      const header = headerRef.current;
      const showBtn = showBtnRef.current;
  
      if (!header || !showBtn) return;
  
      let lastScrollTop = 0;
      let headerVisible = true;
  
      const onScroll = () => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
  
        if (scrollTop > lastScrollTop && scrollTop > 100 && headerVisible) {
          header.style.transform = "translateY(-100%)";
          header.style.opacity = "0";
          showBtn.classList.add("visible");
          showBtn.style.display = "block";
          headerVisible = false;
        } else if (scrollTop < lastScrollTop && !headerVisible) {
          header.style.transform = "translateY(0)";
          header.style.opacity = "1";
          showBtn.classList.remove("visible");
          showBtn.style.display = "none";
          headerVisible = true;
        }
  
        lastScrollTop = Math.max(0, scrollTop);
      };
  
      window.addEventListener("scroll", onScroll);
      return () => window.removeEventListener("scroll", onScroll);
    }, []);
  
    const showHeader = () => {
      if (headerRef.current && showBtnRef.current) {
        headerRef.current.style.transform = "translateY(0)";
        headerRef.current.style.opacity = "1";
        showBtnRef.current.style.display = "none";
        showBtnRef.current.classList.remove("visible");
      }
    };
  return (
    <>
    <header className="header" ref={headerRef}>
  <div className="header-container">
    <a
      href="/assets/files/MariiaShmidtResume.pdf"
      download
      className="logo-download"
      title="Download My Resume"
    >
      <img
        src="/assets/img/logo.png"
        alt="Download Resume"
        className="logo-img"
      />
    </a>
    <nav>
      <ul className="nav-links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/about">About</Link></li>
        <li><Link to="/projects">Projects</Link></li>
        <li><Link to="/contact">Contact</Link></li>
      </ul>
    </nav>
  </div>
</header>

    <button
        id="show-header-btn"
        ref={showBtnRef}
        onClick={showHeader}
        aria-label="Show Header"
        style={{ display: 'none' }}
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#fff" viewBox="0 0 16 16">
          <path fillRule="evenodd" d="M8 3.293l5.354 5.353-.708.708L8 4.707l-4.646 4.647-.708-.708z" />
        </svg>
      </button>
    </>
    
  );
};

export default Header;
