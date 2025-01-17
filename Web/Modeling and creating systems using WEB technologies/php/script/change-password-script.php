<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $userId = $_SESSION['id'];
    $oldPassword = $_POST['oldPassword'];
    $newPassword = $_POST['newPassword'];
    $confirmPassword = $_POST['confirmPassword'];

    $sql = "SELECT password FROM credentials WHERE id = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $userId);
    $stmt->execute();
    $result = $stmt->get_result();
    $stmt->close();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $currentPassword = $row["password"];

        if ($currentPassword === $oldPassword) {
            if ($newPassword === $confirmPassword) {
                $updatePasswordSql = "UPDATE credentials SET password = ? WHERE id = ?";
                $updatePasswordStmt = $conn->prepare($updatePasswordSql);
                $updatePasswordStmt->bind_param("si", $newPassword, $userId);

                if ($updatePasswordStmt->execute()) {
                    $updatePasswordStmt->close();
                    $response = array("success" => "Password changed successfully");
                    echo json_encode($response);
                } else {
                    $updatePasswordStmt->close();
                    $response = array("error" => "Error updating the password: " . $updatePasswordStmt->error);
                    echo json_encode($response);
                }
            } else {
                $response = array("error" => "New password and confirmation do not match.");
                echo json_encode($response);
            }
        } else {
            $response = array("error" => "Old password is incorrect.");
            echo json_encode($response);
        }
    }
}

$conn->close();
?>
