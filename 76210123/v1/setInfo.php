<?php
	require_once '../includes/DbOperations.php';

	$response = array();
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		 if(
		 	isset($_POST['id']) and 
				isset($_POST['category']) and 
					isset($_POST['title'] ) and
						isset($_POST['text']) and
							isset($_POST['photo']) and
								isset($_POST['u_id'])
		 ){
		 	//operate the data further
		 	$db = new DbOperations();
		 	$result = $db->addInfo(
		 		$_POST['id'],
		 		$_POST['category'],
		 		$_POST['title'],
		 		$_POST['text'],
		 		$_POST['photo'],
		 		$_POST['u_id']
		 		);

		 	if($result == true){
		 			$response['error'] = false;
		 			$response['message'] = "Info added successfully";
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
