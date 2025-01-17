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

if ($user['role'] == 'Mentor') {
    $sql = "SELECT *
    FROM homework WHERE mentor_id = '". $_SESSION['id'] ."'";
}
else if ($user['role'] == 'Intern') {
    $sql = "SELECT h.id as homework_id, title, description, s.name as speciality FROM 
    homework h INNER JOIN user u ON h.mentor_id = u.id
    INNER JOIN speciality s ON u.speciality = s.id
    WHERE s.name = '". $user['speciality']."'";
}
else {
    $sql = "SELECT h.id as homework_id, title, description, s.name as speciality FROM 
    homework h INNER JOIN user u ON h.mentor_id = u.id
    INNER JOIN speciality s ON u.speciality = s.id";
}

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
    <title>Homework</title>
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
                <h1>Homework</h1>
                <?php
                    if ($user['role'] == 'Mentor') {
                        echo '<button id="internsHomeworkPage">Check Homework</button>';
                    }
                ?> 
            </div>
            <div class="homework-container">
                <?php foreach ($homeworks as $homework) { ?>
                    <div class="homework-item">
                        <?php
                        if ($user['role'] == 'Admin') {
                            echo '<p class="homework-details">'.$homework['speciality'].':</p>';
                        }
                        ?> 
                        <label class="checkbox-container">
                            <?php if ($user['role'] == "Intern") {
                                $sql = "SELECT COUNT(*) FROM homework_intern WHERE intern_id = ? AND homework_id = ?";
                                $statement = $conn->prepare($sql);
                                $statement->bind_param("ii", $_SESSION['id'], $homework['homework_id']);
                                $statement->execute();
                                $statement->bind_result($count);
                                $statement->fetch();
                                $checked = ($count === 1) ? 'checked' : '';
                                echo '<input type="checkbox" class="homework-checkbox" disabled ' . $checked . '>';
                                $statement->close();
                            } ?>
                            <h1 class="homework-details" id="homework-title"><?php echo $homework['title']; ?></h1>
                            <?php
                                if ($user['role'] == 'Mentor') {
                                    echo '<div class="close-button">✖</div>';
                                    echo '<div style="display: none" class="homework-id">' . $homework['id'] . '</div>';
                                }
                            ?>
                        </label>
                        <p class="homework-details"><?php echo $homework['description']; ?></p>
                        <?php
                        if ($user['role'] == 'Intern') {
                            echo '<form class="submit-homework" method="post" action="../script/present-homework-script.php" enctype="multipart/form-data">
                                    <input type="hidden" name="intern-id" value="' .$_SESSION['id']. '">
                                    <input type="hidden" name="homework-id" value="' .$homework['homework_id']. '">

                                    <input type="submit" disabled>
                                    <input type="file" id="homework-file" name="homework-file" required>
                                </form>';
                        }
                        ?>
                    </div>
                <?php 
                    } 
                ?>
            </div>
        </div>

        <?php
            if ($user['role'] == 'Mentor') {
                echo '
                <div class="overlay" id="overlay">
                <div class="popup" id="popup">
                    <form id="homework-form" method="post" action="../script/add-homework-script.php">
                        <span class="close" onclick="closePopup()">&times;</span>
                        <h2 id="popup-name">Add Homework</h2>

                        <label for="title">Title:</label>
                        <input type="text" id="title" name="title" maxlength="50" required>

                        <label for="description">Description:</label>
                        <textarea id="description" name="description" rows="20" cols="100" maxlength="1000" required></textarea>

                        <button type="submit" id="submit">Add Homework</button>
                    </form>
                </div>
            </div>';
            }
        ?>

        <div id="scroll-to-top">
            <a href="#top">
                <img src="../../img/items/up-arrow.png" alt="arrow-up">
            </a>
        </div>

        <?php
        if ($user['role'] == 'Mentor') {
            echo '<div id="add-button">
                    <a href="#add-topic">
                        <img onclick="openPopup()" src="../../img/items/add.png" alt="add-new-homework">
                    </a>
                </div>';
            }
        ?>

        <script src="../../js/common.js"></script>
        <script src="../../js/homework-page.js"></script>
    </div>

</body>

</html>

<?php
$conn->close();
?>