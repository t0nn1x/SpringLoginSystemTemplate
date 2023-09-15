package com.loginsystem.authenticated_backend.dto;

import com.loginsystem.authenticated_backend.model.ApplicationUser;

public class LoginResponseDTO {
    private ApplicationUser user;
    private String jwt;


    public LoginResponseDTO(ApplicationUser user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    /**
     * @return ApplicationUser return the user
     */
    public ApplicationUser getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    /**
     * @return String return the jwt
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * @param jwt the jwt to set
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
