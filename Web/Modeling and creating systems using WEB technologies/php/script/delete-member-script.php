<?php
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    if (isset($_POST["username"])) {
        $servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "internship";

        $conn = new mysqli($servername, $username, $password, $dbname);

        $memberUsername = $_POST["username"];

        session_start();
        /* get user id */
        $statement = $conn->prepare("SELECT id FROM credentials WHERE username = ?");
        $statement->bind_param("s", $memberUsername);
        $statement->execute();
        $statement->bind_result($memberId);
        $statement->fetch();
        $statement->close();

        /* delete from homework_intern table */
        $statement = $conn->prepare("DELETE hi FROM homework_intern hi
                                    INNER JOIN homework h ON hi.homework_id = h.id
                                    WHERE h.mentor_id = ? OR hi.intern_id = ?;");
        $statement->bind_param("ii", $memberId, $memberId);
        $statement->execute();
        $statement->close();

        /* delete from homework_intern table */
        $statement = $conn->prepare("DELETE FROM homework WHERE mentor_id = ?");
        $statement->bind_param("i", $memberId);
        $statement->execute();
        $statement->close();

        /* delete from feedback table */
        $statement = $conn->prepare("DELETE FROM feedback WHERE to_id = ? OR from_id = ?");
        $statement->bind_param("ii", $memberId, $memberId);
        $statement->execute();
        $statement->close();
        
        /* delete from user table */
        $statement = $conn->prepare("DELETE FROM user WHERE id = ?");
        $statement->bind_param("i", $memberId);
        $statement->execute();
        $statement->close();

        /* delete from credentials table */
        $statement = $conn->prepare("DELETE FROM credentials WHERE id = ?");
        $statement->bind_param("i", $memberId);
        $statement->execute();
        $statement->close();

        $conn->close();
        exit;
    }
}
?>
