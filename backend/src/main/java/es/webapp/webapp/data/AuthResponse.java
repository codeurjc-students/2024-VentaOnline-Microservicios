package es.webapp.webapp.data;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponse(String accessToken){
        this.accessToken = accessToken;
    }

    public AuthResponse(Status status, String msg){
        
    }

}
