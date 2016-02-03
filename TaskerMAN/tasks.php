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
$addtitle = "";
$addstart = "";
$addend = "";
$addmember = "";

$edittitle = "";
$editstart = "";
$editend = "";
$editmember = "";
$editstatus = "";

$editmsg= "";
$addmsg= "";
$addcheck = 1;
$editcheck = 1;
$search = "";
$search1 = "";
$rowsonpage =5; 

// If not logged in then login
if (!isset($_SESSION["managerID"])) {
	smartRedirect("index.php");
} else {
	$managerID = $_SESSION["managerID"];
	$select = pg_query($db, "SELECT id,email,firstname,surname,password FROM managers where id='$managerID'");
	if (!$select) {
		echo "An error occurred with the database.\n"; 
	}
	if($row = pg_fetch_row($select)) {

	// Code to delete a task and delete all traces of the task throughout the database.
		if (isset($_POST["deleteTask"])) {
			$id = $_POST["id"];
			$delete = pg_query($db, "DELETE FROM tasks WHERE id={$id}");
			$select = pg_query($db, "SELECT task_id FROM taskmembers where task_id={$id}");
			if($row = pg_fetch_row($select)) $delete = pg_query($db, "DELETE FROM taskmembers WHERE task_id={$id}");
			$select = pg_query($db, "SELECT task_id FROM taskelements where task_id={$id}");
			if($row = pg_fetch_row($select)) $delete = pg_query($db, "DELETE FROM taskelements WHERE task_id={$id}");
			$editmsg= "Task deleted";
		}
	
	// Code to edit a task
		if (isset($_POST["editLink"])) {
			$edittitle =  $_POST["edittitle"];
			$editmember = $_POST["editmem"];
			$editstart = $_POST["editstart"];
			$editend = $_POST["editend"];
			$editstatus = $_POST["editstatus"];
			$id = $_POST["id"];
			$editcheck=0;
			$check = 0;

			if (preg_match("[a-zA-Z- ]",$edittitle)!==false && $_POST["editend"] > $_POST["editstart"]){
				$filttitle = pg_escape_literal($edittitle);
				$filtstart  = pg_escape_literal($editstart);
				$filtend  = pg_escape_literal($editend);
				$filtmem  = pg_escape_literal($editmember);
				$filtstatus  = pg_escape_literal($editstatus);
			
				$select = pg_query($db, "SELECT title FROM tasks where id={$id}");
			
				if($row = pg_fetch_row($select)) {
					if ($row[0] == $edittitle) {
						$check = 1;
					} else {
						$select = pg_query($db, "SELECT title FROM tasks where title={$filttitle}");

						if($row = pg_fetch_row($select)) {
							$editmsg = "Task with that title already exists";
						} else {
							$check = 1;
						}
					}
				}
			
				// If input is valid update table
				if ($check==1) {
					$update = pg_query($db, "UPDATE tasks SET title={$filttitle},startdate={$filtstart},
					enddate={$filtend}, status={$filtstatus} WHERE id={$id}");	
					$update = pg_query($db, "UPDATE taskmembers SET member_id={$filtmem} WHERE task_id = {$id}");
					$editmsg = "Task edited successfully";
					$editcheck = 1;
				}
		}
		else {
			$editmsg = "One or more of your inputs were incorrect!";
		}
	}
	
	// Code to add a task
	if (isset($_POST["addLink"])) {
		$addcheck = 0;
		$addtitle = $_POST["addtitle"];
		$addmember = $_POST["addmem"];
		$addstart = $_POST["addstart"];
		$addend = $_POST["addend"];
		
		if (preg_match("[a-zA-Z- ]",$addtitle)!==false && $_POST["addend"] > $_POST["addstart"]){
			$filttitle  = pg_escape_literal($addtitle);
			$filtstart  = pg_escape_literal($addstart);
			$filtend  = pg_escape_literal($addend);
			$filtmem  = pg_escape_literal($addmember);
		
			$select = pg_query($db, "SELECT title FROM tasks where title={$filttitle}");

			if($row = pg_fetch_row($select)) {
				$addmsg = "Task with that title already exists";
			} else {
				$addcheck = 1;
				$insert = pg_query($db, "INSERT into tasks (title,startdate,enddate,status) 
				VALUES ({$filttitle},{$filtstart},{$filtend}, 1) RETURNING id");
				$insrow = pg_fetch_row($insert);
				$insert = pg_query($db, "INSERT into taskmembers (task_id,member_id) 
				VALUES ('$insrow[0]',{$filtmem})");
				$addmsg = "Task added successfully";
			}
		}
		else {
			$addmsg = "One or more of your inputs were incorrect!";
		}
	}

echo '<div class="mid">';
// Code for filters/searches
if  (isset($_GET["search"])) $search = $_GET["search"];
if  (isset($_GET["search1"])) $search1 = $_GET["search1"];
	?><form  class="center" method="GET">
			<select <?php echo $search; ?> name="search">
				<option value="">Task Status</option>
				<option <?php if ($search==1) echo 'selected';?> value="1">Allocated</option>
				<option <?php if ($search==3) echo 'selected';?> value="3">Completed</option>
				<option <?php if ($search==2) echo 'selected';?> value="2">Abandoned</option>
			</select>
			<select <?php echo $search1; ?> name="search1"> 
				<option value="">Choose member</option>
				<?php $select = pg_query($db, "SELECT id,firstname,surname FROM members"); 
				while ($row1 = pg_fetch_array($select)) {
					if ($search1 == $row1[0])  
						echo '<option selected value="'.$row1[0].'">'.$row1[1].' '.$row1[2].'</option>';
					else 
						echo '<option value="'.$row1[0].'">'.$row1[1].' '.$row1[2].'</option>';
				} 
			?>
			</select>			
			<input value="SEARCH" class="search" type="submit">
	  </form>
	<?php
	$countq = "SELECT COUNT(*) from tasks as t INNER JOIN taskmembers as tm on tm.task_id = t.id INNER JOIN members as m on tm.member_id=m.id ";
	$query = "SELECT t.id,m.firstname,m.surname,t.title from tasks as t INNER JOIN taskmembers as tm on tm.task_id = t.id INNER JOIN members as m on tm.member_id=m.id ";
	
	if (isset($_GET["search"])) {
		$search = $_GET["search"];
	if ($search != "") {  $query .=  "WHERE status = '$search' ";
		$countq .= "WHERE status = '$search' "; }
	}
	
	if (isset($_GET["search1"])) {
		$search1 = $_GET["search1"];
	if ($search1 != "") {  $query .=  "AND m.id = '$search1'";
		$countq .= "AND m.id = '$search1'"; }
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
	
	$tasks = pg_query($db,$query);
	$rows = pg_num_rows($tasks);
		echo '<table class="members"><tr><td>TITLE</td><td colspan="2">MEMBER</td></tr> ';
		while ($row = pg_fetch_array ($tasks)){	
			if (isset($_POST["editField"]) && ($_POST["id"]==$row[0]))
				echo '<tr class="shine">';
			else 
				echo "<tr>";
				echo '<td>' . htmlspecialchars($row[3], ENT_QUOTES) . '</td>';
				echo '<td>' . htmlspecialchars($row[1], ENT_QUOTES) . '</td>';
				echo '<td>' . htmlspecialchars($row[2], ENT_QUOTES) . '</td>';
			if (isset($_POST["editField"]) && ($_POST["id"]==$row[0]))
				echo '<td><form method="post"><input type="hidden" value="'.$row[0].'" name="id"><input class="shine" type="submit" name="editField" value="EDIT"></form></td>';
			else 
				echo '<td><form method="post"><input type="hidden" value="'.$row[0].'" name="id"><input class="editField" type="submit" name="editField" value="EDIT"></form></td>';
				echo "</tr>\n";
		}
		echo "</tbody>\n</table><br><br>";
		
?>
<br>

<!-- Code for Paging - Move forwards and backwards between pages -->
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
<!-- Code for the add form -->
<form style="text-align: right;" id="addform" method="post"> 
<?php if ((isset($_POST["addField"])) || $addcheck == 0) { ?>
	<input pattern="[a-zA-Z0-9@#$%^&*_-!?<>]*" value="<?php echo $addtitle; ?>" type="text" name="addtitle" size="20" maxlength="20" placeholder="Task Title" ><br> <br>
	<select required name="addmem">
	<option value="">Choose member</option>
	<?php $select = pg_query($db, "SELECT id,firstname,surname FROM members"); 
		while ($row = pg_fetch_array($select)) {
			echo '<option value="'.$row[0].'">'.$row[1].' '.$row[2].'</option>';
		} 
	?>
	</select>
	<br><br>
	<input required value="<?php echo $addstart; ?>" type="date" name="addstart" size="20" placeholder="Start Date dd/mm/yy"><br><br>
	<input required value="<?php echo $addend; ?>" type="date" name="addend" size="20" placeholder="End Date dd/mm/yy"><br><br>
	<?php echo $addmsg; ?><br><br>
	<input class="addLink" type="submit" name="addLink" value="ADD"> 
<?php
	} else { 
?>
	<input class="addField" type="submit" name="addField" value="ADD TASK"> <br><br>
	<?php echo $addmsg; ?>
	<?php if ($editcheck == 1) { ?>
	<?php echo $editmsg; ?>
	<?php } ?>
</form>
<?php
if ((isset($_POST["editField"])) || $editcheck == 0) {
	$select	= pg_query($db, "SELECT member_id FROM taskmembers where task_id={$_POST["id"]} ORDER BY  id DESC LIMIT 1");
	$mem_id = pg_fetch_row($select);
	$select = pg_query($db, "SELECT title,startdate,enddate,status FROM tasks where id={$_POST["id"]}");
			if($row = pg_fetch_row($select)) {
?>
	<!-- Code for the edit form -->
    <form style="margin-right:10px;text-align: right;" method="post">
		<input pattern="[a-zA-Z0-9@#$%^&*_-!?<>]*" value="<?php echo $row[0]; ?>" type="text" name="edittitle" size="20" maxlength="20" placeholder="Task Title" ><br> <br>
		<select required name="editmem">
			<option value="">Choose member</option>
			<?php $select = pg_query($db, "SELECT id,firstname,surname FROM members"); 
			while ($row1 = pg_fetch_array($select)) {
				if ($mem_id[0] == $row1[0])  
					echo '<option selected value="'.$row1[0].'">'.$row1[1].' '.$row1[2].'</option>';
				else 
					echo '<option value="'.$row1[0].'">'.$row1[1].' '.$row1[2].'</option>';
			} 
			?>
		</select>
		<br><br>
		<input required value="<?php echo $row[1]; ?>" type="date" name="editstart" size="20" placeholder="Start Date dd/mm/yy"><br><br>
		<input required value="<?php echo $row[2]; ?>" type="date" name="editend" size="20" placeholder="End Date dd/mm/yy"><br><br>
		<select required  name="editstatus">
		<?php $select = pg_query($db, "SELECT id,text FROM taskstatuses"); 
			while ($row2 = pg_fetch_array($select)) {
				if ($row2[0] == $row[3])  
					echo '<option selected value="'.$row2[0].'">'.$row2[1].'</option>';
				else 
					echo '<option value="'.$row2[0].'">'.$row2[1].'</option>';
			} 
		?>	
		</select><br>
		<input type="hidden" value="<?php echo $_POST['id']; ?>" name="id"><br>
		<input class="deleteMem" type="submit" name="deleteTask" value="DELETE"> <br>
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
