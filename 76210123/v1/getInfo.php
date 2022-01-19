<?php
	require_once '../includes/DbOperations.php';

	if($_SERVER['REQUEST_METHOD'] == 'GET'){
		//$response['error'] = false; //no need because using isNull()
		$db = new DbOperations();
		$response = $db->getAllInfo();
		//response will get the rows on 'Info'.
	}else{
		$response['error'] = true;
		$response['message'] = "Request error detected.";
	}
	echo json_encode($response);	