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
    $email = $_GET['email'];
     
    // get all products from products table
    $result = mysql_query("SELECT *FROM users WHERE email=$email") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
     
    while ($row = mysql_fetch_array($result)) {
    // temp user array
    $category = array();
     
    $category["uid"] = $row["uid"];
    $category["name"] = $row["name"];
    $category["email"] = $row["email"];
    $category["password"] = $row["password"];
    // push single product into final response array
    array_push($response["products"], $category);
    }
    // success
    $response["success"] = 1;
     
    // echoing JSON response
    echo json_encode($response);
    } else {
    // user not found
    $response["success"] = 0;
    $response["message"] = "User not found";
     
    // echo no users JSON
    echo json_encode($response);
    }
    ?>