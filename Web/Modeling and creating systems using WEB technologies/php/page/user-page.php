<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

session_start();
$sql = "SELECT u.id as id, firstname, secondname, r.name as role, u.photo as photo, s.name as speciality, c.username as username
        FROM user u INNER JOIN role r ON u.role = r.id
        INNER JOIN speciality s ON u.speciality = s.id 
        INNER JOIN credentials c ON c.id = u.id
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
    <link rel="stylesheet" href="../../css/common.css">
    <link rel="stylesheet" href="../../css/user-page.css">
    <title>My Account</title>
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
                <h1>My Account</h1>
            </div>
            <div id="centralContent">
                <div id="mainInfo">
                    <p><b>Username:</b><br><?php echo $mainUser['username'];?></p>
                    <p><b>Firstname:</b><br><?php echo $mainUser['firstname'];?></p>
                    <p><b>Secondname:</b><br><?php echo $mainUser['secondname'];?></p>
                    <p><b>Role:</b><br><?php echo $mainUser['role'];?></p>
                    <p><b>Speciality:</b><br><?php echo $mainUser['speciality'];?></p>
                </div>
                <div id="changePhoto">
                    <form id="photoForm" action="../script/user-photo-upload-script.php" method="post" enctype="multipart/form-data">
                        <img id="profileImage" src="<?php echo $_SESSION['photo']; ?>" alt="Profile Image">
                        <input type="file" id="photoUpload" name="newPhoto" accept="image/*" required>
                        <button type="submit" id="saveButton">Save</button>
                    </form>
                </div>
                <div id="changePassword">
                <form id="passwordForm" action="../script/change-password-script.php" method="post">
                    <input type="password" id="oldPassword" name="oldPassword" placeholder="Old Password" required>
                    <input type="password" id="newPassword" name="newPassword" placeholder="New Password" required>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm New Password" required>
                    <button type="submit" id="savePasswordButton">Save</button>
                </form>
                <div id="statusMessage"><p></p></div>
                </div>
            </div>
        </div>

        <div id="scroll-to-top">
            <a href="#top">
                <img src="../../img/items/up-arrow.png" alt="arrow-up">
            </a>
        </div>

    </div>

    <script src="../../js/common.js"></script>
    <script src="../../js/user-page.js"></script>
    <script src="../../js/change-password-page.js"></script>
</body>

</html>

<?php
$conn->close();
?>