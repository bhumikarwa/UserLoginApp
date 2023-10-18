package com.sbms.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "User's first name cannot be empty.")
	private String first_name;

	@NotEmpty(message = "User's last name cannot be empty.")
	private String last_name;

	@Email(message = "Enter valid email")
	@NotEmpty(message = "Email should not ne empty")
	private String email;

	private String gender;

	@NotNull
	@Size(max = 10, min = 10, message = "Mobile number should be of 10 digits")
	@Pattern(regexp = "[7-9][0-9]{9}", message = "Mobile number is invalid!!")
	private String phno;

	@NotNull(message = "Please enter birth date")
	@Past(message = "Birth date should be less than current date!!")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dob;

	private String country;
	private String state;
	private String city;
	private String password;
	
	
	private String active_sw = "y";
	private Integer is_acc_locked = 1;

	@CreationTimestamp
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "created_date", updatable = false)
	private LocalDate createdDate;

	@UpdateTimestamp
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "updated_date", insertable = false)
	private LocalDate updatedDate;

}
