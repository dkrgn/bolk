function validate() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if(email == "admin" && password == "admin") {
        alert("login succesfully");
        return false;
    } else {
        alert("login failed");
        return false;
    }

    /*
    for(i = 0; i < obj.length; i++) {
        if(email == obj[i].email && password == obj[i].password) {
            alert("login succesfully");
            return;
        }
    }
    alert("login failed");
    */
}