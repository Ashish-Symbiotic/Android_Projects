<?php
$user=$_POST['user_name'];
$pass=$_POST['password'};
$db="SagarMarbles";
$url="localhost";
$userP="root";
$passP="";
$conn=mysqli_connect($url,$userP,$passP,$db);
if(!$conn)
{
$db1="mysql";
$conn=mysqli_connect($url,$userP,$passP,$db1);
if(!$conn)
{
echo"hello";
}


}
?>