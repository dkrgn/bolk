$(function() {

    let unit = {};

    /**
     * Method to call other methods when DOM element is rendered
     */
    $(document).ready(function() {
        if (checkIfLoggedIn()) {
            $.get("/edit-unit.html/" + localStorage.getItem("unit-id"), {}, function(response) {
                appendDate(response);
            });
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    /**
     * Method to check if token is stil valid/user still logged in and token has not expired
     * @returns {boolean}
     */
    let checkIfLoggedIn = function() {
        return sessionStorage.getItem("token") !== null;
    }

    /**
     * Method to set data retrieved from backend to DOM elements
     * @param response
     */
    let appendDate = function(response) {
        $("#unit-id").text("Unit ID: " + response.id);
        unit["id"] = response.id;
        $("#cur-del-com").text("Current company: " + response.deliveryCompany);
        unit["deliveryCompany"] = response.deliveryCompany;
        $("#cur-type").text("Current type: " + response.type);
        unit["type"] = response.type;
        $("#cur-height").text("Current height: " + response.height);
        unit["height"] = response.height;
        $("#cur-length").text("Current length: " + response.length);
        unit["length"] = response.length;
        $("#cur-width").text("Current width: " + response.width);
        unit["width"] = response.width;
        $("#cur-weight").text("Current weight: " + response.weight);
        unit["weight"] = response.weight;
        $("#cur-fragile").text("Is fragile: " + response.fragile);
        unit["fragile"] = response.fragile;
        $("#cur-sealed").text("Is sealed: " + response.sealed);
        unit["sealed"] = response.sealed;
    };

    /**
     * Method to navigate to specified page on button click
     */
    $('#p-page').on('click', function() {
        if (checkIfLoggedIn()) {
            window.location.href = "/admin-form.html";
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    /**
     * Method to retrieve data from input fields
     * @returns {{sealed: (boolean|*), length: (*|jQuery|string), width: (*|jQuery|string), weight: (*|jQuery|string), fragile: (boolean|*), deliveryCompany: (*|string|jQuery), type: (*|string|jQuery), height: (*|jQuery|string)}}
     */
    let getData = function() {
        let delivery = $('#shipping').val() === "default" ? unit["deliveryCompany"] : $('#shipping').val();
        let type = $('#pallet').val() === "default" ? unit["type"] : $('#pallet').val();
        let heightInp = $("#height").val();
        let lengthInp = $("#length").val();
        let widthInp = $("#width").val();
        let weightInp = $("#weight").val();
        let height = heightInp === '' ? unit["height"] : heightInp;
        let width = widthInp === '' ? unit["width"] : widthInp;
        let length = lengthInp === '' ? unit["length"] : lengthInp;
        let weight = weightInp === '' ? unit["weight"] : weightInp;
        let sealedVal = function () {
            if (unit["sealed"] === true) {
                return false;
            } else {
                return true;
            }
        }
        let fragileVal = function() {
            if (unit["fragile"] === true) {
                return false;
            } else {
                return true;
            }
        }
        let sealed = $("#sealed").is(":checked") === true ? sealedVal() : unit["sealed"];
        let fragile = $("#fragile").is(":checked") === true ? fragileVal() : unit["fragile"];
        return {
            "deliveryCompany" : delivery,
            "fragile" : fragile,
            "height" : height,
            "length" : length,
            "sealed" : sealed,
            "type" : type,
            "weight" : weight,
            "width": width
        };
    }

    /**
     * Method to submit data and send it to backend on button click
     */
    $('#submit-form').on('click', function() {
        if (checkIfLoggedIn()) {
            postData(JSON.stringify(getData()));
            alert("Data was successfully updated!");
            window.location.href = "/admin-form.html";
            return false;
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    /**
     * Method to send POST request to backend to save data
     * @param data
     */
    let postData = function(data) {
        $.ajax({
            "type": 'PUT',
            "contentType": 'application/json; charset=UTF-8',
            "url": 'edit-unit.html/update/' + localStorage.getItem("unit-id"),
            "data": data,
            "dataType":'json',
            "success": function(data, status, jqXHR) {
                console.log('response: ' + data + ' status: ' + status + ' object: ' + jqXHR);
            },
            "error": function(jqXHR, status, err) {
                console.log('object: ' +  jqXHR  + ' status: ' + status + ' error: ' + err);
            }
        });
    }
})