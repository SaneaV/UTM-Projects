<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

session_start();
$sql = "SELECT u.id as id, firstname, secondname, r.name as role 
        FROM user u INNER JOIN role r ON u.role = r.id 
        WHERE u.id = '" . $_SESSION['id'] . "'";
$result = $conn->query($sql);
$mainUser = $result->fetch_assoc();

$sql = "SELECT * FROM news";
$result = $conn->query($sql);

$sql = "SELECT * FROM user WHERE role <> 3";
$result = $conn->query($sql);
while ($user = $result->fetch_assoc()) {
    $users[] = $user;
}

?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="../../img/logo/icon.png">
    <link rel="stylesheet" href="../../css/feedback-page.css">
    <link rel="stylesheet" href="../../css/common.css">
    <title>My Feedbacks</title>
</head>

<body>

    <div class="central-container">
        <div class="sidenav">
            <div class="logo">
                <img src="../../img/logo/icon.png" alt="Site Logo">
                <p id="internship-label">Internship <span id="year"></span></p>
            </div>
            <div class="menu">
                <a href="../page/main-page.php">Home</a>
                <?php
                if ($mainUser['role'] == 'Admin') {
                    echo '<a href="../page/members-page.php">Members</a>';
                }
                ?>
                <a href="../page/homework-page.php">Homework</a>
                <?php
                if ($mainUser['role'] != 'Admin') {
                    echo '<a href="../page/feedback-page.php">Feedback</a>';
                }
                ?>
            </div>
                <div class="user">
                    <img class="user-logo" src="<?php echo $_SESSION['photo']; ?>" alt="user-logo">
                    <a href="../page/user-page.php" id="user-account">
                        <p><?php echo $mainUser['firstname'];?> <?php echo $mainUser['secondname'];?></p>
                    </a>
                    <a id="logout-link" href="../script/logout-script.php">Logout</a>
                </div>
        </div>

        <div class="main-content">
            <div class="header-container">
                <h1>My Feedbacks</h1>
                <button id="leaveFeedback">Leave feedback</button>
            </div>
            <div class="feedback-container">
                    <?php
                        $sql = "SELECT u.firstname as firstname, u.secondname as secondname, text 
                        FROM feedback f INNER JOIN user u ON f.from_id = u.id
                        WHERE to_id = '".$_SESSION["id"]."'";

                        $result = $conn->query($sql);

                        $feedbacks = array();
                        while ($feedback = $result->fetch_assoc()) {
                            $feedbacks[] = $feedback;
                        }
                        $feedbacks = array_reverse($feedbacks);

                        foreach ($feedbacks as $feedback) {
                            echo '<div class="feedback-item">';
                            echo '<div class="feedback-details">';
                            echo '<h1>' . $feedback['firstname'] . ' ' . $feedback['secondname'] . '</h1>';
                            echo '<p>' . $feedback['text'].'</p>';
                            echo '</div>';
                            echo '</div>';
                        }
                        ?>
            </div>
        </div>

        <div id="scroll-to-top">
            <a href="#top">
                <img src="../../img/items/up-arrow.png" alt="arrow-up">
            </a>
        </div>

    </div>

    <script src="../../js/common.js"></script>
    <script src="../../js/feedback-about-me-page.js"></script>
</body>

</html>

<?php
$conn->close();
?>