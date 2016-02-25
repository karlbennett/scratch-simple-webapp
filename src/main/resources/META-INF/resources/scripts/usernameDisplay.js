function UsernameDisplay(usernameElementFactory, usernameReplacerFactory, usernameService) {
    this.usernameElementFactory = usernameElementFactory;
    this.usernameReplacerFactory = usernameReplacerFactory;
    this.usernameService = usernameService;
}

UsernameDisplay.prototype.show = function () {
    this.usernameService.getUsername(this.usernameReplacerFactory.create(this.usernameElementFactory.create()));
};

function UsernameElementFactory(document) {
    this.document = document;
}

UsernameElementFactory.prototype.create = function () {
    var anchor = this.document.createElement('a');

    var div = this.document.createElement('div');
    div.setAttribute('class', 'username');
    div.appendChild(anchor);

    return div;
};

function UsernameReplacerFactory(document) {
    this.document = document;
}

UsernameReplacerFactory.prototype.create = function (usernameElement) {
    var document = this.document;
    return function (username) {
        var anchor = usernameElement.getElementsByTagName('a')[0];
        anchor.setAttribute('href', '/profile/' + username);
        anchor.appendChild(document.createTextNode(username));

        var signin = document.getElementsByClassName('signin')[0];
        signin.parentElement.replaceChild(usernameElement, signin);
    };
};

function UsernameService(usernameHandlerFactory, client) {
    this.usernameHandlerFactory = usernameHandlerFactory;
    this.client = client;
}

UsernameService.prototype.getUsername = function (usernameReplacer) {
    this.client.path('/username').get(this.usernameHandlerFactory.create(usernameReplacer));
};

function UsernameHandlerFactory() {
}

UsernameHandlerFactory.prototype.create = function (usernameReplacer) {
    return function (response) {
        usernameReplacer(response.body().username)
    };
};

function HttpClient(xmlHttpRequestFactory) {
    this.xmlHttpRequestFactory = xmlHttpRequestFactory;
}

HttpClient.prototype.path = function (path) {
    this._path = path;
    return this;
};

HttpClient.prototype.get = function (responseHandler) {
    var request = this.xmlHttpRequestFactory.create();
    request.onreadystatechange = function () {
        if (request.readyState !== 4) {
            return;
        }

        if (request.status === 200) {
            responseHandler(new Response(JSON.parse(request.responseText)));
        }
    };
    request.open('GET', this._path, true);
    request.send();
};

function Response(body) {
    this._body = body;
}

Response.prototype.body = function () {
    return this._body;
};

function XMLHttpRequestFactory() {
}

XMLHttpRequestFactory.prototype.create = function () {
    return new XMLHttpRequest();
};