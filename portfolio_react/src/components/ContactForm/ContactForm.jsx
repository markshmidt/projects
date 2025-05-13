import React, { useState } from 'react';
import './ContactForm.css';

const ContactForm = () => {
  const [formData, setFormData] = useState({ name: '', email: '', message: '' });
  const [status, setStatus] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    const formData = { name, email, message };
  
    try {
      const response = await fetch('http://your-server-ip:3001/send', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });
  
      if (response.ok) {
        alert('Message sent successfully!');
        setName('');
        setEmail('');
        setMessage('');
      } else {
        alert('Failed to send message');
      }
    } catch (err) {
      console.error('Error:', err);
      alert('Error sending message');
    }
  };
  

  return (
    <form className="contact-form" onSubmit={handleSubmit}>
      <label>Name</label>
      <input type="text" name="name" value={formData.name} onChange={handleChange} required />

      <label>Email</label>
      <input type="email" name="email" value={formData.email} onChange={handleChange} required />

      <label>Message</label>
      <textarea name="message" value={formData.message} onChange={handleChange} required />

      <button type="submit">Send</button>
      {status && <p className="form-status">{status}</p>}
    </form>
  );
};

export default ContactForm;
