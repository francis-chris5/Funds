<?php

	
		//from simple book
	$fromFile = fopen("Assets.json", "r");
	echo fread($fromFile, filesize("Assets.json"));
	fclose($fromFile);
	
	/*
		//from categorized book
	$fromFile = fopen("Current Assets.json", "r");
	echo fread($fromFile, filesize("Current Assets.json"));
	fclose($fromFile);
	*/
?>