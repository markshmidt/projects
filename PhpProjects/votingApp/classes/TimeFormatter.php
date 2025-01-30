<?php
class TimeFormatter {
    public static function formatTimestamp(int $timestamp): string {
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
?>
