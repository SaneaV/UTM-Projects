<?php
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    if (isset($_POST["id"])) {
        $servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "internship";

        $conn = new mysqli($servername, $username, $password, $dbname);

        $id = $_POST["id"];

        $statement = $conn->prepare("DELETE FROM news WHERE id = ?");
        $statement->bind_param("s", $id);
        $statement->execute();

        $conn->close();
        exit;
    }
}
?>
