<?php
class User
{
    private $pdo;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    //Registering user
    public function registerUser($username, $email, $password)
    {
        // Validate input
        if (empty($username) || !filter_var($email, FILTER_VALIDATE_EMAIL) || strlen($password) < 9) {
            return false;
        }

        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM Users WHERE username = :username OR email = :email");
        $stmt->execute(['username' => $username, 'email' => $email]);
        if ($stmt->fetchColumn() > 0) {
            return false;
        }
        
        $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

        // Insert user
        $stmt = $this->pdo->prepare("INSERT INTO Users (username, email, password) VALUES (:username, :email, :password)");
        return $stmt->execute([
            'username' => $username,
            'email' => $email,
            'password' => $hashedPassword
        ]);
    }

    //Authenticating User
    public function authenticateUser($username, $password)
    {
        $stmt = $this->pdo->prepare("SELECT id, password FROM Users WHERE username = :username");
        $stmt->execute(['username' => $username]);
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        // Verify the password
        if ($user && password_verify($password, $user['password'])) {
            return $user['id'];
        }
        return false;
    }
}
?>
