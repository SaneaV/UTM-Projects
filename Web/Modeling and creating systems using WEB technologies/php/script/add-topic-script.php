<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$newsName = $_POST['news-name'];
$newsText = $_POST['news-text'];
$data = file_get_contents($_FILES['news-image']['tmp_name']);

$stmt = $conn->prepare("INSERT INTO news (title, text, image) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $newsName, $newsText, $data);

if ($stmt->execute()) {
    header("Location: ../page/main-page.php");
    die();
} else {
    echo "Error: " . $stmt->error;
}

$conn->close();
?>