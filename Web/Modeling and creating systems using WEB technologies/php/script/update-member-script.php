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
$oldUsername = $_POST['old-username'];
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

/* update credentials */
$updateCredentialsStatement = $conn->prepare("UPDATE credentials SET username = ? WHERE username = ?");
$updateCredentialsStatement->bind_param("ss", $username, $oldUsername);
$updateCredentialsStatement->execute();
$updateCredentialsStatement->close();

/* get member id */
$memeberIdStatement = $conn->prepare("SELECT id FROM credentials WHERE username = ?");
$memeberIdStatement->bind_param("s", $username);
$memeberIdStatement->execute();
$memeberIdStatement->bind_result($memberId);
$memeberIdStatement->fetch();
$memeberIdStatement->close();

/* insert new member */
$updateMemeberStatement = $conn->prepare("UPDATE user SET firstname = ?, secondname = ?, role = ?, speciality = ?
                                            WHERE id = ?");
$updateMemeberStatement->bind_param("ssiii", $firstname, $lastname, $roleId, $specialityId, $memberId);

if ($updateMemeberStatement->execute()) {
    header("Location: ../page/members-page.php");
    die();
} else {
    echo "Error: " . $updateMemeberStatement->error;
}

$conn->close();
?>