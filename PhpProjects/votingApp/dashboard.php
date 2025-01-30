<?php
require_once 'db.config.php';
require_once 'classes/Topic.php';
require_once 'classes/TimeFormatter.php';
require_once 'classes/Vote.php';
require_once 'classes/Comment.php';
require_once 'header.php';
require_once 'pdo.php';

// the user is logged in, else redirect to login page
if (!isset($_SESSION['user_id'])) {
    header('Location: login.php');
    exit();
}

// set up database connection
$config = include('db.config.php');

// Voting
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['topic_id'], $_POST['vote_type'])) {
    $topicId = $_POST['topic_id'];
    $voteType = $_POST['vote_type'];
    $userId = $_SESSION['user_id'];

    $vote = new Vote($pdo);
    $vote->vote($topicId, $userId, $voteType);


    header("Location: dashboard.php");
    exit();
}

// comment submission
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit_comment'])) {
    $topicId = $_POST['topic_id'];
    $commentText = trim($_POST['comment']);
    $userId = $_SESSION['user_id'];

    if (!empty($commentText)) {
        $commentInstance = new Comment($pdo);
        $commentAdded = $commentInstance->addComment($userId, $topicId, $commentText);

        //Reloading the dashboard after adding a comment
        if ($commentAdded) {
            header("Location: dashboard.php");
        } else {
            echo "<p style='color: red;'>Failed to add comment. Please try again.</p>";
        }
    } else {
        echo "<p style='color: red;'>Comment cannot be empty.</p>";
    }
}

//Fetching all topics
$topicInstance = new Topic($pdo);
$topics = $topicInstance->getTopics();
?>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="style.css">
    <style>
        <?php if (isset($_SESSION['theme']) && $_SESSION['theme'] === 'dark'): ?>
        body {
            background-color: #333;
            color: #fff;
        }
        .container {
            background-color: #444;
            color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            max-width: 800px;
            margin: 20px auto;
        }
        h1, h3, h4 {
            text-align: center;
        }
        input[type="text"], textarea {
            background-color: #555;
            color: #fff;
            border: 1px solid #888;
            padding: 8px;
            border-radius: 4px;
            width: 30%;
            margin-top: 5px;
        }
        button {
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover {
            background-color: #e68a00;
        }
        a {
            color: #ff9800;
        }
        hr {
            border: 0;
            height: 1px;
            background-color: #888;
            margin: 20px 0;
        }
        <?php endif; ?>
    </style>
</head>
<body class="<?php echo htmlspecialchars(getTheme()); ?>">

<div class="container">
    <h1>Welcome, <?php echo htmlspecialchars($_SESSION['username']); ?>!</h1>
    <br>
    <?php
    $vote = new Vote($pdo);
    $commentInstance = new Comment($pdo);

    foreach ($topics as $topic) :
        $voteResults = Vote::getVoteResults($pdo, $topic->id); // Fetch vote counts
        $comments = $commentInstance->getComments($topic->id); // Fetch comments
        ?>
        <div class="topic">
            <h3><?= htmlspecialchars($topic->title) ?></h3>
            <p><?= htmlspecialchars($topic->description) ?></p>
            <p>Created by: <?= htmlspecialchars($topic->username) ?> | Upvotes: <?= $voteResults['up'] ?> | Downvotes: <?= $voteResults['down'] ?></p>
            <p>Created at: <?= TimeFormatter::formatTimestamp(strtotime($topic->created_at)) ?></p>

            <!-- If the user has already voted -->
            <?php if ($vote->hasVoted($_SESSION['user_id'], $topic->id)): ?>
                <p style="color: green;">You have already voted on this topic.</p>
            <?php else: ?>
                <!-- Voting form -->
                <form method="POST" action="dashboard.php">
                    <input type="hidden" name="topic_id" value="<?= $topic->id ?>">
                    <button type="submit" name="vote_type" value="up">Upvote</button>
                    <button type="submit" name="vote_type" value="down">Downvote</button>
                </form>
            <?php endif; ?>

            <br>
            <!-- Comments for the topic -->
            <h4>Comments:</h4>
            <?php if (!empty($comments)): ?>
                <?php foreach ($comments as $comment): ?>
                    <p><strong>User <?= htmlspecialchars($comment['username']) ?>:</strong> <?= htmlspecialchars($comment['comment']) ?></p>
                    <p><small>Posted at: <?= TimeFormatter::formatTimestamp(strtotime($comment['commented_at'])) ?></small></p>
                <?php endforeach; ?>
            <?php else: ?>
                <p>No comments yet. Be the first to comment!</p>
            <?php endif; ?>

            <!-- Comment form -->
            <form method="POST" action="dashboard.php">
                <input type="hidden" name="topic_id" value="<?= $topic->id ?>">
                <textarea name="comment" required placeholder="Add your comment"></textarea><br>
                <button type="submit" name="submit_comment">Submit Comment</button>
            </form>
        </div>
        <hr>
    <?php endforeach; ?>
</div>

</body>
</html>
