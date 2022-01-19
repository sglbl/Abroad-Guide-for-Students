<?php
	require_once '../includes/DbOperations.php';

	$response = array();
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		 if(isset($_POST['id']) and isset($_POST['password'])){
		 	$db = new DbOperations();
		 	if($db->userLogin($_POST['id'], $_POST['password'])){
		 		$user = $db->getUserById($_POST['id']);
		 		$response['error'] = false;
		 		$response['id'] = $user['id']; 
		 		$response['name_surname'] = $user['name_surname'];
		 		$response['role'] = $user['role'];
		 	}else{
		 		$response['error'] = true;
		 		$response['message'] = "Invalid id or password";
		 	}

		 }else{
		 	$response['error'] = true;
	 		$response['message'] = "Required fields are missing!";
		 }


	}
	echo json_encode($response);	