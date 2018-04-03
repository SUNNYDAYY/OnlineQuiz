package com.b3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.b3.model.User;
import com.b3.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = Logger
			.getLogger(UserController.class);

	public UserController() {
		System.out.println("UserController()");
	}

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list")
	public ModelAndView listUser(ModelAndView model) throws IOException {
		List<User> listUser = userService.getAllUsers();
		model.addObject("listUser", listUser);
		model.setViewName("UserManage");
		return model;
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public ModelAndView newContact(ModelAndView model) {
		User user = new User();
		model.addObject("user", user);
		model.setViewName("UserForm");
		return model;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute User user) {
		if (userService.getUser(user.getId()) == null) { // if user id is 0 then creating the
			// user other updating the user
			userService.addUser(user);
			return new ModelAndView("redirect:/user/list");
		} else {
				//userService.updateUser(user);
			ModelAndView model = new ModelAndView();
			model.addObject("msg", "Account already exist!");
			model.setViewName("UserForm");
			return model;
			
		}
		//return new ModelAndView("redirect:/user/list");
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request) {
		String userId = request.getParameter("id");
		userService.deleteUser(userId);
		return new ModelAndView("redirect:/user/list");
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public ModelAndView editContact(HttpServletRequest request) {
		String userId = request.getParameter("id");
		User user = userService.getUser(userId);
		ModelAndView model = new ModelAndView("UserForm");
		model.addObject("user", user);

		return model;
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView login() {
		return new ModelAndView("redirect:/");
	}

}