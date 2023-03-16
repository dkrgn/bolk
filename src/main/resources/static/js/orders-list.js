$(function() {
    $('.show-orders').on('click', function() {
        $.get('/all', {}, function(response) {
            console.log(response)
;        })
    })
})