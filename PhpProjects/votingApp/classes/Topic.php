<?php
class Topic
{
    private $pdo;
    public $created_at;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    //Creating new topic
    public function createTopic($userId, $title, $description)
    {
        // validation
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

        //Insert topic
        $stmt = $this->pdo->prepare("INSERT INTO Topics (user_id, title, description, created_at) VALUES (:user_id, :title, :description, NOW())");
        return $stmt->execute([
            'user_id' => $userId,
            'title' => $title,
            'description' => $description
        ]);
    }

    //Getting topics
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
