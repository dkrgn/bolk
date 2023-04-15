$(function() {

    /**
     * Method to get data from inputs and call validate() method
     */
    $("#login-button").on('click', function() {
        let email = $("#email").val();
        let password = $("#password").val();
        validate(email, password);
        return false;
    });

    /**
     * Method to send POST request to backend with gathered data
     * @param email
     * @param password
     */
    let validate = function(email, password) {
        if (email.length > 0 && password > password.length) {
            let req = {'email' : email, 'password' : password};
            $.ajax({
                "type": 'POST',
                "contentType": 'application/json; charset=UTF-8',
                "url": 'login.html/check',
                "data": JSON.stringify(req),
                "dataType":'json',
                "cache" : false,
                "processData" : false,
                "success": function(response) {
                    console.log(response);
                    let token = response.token;
                    let role = response.role;
                    sessionStorage.setItem("token", token);
                    sessionStorage.setItem("role", role);
                    window.location.href = "http://localhost:8080/";
                },
                "error": function(jqXHR, status, err) {
                    alert("Error with login");
                }
            });
        }
    }
});