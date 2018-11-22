<?php

$con = mysqli_connect("localhost","root","","photo");

$sql = 'UPDATE user set password="'.$_POST['password'].'" where id="'.$_POST['id'].'"';

$result = mysqli_query($con,$sql);

if($result){
  echo "Success";
}else{
  echo "Failed";
}

?>