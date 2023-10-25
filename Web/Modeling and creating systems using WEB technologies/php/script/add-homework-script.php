<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

session_start();
$title = $_POST['title'];
$description = $_POST['description'];

$statement = $conn->prepare("INSERT INTO homework (title, description, mentor_id) VALUES (?, ?, ?)");
$statement->bind_param("ssi", $title, $description, $_SESSION['id']);

if ($statement->execute()) {
    header("Location: ../page/homework-page.php");
    die();
} else {
    echo "Error: " . $statement->error;
}

$conn->close();
?>