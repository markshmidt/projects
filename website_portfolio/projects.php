<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projects</title>
    <link rel="stylesheet" href="/assets/css/styles.css">
</head>

<?php include('includes/header.php'); ?>
<main class="projects-container">
    <h1>My Projects</h1>
    <div class="projects-description">
        Here are some of the projects I have worked on during my educational journey. My portfolio contains web applications that were build in a team of three people, for example, Airline Reservation System, and programs developed during the individual studying process like Tic-tac-toe game.
        You can find all my projects on <a href="https://github.com/markshmidt/projects" style="color:#00b7ff;">GitHub</a>.
    </div>

    <div class="project-list">
        <!-- Kamisado Game -->
        <div class="project-card">
            <img src="assets/img/kamisado.gif" alt="Kamisado Game">
            <div class="project-info">
                <h2>Kamisado Game</h2>
                <p>Interactive digital version of the strategic board game Kamisado, using Python object-oriented programming and Pygame library. </p>
                <a href="https://github.com/markshmidt/projects/tree/main/PythonProjects/kamisado" class="project-btn" target="_blank">See Code</a>
            </div>
        </div>

        <!-- Racing Game -->
        <div class="project-card">
            <img src="assets/img/project-maven.gif" alt="Racing Game">
            <div class="project-info">
                <h2>Racing Game</h2>
                <p>A fast-paced JavaFX racing game built with the JavaRush graphics engine with focus on dependencies and plugins.</p>
                <a href="https://github.com/markshmidt/projects/tree/main/JavaProjects/project-maven" class="project-btn" target="_blank">See Code</a>
            </div>
        </div>

        <!-- Tic-Tac-Toe -->
        <div class="project-card">
            <img src="assets/img/tic-tac-toe.gif" alt="Tic Tac Toe">
            <div class="project-info">
                <h2>Tic-Tac-Toe Game</h2>
                <p>A strategic Tic-Tac-Toe game on Tomcat using servlets and JSP as well as working with HTTP requests and responses.</p>
                <a href="https://github.com/markshmidt/projects/tree/main/JavaProjects/project-servlet" class="project-btn" target="_blank">See code</a>
            </div>
        </div>

        <!-- Management System -->
        <div class="project-card">
            <img src="assets/img/management.gif" alt="HRM System">
            <div class="project-info">
                <h2>HRM & Payroll System</h2>
                <p>Comprehensive JavaFX-based HRM and Payroll System with employee record management and generating reports.</p>
                <a href="https://github.com/markshmidt/projects/tree/main/JavaProjects/project-management" class="project-btn" target="_blank">See code</a>
            </div>
        </div>

        <!-- Airline Reservation System -->
        <div class="project-card">
            <img src="assets/img/oop2.gif" alt="Airline Reservation">
            <div class="project-info">
                <h2>Airline Reservation System</h2>
                <p>An OOP-based Airline Reservation System with database storage and exception handling mechanisms.</p>
                <a href="https://github.com/markshmidt/projects/tree/main/JavaProjects/project-maven" class="project-btn" target="_blank">See code</a>
            </div>
        </div>

        <!-- RPG Admin Panel -->
        <div class="project-card">
            <img src="assets/img/adminpanel.gif" alt="RPG Admin Panel">
            <div class="project-info">
                <h2>RPG Admin Panel</h2>
                <p>A CRUD-based web app for managing online game player accounts using JS and jQuery.</p>
                <a href="https://github.com/markshmidt/projects/tree/main/JavaProjects/project-front" class="project-btn" target="_blank">See code</a>
            </div>
        </div>

        <!-- PHP Voting Application -->
        <div class="project-card">
            <img src="assets/img/vote.gif" alt="Voting App">
            <div class="project-info">
                <h2>Voting Application</h2>
                <p>Initial voting application structured with OOP principles using PHP with MySQL database</p>
                <a href="https://github.com/markshmidt/projects/tree/main/JavaProjects/project-management" class="project-btn" target="_blank">See code</a>
            </div>
        </div>
    </div>
    <br>
    <br>
</main>
<?php include('includes/footer.php'); ?>
