var content = new Vue({
    el: '#main-content',
    data: {
        message: 'asdfasdf',
        template:
            "<div class='container-fluid navbar-dark'>" +
                "<div class='nav-item'>" +
                  "{{ message }}" +
                "</div>" +
            "</div>"
    }
});