<?php
require_once 'classes/User.php';
require_once 'db.config.php';
require_once 'pdo.php';

session_start();

$config = include('db.config.php');
if (!isset($config['default']['host'], $config['default']['username'], $config['default']['password'], $config['default']['dbname'])) {
    die("Database configuration is incomplete. Please check db.config.php.");
}


// form submission
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $user = new User($pdo); // instance of the User class

    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // register the user
    if ($user->registerUser($username, $email, $password)) {
        // if registration successful, redirect to login
        $_SESSION['message'] = "Registration successful! Please login.";
        header("Location: login.php");
        exit();
    } else {

        $error = "Registration failed. Username or email already exists.";
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Register</h1>
<!-- error message if registration fails -->
<?php if (isset($error)) echo "<p style='color:red;'>$error</p>"; ?>


<form method="POST">
    <label>Username: <input type="text" name="username" required></label><br>
    <label>Email: <input type="email" name="email" required></label><br>
    <label>Password: <input type="password" name="password" required></label><br>
    <button type="submit">Register</button>
</form>
<p>Already have an account? <a href="login.php">Login</a></p>
</body>
</html>
