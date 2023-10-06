package com.sbms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UnlockAccount {
	private Integer userId;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

}
