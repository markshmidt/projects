// src/components/Projects/Projects.jsx
import React from "react";
import "./Projects.css";

const projectsData = [
  {
    title: "Kamisado Game",
    description: "Interactive digital version of the strategic board game Kamisado, using Python object-oriented programming and Pygame library.",
    image: "../public/assets/img/kamisado.gif",
    github: "https://github.com/markshmidt/projects/tree/main/PythonProjects/kamisado",
  },
  {
    title: "Racing Game",
    description: "A fast-paced JavaFX racing game built with the JavaRush graphics engine with focus on dependencies and plugins.",
    image: "../public/assets/img/project-maven.gif",
    github: "https://github.com/markshmidt/projects/tree/main/JavaProjects/project-maven",
  },
  {
    title: "Tic-Tac-Toe Game",
    description: "A strategic Tic-Tac-Toe game on Tomcat using servlets and JSP as well as working with HTTP requests and responses.",
    image: "public/assets/img/tic-tac-toe.gif",
    github: "https://github.com/markshmidt/projects/tree/main/JavaProjects/project-servlet",
  },
  {
    title: "HRM & Payroll System",
    description: "Comprehensive JavaFX-based HRM and Payroll System with employee record management and generating reports.",
    image: "../public/assets/img/management.gif",
    github: "https://github.com/markshmidt/projects/tree/main/JavaProjects/project-management",
  },
  {
    title: "Airline Reservation System",
    description: "An OOP-based Airline Reservation System with database storage and exception handling mechanisms.",
    image: "../public/assets/img/oop2.gif",
    github: "https://github.com/markshmidt/projects/tree/main/JavaProjects/project-maven",
  },
  {
    title: "RPG Admin Panel",
    description: "A CRUD-based web app for managing online game player accounts using JS and jQuery.",
    image: "../public/assets/img/adminpanel.gif",
    github: "https://github.com/markshmidt/projects/tree/main/JavaProjects/project-front",
  },


];

const Projects = () => {
  return (
    <section className="projects-container">
      <h1>My Projects</h1>
      <div className="projects-description">
      Here are some of the projects I have worked on during my educational journey. My portfolio contains web applications that were build in a team of three people, for example, Airline Reservation System, and programs developed during the individual studying process like Tic-tac-toe game. 
      You can find all my projects on <a href="https://github.com/markshmidt/projects" style={{ color: '#00b7ff' }}>GitHub</a>..
      </div>
      <div className="project-list">
        {projectsData.map((project, index) => (
          <div className="project-card" key={index}>
            <img src={project.image} alt={project.title} />
            <div className="project-info">
              <h2>{project.title}</h2>
              <p>{project.description}</p>
              <a className="project-btn" href={project.github} target="_blank" rel="noopener noreferrer">
                View on GitHub
              </a>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default Projects;
