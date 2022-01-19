<?php

	class DbOperations{
		private $con;

		function __construct(){
			require_once dirname(__FILE__).'/DbConnect.php';
			$db = new DbConnect();
			$this->con = $db->connect();

		}

		// CRUD -> C(CREATE)
		public function createUser($id, $role, $pass, $name_surname, $s_name){
			if($this->isUserExist($id)){
				return 0;
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `User` (`id`, `role`, `password`, `name_surname`, `s_name`) VALUES (?,?,?,?,?);");
				$stmt->bind_param("issss", $id, $role, $password, $name_surname, $s_name);
				if( $stmt->execute() )
					return 1;
				else 
					return 2;
			}  
		}

		public function addInfo($id, $category, $title, $text, $photo, $u_id){	
			$stmt = $this->con->prepare("INSERT INTO `Info` (`id`, `category`, `title`, `text`, `photo`, `u_id` ) VALUES (?,?,?,?,?,?);");
			$stmt->bind_param("issssi", $id, $category, $title, $text, $photo, $u_id);
			if( $stmt->execute() )
				return true;
			else 
				return false;
		}

		public function removeInfo($id){
			$stmt = $this->con->prepare("DELETE FROM `Info` WHERE `Info`.`id` = ?");
			$stmt->bind_param("i", $id);
			$stmt->execute();
			return true;
		}

		public function userLogin($id, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id FROM User WHERE id = ? AND password = ?");  //statement
			$stmt->bind_param("is", $id,$password);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;  //if 0, means that no such user
		}

		public function getUserById($id){
			$stmt = $this->con->prepare("SELECT * FROM User WHERE id =?");
			$stmt->bind_param("i", $id);
			$stmt->execute(); 
			// FETCH_ASSOC puts the results in an array where values are mapped to their field names.
			return $stmt->get_result()->fetch_assoc();
		}

		private function isUserExist($id){
			$stmt = $this->con->prepare("SELECT id FROM User WHERE id = ?");
			$stmt->bind_param("i", $id);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0; //0 means that no such user
		}

		public function isThereSuchInfo($id){
			$stmt = $this->con->prepare("SELECT id FROM Info WHERE id = ?");
			$stmt->bind_param("i", $id);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;  //if not 0, means that there is such info.
		}

		public function getInfoById($id){
			$stmt = $this->con->prepare("SELECT id FROM Info WHERE id = ?");
			$stmt->bind_param("i", $id);
			$stmt->execute();
			// FETCH_ASSOC puts the results in an array where values are mapped to their field names.
			return $stmt->get_result()->fetch_assoc();
		} 

		public function getAllInfo(){
			// $res = mysqli_query($this->con, 'SELECT * FROM Info');
			// return $res->fetch_all(MYSQLI_ASSOC);

			// $stmt = $this->con->prepare("SELECT * FROM Info");
			// $stmt->execute();
			// return $stmt->get_result()->fetch_array(MYSQLI_ASSOC);

			// $stmt = $this->con->prepare("SELECT * FROM Info");
			// $stmt->execute();
			// $rows = array();
			// while($row = $stmt->get_result()->fetch_assoc() ){
			// 	$rows[] = $row; //adding row to rows in every iteration of loop.
			// }
			// return $rows;

			// $stmt = $this->con->prepare("SELECT * FROM Info");
			// $stmt->execute();
			// $rows = array();
			// while($row = $stmt->get_result()->fetch_array(MYSQLI_ASSOC) ){
			// 	$rows[] = $row; //adding row to rows in every iteration of loop.
			// }
			// return $rows;

			// $stmt = $this->con->prepare("SELECT * FROM Info");
			// $stmt->execute();
			// return mysqli_fetch_all($stmt->get_result(), MYSQLI_ASSOC);
			//return $stmt->get_result()->fetch_all(MYSQLI_ASSOC);

			// $stmt = $this->con->prepare("SELECT * FROM Info");
			// $stmt->execute();
			// return $stmt->get_result()->fetch_array(MYSQLI_BOTH);

			$sql = "SELECT * FROM Info";
			$result = mysqli_query($this->con, $sql);
			return mysqli_fetch_all($result, MYSQLI_ASSOC);
		}



	}