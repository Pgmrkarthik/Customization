package com.dev.customization.payload;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class JWTResponse {

    private String message;
    private String jwtToken;
}


