describe('Handle the username HTTP response', function () {

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
});