<?php include('includes/header.php'); ?>
<main class="contacts py-5">
    <h1 class="contact-title">Contact Me</h1>

    <!-- Contact Introduction -->
    <section class="contact-intro">
        <p>I am always open for new collaborations and opportunities. If you want to stay in touch, you can find me here:</p>
    </section>

    <!-- Contact Information -->
    <section class="contact-info text-center mb-5">
        <p>Email: <a href="mailto:masha.shmidt.04@gmail.com">masha.shmidt.04@gmail.com</a></p>
        <p>GitHub: <a href="https://github.com/markshmidt" target="_blank">github.com/markshmidt</a></p>
        <p>LinkedIn: <a href="https://www.linkedin.com/in/mariia-shmidt-14a084324/" target="_blank">linkedin.com/in/mariia-shmidt-14a084324/</a></p>
    </section>

    <!-- Contact Form -->
    <section class="contact-form">
        <form action="send_email.php" method="POST">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>

            <div class="mb-3">
                <label for="message" class="form-label">Message</label>
                <textarea class="form-control" id="message" name="message" rows="4" required></textarea>
            </div>

            <button type="submit" class="btn btn-primary">Send Message</button>
        </form>
    </section>
</main>
<?php include('includes/footer.php'); ?>
