$(function() {

    /**
     * Method to call other methods when DOM object is rendered
     */
    $(document).ready(function() {
        if (checkIfLoggedIn()) {
            $('#login-li').attr("hidden", true);
            $('#logout-li').removeAttr("hidden");
            if (sessionStorage.getItem("role") === 'ADMIN') {
                $("#register-li").removeAttr("hidden");
            }
            loadOrders("/all");
            localStorage.clear();
        } else {
            alert("Your session is either expired or you're not logged in");
            window.location.href = "/login.html";
        }
    });

    /**
     * Method to check if token is valid every hour
     */
    setInterval(function() {
        $.get('/check', {"token" : sessionStorage.getItem("token")}, function(res) {
            if (res === false) {
                $('#register-li').attr("hidden", true);
                $('#login-li').removeAttr("hidden");
                $('#logout-li').attr("hidden", true);
                sessionStorage.clear();
                alert("Your session is either expired or you're not logged in");
                window.location.href = "/login.html";
            }
        });
    }, 1000 * 60 * 60);

    /**
     * Method to check if token is still valid/user is logged in
     * @returns {boolean}
     */
    let checkIfLoggedIn = function() {
        return sessionStorage.getItem("token") !== null;
    }

    /**
     * Method to append elements to DOM object with retrieved data from backend
     * @param response
     * @returns {*|jQuery|HTMLElement}
     */
    let getResponseElement = function(response) {
        let tbody = $(".orders");
        tbody.append($("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>"));
        let trow = $("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>");
        trow.append($("<th scope=\"row\" id=\"id-order-" + response.id + "\" class=\"px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white\">" +
            response.id + "</th>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"date-order-" + response.id + "\">" +
            response.date + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"customer-order-" + response.id + "\">" +
            response.customerName + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"status-order-" + response.id + "\">" +
            response.status + "</td>"));
        checkRoles(trow, response);
        return trow;
    };

    /**
     * Method to check which tole the user has and  depending on that appends specific elements to DOM object
     * @param trow
     * @param response
     */
    let checkRoles = function(trow, response) {
        //edit
        if (response.status === 'FINISHED' && sessionStorage.getItem("role") === 'EMPLOYEE') {
            trow.append($("<td class=\"px-6 py-4\">" +
                "<button id='edit-id-" + response.id + "' disabled='disabled' style='color : gray' type='submit' class='edit-button'>Edit</button></td>"));
        } else {
            trow.append($("<td class=\"px-6 py-4\">" +
                "<button id='edit-id-" + response.id + "' type='submit' value='" + response.status + "' class='edit-button'>Edit</button></td>"));
        }
        //delete
        if (sessionStorage.getItem("role") === 'ADMIN') {
            trow.append($("<td class=\"px-6 py-4\">" +
                "<button id='delete-id-" + response.id + "' type='submit' style='color: red' class='delete-button'>Delete</button></td>"));
        } else {
            trow.append($("<td class=\"px-6 py-4\">" +
                "<button disabled='disabled' type='submit' style='color: gray'>Delete</button></td>"));
        }
    }

    /**
     * Method to append Order object to DOM elements
     * @param url
     */
    let loadOrders = function(url) {
        if (checkIfLoggedIn()) {
            $.get(url, {}, function(response) {
                let tbody = $(".orders");
                tbody.empty();
                for (i in response) {
                    let res = getResponseElement(response[i]);
                    tbody.append(res);
                }
            });
        } else {
            alert("Your session is either expired or you're not logged in");
        }
    }

    /**
     * Method to retrieve Order by specific id
     * @param id
     */
    let loadOrderBySearch = function(id) {
        if (checkIfLoggedIn()) {
            $.get("/search/" + id, {}, function(response) {
                let tbody = $(".orders");
                tbody.empty();
                let res = getResponseElement(response);
                tbody.append(res);
            });
        } else {
            alert("Your session is either expired or you're not logged in");
        }
    }

    /**
     * Method to navigate to specified page on button click
     */
    $('#register-p').on('click', function() {
        window.location.href = "/register.html";
    });

    /**
     * Method to navigate to specified page on button click
     */
    $('#logout-p').on('click', function() {
        sessionStorage.clear();
        window.location.href = "/login.html";
    });

    /**
     * Method to either navigate to employee's role or admin's role pages. Another method is for admin
     * to delete Order on button click.
     */
    $('tbody')
        .on('click', '.edit-button',  function() {
        let stringId = this.id;
        let id = stringId.substring("edit-id-".length);
        let status = this.value;
        localStorage.setItem("id", id);
        console.log(id);
        if (sessionStorage.getItem("role") === 'ADMIN' && status === 'FINISHED') {
            window.location.href = "/admin-form.html";
        } else {
            window.location.href = "/orders-form.html";
        }
    })
        .on('click', '.delete-button',  function() {
        let stringId = this.id;
        let id = stringId.substring("delete-id-".length);
        let result = confirm("Are you sure you want to delete order #" + id);
        if (result) {
            $.ajax({
                "url": '/delete/' + id,
                "type": 'DELETE',
                "success": function(result) {
                    alert("Order #" + result + " was successfully deleted!");
                    location.reload();
                },
                "error": function () {
                    alert("Error deleting order #" + id);
                }
            });
        }
    });

    /**
     * Method to retrieve Orders
     */
    $('#all').on('click', function() {
        loadOrders("/all")
    });

    /**
     * Method to retrieve Orders with pending status
     */
    $('#pending').on('click', function() {
        loadOrders("/pending")
    });

    /**
     * Method to retrieve Orders with finished status
     */
    $('#finished').on('click', function() {
        loadOrders("/finished")
    });

    /**
     * Method to show requested Order by id
     */
    $('#search-button').on('click', function() {
        let input = $('#input-placeholder').val();
        loadOrderBySearch(input);
    });

    /**
     * Method to navigate to specified page on button click
     */
    $('#login-p').on('click', function() {
        window.location.href = "/login.html";
    });
})