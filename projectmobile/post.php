<?php

$con = mysqli_connect("localhost","root","","photo");

$sql = 'select * from postphoto inner join user on postphoto.user_id=user.id where user_id ="'.$_POST['id'].'"';

$front = 'select Count(*) from postphoto where user_id="'.$_POST['id'].'"';
$fab = mysqli_query($con,$front);
while ($chang = mysqli_fetch_array($fab)){
	$xd = $chang['Count(*)'];
}
$res = mysqli_query($con,$sql);
$camp = array();
while($frin = mysqli_fetch_array($res)){
	array_push($camp,array("url"=>$frin["url"],
				"name"=>$frin["Name"],
				"count"=>$xd,"img"=>$frin["img"]));
}
echo json_encode($camp);
// echo $sql;

?>