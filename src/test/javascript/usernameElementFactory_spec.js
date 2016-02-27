describe('Create the username element', function () {

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
});