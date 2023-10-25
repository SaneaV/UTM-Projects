<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

session_start();
$sql = "SELECT firstname, secondname, r.name as role, s.name as speciality
         FROM user u INNER JOIN role r ON u.role = r.id
         INNER JOIN speciality s ON u.speciality = s.id
         WHERE u.id = '" . $_SESSION['id'] . "'";
$result = $conn->query($sql);
$user = $result->fetch_assoc();

$sql = "SELECT * FROM homework WHERE mentor_id = '". $_SESSION['id'] ."'";

$result = $conn->query($sql);

$homeworks = array();
while ($homework = $result->fetch_assoc()) {
    $homeworks[] = $homework;
}
$homeworks = array_reverse($homeworks);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="../../img/logo/icon.png">
    <link rel="stylesheet" href="../../css/homework-page.css">
    <link rel="stylesheet" href="../../css/common.css">
    <title>Intern's Homeworks</title>
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
                if ($user['role'] == 'Admin') {
                    echo '<a href="../page/members-page.php">Members</a>';
                }
                ?>
                <a href="../page/homework-page.php">Homework</a>
                <?php
                if ($user['role'] != 'Admin') {
                    echo '<a href="../page/feedback-page.php">Feedback</a>';
                }
                ?>
            </div>
                <div class="user">
                    <img class="user-logo" src="<?php echo $_SESSION['photo']; ?>" alt="user-logo">
                    <a href="../page/user-page.php" id="user-account">
                        <p><?php echo $user['firstname'];?> <?php echo $user['secondname'];?></p>
                    </a>
                    <a id="logout-link" href="../script/logout-script.php">Logout</a>
                </div>
        </div>

        <div class="main-content">
            <div class="header-container">
                <h1>Intern's Homeworks</h1>
                <?php
                    if ($user['role'] == 'Mentor') {
                        echo '<button id="homeworksPage">Homeworks</button>';
                    }
                ?> 
            </div>
            <div class="homework-container">
                <?php foreach ($homeworks as $homework) { ?>
                    <div class="homework-item">
                        <label class="checkbox-container">
                            <h1 class="homework-details"><?php echo $homework['title']; ?></h1>
                        </label>
                        <div id="intern-name">
                            <?php
                            $sql = "SELECT u.id as id, u.firstname as firstname, u.secondname as secondname FROM 
                            homework_intern hi INNER JOIN user u ON hi.intern_id = u.id
                            WHERE homework_id = '" . $homework['id'] . "'";

                            $result = $conn->query($sql);

                            $interns = array();
                            while ($intern = $result->fetch_assoc()) {
                                $interns[] = $intern;
                            }
                            $interns = array_reverse($interns);

                            foreach ($interns as $intern) {
                                echo '<div class="homework-intern-details">';
                                echo '<p>' . $intern['firstname'] . ' ' . $intern['secondname'] . '</p>';
                                echo '<a href="../script/download-homework-script.php?homework_id=' . $homework['id'] . '&intern_id=' . $intern['id'] . '">
                                    <img id="download" src="../../img/items/download.png" alt="Download">
                                </a>';
                                echo '</div>';
                            }
                            ?>
                        </div>
                    </div>
                <?php 
                    } 
                ?>
            </div>
        </div>

        <div id="scroll-to-top">
            <a href="#top">
                <img src="../../img/items/up-arrow.png" alt="arrow-up">
            </a>
        </div>

        <script src="../../js/common.js"></script>
        <script src="../../js/homework-page.js"></script>
        <script src="../../js/homework-mentor-page.js"></script>
    </div>

</body>

</html>

<?php
$conn->close();
?>