<?php

global $currentTheme;
require_once 'db.config.php';
require_once 'classes/Vote.php';
require_once 'classes/Comment.php';
require_once 'classes/TimeFormatter.php';
require_once 'header.php';
require_once 'pdo.php';

//Redirect to login if not authenticated
if (!isset($_SESSION['user_id'])) {
    header('Location: login.php');
    exit();
}

// Getting user info
$userId = $_SESSION['user_id'];
$username = $_SESSION['username'];

$config = include('db.config.php');
$voteInstance = new Vote($pdo);
$commentInstance = new Comment($pdo);

// getting the user's voting and comment history
$votingHistory = $voteInstance->getUserVoteHistory($userId);
$commentHistory = $commentInstance->getUserComments($userId);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="style.css">
    <style>
        <?php if (isset($_SESSION['theme']) && $_SESSION['theme'] === 'dark'): ?>
        table {
            background-color: #555;
            color: black;
            border: 1px solid #888;
        }
        th {
            background-color: #666;
            color: #ff9800;
        }
        td {
            background-color: #333;
            color: #fff;
        }
        table tr:hover {
            background-color: #555;
        }
        h3{
            color: black;
        }
        h2{
            color:black;
        }
        <?php endif; ?>
    </style>
</head>
<body class="<?php echo htmlspecialchars(getTheme()); ?>">

<div class="container">

    <h2>Welcome, <?= htmlspecialchars($username); ?>!</h2>

    <!-- user's voting history -->
    <h3>Your Voting History</h3>
    <?php if (!empty($votingHistory)): ?>
        <table>
            <thead>
            <tr>
                <th>Topic Title</th>
                <th>Vote Type</th>
                <th>Voted At</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach ($votingHistory as $vote): ?>
                <tr>
                    <td><?= htmlspecialchars($vote['title']); ?></td>
                    <td><?= htmlspecialchars(ucfirst($vote['vote_type'])); ?></td>
                    <td><?= TimeFormatter::formatTimestamp(strtotime($vote['voted_at'])); ?></td>
                </tr>
            <?php endforeach; ?>
            </tbody>
        </table>
    <?php else: ?>
        <p>You haven't voted on any topics yet.</p>
    <?php endif; ?>

    <!-- user's comment history -->
    <h3>Your Comment History</h3>
    <?php if (!empty($commentHistory)): ?>
        <table>
            <thead>
            <tr>
                <th>Topic Title</th>
                <th>Comment</th>
                <th>Commented At</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach ($commentHistory as $comment): ?>
                <tr>
                    <td><?= htmlspecialchars($comment['title']); ?></td>
                    <td><?= htmlspecialchars($comment['comment']); ?></td>
                    <td><?= TimeFormatter::formatTimestamp(strtotime($comment['commented_at'])); ?></td>
                </tr>
            <?php endforeach; ?>
            </tbody>
        </table>
    <?php else: ?>
        <p>You haven't commented on any topics yet.</p>
    <?php endif; ?>
</div>

</body>
</html>
