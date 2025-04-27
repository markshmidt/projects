<?php

require_once 'db.config.php';
require_once 'classes/User.php';
require_once 'classes/Topic.php';
require_once 'classes/Vote.php';
require_once 'classes/Comment.php';

$config = include 'db.config.php';

if (!isset($config['default']['host'], $config['default']['username'], $config['default']['password'], $config['default']['dbname'])) {
    die("Database configuration is incomplete. Please check db.config.php.");
}

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
