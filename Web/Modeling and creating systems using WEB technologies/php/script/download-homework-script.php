<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (isset($_GET['intern_id']) && isset($_GET['homework_id'])) {
    $internId = $_GET['intern_id'];
    $homeworkId = $_GET['homework_id'];

    $sql = "SELECT file, extension FROM homework_intern WHERE intern_id = ? AND homework_id = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ii", $internId, $homeworkId);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows == 1) {
        $stmt->bind_result($file_data, $extension);
        $stmt->fetch();

        $random_filename = uniqid() . "." . $extension;

        header('Content-Type: ' . $extension);
        header('Content-Disposition: attachment; filename="' . $random_filename . '"');

        echo $file_data;
    }

    $stmt->close();
}

$conn->close();
?>
