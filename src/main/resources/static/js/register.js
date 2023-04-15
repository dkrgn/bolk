$(function() {

    /**
     * Method to retrieve data from input fields and call other methods
     */
    $('#register-button').on('click', function() {
        if (checkIfLoggedIn()) {
            let name = $('#name').val();
            let email = $('#email').val();
            let password = $('#password').val();
            let confirm = $('#confirm-password').val();

            let req = {"name" : name, "email" : email, "password" : password};
            let json = JSON.stringify(req);
            if (password !== confirm) {
                alert("Passwords are not the same");
                location.reload();
            }
            register(json);
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
        return false;
    });

    /**
     * Method to navigate to specified page on button click
     */
    $('#previous-page').on('click', function() {
        if (checkIfLoggedIn()) {
            window.location.href = "http://localhost:8080/";
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    /**
     * Method to check if token is still valid/user is logged in
     * @returns {boolean}
     */
    let checkIfLoggedIn = function() {
        return sessionStorage.getItem("token") !== null;
    }

    /**
     * Method to send POST request to save new User in database
     * @param json
     */
    let register = function(json) {
        $.ajax({
            "type": 'POST',
            "contentType": 'application/json; charset=UTF-8',
            "url": '/register',
            "data": json,
            "dataType":'json',
            "cache" : false,
            "processData" : false,
            "success": function(response) {
                alert("Employee registered successfully! [" + response.email + "]")
                window.location.href = "http://localhost:8080/";
            },
            "error": function() {
                alert("Error registering employee: email either is taken or invalid");
                location.reload();
            }
        });
    }
})