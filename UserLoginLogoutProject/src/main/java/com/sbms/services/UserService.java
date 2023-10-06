package com.sbms.services;

import java.util.List;
import java.util.Optional;

import com.sbms.entities.City;
import com.sbms.entities.Country;
import com.sbms.entities.Loginform;
import com.sbms.entities.State;
import com.sbms.entities.UnlockAccount;
import com.sbms.entities.User;

public interface UserService {
	
	public String saveUser(User user);
	public List<Country> getcountryList();
	public List<State> getStateList(Integer id);
	public List<City> getCityList(Integer id);
	
	public boolean checkLoginCredentials(Loginform loginform);
	public Optional<User> checkPasswordCredentials(String password);
	public Integer updatePassword(UnlockAccount unlockAccount);
	public Optional<User> checkUserExistByEmail(String email);
	public String sendEmailForforgotPassword(User user);
	public String sendEmailToUnlockAccount(User user);

}
