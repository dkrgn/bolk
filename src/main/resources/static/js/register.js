$(function() {

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

    let checkIfLoggedIn = function() {
        return sessionStorage.getItem("token") !== null;
    }

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