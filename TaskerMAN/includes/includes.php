<?php
session_save_path("/tmp");
session_start();
$db = pg_connect('dbname=cs27020_15_16 port=5432 host=db.dcs.aber.ac.uk user=nep5 password=groupXYZ');
?>
