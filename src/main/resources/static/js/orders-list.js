$(function() {

    $(document).ready(function() {
        loadOrders("/all")
    })

    let getResponseElement = function(response) {
        let tbody = $(".orders");
        tbody.append($("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>"));
        let trow = $("<tr class=\"bg-white border-b dark:bg-gray-900 dark:border-gray-700\"></tr>");
        trow.append($("<th scope=\"row\" class=\"px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white\">" +
            response.id + "</th>"));
        trow.append($("<td class=\"px-6 py-4\">" +
            response.date + "</td>"));
        trow.append($("<td class=\"px-6 py-4\">" +
            response.customerName + "</td>"));
        trow.append($("<td class=\"px-6 py-4\">" +
            response.status + "</td>"));
        trow.append($("<td class=\"px-6 py-4\">" +
            "<a href=\"#\" class=\"font-medium text-blue-600 dark:text-blue-500 hover:underline\">Edit</a></td>"));
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
        })
    }

    $('#all').on('click', function() {
        loadOrders("/all")
    })

    $('#pending').on('click', function() {
        loadOrders("/pending")
    })

    $('#finished').on('click', function() {
        loadOrders("/finished")
    })
})