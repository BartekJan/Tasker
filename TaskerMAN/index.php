<?php
require_once "./includes/includes.php";
require_once "./includes/functions.php";
?>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>TaskerMAN</title>
	<meta charset="UTF-8">
	<meta name="description" content="TaskerMAN">
	<meta name="author" content="Nedialko Petrov Jake Doran">
	<link rel="stylesheet" type="text/css" href="includes/login.css">
</head>
<body>
<?php
$emailreg = "";
$passreg = "";
$firstname = "";
$surname = "";
$emailsign = "";
$passsign = "";
$signmsg = "";
$regmsg = "";

if (isset($_SESSION["managerID"])) {
	smartRedirect("tasks.php");
}

if (isset($_POST["register"])) {
	$emailreg =  $_POST["emailreg"];
	$firstname = $_POST["firstname"];
	$surname = 	 $_POST["surname"];
	$passreg = 	 $_POST["passreg"];
	
	if ((filter_var($emailreg, FILTER_VALIDATE_EMAIL))!==false  && preg_match("[a-zA-Z- ]",$firstname)!==false &&
	preg_match("[a-zA-Z- ]",$surname)!==false && strlen($passreg)>=5 && preg_match("[a-zA-Z0-9@#$%^&*_-!?<>]",$passreg)!==false) {
		$filtemailreg  = pg_escape_literal($emailreg);
		$filtfirstname = pg_escape_literal($firstname);
		$filtsurname   = pg_escape_literal($surname);
		$filtpassreg   = pg_escape_literal(password_hash($passreg,PASSWORD_DEFAULT));
	
		$select = pg_query($db, "SELECT email FROM managers where email={$filtemailreg}");
		if (!$select) {
			$regmsg = "An error occurred with the database.\n"; 
		}

		if($row = pg_fetch_row($select)) {
			$regmsg = "User with that email already exists, please try another\n";
		} else {
			$insert = pg_query($db, "INSERT into managers (email,firstname,surname,password) 
			VALUES ({$filtemailreg},{$filtfirstname},{$filtsurname},{$filtpassreg}) RETURNING id");
			if (!$insert) {
				$regmsg = "An error occurred with the database.\n"; 
			} else {
				$insrow = pg_fetch_row($insert);
				$_SESSION["managerID"] = $insrow[0];
				smartRedirect("tasks.php");
			}	
		}
	}
	else {
		$regmsg = "One or more of your inputs were incorrect!";
	}
}

if (isset($_POST["signin"])) {
	$emailsign =  $_POST["emailsign"];
	$passsign  =  $_POST["passsign"];
	
	if ((filter_var($emailsign, FILTER_VALIDATE_EMAIL))!==false && preg_match("[a-zA-Z0-9@#$%^&*_-!?<>]",$passsign)!==false) {
		$filtemailsign  = pg_escape_literal($emailsign);
		
		$select = pg_query($db, "SELECT id,email,password FROM managers where email={$filtemailsign}");
		if (!$select) {
			$signmsg = "An error occurred with the database."; 
		}

		if($row = pg_fetch_row($select)) {
			if (password_verify($passsign,$row[2])!==false) {
				$_SESSION["managerID"] = $row[0];
				smartRedirect("tasks.php");
			} else {
				$signmsg =  "Wrong password of manager!";
			}
		} else {
			$signmsg = "No manager with such name exists!";
		}	
	}
	else {
		$signmsg = "One or more of your inputs were incorrect!";
	}
}
pg_close($db);
?>
		<h1>TaskerMAN</h1>
		<div class="signF">
		<br>
		<h2>Login</h2>
		<form id="signin" method="post"> 
				<input required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" value="<?php echo $emailsign; ?>" placeholder="Email Address" type="text" name="emailsign" size="20" maxlength="100"><br> 
          			<input required pattern="[a-zA-Z0-9@#$%^&*_-!?<>]*" value="<?php echo $passsign; ?>" placeholder="Password" type="password" name="passsign" size="20" maxlength="40"><br> 
				<?php
					echo "$signmsg";
				?>
				<br>
				<input class="link" type="submit" name="signin" value="SIGN IN"> 
		</form> 
		<h2>Register</h2>
		<form id="register" method="post"> 
	  		      <input required pattern="[A-Z]+[a-zA-Z- ]*" value="<?php echo $firstname; ?>" type="text" name="firstname" size="20" maxlength="20" placeholder="First Name"><br> 
      			      <input required pattern="[A-Z]+[a-zA-Z- ]*" value="<?php echo $surname; ?>" type="text" name="surname" size="20" maxlength="40" placeholder="Surname"><br> 
			      <input required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" value="<?php echo $emailreg; ?>" type="text" name="emailreg" size="20" maxlength="100" placeholder="Email Address"><br> 
			      <input required pattern="[a-zA-Z0-9@#$%^&*_-!?<>]*" value="<?php echo $passreg; ?>" type="password" name="passreg" size="20" maxlength="40" placeholder="Password" ><br> 
				<?php
					echo "$regmsg";
				?>
				<br>
				<input class="link" type="submit" name="register" value="REGISTER"> 
		</form> 
		</div>
		<br>
		<br>
		<div class="About">
		<h2> About </h2>
		</div>
		<p>
			TaskerMAN is the accompanying website to the Java program TaskerCLI. <br>
			TaskerMAN allows managers to create and edit tasks as well as creating and editing
			team members. <br> This is done through the use of forms that add and update information
			that is kept within a database.<br> <br>
			TaskerMAN is a website created for the CS22120 Group Project by Group 09. <br>This site
			doesn't reflect the views and opinions of Aberystwyth University in anyway.
		</p>
	</body>
