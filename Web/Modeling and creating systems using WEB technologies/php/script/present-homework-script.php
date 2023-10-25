<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$homeworkId = $_POST['homework-id'];
$internId = $_POST['intern-id'];
$data = file_get_contents($_FILES['homework-file']['tmp_name']);

$file = $_FILES['homework-file'];
$ext = pathinfo($file['name'], PATHINFO_EXTENSION);

$sql = "SELECT COUNT(*) FROM homework_intern WHERE intern_id = ? AND homework_id = ?";
$statement = $conn->prepare($sql);
$statement->bind_param("ii", $internId, $homeworkId);
$statement->execute();
$statement->bind_result($count);
$statement->fetch();
$exist = ($count === 1) ? true : false;
$statement->close();

if ($exist) {
    $statement = $conn->prepare("UPDATE homework_intern SET file = ?, extension = ? WHERE intern_id = ? AND homework_id = ?");
    $statement->bind_param("ssii", $data, $ext, $internId, $homeworkId);
}
else{
    $statement = $conn->prepare("INSERT INTO homework_intern (homework_id, intern_id, file, extension) VALUES (?, ?, ?, ?)");
    $statement->bind_param("iiss", $homeworkId, $internId, $data, $ext);
}

if ($statement->execute()) {
    header("Location: ../page/homework-page.php");
    die();
} else {
    echo "Error: " . $statement->error;
}

$conn->close();
?>