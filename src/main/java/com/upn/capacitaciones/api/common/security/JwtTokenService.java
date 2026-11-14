package com.upn.capacitaciones.api.common.security;

import com.upn.capacitaciones.api.perfil.entity.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;

    public String generar(Perfil perfil) {
        Instant ahora = Instant.now();
        Instant expira = ahora.plus(properties.getExpirationMinutes(), ChronoUnit.MINUTES);

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(properties.getIssuer())
                .audience(List.of(properties.getAudience()))
                .issuedAt(ahora)
                .expiresAt(expira)
                .subject(perfil.getPerfilId().toString())
                .claim("unique_name", perfil.getNombreCompleto())
                .claim("email", perfil.getEmail())
                .claim("role", perfil.getRol())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}