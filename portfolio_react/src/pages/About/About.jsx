import React from 'react';
import Timeline from '../../components/Timeline/Timeline';
import './About.css';


const About = () => {
  return (
    <main className="container_about">
      <section id="about-me" className="text-center mb-5">
        <h1>About Me</h1>
        <p>
          I am an international student in Toronto, Canada, pursuing a diploma in Computer Programming and Analysis at George Brown College with an expected graduation date in April 2026.
          <br /><br />
          Since childhood, I have loved playing chess, and my passion for calculating winning combinations for several moves ahead, analyzing the positions of pieces on the board, and coming up with the right strategy for the game led me to the IT field,
          where I can use my analytical mindset, attention to details, and problem-solving skills in developing algorithms and writing code.
          <br /><br />
          Through my academic projects, I honed my skills in building web applications using Python, Java, and C#, working with databases (MySQL, SQLite, Oracle), and exploring both backend (Javascript, Node.js, PHP) and frontend (HTML, CSS, Bootstrap, React, jQuery) technologies.
          Currently I am looking for an internship or an entry-level job where I can get hands-on useful experience and implement skills I have in a fast-paced environment. I aspire to contribute as a software developer, collaborating with dynamic teams to build impactful and user-friendly applications.
          <br /><br />
          When I’m not at my computer I like to spend my time reading, keeping fit by dancing, discussing cinematography with friends and crocheting :)
        </p>
      </section>
      <Timeline />
    </main>
  );
};

export default About;
