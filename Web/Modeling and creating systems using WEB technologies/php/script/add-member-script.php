<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$username = $_POST['username'];
$password = $_POST['password'];
$firstname = $_POST['firstname'];
$lastname = $_POST['lastname'];
$role = $_POST['role'];
$speciality = $_POST['speciality'];

/* get role id */
$roleIdStatement = $conn->prepare("SELECT id FROM role WHERE name = ?");
$roleIdStatement->bind_param("s", $role);
$roleIdStatement->execute();
$roleIdStatement->bind_result($roleId);
$roleIdStatement->fetch();
$roleIdStatement->close();

/* get speciality id */
$specialityIdStatement = $conn->prepare("SELECT id FROM speciality WHERE name = ?");
$specialityIdStatement->bind_param("s", $speciality);
$specialityIdStatement->execute();
$specialityIdStatement->bind_result($specialityId);
$specialityIdStatement->fetch();
$specialityIdStatement->close();

/* insert new credentials */
$insertNewCredentialsStatement = $conn->prepare("INSERT INTO credentials (username, password) VALUES (?, ?)");
$insertNewCredentialsStatement->bind_param("ss", $username, $password);
$insertNewCredentialsStatement->execute();
$insertNewCredentialsStatement->close();

/* get new member id */
$memeberIdStatement = $conn->prepare("SELECT id FROM credentials WHERE username = ?");
$memeberIdStatement->bind_param("s", $username);
$memeberIdStatement->execute();
$memeberIdStatement->bind_result($memberId);
$memeberIdStatement->fetch();
$memeberIdStatement->close();

/* insert new member */
$insertNewMemeberStatement = $conn->prepare("INSERT INTO user (id, firstname, secondname, role, speciality) VALUES (?, ?, ?, ?, ?)");
$insertNewMemeberStatement->bind_param("issii", $memberId, $firstname, $lastname, $roleId, $specialityId);

if ($insertNewMemeberStatement->execute()) {
    header("Location: ../page/members-page.php");
    die();
} else {
    echo "Error: " . $insertNewMemeberStatement->error;
}

$conn->close();
?>