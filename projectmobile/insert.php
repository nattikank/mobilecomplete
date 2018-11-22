<?php


$con = mysqli_connect("localhost","root","","photo");

$sql = 'INSERT INTO user(username,password,Name,tel,idcard,address,type_member,img) VALUES("'.$_POST['username'].'","'.$_POST['password'].'","'.$_POST['Name'].'","'.$_POST['tel'].'","'.$_POST['idcard'].'","'.$_POST['address'].'","'.$_POST['type_member'].'","'.$_POST['img'].'")';

$result = mysqli_query($con,$sql);
if($result){
  echo "Success";
}else{
  echo "Failed";
}


?>