<?php
	require_once '../includes/DbOperations.php';

	$response = array();
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		 if( isset($_POST['id']) ){
		 	//operate the data further
		 	$db = new DbOperations();
		 	$result = $db->removeInfo(
		 		$_POST['id']
		 	);

		 	if($result == true){
		 			$response['error'] = false;
		 			$response['message'] = "Info removed successfully";
		 	}elseif($result == false){
	 			$response['error'] = true;
	 			$response['message'] = "Some error occured, try again!";
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