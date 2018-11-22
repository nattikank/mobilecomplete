<?php

$con = mysqli_connect("localhost","root","","photo");

$sql = 'INSERT INTO postphoto(url,user_id) VALUES("'.$_POST['url'].'"
,"'.$_POST['user_id'].'")';

$result = mysqli_query($con,$sql);
if($result){
  echo "Success";
}else{
  echo "Failed";
}



?>