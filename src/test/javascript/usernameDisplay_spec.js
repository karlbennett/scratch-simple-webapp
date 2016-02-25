describe('Show Username', function () {

    it('Can replace the signin with the username', function () {

        var usernameElementFactory = mock(UsernameElementFactory);
        var usernameReplacerFactory = mock(UsernameReplacerFactory);
        var usernameService = mock(UsernameService);

        var usernameElement = mock(Element);
        var usernameReplacer = function () {
        };

        // Given
        when(usernameElementFactory).create().thenReturn(usernameElement);
        when(usernameReplacerFactory).create(usernameElement).thenReturn(usernameReplacer);

        // When
        new UsernameDisplay(usernameElementFactory, usernameReplacerFactory, usernameService).show();

        // Then
        verify(usernameService).getUsername(usernameReplacer);
    });

    it('Can create a username element', function () {

        var document = mock(Document);

        var anchor = mock(Element);
        var div = mock(Element);

        // Given
        when(document).createElement('a').thenReturn(anchor);
        when(document).createElement('div').thenReturn(div);

        // When
        var actual = new UsernameElementFactory(document).create();

        // Then
        assertThat(actual, is(div));
        verify(div).setAttribute('class', 'username');
        verify(div).appendChild(anchor);
    });

    it('Can create a username replacer', function () {

        var document = mock(Document);

        var usernameElement = mock(Element);
        var username = 'some username';

        var text = mock(Node);
        var anchor = mock(Element);
        var signin = mock(Element);
        var signinParent = mock(Element);

        // Given
        when(document).createTextNode(username).thenReturn(text);
        when(usernameElement).getElementsByTagName('a').thenReturn([anchor]);
        when(document).getElementsByClassName('signin').thenReturn([signin]);
        signin.parentElement = signinParent;

        // When
        new UsernameReplacerFactory(document).create(usernameElement)(username);

        // Then
        verify(anchor).setAttribute('href', '/profile/' + username);
        verify(anchor).appendChild(text);
        verify(signinParent).replaceChild(usernameElement, signin);
    });

    it('Can request the username', function () {

        var client = mock(HttpClient);
        var usernameHandlerFactory = mock(UsernameHandlerFactory);

        var usernameReplacer = mockFunction();

        var pathClient = mock(HttpClient);
        var usernameHandler = mockFunction();

        // Given
        when(usernameHandlerFactory).create(usernameReplacer).thenReturn(usernameHandler);
        when(client).path('/username').thenReturn(pathClient);

        // When
        new UsernameService(usernameHandlerFactory, client).getUsername(usernameReplacer);

        // Then
        verify(pathClient).get(usernameHandler);
    });

    it('Can create a username handler', function () {

        var usernameReplacer = mockFunction();
        var response = mock(Response);

        var username = 'some user name';
        var body = new function () {
        };

        // Given
        when(response).body().thenReturn(body);
        body.username = username;

        // When
        new UsernameHandlerFactory().create(usernameReplacer)(response);

        // Then
        verify(usernameReplacer)(username);
    });

    it('Can make a json request', function () {

        var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

        var url = 'http://so.me/where';
        var responseHandler = mockFunction();

        var request = mock(XMLHttpRequest);
        var text = '{"username" : "some username"}';

        // Given
        when(xmlHttpRequestFactory).create().thenReturn(request);
        request.readyState = 4;
        request.status = 200;
        request.responseText = text;

        // When
        new HttpClient(xmlHttpRequestFactory).path(url).get(responseHandler);
        request.onreadystatechange();

        // Then
        verify(responseHandler)(instanceOf(Response));
        verify(request).open('GET', url, true);
        verify(request).send();
    });

    it('Will not try to handle the json request too early', function () {

        var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

        var url = 'http://so.me/where';
        var responseHandler = mockFunction();

        var request = mock(XMLHttpRequest);
        var text = 'some text';

        // Given
        when(xmlHttpRequestFactory).create().thenReturn(request);
        request.readyState = 3;

        // When
        new HttpClient(xmlHttpRequestFactory).path(url).get(responseHandler);
        request.onreadystatechange();

        // Then
        verify(responseHandler, never())(anything());
        verify(request).open('GET', url, true);
        verify(request).send();
    });

    it('Cannot make a failed json request', function () {

        var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

        var url = 'http://so.me/where';
        var responseHandler = mockFunction();

        var request = mock(XMLHttpRequest);
        var text = 'some text';

        // Given
        when(xmlHttpRequestFactory).create().thenReturn(request);
        request.readyState = 4;
        request.status = 400;

        // When
        new HttpClient(xmlHttpRequestFactory).path(url).get(responseHandler);
        request.onreadystatechange();

        // Then
        verify(responseHandler, never())(anything());
        verify(request).open('GET', url, true);
        verify(request).send();
    });

    it('Can create an XMLHttpRequest', function () {

        // When
        var actual = new XMLHttpRequestFactory().create();

        // Then
        assertThat(actual, typeOf('object'));
    });

    it('Can create a response', function () {

        // Given
        var body = 'some body';

        // When
        var actual = new Response(body).body();

        // Then
        assertThat(actual, is(body));
    });

    function Node() {
    }

    function Element() {
    }

    Element.prototype.getElementsByClassName = function () {
    };

    Element.prototype.getElementsByTagName = function () {
    };

    Element.prototype.replaceChild = function () {
    };

    Element.prototype.setAttribute = function () {
    };

    Element.prototype.appendChild = function () {
    };

    function Document() {
    }

    Document.prototype = Element.prototype;

    Document.prototype.createElement = function () {
    };

    Document.prototype.createTextNode = function () {
    };

    function XMLHttpRequest() {
    }

    XMLHttpRequest.prototype.overrideMimeType = function () {
    };

    XMLHttpRequest.prototype.open = function () {
    };

    XMLHttpRequest.prototype.send = function () {
    };
});