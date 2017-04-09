package scratch.simple.webapp.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.sql.Date;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLongBetween;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class JJwtEncoderTest {

    @Test
    public void Can_encode_a_username_into_a_jwt_token() {

        final JwtBuilderFactory jwtBuilderFactory = mock(JwtBuilderFactory.class);
        final String username = someString();

        final JwtBuilder jwtBuilder = mock(JwtBuilder.class);
        final JwtBuilder claimJwtBuilder = mock(JwtBuilder.class);
        final PrivateKey privateKey = mock(PrivateKey.class);
        final KeyPair keyPair = new KeyPair(null, privateKey);
        final SignatureAlgorithm algorithm = someEnum(SignatureAlgorithm.class);
        final JwtBuilder signedJwtBuilder = mock(JwtBuilder.class);
        final JwtBuilder expiredJwtBuilder = mock(JwtBuilder.class);
        final String expected = someString();

        // Given
        given(jwtBuilderFactory.create()).willReturn(jwtBuilder);
        given(jwtBuilder.claim("username", username)).willReturn(claimJwtBuilder);
        given(privateKey.getAlgorithm()).willReturn(algorithm.getJcaName());
        given(claimJwtBuilder.signWith(algorithm, privateKey)).willReturn(signedJwtBuilder);
        given(signedJwtBuilder.setExpiration(any(Date.class))).willReturn(expiredJwtBuilder);
        given(expiredJwtBuilder.compact()).willReturn(expected);

        // When
        final String actual = new JJwtEncoder(
            jwtBuilderFactory,
            keyPair,
            someLongBetween(1L, 100L),
            someThing(SECONDS, MINUTES, HOURS, DAYS)
        ).encodeUsername(username);

        // Then
        assertThat(actual, is(expected));
    }
}