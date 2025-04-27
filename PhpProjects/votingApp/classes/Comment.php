<?php
class Comment
{
    private $pdo;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    //Adding comment
    public function addComment($userId, $topicId, $comment): bool
    {
        $stmt = $this->pdo->prepare("
        INSERT INTO Comments (user_id, topic_id, comment, commented_at) 
        VALUES (:user_id, :topic_id, :comment, :commented_at)
    ");
        return $stmt->execute([
            'user_id' => $userId,
            'topic_id' => $topicId,
            'comment' => $comment,
            'commented_at' => date('Y-m-d H:i:s')
        ]);
    }
    //Getting all comments
    public function getComments($topicId): array
    {
        $stmt = $this->pdo->prepare("
        SELECT Comments.*, Users.username 
        FROM Comments 
        JOIN Users ON Comments.user_id = Users.id 
        WHERE topic_id = :topic_id 
        ORDER BY Comments.commented_at ASC;
    ");
        $stmt->execute(['topic_id' => $topicId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    //Getting user comments
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
?>
