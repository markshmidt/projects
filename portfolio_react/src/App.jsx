// src/App.jsx
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header/Header.jsx";
import Home from "./pages/Home/Home.jsx";
import About from "./pages/About/About.jsx";
import Projects from "./pages/Projects/Projects.jsx";
import Contact from "./pages/Contact/Contact.jsx";
import Footer from "./components/Footer/Footer.jsx";
import "./index.css";
import { useHeaderScroll } from "./components/Header/headerScroll.js";
import MatrixPreloader from "./components/MatrixPreloader/MatrixPreloader.jsx";

function App() {
  useHeaderScroll();
  return (
    <BrowserRouter>
    <MatrixPreloader />
      <Header />
      
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/projects" element={<Projects />} />
        <Route path="/contact" element={<Contact />} />      </Routes>
        <Footer />
    </BrowserRouter>
  );
}

export default App;