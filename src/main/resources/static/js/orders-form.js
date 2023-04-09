$(function() {

    let id = localStorage.getItem("id");
    let submitted = 0;
    let palletsNr;
    let units = [];

    $(document).ready(function() {
        if (checkIfLoggedIn()) {
            validate();
            $.get("/orders-form.html/" + id, {}, function(response) {
                setOrder(response);
            })
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    let checkIfLoggedIn = function() {
        return sessionStorage.getItem("token") !== null;
    }


    $('#previous-page').on('click', function() {
        if (checkIfLoggedIn()) {
            window.location.href = "http://localhost:8080/";
            localStorage.clear();
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    })

    let setOrder = function(response) {
        $("#customer-id-div").append("<p id=\"customer-id\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + response.customerName + "</p>");
        $("#order-id-div").append("<p id=\"order-id\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + response.id + "</p>");
        palletsNr = response.palletNr;
        localStorage.setItem("pallet-nr", palletsNr);
        $("#pallets-nr-div").append("<p id=\"pallet-nr\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + palletsNr + "</p>");
        $("#date-order-div").append("<p id=\"date\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + response.date + "</p>");
        $("#submitted-div").append("<p id='submitted' class=\"font-normal text-gray-700 dark:text-gray-400\">" + submitted + "/" + palletsNr + "</p>");
    };

    let validate = function() {
        if (localStorage.getItem("submitted") === null) {
            submitted = 0;
        } else {
            submitted = localStorage.getItem("submitted");
        }
        if (localStorage.getItem("units") === null) {
            units = [];
        } else {
            units = JSON.parse(localStorage.getItem("units"));
        }
        if (localStorage.getItem("pallet-nr") === null) {
            palletsNr = 0;
        } else {
            palletsNr = localStorage.getItem("pallet-nr");
            if (palletsNr === submitted) {
                $('.submit-form-div').removeAttr("hidden");
                $("#form").css("display", "none");
                $(".next-unit-div").css("display", "none");
            }
        }
    }

    let getData = function() {
        const isEmpty = str => !str.trim().length;
        let delivery = $('#shipping').val();
        let heightInp = $("#height").val();
        let widthInp = $("#width").val();
        let lengthInp = $("#length").val();
        let weightInp = $("#weight").val();
        let type = $('#pallet').val();
        let height = isEmpty(heightInp) ? "0" : heightInp;
        let width = isEmpty(widthInp) ? "0" : widthInp;
        let length = isEmpty(lengthInp) ? "0" : lengthInp;
        let weight = isEmpty(weightInp) ? "0" : weightInp;
        let sealed = $("#sealed").is(":checked").toString();
        let fragile = $("#fragile").is(":checked").toString();
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

    $('.next-unit').on('click', function() {
        if (checkIfLoggedIn()) {
            if (submitted <= palletsNr) {
                submitted++;
                units.push(JSON.stringify(getData()));
                localStorage.setItem("submitted", submitted);
                localStorage.setItem(submitted + "unit", JSON.stringify(units));
                $("#submitted").textContent = localStorage.getItem("submitted") + "/" + palletsNr;
                erase();
            }
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    })

    let erase = function() {
        let shipping = $('#shipping');
        for (let i = 0; i < shipping.children('option').length; i++) {
            if(shipping[i] === "Tielbeke") {
                shipping.selectedIndex = i;
                break;
            }
        }
        let pallet = $('#pallet');
        for (let i = 0; i < pallet.children('option').length; i++) {
            if(pallet[i] === "EURO") {
                pallet.selectedIndex = i;
                break;
            }
        }
        $("#height").textContent = '';
        $("#width").textContent = '';
        $("#length").textContent = '';
        $("#weight").textContent = '';
        $("#sealed").checked = false;
        $("#fragile").checked = false;
    }

    $('#submit-form').on('click', function() {
        if (checkIfLoggedIn()) {
            let unitsSet = [];
            for (let i = 1; i <= submitted; i++) {
                unitsSet.push(JSON.parse(JSON.parse(localStorage.getItem(i + "unit"))));
            }
            unitsSet = JSON.stringify({'units' : unitsSet});
            postData(unitsSet);
            alert("Data was successfully saved!");
            window.location.href = "http://localhost:8080/";
            return false;
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    let postData = function(data) {
        $.ajax({
            "type": 'POST',
            "contentType": 'application/json; charset=UTF-8',
            "url": 'orders-form.html/save/' + id,
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