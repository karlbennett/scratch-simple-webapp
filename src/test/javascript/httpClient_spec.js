describe('Make HTTP requests', function () {

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
});