<?php
require_once 'classes/User.php';
require_once 'db.config.php';
require_once 'pdo.php';

session_start();
$config = include('db.config.php');

if (!isset($config['default']['host'], $config['default']['username'], $config['default']['password'], $config['default']['dbname'])) {
    die("Database configuration is incomplete. Please check db.config.php.");
}


//login logic
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $user = new User($pdo);

    $username = trim($_POST['username']);
    $password = $_POST['password'];

    if (empty($username) || empty($password)) {
        $error = "Username and password are required.";
    } else {
        $userId = $user->authenticateUser($username, $password);

        if ($userId) {
            // Store user information in the session
            $_SESSION['username'] = $username;
            $_SESSION['user_id'] = $userId;

            header("Location: dashboard.php");
            exit();
        } else {
            $error = "Invalid username or password.";
        }
    }
}

?>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<?php if (isset($error)) echo "<p style='color:red;'>$error</p>"; ?>
<form method="POST">
    <label>Username: <input type="text" name="username" required></label><br>
    <label>Password: <input type="password" name="password" required></label><br>
    <button type="submit">Login</button>
</form>
<p>Don't have an account? <a href="register.php">Register</a></p>
</body>
</html>
