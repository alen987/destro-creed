    <?php
     
    /*
    * Following code will create a new product row
    * All product details are read from HTTP Post Request
    */
     
    $date = new DateTime();
    $date->setTimezone(new DateTimeZone('Europe/Belgrade'));
     
    $bihdate = $date->format('Y-m-d H:i:s');// array for JSON response
    $response = array();
     
    // check for required fields
    if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
     
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
     
    // include db connect class
    require_once '/home/a1374054/public_html/php_data/' . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();
     
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO users(name, email, password,created_at) VALUES('$name', '$email', '$password','$bihdate')");
     
    // check if row inserted or not
    if ($result) {
    // successfully inserted into database
    $response["success"] = 1;
    $response["message"] = "New user registered.";
     
    // echoing JSON response
    echo json_encode($response);
    } else {
    // failed to insert row
    $response["success"] = 0;
    $response["message"] = "Oops! Something went wrong.";
     
    // echoing JSON response
    echo json_encode($response);
    }
    } else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
     
    // echoing JSON response
    echo json_encode($response);
    }
    ?>