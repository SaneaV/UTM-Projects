<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$id = $_POST['news-id'];
$title = $_POST['news-title'];
$text = $_POST['news-text'];
$image = $_POST['news-image'];

$statement = $conn->prepare("UPDATE news SET title = ?, text = ?, image = ?WHERE id = ?");
$statement->bind_param("sssi", $title, $text, $image, $id);

if ($statement->execute()) {
    header("Location: ../page/main-page.php");
    die();
} else {
    echo "Error: " . $statement->error;
}

$conn->close();
?>