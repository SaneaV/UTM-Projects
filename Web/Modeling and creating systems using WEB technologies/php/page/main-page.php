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

$sql = "SELECT * FROM news";
$result = $conn->query($sql);

?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="../../img/logo/icon.png">
    <link rel="stylesheet" href="../../css/main-page.css">
    <link rel="stylesheet" href="../../css/common.css">
    <title>Home</title>
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
                <h1>Home</h1>
            </div>
            <div class="news-container">
            <?php
                $newsItems = array();
                while ($news = $result->fetch_assoc()) {
                    $newsItems[] = $news;
                }

                $newsItems = array_reverse($newsItems);

                foreach ($newsItems as $news) {
                ?>
                    <div class="news-item">
                    <?php
                        if ($user['role'] == 'Admin') {
                            echo '<div class="close-button">✖</div>';
                            echo '<div style="display: none" class="news-id">' . $news['id'] . '</div>';
                        }
                    ?>
                        <h3 class="news-title"><?php echo $news['title']; ?></h3>
                        <img class="news-image" src="data:image/jpeg;base64,<?php echo base64_encode($news['image']); ?>" alt="News Image">
                        <p class="news-text"><?php echo $news['text']; ?></p>
                    </div>
                <?php
                }
            ?>
            </div>
        </div>

        <div class="overlay" id="overlay">
            <div class="popup" id="popup">
                <form id="news-form" method="post" action="../script/add-topic-script.php" enctype="multipart/form-data">
                    <span class="close" onclick="closePopup()">&times;</span>
                    <h2 id="popup-name">Add News</h2>

                    <label for="news-name">Title:</label>
                    <input type="text" id="news-name" name="news-name" maxlength="100" required>

                    <label for="news-text">Description:</label>
                    <textarea id="news-text" name="news-text" rows="20" cols="100" maxlength="1000" required></textarea>

                    <label for="news-image">Image:</label>
                    <input type="file" id="news-image" name="news-image" accept="image/*" required>

                    <button type="submit">Add News</button>
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
                    <a>
                        <img onclick="openPopup()" src="../../img/items/add.png" alt="add-new-topic">
                    </a>
                </div>';
            }
        ?>

    </div>

    <script src="../../js/common.js"></script>
    <script src="../../js/main-page.js"></script>
</body>

</html>

<?php
$conn->close();
?>