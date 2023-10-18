package com.sbms.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.sbms.entities.City;
import com.sbms.entities.CityEntity;
import com.sbms.entities.Country;
import com.sbms.entities.Loginform;
import com.sbms.entities.State;
import com.sbms.entities.StateEntity;
import com.sbms.entities.UnlockAccount;
import com.sbms.entities.User;
import com.sbms.repos.CityRepo;
import com.sbms.repos.CountryRepo;
import com.sbms.repos.StateRepo;
import com.sbms.repos.UserRepo;
import com.sbms.utils.EmailUtils;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private EmailUtils emailUtils;

	private static char[] generatePassword(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		Random random = new Random();
		char[] password = new char[length];

		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password[3] = numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 4; i < length; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		return password;
	}

	@Override
	public String saveUser(User user) {
		user.setPassword(generatePassword(8).toString());
		User newUser = repo.save(user);
		if (repo.existsById(newUser.getId())) {
			return sendEmailToUnlockAccount(newUser);
		}
		return "Error";
	}

	public boolean checkLoginCredentials(Loginform loginform) {
		Optional<User> findByEmailAndPassword = repo.findByEmailAndPassword(loginform.getEmail(), loginform.getPassword());
		if(findByEmailAndPassword.isPresent()) {
			return true;
		}
		return false;
	}

	public Optional<User> checkPasswordCredentials(String password) {
		return repo.findByPassword(password);
	}

	public Optional<User> checkUserExistByEmail(String email) {
		return repo.findByEmail(email);
	}

	public Integer updatePassword(UnlockAccount unlockAccount) {
		return repo.updatePassword(unlockAccount.getUserId(), 0,unlockAccount.getConfirmPassword());
	}

	public List<Country> getcountryList() {
		return countryRepo.findAll();
	}

	public List<StateEntity> getStateList(Integer id) {
		return stateRepo.findByCountry(id);
	}

	public List<CityEntity> getCityList(Integer id) {
		return cityRepo.findByState(id);
	}

	public String sendEmailForforgotPassword(User user) {
		Context context = new Context();
		context.setVariable("firstName", user.getFirst_name());
		context.setVariable("lastName", user.getLast_name());
		context.setVariable("password", user.getPassword());
		try {
			emailUtils.sendEmailWithHtmlTemplate(user.getEmail(), "Reset Password", "email-template-forgot-password",context);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "Success";
	}

	public String sendEmailToUnlockAccount(User user) {
		Context context = new Context();
		context.setVariable("firstName", user.getFirst_name());
		context.setVariable("lastName", user.getLast_name());
		context.setVariable("password", user.getPassword());
		context.setVariable("userId", user.getId());
		try {
			emailUtils.sendEmailWithHtmlTemplate(user.getEmail(), "Unlock IES Account", "email-template-unlock",context);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "Success";
	}

}
