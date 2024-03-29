$(function() {
    let id = localStorage.getItem("id");
    let palletsNr;

    /**
     * Method to call other method when the DOM object is loaded and rendered
     */
    $(document).ready(function() {
        if (checkIfLoggedIn()) {
            $.get("/admin-form.html/" + id, {}, function(response) {
                setOrder(response);
            })
            $.get("/admin-form.html/units/" + id, {}, function(response) {
                setUnits(response);
            })
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    /**
     * Method to check if the token is saved into local storage, meaning User is logged in
     * @returns {boolean}
     */
    let checkIfLoggedIn = function() {
        return sessionStorage.getItem("token") !== null;
    }

    /**
     * Method to check if token is valid every hour
     */
    setInterval(function() {
        $.get('/check', {"token" : sessionStorage.getItem("token")}, function(res) {
            if (res === false) {
                sessionStorage.clear();
                alert("Your session is either expired or you're not logged in");
                window.location.href = "/login.html";
            }
        });
    }, 1000 * 60 * 60);

    /**
     * Method to navigate to the previous page on click
     */
    $('#previous-page').on('click', function() {
        if (checkIfLoggedIn()) {
            window.location.href = "http://localhost:8080/";
            localStorage.clear();
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    /**
     * Method provides functionality to navigate to edit unit page to edit particular unit, or to delete particular unit
     * on associated button click
     */
    $("tbody")
        .on('click', '.edit-button', function() {
        if (checkIfLoggedIn()) {
            let stringId = this.id;
            let id = stringId.substring("edit-id-".length);
            localStorage.setItem("unit-id", id);
            console.log(id);
            window.location.href = "/edit-unit.html";
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    })
        .on('click', '.delete-button',  function() {
            if (checkIfLoggedIn()) {
                let stringId = this.id;
                let id = stringId.substring("delete-id-".length);
                let result = confirm("Are you sure you want to delete unit #" + id);
                if (result) {
                    $.ajax({
                        "url": '/admin-form.html/delete/' + id,
                        "type": 'DELETE',
                        "success": function (result) {
                            alert("Unit #" + result + " was successfully deleted!");
                            location.reload();
                        },
                        "error": function () {
                            alert("Error deleting unit #" + id);
                        }
                    });
                }
            } else {
                alert("Your session is either expired or you're not logged in");
                window.location.href = "/login.html";
            }
    });

    /**
     * Method to build HTML elements and append them to the DOM element with Order data
     * @param response
     */
    let setOrder = function(response) {
        $("#customer-id-div").append("<p id=\"customer-id\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + response.customerName + "</p>");
        $("#order-id-div").append("<p id=\"order-id\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + response.id + "</p>");
        palletsNr = response.palletNr;
        localStorage.setItem("pallet-nr", palletsNr);
        $("#pallets-nr-div").append("<p id=\"pallet-nr\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + palletsNr + "</p>");
        $("#date-order-div").append("<p id=\"date\" class=\"font-normal text-gray-700 dark:text-gray-400\">" + response.date + "</p>");
    };

    /**
     * Method to append Unit elements to the DOM element retrieved from backend -> database
     * @param response
     */
    let setUnits = function(response) {
        let tbody = $(".units");
        tbody.empty();
        for (i in response) {
            let res = getResponseElement(response[i]);
            tbody.append(res);
        }

    };

    /**
     * Method to append Unit elements to DOM element to render them on page
     * @param response
     * @returns {*|jQuery|HTMLElement}
     */
    let getResponseElement = function(response) {
        let tbody = $(".units");
        tbody.append($("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>"));
        let trow = $("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>");
        trow.append($("<th scope=\"row\" id=\"id-unit-" + response.id + "\" class=\"px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white\">" +
            response.id + "</th>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"delivery-unit-" + response.id + "\">" +
            response.deliveryCompany + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"type-unit-" + response.id + "\">" +
            response.type + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"height-unit-" + response.id + "\">" +
            response.height + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"length-unit-" + response.id + "\">" +
            response.length + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"width-unit-" + response.id + "\">" +
            response.width + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"weight-unit-" + response.id + "\">" +
            response.weight + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"sealed-unit-" + response.id + "\">" +
            response.sealed + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"fragile-unit-" + response.id + "\">" +
            response.fragile + "</td>"));
        trow.append($("<td class=\"px-6 py-4\">" +
            "<button id='edit-id-" + response.id + "' type='submit' class='edit-button'>Edit</button></td>"));
        trow.append($("<td class=\"px-6 py-4\">" +
            "<button id='delete-id-" + response.id + "' type='submit' style='color: red' class='delete-button'>Delete</button></td>"));
        return trow;
    };
});