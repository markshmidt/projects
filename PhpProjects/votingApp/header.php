<?php
session_start();

// Get the current theme from session
function getTheme() {
    return isset($_SESSION['theme']) ? $_SESSION['theme'] : 'light';
}

//Setting theme
function setTheme($theme) {
    $_SESSION['theme'] = $theme;
}

$currentTheme = getTheme();
if (isset($_GET['theme'])) {
    $theme = $_GET['theme'];
    setTheme($theme);
    header("Location: " . $_SERVER['PHP_SELF']);
    exit;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Voting App</title>
    <link rel="stylesheet" href="style.css">
</head>
<body class="<?php echo htmlspecialchars($currentTheme); ?>">
<header>
    <h1>Welcome to the Voting App</h1>
    <nav>
        <a href="create_topic.php">Create new topic</a>
        <a href="dashboard.php">Topics</a>
        <a href="profile.php">Profile</a>
        <a href="logout.php">Logout</a>
    </nav>
    <div>

        <a href="?theme=light" <?php if ($currentTheme === 'light') echo 'style="font-weight: bold;"'; ?>>Light</a> |
        <a href="?theme=dark" <?php if ($currentTheme === 'dark') echo 'style="font-weight: bold;"'; ?>>Dark</a>
    </div>
</header>
