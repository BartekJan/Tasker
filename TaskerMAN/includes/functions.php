<?php 
function smartRedirect($location) {
				$host  = $_SERVER['HTTP_HOST'];
				$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
				header("Location: http://$host$uri/$location");
				exit;
}

function logout() {
		$_SESSION = array();
		session_destroy();
		smartRedirect("index.php");
}
?>