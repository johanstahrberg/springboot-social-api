package se.jensen.johan.springboot.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtKeyConfig {

    @Bean
    public KeyPair keyPair(
            @Value("${jwt.private-key}") String privateKey,
            @Value("${jwt.public-key}") String publicKey
    ) throws Exception {
        byte[] privateBytes = Base64.getDecoder().decode(privateKey);
        byte[] publicBytes = Base64.getDecoder().decode(publicKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));
        return new KeyPair(pubKey, privKey);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID("jwt-key-1")
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (selector, context) -> selector.select(jwkSet);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair keyPair) {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPair.getPublic()).build();
    }
}