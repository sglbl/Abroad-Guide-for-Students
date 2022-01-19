<?php
	require_once '../includes/DbOperations.php';

	$response = array();
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		 if(
		 	isset($_POST['id']) and 
				isset($_POST['role']) and 
					isset($_POST['password'] ) and
						isset($_POST['name_surname']) and
							isset($_POST['s_name'])
		 ){
		 	//operate the data further
		 	$db = new DbOperations();
		 	$result = $db->createUser(
		 		$_POST['id'],
		 		$_POST['role'],
		 		$_POST['password'],
		 		$_POST['name_surname'],
		 		$_POST['s_name']
		 		);

		 	if($result == 1){
		 			$response['error'] = false;
		 			$response['message'] = "User registered successfully";
		 	}elseif($result == 2){
	 			$response['error'] = true;
	 			$response['message'] = "Some error occured, try again!";
		 	}elseif($result == 0){
		 		$response['error'] = true;
	 			$response['message'] = "This user already exists. Please choose other!";
		 	}
		 }else{
	 		$response['error'] = true;
 			$response['message'] = "Required fields are missing!!";
		 }


	}else{
		$response['error'] = true;
		$response['message'] = "Invalid Request";

	}
	echo json_encode($response);		 		
