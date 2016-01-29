<?php
require_once "includes.php";
require_once "functions.php";
?>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>TaskerMAN</title>
	<meta charset="UTF-8">
	<meta name="description" content="TaskerMAN">
	<meta name="author" content="Nedialko Petrov Jake Doran">
    <link rel="stylesheet" type="text/css" href="includes/style.css">
</head>
<body>
<?php
if (isset($_POST['logout'])) {
	logout();
}
?>
