package com.diop.learning.web.rest.jwt;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTools {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.Lookup.class);

	private final String tokenHeader = "Authorization";
	private final String userIdHeader = "userId";
	private final String channelHeader = "channel";
	private final String correlationId = "CorrelationID";

	/**
	 * Extract userId header from httpRequest.
	 *
	 * @param httpRequest
	 *            TODO fill parameter description
	 *
	 * @return value of userId.
	 */
	public String extractUserIdFromHeaderRequest(HttpServletRequest httpRequest) {
		return httpRequest.getHeader(userIdHeader);
	}

	/**
	 * Extract channel communication header from httpRequest.
	 *
	 * @param httpRequest
	 *            TODO fill parameter description
	 *
	 * @return value of channel.
	 */
	public String extractChannelFromHeaderRequest(HttpServletRequest httpRequest) {
		return httpRequest.getHeader(channelHeader);
	}

	/**
	 * Extract channel communication header from httpRequest.
	 *
	 * @param httpRequest
	 *            TODO fill parameter description
	 * @return value of correlationId.
	 */
	public String extractCorrelationIdFromHeaderRequest(HttpServletRequest httpRequest) {
		/* Get channel from header */

		return httpRequest.getHeader(correlationId);
	}

	/**
	 * Extract token from httpRequest.
	 *
	 * @param httpRequest
	 *            TODO fill parameter description
	 * @return value of token.
	 */
	public String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String header = httpRequest.getHeader(tokenHeader);
		if (header == null || !header.startsWith("Bearer ")) {
			return "";
		}

		return header.substring(7);
	}

	/**
	 * Verify the token
	 *
	 * @param accessToken
	 *            TODO fill parameter description
	 *
	 * @return TODO fill return description
	 *
	 * @throws IOException
	 *             TODO fill exception description
	 *
	 * @throws ParseException
	 *             TODO fill exception description
	 */
	public static String verify(String accessToken) throws IOException, ParseException {
		try {

			PEMParser pemParser = new PEMParser(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("oidc-dev.mabanque.bnpparibas.pub.pem"), Charset.forName("UTF-8")));
			SubjectPublicKeyInfo spki = (SubjectPublicKeyInfo) pemParser.readObject();

			byte[] encodedPublicKey = spki.getEncoded();
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKeykey = keyFactory.generatePublic(publicKeySpec);

			Claims body = Jwts.parser().setSigningKey(publicKeykey).parseClaimsJws(accessToken).getBody();

			String customerNumber = body.getSubject();

			LOGGER.info(body.toString());

			return customerNumber;
		} catch (JwtException | IOException | IllegalArgumentException e) {
			LOGGER.info("Forbidden: Authorization token was either missing or invalid.", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.info("Forbidden: Authorization token was either missing or invalid.", e);
		} catch (InvalidKeySpecException e) {
			LOGGER.info("Forbidden: Authorization token was either missing or invalid.", e);
		}

		return null;
	}

}
