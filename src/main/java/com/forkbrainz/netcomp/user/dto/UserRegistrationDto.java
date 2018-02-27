package com.forkbrainz.netcomp.user.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.AssertTrue;

public class UserRegistrationDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String phone;

    @AssertTrue
    private Boolean terms;

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
