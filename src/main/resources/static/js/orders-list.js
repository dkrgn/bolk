$(function() {

    $(document).ready(function() {
        loadOrders("/all")
    });

    let getResponseElement = function(response) {
        let tbody = $(".orders");
        tbody.append($("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>"));
        let trow = $("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>");
        trow.append($("<th scope=\"row\" id=\"id-order\" class=\"px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white\">" +
            response.id + "</th>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"date-order\">" +
            response.date + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"customer-order\">" +
            response.customerName + "</td>"));
        trow.append($("<td class=\"px-6 py-4\" id=\"status-order\">" +
            response.status + "</td>"));
        trow.append($("<td class=\"px-6 py-4\">" +
            "<a href=\"orders-form.html\" id=\"edit\" class=\"font-medium text-blue-600 dark:text-blue-500 hover:underline\">Edit</a></td>"));
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
            for (i in response) {
                let res = getResponseElement(response[i]);
                tbody.append(res);
                console.log(tbody);
            }
        });
    }

    $("#edit").on('click', function() {
        let id = $("#id-order").val();
        localStorage.setItem("id", id);
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