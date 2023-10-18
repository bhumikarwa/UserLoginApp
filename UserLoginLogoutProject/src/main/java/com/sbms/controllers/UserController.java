package com.sbms.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.sbms.entities.Loginform;
import com.sbms.entities.UnlockAccount;
import com.sbms.entities.User;
import com.sbms.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/register")
	public String loadUserForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("countrylist", service.getcountryList());
		return "user-register";
	}

	@PostMapping("/registerUser")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "user-register";
		}

		String msg = service.saveUser(user);
		if (msg.equals("Error")) {
			redirectAttributes.addFlashAttribute("message", "Error in registration");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/register";
		}
		redirectAttributes.addFlashAttribute("message",
				"Successfully registered. Please check email '" + user.getEmail() + "' to unlock your account!");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String loadLoginForm(Model model) {
		model.addAttribute("login", new Loginform());
		return "user-login";
	}

	@PostMapping("/verifyLogin")
	public String saveUserLogin(@ModelAttribute("login") Loginform login, Model model,
			RedirectAttributes redirectAttributes) {
		Optional<User> userExist = service.checkUserExistByEmail(login.getEmail());
		if (userExist.isPresent()) {
			User user1 = userExist.get();
			if (user1.getIs_acc_locked() == 0) {
				if (service.checkLoginCredentials(login)) {
					redirectAttributes.addFlashAttribute("message",
							user1.getFirst_name() + " " + user1.getLast_name() + ", login successfully!");
					redirectAttributes.addFlashAttribute("user", user1);
					redirectAttributes.addFlashAttribute("alertClass", "alert-success");
					return "welcome";
				} else {
					redirectAttributes.addFlashAttribute("message", "Incorrect email or password");
					redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
					return "redirect:/login";
				}
			} else {
				redirectAttributes.addFlashAttribute("message",
						user1.getFirst_name() + " " + user1.getLast_name() + ", your account is locked.");
				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
				return "redirect:/login";
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "Email not exist");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/login";
		}
	}

	@GetMapping("/unlock/{userId}")
	public String loadUnlockAccForm(@PathVariable("userId") Integer userId, Model model) {
		UnlockAccount unlockAccount = new UnlockAccount();
		unlockAccount.setUserId(userId);
		model.addAttribute("unlockAccount", unlockAccount);
		return "unlock-account";
	}

	@PostMapping("/unlockAccount")
	public String updateUserPassword(@ModelAttribute("unlockAccount") UnlockAccount unlockAccount, Model model,
			RedirectAttributes redirectAttributes) {
		Optional<User> userAcc = service.checkPasswordCredentials(unlockAccount.getOldPassword());
		if (userAcc.isPresent()) {
			User user = userAcc.get();
			if (unlockAccount.getNewPassword().equals(unlockAccount.getConfirmPassword())) {
				Integer updatePassword = service.updatePassword(unlockAccount);
				if (updatePassword > 0) {
					redirectAttributes.addFlashAttribute("message", user.getFirst_name() + " " + user.getLast_name()
							+ ", your account is unlocked. Please proceed with login.");
					redirectAttributes.addFlashAttribute("alertClass", "alert-success");
					return "redirect:/login";
				} else {
					redirectAttributes.addFlashAttribute("message", "Error in Unlocking account");
					redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
					return "redirect:/unlock";
				}
			}
		}
		return null;
	}

	@GetMapping("/forgotpassword")
	public String loadForgotPasswordForm(Model model) {
		model.addAttribute("user", new User());
		return "forgot-password";
	}

	@PostMapping("/resetPassword")
	public String sendemail(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) {
		Optional<User> userExist = service.checkUserExistByEmail(email);
		if (userExist.isPresent()) {
			User user = userExist.get();
			if (user.getIs_acc_locked() == 0) {
				String msg = service.sendEmailForforgotPassword(user);
				if (msg.equals("Success")) {
					redirectAttributes.addFlashAttribute("message",
							"Password sent to the email : '" + user.getEmail() + "'");
					redirectAttributes.addFlashAttribute("alertClass", "alert-success");
					return "redirect:/login";
				} else {
					redirectAttributes.addFlashAttribute("message", "Error in sending email");
					redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
					return "redirect:/forgotpassword";
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "Please unlock your account");
				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
				return "redirect:/forgotpassword";
			}

		} else {
			redirectAttributes.addFlashAttribute("message", "Email not exist");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/forgotpassword";
		}
	}

	@GetMapping("/validateEmail")
	@ResponseBody
	public String validateEmail(@RequestParam("email") String email) {
		Optional<User> checkUserExistByEmail = service.checkUserExistByEmail(email);
		if (checkUserExistByEmail.isPresent()) {
			return "Duplicate";
		} else {
			return "Unique";
		}
	}

	
	@ResponseBody
	@GetMapping("loadStatesByCountry/{id}")
	public String loadStatesByCountry(@PathVariable("id") int id) {
		Gson gson = new Gson();
		return gson.toJson(service.getStateList(id));
	}
	
	@ResponseBody
	@GetMapping("loadCitiesByState/{id}")
	public String loadCitiesByState(@PathVariable("id") int id) {
		Gson gson = new Gson();
		return gson.toJson(service.getCityList(id));
	}
	

}
