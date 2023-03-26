$(function() {

    $(document).ready(function() {
        loadOrders("/all")
        localStorage.clear();
    });

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
        trow.append($("<td class=\"px-6 py-4\">" +
            "<button id='edit-id-" + response.id + "' type='submit' class='edit-button'>Edit</button></td>"));
        return trow;
    };

    let loadOrders = function(url) {
        $.get(url, {}, function(response) {
            let tbody = $(".orders");
            tbody.empty();
            for (i in response) {
                let res = getResponseElement(response[i]);
                tbody.append(res);
            }
        });
    }

    let loadOrderBySearch = function(id) {
        $.get("/search/" + id, {}, function(response) {
            let tbody = $(".orders");
            tbody.empty();
            let res = getResponseElement(response);
            tbody.append(res);
        });
    }

    $('tbody').on('click', '.edit-button',  function() {
        let stringId = this.id;
        let id = stringId.substring(stringId.length - 1);
        localStorage.setItem("id", id);
        console.log(id);
        window.location.replace("/orders-form.html");
    });

    $('#all').on('click', function() {
        loadOrders("/all")
    });

    $('#pending').on('click', function() {
        loadOrders("/pending")
    });

    $('#finished').on('click', function() {
        loadOrders("/finished")
    });

    $('#search-button').on('click', function() {
        let input = $('#input-placeholder').val();
        loadOrderBySearch(input);
    })
})