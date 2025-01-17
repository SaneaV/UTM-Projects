<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "internship";

$conn = new mysqli($servername, $username, $password, $dbname);

session_start();
$sql = "SELECT firstname, secondname, r.name as role FROM user u INNER JOIN role r ON u.role = r.id WHERE u.id = '" . $_SESSION['id'] . "'";
$result = $conn->query($sql);
$user = $result->fetch_assoc();

$sql = "SELECT * FROM role";
$result = $conn->query($sql);
while ($role = $result->fetch_assoc()) {
    $roles[] = $role;
}

$sql = "SELECT * FROM speciality";
$result = $conn->query($sql);
while ($speciality = $result->fetch_assoc()) {
    $specialities[] = $speciality;
}

$sql = "SELECT c.id, c.username, u.firstname, u.secondname, u.photo as photo, r.name as role, s.name as speciality
        FROM credentials c INNER JOIN user u ON c.id = u.id
        INNER JOIN role r ON u.role = r.id 
        INNER JOIN speciality s ON u.speciality = s.id
        ORDER BY c.id";
$result = $conn->query($sql);
$members = array();
while ($member = $result->fetch_assoc()) {
    $members[] = $member;
}
$members = array_reverse($members);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="../../img/logo/icon.png">
    <link rel="stylesheet" href="../../css/members-page.css">
    <link rel="stylesheet" href="../../css/common.css">
    <title>Members</title>
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
                <h1>Members</h1>
            </div>
            <div class="member-container">
                <?php foreach ($members as $member) { ?>
                    <div class="member-item"    data-username="<?php echo $member['username']; ?>" 
                                                data-firstname="<?php echo $member['firstname']; ?>" 
                                                data-lastname="<?php echo $member['secondname']; ?>" 
                                                data-role="<?php echo $member['role']; ?>" 
                                                data-speciality="<?php echo $member['speciality']; ?>">
                        <?php
                        if ($member['id'] != $_SESSION['id']) {
                            echo '<div class="close-button">✖</div>';
                            echo '<img src="../../img/items/pen.png" alt="Edit Icon" class="edit-icon" onclick="openEditPopup(this)">';

                        }
                        else {
                            echo '<img style="top: -5px;" src="../../img/items/pen.png" alt="Edit Icon" class="edit-icon" onclick="openEditPopup(this)">';
                        }
                        ?>
                        <?php
                            if($member['photo']) {
                                echo '<img src="data:image/jpeg;base64,'.base64_encode($member['photo']).'" alt="user-img" class="member-image">';
                            }
                            else {
                                echo '<img src="../../img/user/user.png" alt="user-img" class="member-image">';
                            }
                        ?>
                        <p class="member-details"><b>Username:</b><br><?php echo $member['username']; ?></p>
                        <p class="member-details"><b>Firstname:</b><br><?php echo $member['firstname']; ?></p>
                        <p class="member-details"><b>Lastname:</b><br><?php echo $member['secondname']; ?></p>
                        <p class="member-details"><b>Role:</b><br><?php echo $member['role']; ?></p>
                        <p class="member-details"><b>Speciality:</b><br><?php echo $member['speciality']; ?></p>
                    </div>
                <?php 
                    } 
                ?>
            </div>
        </div>

        <div class="overlay" id="overlay">
            <div class="popup" id="popup">
                <form id="members-form" method="post" action="../script/add-member-script.php">
                    <span class="close" onclick="closePopup()">&times;</span>
                    <h2 id="popup-name">Add Member</h2>

                    <input type="hidden" id="old-username" name="old-username">

                    <label for="Username">Username:</label>
                    <input type="text" id="username" name="username" maxlength="50" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" maxlength="50" required>

                    <label for="firstname">Firstname:</label>
                    <input type="text" id="firstname" name="firstname" maxlength="100" required>

                    <label for="lastname">Lastname:</label>
                    <input type="text" id="lastname" name="lastname" maxlength="100" required>

                    <label for="role">Role:</label>
                    <select name="role" id="role" required>
                    <?php
                        foreach ($roles as $role) {
                        ?>
                        <option><?php echo $role['name']; ?></option>
                        <?php
                        }
                    ?>
                    </select>

                    <label for="speciality">Speciality:</label>
                    <select name="speciality" id="speciality" required>
                    <?php
                        foreach ($specialities as $speciality) {
                        ?>
                        <option><?php echo $speciality['name']; ?></option>
                        <?php
                        }
                    ?>
                    </select>

                    <button type="submit" id="submit">Add Member</button>
                </form>
            </div>
        </div>
    
        <div id="scroll-to-top">
            <a href="#top">
                <img src="../../img/items/up-arrow.png" alt="arrow-up">
            </a>
        </div>

        <?php
        if ($user['role'] == 'Admin') {
            echo '<div id="add-button">
                    <a href="#add-topic">
                        <img onclick="openAddPopup()" src="../../img/items/add.png" alt="add-new-member">
                    </a>
                </div>';
            }
        ?>
    </div>

    <script src="../../js/common.js"></script>
    <script src="../../js/member-page.js"></script>
</body>

</html>

<?php
$conn->close();
?>