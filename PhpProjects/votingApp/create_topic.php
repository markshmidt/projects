<?php
require_once 'header.php';
require_once 'pdo.php';

if (!isset($_SESSION['user_id'])) {
    header('Location: login.php');
    exit();
}

require_once 'db.config.php';
require_once 'classes/Topic.php';

// database connection
$config = include('db.config.php');
try {
    $pdo = new PDO(
        "mysql:host={$config['default']['host']};dbname={$config['default']['dbname']}",
        $config['default']['username'],
        $config['default']['password']
    );
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Database connection failed: " . $e->getMessage());
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $title = $_POST['title'];
    $description = $_POST['description'];
    $user_id = $_SESSION['user_id'];

    $topic = new Topic($pdo);
    $topic->createTopic($user_id, $title, $description);

    header('Location: dashboard.php');
    exit();
}

?>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <title>Create Topic</title>
    <style>
        <?php if (isset($_SESSION['theme']) && $_SESSION['theme'] === 'dark'): ?>
        body {
            background-color: #333;
            color: #fff;
        }

        .container{
            background-color: #444444;
            text-align: center;

        }
        button:hover {
            background-color: #e68a00;
        }
        a {
            color: #ff9800;
        }
        <?php endif; ?>
    </style>
</head>
<body class="<?php echo htmlspecialchars(getTheme()); ?>">
<div class="container">
    <h2>Create a New Topic</h2>
    <form method="POST" action="create_topic.php">
        <label for="title">Title:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="description">Description:</label><br>
        <textarea id="description" name="description" required></textarea><br><br>

        <button type="submit">Create Topic</button>
    </form>
    <br>
    <a href="dashboard.php">Back to Dashboard</a>
</div>
</body>
</html>
