console.log(">>> Starting mail-server…");

const bodyParser = require('body-parser');
const express = require('express');
const nodemailer = require('nodemailer');
const cors = require('cors');

const app = express();
const PORT = 5000;
app.use(express.json())

app.use(cors({ origin: 'https://mariia-shmidt-portfolio.com' }));
app.use(bodyParser.json());


const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'masha.shmidt.04@gmail.com', 
    pass: 'vmns pzpk fqon zfel'
  }
});
app.post('/send', (req, res) => {
  const { name, email, message } = req.body;
  console.log('>>> Received message:', { name, email, message });

  const mailOptions = {
    from: email,
    to: 'masha.shmidt.04@gmail.com',
    subject: `Message from ${name} using Portfolio website`,
    text: message
  };

  transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
      console.error('Error sending mail:', error);
      res.status(500).send('Something went wrong.');
    } else {
      console.log('>>> Mail sent:', info.response);
      res.status(200).send('Email sent successfully!');
    }
  });
});
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
