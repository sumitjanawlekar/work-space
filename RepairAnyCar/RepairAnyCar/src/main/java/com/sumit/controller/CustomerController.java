package com.sumit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.DefaultEditorKit.CutAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sumit.dao.CustomerDAO;
import com.sumit.pojo.Customer;
import com.sumit.pojo.CustomerOriginal;
import com.sumit.validation.CustomerValidation;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	

//	private CustomerValidation customerValidation;
	
//	public CustomerController() {
//		
//	}
//	
//	@Autowired
//	public CustomerController(CustomerValidation customerValidation) {
//		this.customerValidation = customerValidation;
//	}
//	
	
	

	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String viewForm(HttpServletRequest request, Model model, Customer customer, CustomerValidation customerValidation) {
		
		model.addAttribute("customer", new Customer());
		return "register";
	}
	
	@RequestMapping(value= "/register", method = RequestMethod.POST)
	public String saveForm(@ModelAttribute("customer") Customer customer, CustomerDAO customerDao,BindingResult br, HttpSession session,CustomerValidation customerValidation) {
		
		
		customerValidation.validate(customer,br);
		
		if(br.hasErrors()) {
			return "register";
		}
		else {
			customerDao.saveCustomer(customer);
			session.setAttribute("customer", customer);
			return "success";
		}
		
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String viewLogin(HttpServletRequest request, Model model, Customer customer) {
		
		model.addAttribute("customer", customer);
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("customer")Customer customer,Model model, CustomerDAO customerDao, HttpSession session) {
		
		if(customer.getEmailAddress()!=null && customer.getPassword()!= null && session.getAttribute("customer") ==null) {
			customer = customerDao.customerLogin(customer);
			if(customer!=null) {
				session.setAttribute("customer", customer);
				session.setAttribute("customerId", customer.getCustomerId());
				
				return new ModelAndView("success","customer",customer);
			}
			else {
				model.addAttribute("failed", "Login Failed");
				return new ModelAndView("login","customer",new Customer());
			}
		}
		else {
			return new ModelAndView("login","customer",new Customer());
		}
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		
		session.removeAttribute("customer");
		return "redirect: login";
		
	}
	
	//Used to redirect to success.jsp
	@RequestMapping(value="/success", method = RequestMethod.GET)
	public String successView(Model model) {
		
		model.addAttribute("success", new Customer());
		return "success";
	}
}