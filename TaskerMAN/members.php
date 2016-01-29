<?php
require_once "./includes/header.php";
$page = basename(__FILE__);
?>
<h1>TaskerMAN</h1>
<form  id="menuform" method="post">
<p>
<a <?php if ($page=="tasks.php") echo 'class="currentLink"';?> href="tasks.php">TASKS</a><br><br>
<a <?php if ($page=="members.php") echo 'class="currentLink"';?> href="members.php">MEMBERS</a><br><br>
<input class="logoutLink" type="submit" name="logout" value="LOGOUT"> 
</p>
</form>

<?php
$addfname = "";
$addsname = "";
$addemail = ""; 
$addpass = "";
$editfname = "";
$editsname = "";
$editemail = "";
$editpass = "";
$editmsg= "";
$addmsg= "";
$addcheck = 1;
$editcheck = 1;
$search = "";
$rowsonpage = 5; 

if (!isset($_SESSION["managerID"])) {
	smartRedirect("index.php");
} else {
	$managerID = $_SESSION["managerID"];
	$select = pg_query($db, "SELECT id,email,firstname,surname,password FROM managers where id='$managerID'");
	if (!$select) {
		echo "An error occurred with the database.\n"; 
	}
	if($row = pg_fetch_row($select)) {

	if (isset($_POST["deleteMem"])) {
		$id = $_POST["id"];
		$delete = pg_query($db, "DELETE FROM members WHERE id={$id}");
		$select = pg_query($db, "SELECT member_id FROM taskelementcomments where member_id={$id}");
	    if($row = pg_fetch_row($select)) $delete = pq_query($db, "DELETE FROM taskelementcomments WHERE member_id={$id}");
		$select = pg_query($db, "SELECT member_id FROM taskelementmembers where member_id={$id}");
	    if($row = pg_fetch_row($select)) $delete = pq_query($db, "DELETE FROM taskelementmembers WHERE member_id={$id}");
		$select = pg_query($db, "SELECT member_id FROM taskmembers where member_id={$id}");
	    if($row = pg_fetch_row($select)) $delete = pq_query($db, "DELETE FROM taskmembers WHERE member_id={$id}");
		$editmsg= "Member deleted";
	}

	if (isset($_POST["editLink"])) {
		$editemail =  $_POST["editemail"];
		$editfname = $_POST["editfname"];
		$editsname = $_POST["editsname"];
		$editpass = $_POST["editpass"];
		$id = $_POST["id"];
		$editcheck=0;
		$check = 0;

		if ((filter_var($editemail, FILTER_VALIDATE_EMAIL))!==false  && preg_match("[a-zA-Z- ]",$editfname)!==false &&
		preg_match("[a-zA-Z- ]",$editsname)!==false && preg_match("[a-zA-Z0-9@#$%^&*_-!?<>]",$editpass)!==false) {
			$filtemail  = pg_escape_literal($editemail);
			$filtfirstname = pg_escape_literal($editfname);
			$filtsurname   = pg_escape_literal($editsname);
			$filtpass  = pg_escape_literal($editpass);

			$select = pg_query($db, "SELECT email FROM members where id={$id}");
			
			if($row = pg_fetch_row($select)) {
				if ($row[0] == $editemail) {
					$check = 1;
				} else {
				 	$select = pg_query($db, "SELECT email FROM members where email={$filtemail}");

					if($row = pg_fetch_row($select)) {
						$editmsg = "Member with that email already exists";
					} else {
					$check = 1;
					}
			}
			}

		

			if ($check==1) {
				$update = pg_query($db, "UPDATE members SET email={$filtemail},firstname={$filtfirstname},
				surname={$filtsurname},password={$filtpass} WHERE id={$id}");	
				$editmsg = "Member edited successfully";
				$editcheck = 1;
			}
		}
		else {
			$editmsg = "One or more of your inputs were incorrect!";
		}
	}

	if (isset($_POST["addLink"])) {
		$addcheck = 0;
		$addemail =  $_POST["addemail"];
		$addfname = $_POST["addfname"];
		$addsname = $_POST["addsname"];
		$addpass = $_POST["addpass"];
		
		if ((filter_var($addemail, FILTER_VALIDATE_EMAIL))!==false  && preg_match("[a-zA-Z- ]",$addfname)!==false &&
		preg_match("[a-zA-Z- ]",$addsname)!==false  && preg_match("[a-zA-Z0-9@#$%^&*_-!?<>]",$addpass)!==false) {
			$filtemail  = pg_escape_literal($addemail);
			$filtfirstname = pg_escape_literal($addfname);
			$filtsurname   = pg_escape_literal($addsname);
			$filtpass  = pg_escape_literal($addpass);
		
			$select = pg_query($db, "SELECT email FROM members where email={$filtemail}");

			if($row = pg_fetch_row($select)) {
				$addmsg = "Member with that email already exists";
			} else {
				$addcheck = 1;
				$insert = pg_query($db, "INSERT into members (email,firstname,surname,password) 
				VALUES ({$filtemail},{$filtfirstname},{$filtsurname},{$filtpass}) RETURNING id");
				$addmsg = "Member added successfully";

			}
		}
		else {
			$addmsg = "One or more of your inputs were incorrect!";
		}
	}

echo '<div class="mid">';
if  (isset($_GET["search"])) $search = $_GET["search"];
	?><form class="center" method="GET">
			<input value="<?php echo $search; ?>" placeholder="Search for any attribute" pattern="[a-zA-Z0-9@#$%^&*_-!?<>]*" type="text" name="search">
			<input value="SEARCH" class="search" type="submit">
		
	  </form>
	<?php
	$countq = "SELECT COUNT(*) from members ";
	$query = "SELECT id,email,firstname,surname FROM members ";
	if  (isset($_GET["search"])) {
	$search = $_GET["search"];
	if ($search != "") {  $query .=  "WHERE email LIKE '%$search%' OR  firstname LIKE '%$search%' OR surname LIKE '%$search%' ";
	$countq .= "WHERE email LIKE '%$search%' OR  firstname LIKE '%$search%' OR surname LIKE '%$search%'"; }
	}
	$countarr = pg_fetch_row(pg_query($db,$countq));
	$count = $countarr[0];
	$query .= "ORDER BY id DESC ";
	if ($count > $rowsonpage) {
		$pages = ceil($count / $rowsonpage);
		if (!isset($_GET["page"])) $_GET["page"]=0;
		$offset = $_GET["page"]*$rowsonpage;
		$query .= "LIMIT '$rowsonpage' OFFSET '$offset'";
	} else {
		unset($_GET["page"]);
	}
	$members = pg_query($db,$query);
	$rows = pg_num_rows($members);
		echo '<table class="members"><tr><td colspan="2">MEMBER</td><td>E-MAIL</td></tr> ';
		while ($row = pg_fetch_array ($members))
		{	if (isset($_POST["editField"]) && ($_POST["id"]==$row[0]))
			echo '<tr class="shine">';
			else 
			echo "<tr>";
			echo '<td>' . htmlspecialchars($row[2], ENT_QUOTES) . '</td>';
			echo '<td>' . htmlspecialchars($row[3], ENT_QUOTES) . '</td>';
			echo '<td>' . htmlspecialchars($row[1], ENT_QUOTES) . '</td>';
			if (isset($_POST["editField"]) && ($_POST["id"]==$row[0]))
			echo '<td><form method="post"><input type="hidden" value="'.$row[0].'" name="id"><input class="shine" type="submit" name="editField" value="EDIT"></form></td>';
			else 
			echo '<td><form method="post"><input type="hidden" value="'.$row[0].'" name="id"><input class="editField" type="submit" name="editField" value="EDIT"></form></td>';
			echo "</tr>\n";
		}
		echo "</tbody>\n</table><br><br>";
		
?>
<br>

<?php  if (isset($_GET["page"])) { ?>
<div>

<?php if ($_GET["page"]>0) {
?> <form class="cancerousforms" method="GET"> <?php
	$lower = $_GET["page"]-1;
	?> 
	
<?php if (isset($_GET["search"])) { ?> 
<input type="hidden" name="search" value='<?php echo $_GET["search"];?>'>
<?php } ?>
<input type="hidden" name="page" value='<?php echo $lower;?>'>
<input class="page" type="submit" name="leftPage" value="<<"> 
</form>
<?php } ?>


<form class="cancerousforms" method="GET">
<?php if (isset($_GET["search"])) { ?> 
<input type="hidden" name="search" value='<?php echo $_GET["search"];?>'>
<?php } ?>
<input type="hidden" name="page" value="0">
<input class="page" type="submit" name="First" value="<?php echo $_GET["page"]+1; ?>/<?php echo $pages; ?>">
</form>



<?php  if ($_GET["page"]<$pages-1) {
	?> <form class="cancerousforms" method="GET"> <?php
		$higher = $_GET["page"]+1; ?> 
<?php if (isset($_GET["search"])) { ?> 
<input type="hidden" name="search" value='<?php echo $_GET["search"];?>'>
<?php }  ?>
<input type="hidden" name="page" value='<?php echo $higher;?>'>
<input class="page" type="submit" name="rightPage" value=">>"> 
</form>
<?php } ?>

</div>
<?php }  ?> 

</div>

<div class="right">
<form style="text-align: right;" id="addform" method="post"> 
<?php if ((isset($_POST["addField"])) || $addcheck == 0) { ?>
    <input required pattern="[A-Z]+[a-zA-Z- ]*" value="<?php echo $addfname; ?>" type="text" name="addfname" size="20" maxlength="20" placeholder="First Name"><br><br>
    <input required pattern="[A-Z]+[a-zA-Z- ]*" value="<?php echo $addsname; ?>" type="text" name="addsname" size="20" maxlength="40" placeholder="Surname"><br><br>
	<input required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" value="<?php echo $addemail; ?>" type="text" name="addemail" size="20" maxlength="100" placeholder="Email Address"><br><br>
	<input pattern="[a-zA-Z0-9@#$%^&*_-!?<>]*" value="<?php echo $addpass; ?>" type="password" name="addpass" size="20" maxlength="40" placeholder="Password" ><br> <br>
	<?php echo $addmsg; ?><br><br>
	<input class="addLink" type="submit" name="addLink" value="ADD"> 
<?php
} else { 

?>
<input class="addField" type="submit" name="addField" value="ADD MEMBER"> <br><br>
<?php echo $addmsg; ?>
<?php if ($editcheck == 1) { ?>
<?php echo $editmsg; ?>
<?php } ?>
</form>
<?php
if ((isset($_POST["editField"])) || $editcheck == 0) {
	$select = pg_query($db, "SELECT firstname,surname,email,password FROM members where id={$_POST["id"]}");

			if($row = pg_fetch_row($select)) {
?>
    <form style="margin-right:10px;text-align: right;" method="post">
    <input required pattern="[A-Z]+[a-zA-Z- ]*" value="<?php echo $row[0]; ?>" type="text" name="editfname" size="20" maxlength="20" placeholder="First Name"><br><br>
    <input required pattern="[A-Z]+[a-zA-Z- ]*" value="<?php echo $row[1]; ?>" type="text" name="editsname" size="20" maxlength="40" placeholder="Surname"><br><br>
	<input required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" value="<?php echo $row[2]; ?>" type="text" name="editemail" size="20" maxlength="100" placeholder="Email Address"><br><br>
	<input pattern="[a-zA-Z0-9@#$%^&*_-!?<>]*" value="<?php echo $row[3]; ?>" type="password" name="editpass" size="20" maxlength="40" placeholder="Password" ><br> 
	<input type="hidden" value="<?php echo $_POST['id']; ?>" name="id"><br>
	<input class="deleteMem" type="submit" name="deleteMem" value="DELETE"> <br>
	<?php echo $editmsg; ?><br>
	<input class="editLink" type="submit" name="editLink" value="SAVE"> 
   </form>
<?php
}
}
?>
<?php } ?>

</div>

<?php	
	} else {
		logout();
	}
}
pg_close($db);
?>
</body>
</html>
