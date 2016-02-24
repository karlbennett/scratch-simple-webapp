describe('Show Username', function () {

    it('Can replace the signin with the username', function () {

        var elementReplacer = mock(ElementReplacer);
        var usernameFactory = mock(UsernameFactory);

        var username = mock(new function () {
        });

        // Given
        when(usernameFactory).create().thenReturn(username);

        // When
        new UsernameDisplay(elementReplacer, usernameFactory).show();

        // Then
        verify(elementReplacer).replaceByClassName('signin', username);
    });

    it('Can replace the signin with the username', function () {

        var document = mock(Document);

        var element = mock(Element);
        var className = 'some class name';

        var signin = mock(Element);
        var parentElement = mock(Element);

        // Given
        when(document).getElementsByClassName(className).thenReturn(signin);
        signin.parentElement = parentElement;

        // When
        new ElementReplacer(document).replaceByClassName(className, element);

        // Then
        verify(parentElement).replaceChild(signin, element);
    });

    it('Can create a username element', function () {

        var document = mock(Document);
        var usernameService = mock(UsernameService);

        var username = 'some username';
        var a = mock(Element);
        var text = mock(Node);

        var expected = mock(Element);

        // Given
        when(usernameService).getUsername().thenReturn(username);
        when(document).createElement('a').thenReturn(a);
        when(document).createTextNode(username).thenReturn(text);
        when(document).createElement('div').thenReturn(expected);

        // When
        var actual = new UsernameFactory(document, usernameService).create();

        // Then
        assertThat(actual, is(expected));
        verify(a).setAttribute('href', '/profile/' + username);
        verify(a).appendChild(text);
        verify(expected).setAttribute('class', 'username');
        verify(expected).appendChild(a);
    });

    it('Can get the username', function () {

        var client = mock(HttpClient);

        var pathClient = mock(HttpClient);
        var acceptClient = mock(HttpClient);

        var expected = 'some user name';

        // Given
        when(client).path('/username').thenReturn(pathClient);
        when(pathClient).accept('application/json').thenReturn(acceptClient);
        when(acceptClient).get().thenReturn(expected);

        // When
        var actual = new UsernameService(client).getUsername();

        // Then
        assertThat(actual, is(expected))
    });

    function Node() {
    }

    function Element() {
    }

    Element.prototype.getElementsByClassName = function () {
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

});