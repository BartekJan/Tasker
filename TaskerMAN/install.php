<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>Installation</title>
	<meta charset="UTF-8">
	<meta name="description" content="TaskerMAN">
	<meta name="author" content="Nedialko Petrov Jake Doran">
	<link rel="stylesheet" type="text/css" href="includes/login.css">
</head>
<body>
<?php
require_once "./includes/functions.php";
$host = "";
$port = "";
$dbname = "";
$dbuser = "";
$dbpw = "";
$dbmsg = array();
if (isset($_POST["connect"])) {
	$dbname = $_POST["dbname"];
	$port = $_POST["port"];
	$host = $_POST["host"];
	$dbuser = $_POST["dbuser"];
	$dbpw = $_POST["dbpw"];
	$i = 0;
	$db = @pg_connect('dbname='.$dbname.' port='.$port.' host='.$host.' user='.$dbuser.' password='.$dbpw.'');
	if (!$db ) {
		$dbmsg[$i++] = "Can't connect to the database";
	} else {
		$problem = 0;
		// 1 tasks
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS tasks(
		id serial PRIMARY KEY NOT NULL,
		title VARCHAR(20) NOT NULL,
		startdate date NOT NULL,
		enddate date NOT NULL,
		status INT NOT NULL
		)");
		if (!$create) {
			$problem = 1;
		  $dbmsg[$i++] = "Can't create table tasks.\n";
		}
		// 2 members
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS members(
		id serial PRIMARY KEY NOT NULL,
		email VARCHAR(100),
		firstname VARCHAR(20) NOT NULL,
		surname VARCHAR(20) NOT NULL,
		password VARCHAR(300)
		)");
		if (!$create) {
			$problem = 1;
		  $dbmsg[$i++] = "Can't create table members.\n";
		}
		// 3 managers
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS managers(
		id serial PRIMARY KEY NOT NULL,
		email VARCHAR(100) NOT NULL,
		firstname VARCHAR(20) NOT NULL,
		surname VARCHAR(20) NOT NULL,
		password VARCHAR(300) NOT NULL
		)");
		if (!$create) {
			$problem = 1;
		  $dbmsg[$i++] = "Can't create table managers.\n";
		}
		// 4 taskstatuses
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS taskstatuses(
		id serial PRIMARY KEY     NOT NULL,
		text VARCHAR(20) NOT NULL
		)");
		if (!$create) {
			$problem = 1;
		  $dbmsg[$i++] = "Can't create table taskstatuses.\n";
		}
		//  5 taskmembers
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS taskmembers(
		id serial PRIMARY KEY  NOT NULL,
		member_id INT NOT NULL,
		task_id INT NOT NULL
		)");
		if (!$create) {
			$problem = 1;
		  $dbmsg[$i++] = "Can't create table taskmembers.\n";
		}
		//  6 taskelements
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS taskelements(
		id serial PRIMARY KEY     NOT NULL,
		task_id INT NOT NULL,
		text VARCHAR(500)
		)");
		if (!$create) {
			$problem = 1;
		  $dbmsg[$i++] = "Can't create table taskelements.\n";
		}
		//  7 taskelementcomments
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS taskelementcomments(
		id serial PRIMARY KEY     NOT NULL,
		member_id INT NOT NULL,
		taskelement_id INT NOT NULL,
		text VARCHAR(500)
		)");
		if (!$create) {
			$problem = 1;
		  $dbmsg[$i++] = "Can't create table taskelementcomments.\n";
		}
		//  8 taskelementmembers
		$create = pg_query($db, "CREATE TABLE IF NOT EXISTS taskelementmembers(
		id serial PRIMARY KEY     NOT NULL,
		member_id INT NOT NULL,
		taskelement_id INT NOT NULL
		)");
		if (!$create) {
		  $dbmsg[$i++] = "Can't create table taskelementmembers.\n";
		  $problem = 1;
		}
		//  9 Adding task statuses
		$insert1 = 1;
		$insert2 = 1;
		$insert3 = 1;
		$select = pg_query($db, "SELECT id FROM taskstatuses where text='Allocated'");
		if (!($row = pg_fetch_row($select))) $insert1 = pg_query($db, "INSERT into taskstatuses (text) VALUES ('Allocated')");
		$select = pg_query($db, "SELECT id FROM taskstatuses where text='Abandoned'");
	    if (!($row = pg_fetch_row($select))) $insert2 = pg_query($db, "INSERT into taskstatuses (text) VALUES ('Abandoned')");
		$select = pg_query($db, "SELECT id FROM taskstatuses where text='Completed'");
	    if (!($row = pg_fetch_row($select))) $insert3 = pg_query($db, "INSERT into taskstatuses (text) VALUES ('Completed')");
		if (!$insert1 || !$insert2 || !$insert3) {
			$problem = 1;
			$dbmsg[$i++] = "Can't insert the task statuses in table taskstatuses.\n";
		}
		if ($problem == 0) {
		$dbmsg[$i++] = "Database connected and established successfully.\n";
		$fh = fopen("./includes/db.php", 'w');

		if (false === $fh) {
			$dbmsg[$i++] = "Can't  save database for further use, please try again.\n";
		} else {
			$defines = '<?php 
			define("DB_SERVER", "'.$host.'");
			define("DB_USER", "'.$dbuser.'");
			define("DB_PASS", "'.$dbpw.'");
			define("DB_NAME", "'.$dbname.'");
			define("DB_PORT", "'.$port.'");
			?>';
			fwrite($fh, $defines);
			$dbmsg[$i++] = "Database saved successfully.\n";
		}
		fclose($fh);
		smartRedirect("index.php");
		}
		pg_close($db);  
	} 
	
	 
}

?>
		<h1>Installation of TaskerMAN </h1>
		<div class="signF">
		<br>
		<h2>Please enter database connection information below</h2>
		<form id="register" method="post"> 
	  		      <input required  value="<?php echo $host; ?>" type="text" name="host" size="20" maxlength="40" placeholder="HOST IP OR DOMAIN"><br> 
      			  <input required value="<?php echo $port; ?>" type="text" name="port" size="20" maxlength="40" placeholder="PORT"><br> 
			      <input required value="<?php echo $dbname; ?>" type="text" name="dbname" size="20" maxlength="40" placeholder="DATABASE NAME"><br> 
			      <input required  value="<?php echo $dbuser; ?>" type="text" name="dbuser" size="20" maxlength="40" placeholder="DATABASE USER" ><br> 
				  <input required  value="<?php echo $dbpw; ?>" type="text" name="dbpw" size="20" maxlength="40" placeholder="PASSWORD" ><br> 
				<?php
					foreach ($dbmsg as $k)
					echo $k;
				?>
				<br>
				<input class="link" type="submit" name="connect" value="CONNECT"> 
		</form> 
		</div>
		<br>
		<br>
		<div class="About">
		<h2> Instructions: </h2>
		</div>
		<p>
			<b>Make sure the following applies:</b><br> 
			The database is PostgreSQL.<br>
			The database user provided has permissions to INSERT , SELECT , DELETE and UPDATE.<br>
			The port of the server is open.<br><br>
			After the connection to the database is successful you will have to manually<br>
			input the link to it in the JAVA application TaskerCLI.
		</p>
	</body>
