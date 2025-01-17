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

$sql = "SELECT u.id as id, u.firstname as firstname, u.secondname as secondname
        FROM user u INNER JOIN role r ON u.role = r.id
        WHERE r.name <> 'Admin' AND u.id <> '" . $_SESSION['id'] . "'
        ORDER BY u.secondname";
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="../../css/feedback-page.css">
    <link rel="stylesheet" href="../../css/common.css">
    <title>Feedback</title>
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
                <h1>Feedback</h1>
                <button id="myFeedbacks">My Feedbacks</button>
            </div>
            <div class="feedback-container">
            <div class="feedback-form">
                <form id="feedbackForm" action="../script/add-feedback-script.php" method="post" onsubmit="handleFormSubmit(event)">
                    <label for="personSelect">Select a person:</label>
                    <select name="to_id" id="user">
                    <?php
                        foreach ($users as $user) {
                        ?>
                        <option value="<?php echo $user['id'];?>" required><?php echo $user['secondname']; ?> <?php echo $user['firstname']; ?></option>
                        <?php
                        }
                    ?>
                    </select>

                    <input type="hidden" name="from_id" value="<?php echo $_SESSION['id'];?>">
                    <input type="hidden" id="feedbackAdded" name="feedbackAdded" value="0">

                    <label for="feedbackText">Write your feedback:</label>
                    <textarea id="feedbackText" name="text" rows="24" cols="150" required></textarea>
                    <button type="submit">Submit</button>
                </form>
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
    <script src="../../js/feedback-page.js"></script>
</body>

</html>

<?php
$conn->close();
?>