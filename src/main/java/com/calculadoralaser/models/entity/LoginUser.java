package com.calculadoralaser.models.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginUser {
	    
	    @NotEmpty(message="Email is required!")
	    @Email(message="Please enter a valid email!")
	    private String e_mail;
	    
	    @NotEmpty(message="Password is required!")
	    @Size(min=8, max=128, message="Password must be between 8 and 128 characters")
	    private String pass;
	    
	    public LoginUser() {}

		public String getE_mail() {
			return e_mail;
		}

		public void setE_mail(String e_mail) {
			this.e_mail = e_mail;
		}

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

		
	    
	    
	    
}