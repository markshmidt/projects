import React from "react";
import "./Home.css";
import profileImage from "../../../public/assets/img/profile.png";

const Home = () => {
  return (
    
    <main className="home-container" id="mainContent">
        
      <div className="content">
        <div className="profile">
          <img src={profileImage} alt="Mari Shmidt" />
        </div>
        <div className="info">
          <p>Hi, I'm</p>
          <h1>Mari Shmidt,</h1>
          <p>
            an international student in Toronto and a junior software developer.
            <br />
            <br />
            On my portfolio website you can learn more about me, see the
            projects I have worked on and contact me for future collaborations!
            Welcome!
          </p>
          <div className="buttons">
            <a href="/about" className="btn">About me</a>
            <a href="/projects" className="btn">My projects</a>
            <a href="/contact" className="btn">Contact me</a>
          </div>
        </div>
      </div>
    </main>
  );
};

export default Home;
