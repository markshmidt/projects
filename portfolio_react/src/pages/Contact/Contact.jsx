import React from "react";
import "./Contact.css";
import ContactForm from "../../components/ContactForm/ContactForm";

const Contact = () => {
  return (
    <section >
      <h1 className="contact-title">Contact Me</h1>

      <div className="contact-intro">
        I am always open for new collaborations and opportunities!<br />
        If you want to stay in touch, you can find me here:
      </div>

      <div className="contact-info">
        <p>Email: <a href="mailto:masha.shmidt.04@gmail.com">masha.shmidt.04@gmail.com</a></p>
        <p>GitHub: <a href="https://github.com/markshmidt" target="_blank" rel="noopener noreferrer">github.com/markshmidt</a></p>
        <p>LinkedIn: <a href="https://linkedin.com/in/mariia-shmidt-14a084324" target="_blank" rel="noopener noreferrer">linkedin.com/in/mariia-shmidt-14a084324</a></p>
      </div>

      <ContactForm />
    </section>
  );
};

export default Contact;
