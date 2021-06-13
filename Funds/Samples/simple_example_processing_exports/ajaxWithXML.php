<?php
	header("Content-type: text/xml; charset=utf-8");
	
	/*
		//asset file
	$fromFile = fopen("Assets.xml", "r");
	echo fread($fromFile, filesize("Assets.xml"));
	fclose($fromFile);
	*/
	
		//current asset file
	$fromFile = fopen("Current Assets.xml", "r");
	echo fread($fromFile, filesize("Current Assets.xml"));
	fclose($fromFile);
?>