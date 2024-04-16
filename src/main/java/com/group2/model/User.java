package com.group2.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	private String username;
    private String email;
    private String password;
    private String fullName;
    private Long phone;
    private String address;
    private List<UserRole> roles;
    
    public User() {
		super();
	}

	public User(String username, String email, String password, String fullName, Long phone, String address,
			List<UserRole> roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.phone = phone;
		this.address = address;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

}
