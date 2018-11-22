<?php 
$con = mysqli_connect("localhost","root","","photo");
$sql = 'select * from user where type_member="Photographer"';
$result = mysqli_query($con,$sql);
$dec = array();
while($frint=mysqli_fetch_array($result)){
	array_push($dec,array("id"=>$frint["id"],"url"=>$frint['img'],
	"name"=>$frint['Name']));
}
echo json_encode($dec);
?>