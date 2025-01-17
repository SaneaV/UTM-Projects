<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

$username = $_GET['username'];
$password = $_GET['password'];

$sql = "SELECT * FROM credentials WHERE username = '" . $username . "' AND password = '" . $password . "'";
$result = $conn->query($sql);
$row = $result->fetch_assoc();

if (is_null($row["id"])) {
    header("Location: ../../html/wrong-password-page.html");
    die();
    
} else {
    session_start();
    $_SESSION['id'] = $row["id"];

    $sql = "SELECT * FROM user WHERE id = '" . $_SESSION['id'] . "'";
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();

    if($row['photo']) {
        $_SESSION['photo'] = "data:image/jpeg;base64,".base64_encode($row['photo'])."";
    }
    else {
        $_SESSION['photo'] = "../../img/user/user.png";
    }

    header("Location: ../page/main-page.php");
    die();
    $conn->close();
}