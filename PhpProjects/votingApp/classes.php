<?php

//classes from classes directory
class User
{
    private $pdo;

    public function __construct($pdo)
    {
        $this->pdo = $pdo;
    }

    public function registerUser($username, $email, $password)
    {
        // validating input
        if (empty($username) || !filter_var($email, FILTER_VALIDATE_EMAIL) || strlen($password) < 9) {
            return false;
        }

        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Users WHERE username = :username OR email = :email");
        $stmt->execute(['username' => $username, 'email' => $email]);
        if ($stmt->fetchColumn() > 0) {
            return false;
        }

        $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

        //inserting user
        $stmt = $this->pdo->prepare("INSERT INTO Users (username, email, password) VALUES (:username, :email, :password)");
        return $stmt->execute([
            'username' => $username,
            'email' => $email,
            'password' => $hashedPassword
        ]);
    }

    public function authenticateUser($username, $password)
    {
        $stmt = $this->pdo->prepare("SELECT id, password FROM Users WHERE username = :username");
        $stmt->execute(['username' => $username]);
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        //verifying the password
        if ($user && password_verify($password, $user['password'])) {
            return $user['id'];
        }
        return false;
    }
}
?>
<?php
class Vote
{
    private $pdo;

    public function __construct($pdo)
    {
        $this->pdo = $pdo;
    }

    public static function getVoteResults($pdo, $topicId): array
    {
        //Counting upvotes
        $stmt = $pdo->prepare("SELECT COUNT(*) FROM Votes WHERE topic_id = :topic_id AND vote_type = 'up'");
        $stmt->execute(['topic_id' => $topicId]);
        $upvotes = $stmt->fetchColumn();

        //Counting downvotes
        $stmt = $pdo->prepare("SELECT COUNT(*) FROM Votes WHERE topic_id = :topic_id AND vote_type = 'down'");
        $stmt->execute(['topic_id' => $topicId]);
        $downvotes = $stmt->fetchColumn();

        return ['up' => $upvotes, 'down' => $downvotes];
    }

    public function vote($userId, $topicId, $voteType)
    {
        //ensutring that user exists
        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Users WHERE id = ?");
        $stmt->execute([$userId]);
        if ($stmt->fetchColumn() == 0) {
            throw new Exception("User with ID $userId does not exist.");
        }

        //ensuring topic exists
        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Topics WHERE id = ?");
        $stmt->execute([$topicId]);
        if ($stmt->fetchColumn() == 0) {
            throw new Exception("Topic with ID $topicId does not exist.");
        }

        //inserting the vote
        $stmt = $this->pdo->prepare("INSERT INTO Votes (topic_id, user_id, vote_type, voted_at)
                                     VALUES (:topic_id, :user_id, :vote_type, NOW())");
        return $stmt->execute([
            'topic_id' => $topicId,
            'user_id' => $userId,
            'vote_type' => $voteType
        ]);

    }

    public function hasVoted($topicId, $userId)
    {
        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Votes WHERE user_id = :user_id AND topic_id = :topic_id");
        $stmt->execute(['user_id' => $userId, 'topic_id' => $topicId]);
        return $stmt->fetchColumn() > 0;
    }

    public function getUserVoteHistory($userId): array
    {
        $stmt = $this->pdo->prepare("
        SELECT Topics.title, Votes.vote_type, Votes.voted_at
        FROM Votes
        JOIN Topics ON Votes.topic_id = Topics.id
        WHERE Votes.user_id = :user_id
        ORDER BY Votes.voted_at DESC
    ");
        $stmt->execute(['user_id' => $userId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}
?>
<?php
class Topic
{
    private $pdo;
    public $created_at;

    public function __construct($pdo)
    {
        $this->pdo = $pdo;
    }

    public function createTopic($userId, $title, $description)
    {
        //validation
        if (!is_numeric($userId)) {
            throw new InvalidArgumentException("User ID must be a valid number.");
        }
        if (empty($title) || !is_string($title)) {
            throw new InvalidArgumentException("Title must be a non-empty string.");
        }
        if (empty($description) || !is_string($description)) {
            throw new InvalidArgumentException("Description must be a non-empty string.");
        }

        $userId = (int)$userId;

        //Inserting topic
        $stmt = $this->pdo->prepare("INSERT INTO Topics (user_id, title, description, created_at) VALUES (:user_id, :title, :description, NOW())");
        return $stmt->execute([
            'user_id' => $userId,
            'title' => $title,
            'description' => $description
        ]);
    }

    public function getTopics()
    {
        $stmt = $this->pdo->query("
        SELECT Topics.*, Users.username
    FROM Topics
    LEFT JOIN Users ON Topics.user_id = Users.id
    ");
        $topicsData = $stmt->fetchAll(PDO::FETCH_ASSOC);

        $topics = [];
        foreach ($topicsData as $data) {
            $topic = new Topic($this->pdo);
            $topic->id = $data['id'];
            $topic->title = $data['title'];
            $topic->description = $data['description'];
            $topic->userId = $data['user_id'];
            $topic->username = $data['username'];
            $topic->created_at = $data['created_at'];
            $topics[] = $topic;
        }
        return $topics;
    }
    public function getCreatedTopics($userId)
    {
        $stmt = $this->pdo->prepare("
            SELECT id, title, description 
            FROM Topics 
            WHERE user_id = ?
        ");
        $stmt->execute([$userId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

}
?>
<?php
class Comment
{
    private $pdo;

    public function __construct($pdo)
    {
        $this->pdo = $pdo;
    }

    public function addComment($userId, $topicId, $comment): bool
    {
        $stmt = $this->pdo->prepare("INSERT INTO Comments (user_id, topic_id, comment, commented_at) VALUES (:user_id, :topic_id, :comment, NOW())");
        return $stmt->execute([
            'user_id' => $userId,
            'topic_id' => $topicId,
            'comment' => $comment
        ]);
    }


    public function getComments($topicId): array
    {
        $stmt = $this->pdo->prepare("
        SELECT Comments.*, Users.username 
        FROM Comments 
        JOIN Users ON Comments.user_id = Users.id 
        WHERE topic_id = :topic_id 
        ORDER BY Users.username ASC
    ");
        $stmt->execute(['topic_id' => $topicId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
    public function getUserComments($userId): array
    {
        $stmt = $this->pdo->prepare("
        SELECT Topics.title, Comments.comment, Comments.commented_at
        FROM Comments
        JOIN Topics ON Comments.topic_id = Topics.id
        WHERE Comments.user_id = :user_id
        ORDER BY Comments.commented_at DESC
    ");
        $stmt->execute(['user_id' => $userId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }


}

class TimeFormatter
{
    public static function formatTimestamp(int $timestamp): string
    {
        $diff = time() - $timestamp;
        if ($diff < 60) {
            return "Just now";
        } elseif ($diff < 3600) {
            return floor($diff / 60) . " minutes ago";
        } elseif ($diff < 86400) {
            return floor($diff / 3600) . " hours ago";
        } elseif ($diff < 31536000) {
            return floor($diff / 86400) . " days ago";
        } else {
            return date("M d, Y", $timestamp);
        }
    }
}

