import React from "react";
import "./Home.css";
import profileImage from "../../../public/assets/img/profile.png";
import { Link } from "react-router-dom";

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
          <Link to="/about" className="btn">About me</Link>
<Link to="/projects" className="btn">My projects</Link>
<Link to="/contact" className="btn">Contact me</Link>
          </div>
        </div>
      </div>
    </main>
  );
};

export default Home;
