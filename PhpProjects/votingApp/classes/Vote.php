<?php
class Vote
{
    private $pdo;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    //Getting votes
    public static function getVoteResults($pdo, $topicId): array
    {
        //Count upvotes
        $stmt = $pdo->prepare("SELECT COUNT(*) FROM Votes WHERE topic_id = :topic_id AND vote_type = 'up'");
        $stmt->execute(['topic_id' => $topicId]);
        $upvotes = $stmt->fetchColumn();

        //Count downvotes
        $stmt = $pdo->prepare("SELECT COUNT(*) FROM Votes WHERE topic_id = :topic_id AND vote_type = 'down'");
        $stmt->execute(['topic_id' => $topicId]);
        $downvotes = $stmt->fetchColumn();

        return ['up' => $upvotes, 'down' => $downvotes];
    }

    //Voting
    public function vote($topicId, $userId, $voteType)
    {
        if (!in_array($voteType, ['up', 'down'])) {
            return false;
        }
        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Users WHERE id = ?");
        $stmt->execute([$userId]);
        if ($stmt->fetchColumn() == 0) {
            throw new Exception("User with ID $userId does not exist.");
        }

        // Ensure topic exists
        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Topics WHERE id = ?");
        $stmt->execute([$topicId]);
        if ($stmt->fetchColumn() == 0) {
            throw new Exception("Topic with ID $topicId does not exist.");
        }

        //if user has voted on this topic
        $stmt = $this->pdo->prepare("SELECT * FROM Votes WHERE topic_id = :topic_id AND user_id = :user_id");
        $stmt->execute(['topic_id' => $topicId, 'user_id' => $userId]);
        $existingVote = $stmt->fetch();

        if ($existingVote) {
            return false;
        }

        //inserting the vote
        $stmt = $this->pdo->prepare("INSERT INTO Votes (topic_id, user_id, vote_type) VALUES (:topic_id, :user_id, :vote_type)");
        if ($stmt->execute([
            'topic_id' => $topicId,
            'user_id' => $userId,
            'vote_type' => $voteType
        ])) {
            return true;
        }

        return false;
    }
    //Checking if the user already voted
    public function hasVoted($userId, $topicId)
    {
        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Votes WHERE user_id = :user_id AND topic_id = :topic_id");
        $stmt->execute(['user_id' => $userId, 'topic_id' => $topicId]);
        return $stmt->fetchColumn() > 0;
    }

    //Getting user voting history
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
