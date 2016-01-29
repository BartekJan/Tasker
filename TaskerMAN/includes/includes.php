<?php
require_once "db.php";
session_save_path("/tmp");
session_start();
$db = pg_connect('dbname='.DB_NAME.' port='.DB_PORT.' host='.DB_SERVER.' user='.DB_USER.' password='.DB_PASS);
?>