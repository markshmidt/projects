
import './Timeline.css';
import React, { useState } from 'react';

const timelineData = [
    {
      date: "Oct 2024 - May 2025",
      title: "Java Developer Intern",
      place: "JavaRush",
      details: [
        "Web application development on Java under mentorship of a senior developer.",
        "Exploring the main concepts and development tools, as well as design patterns and development methodologies with hands-on practice.",
        "Projects with Maven, JUnit, Mockito, Tomcat, MVC.",
        "Aiming to work on projects with Spring, Hibernate, SQL, Docker, etc., and receiving certification of completion the internship."
      ]
    },
    {
      date: "2023 - 2026",
      title: "George Brown College",
      place: "Computer Programming and Analysis program",
      details: [
        "Fullstack, software and mobile application development with project-based learning.",
        "GPA: 3.75",
        "Dean's List of Honor in Fall 2023, Winter 2024, Winter 2025.",
        "Aiming to receive Ontario College Advanced Diploma."
      ]
    },
    {
      date: "2022-2023",
      title: "Braemar College",
      place: "High school",
      details: [
        "Completed 12th grade with subjects like Calculus & Vectors, Advanced Functions, Python development, etc.",
        "Volunteered at Food Bank and Coldest Night of the Year.",
        "Participated in the creation of a podcast for the college YouTube channel and helped new students at the Open Doors Day.",
        "Winner of the college chess tournament :)"
      ]
    },
    {
      date: "2018-2020",
      title: "British College of Banking and Finance",
      place: "Business Management program",
      details: [
        "Explored business communication and organization concepts.",
        "Learned accounting basics, financial literacy, corporate culture.",
        "Received diploma of completion with distinction."
      ]
    }
  ];
  
  const Timeline = () => {
    const [expandedIndex, setExpandedIndex] = useState(null);
  
    const toggleExpand = (index) => {
      setExpandedIndex(prev => (prev === index ? null : index));
    };
  
    return (
      <section className="timeline-section" id="timeline">
        <h2 className="text-center mb-5">MY EDUCATIONAL JOURNEY</h2>
        <div className="timeline">
          {timelineData.map((item, index) => (
            <div
              key={index}
              className="timeline-item"
              data-expanded={expandedIndex === index}
              onClick={() => toggleExpand(index)}
            >
              <span className="timeline-date">{item.date}</span>
              <div className="timeline-content">
                <h3>{item.title}</h3>
                <p>{item.location}</p>
                <h6 className="click-here">
                  {expandedIndex === index ? "Click to hide details" : "Click here to see more information"}
                </h6>
                {expandedIndex === index && (
                  <div className="timeline-detail">
                    <ul>
                      {item.details.map((point, idx) => (
                        <li key={idx}>{point}</li>
                      ))}
                    </ul>
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
      </section>
    );
  };
export default Timeline;
