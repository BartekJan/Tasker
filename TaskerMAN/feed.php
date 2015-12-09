<?php
require_once "./includes/includes.php";
require_once "./includes/functions.php";

if (isset($_POST['logout'])) {
	logout();
}

if (!isset($_SESSION["managerID"])) {
	smartRedirect("index.php");
} else {
	$managerID = $_SESSION["managerID"];
	$select = pg_query($db, "SELECT id,email,firstname,surname,password FROM managers where id='$managerID'");
	if (!$select) {
		echo "An error occurred with the database.\n"; 
	}
	if($row = pg_fetch_row($select)) {
?>
<html>
<head>
</head>
<body>
<?php 
echo "Your id is: $row[0]<br>";
echo "Your email is $row[1]<br>";
echo "Your name is: $row[2] $row[3]<br>";
echo "Your hashed and salted pass is: $row[4]<br>";
?>
<form method="post">
	<input name="logout" type="submit" value="Logout">
</form>
</body>
<?php
	} else {
		logout();
	}
}
pg_close($db);
?>
