<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once '/home/a1374054/public_html/php_data/' . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT *FROM Items WHERE cat_id=04") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $category = array();

	$category["id"] = $row["id"];        	
	$category["naziv_artikla"] = $row["naziv_artikla"];
	$category["slika_artikla"] = $row["slika_artikla"]; 
	$category["kolicina"] = $row["kolicina"];
	$category["cijena"] = $row["cijena"];
	$category["cat_id"] = $row["cat_id"];
        // push single product into final response array
        array_push($response["products"], $category);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>