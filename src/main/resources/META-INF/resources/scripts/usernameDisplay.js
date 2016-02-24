function UsernameDisplay(elementReplacer, usernameFactory) {
    this.elementReplacer = elementReplacer;
    this.usernameFactory = usernameFactory;
}

UsernameDisplay.prototype.show = function () {
    this.elementReplacer.replaceByClassName("signin", this.usernameFactory.create());
};

function ElementReplacer(document) {
    this.document = document;
}

ElementReplacer.prototype.replaceByClassName = function (className, element) {
    var signin = this.document.getElementsByClassName(className);
    signin.parentElement.replaceChild(signin, element);
};

function UsernameFactory(document, usernameService) {
    this.document = document;
    this.usernameService = usernameService;
}

UsernameFactory.prototype.create = function () {
    var username = this.usernameService.getUsername();

    var anchor = this.document.createElement('a');
    var anchorText = this.document.createTextNode(username);
    anchor.setAttribute('href', '/profile/' + username);
    anchor.appendChild(anchorText);

    var div = this.document.createElement('div');
    div.setAttribute('class', 'username');
    div.appendChild(anchor);

    return div;
};

function UsernameService(client) {
    this.client = client;
}

UsernameService.prototype.getUsername = function () {
    return this.client.path('/username').accept('application/json').get();
};

function HttpClient() {

}

HttpClient.prototype.path = function () {
};

HttpClient.prototype.accept = function () {
};

HttpClient.prototype.get = function () {
};