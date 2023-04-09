$(function() {

    let unit = {};

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

    let checkIfLoggedIn = function() {
        return sessionStorage.getItem("token") !== null;
    }

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

    $('#p-page').on('click', function() {
        if (checkIfLoggedIn()) {
            window.location.href = "/admin-form.html";
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

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