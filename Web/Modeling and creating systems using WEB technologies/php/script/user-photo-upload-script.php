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

if (isset($_FILES['newPhoto'])) {
        $data = file_get_contents($_FILES['newPhoto']['tmp_name']);

        $updatePhotoStatement = $conn->prepare("UPDATE user SET photo = ? WHERE id = ?");
        $updatePhotoStatement->bind_param("si", $data, $_SESSION["id"]);

        if ($updatePhotoStatement->execute()) {
            $_SESSION['photo'] = "data:image/jpeg;base64,".base64_encode($data)."";
            header("Location: ../page/user-page.php");
            die();
        } else {
            echo "Error: " . $updatePhotoStatement->error;
        }

        $updatePhotoStatement->close();
} else {
    echo "No file uploaded.";
}

$conn->close();
?>
