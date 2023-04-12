package com.business.controller;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.business.entity.ProductionForm;

/**
 * Gestisce Form submit
 * 
 *
 */
@Controller
public class HomeController {
	
	private final String URL = "jdbc:mysql://localhost:3306/machinedb";
	
	@GetMapping("/")
	public ModelAndView home(Model model) {
		model.addAttribute("productionForm", new ProductionForm());
		return new ModelAndView("startup.html");
	}
	
	@PostMapping("/")
	public String processProduction(@Valid @ModelAttribute ProductionForm productionForm,
			BindingResult result, 
			Model model) throws Exception {
		
		if(result.hasErrors()) {
			return "startup.html";
		}
		
		
		if (!checkIfExistMachine(productionForm.getMachinename(),productionForm.getMachineid())) {
			result.rejectValue("machineid", "invalid.machine", "Machine does not exist");
			result.rejectValue("machinename", "invalid.machine", "Machine does not exist");
			return "startup.html";
		}
		
		insertProduction(productionForm.getMachineid(),
				productionForm.getMachinename(),
				productionForm.getLocation(),
				productionForm.getMake());
		
		return "startup.html";
	
	}	
	

	
	private int getHour() {
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date
		return calendar.get(calendar.get(Calendar.HOUR));
	}
	
	/**
	 * Controlla che nel DB esista una machine con quell'id e name
	 * @param machinename
	 * @param machineid
	 * @throws Exception
	 */
	private boolean checkIfExistMachine(String machinename, String machineid) throws Exception {
		try ( java.sql.Connection c =  DriverManager.getConnection(URL, "root", "password");
				 Statement s = c.createStatement();
				){
		    	PreparedStatement ps = c.prepareStatement("Select * from machine where id=? and name=?");
				ps.setString(1, machineid);
				ps.setString(2, machinename);			
			
				ResultSet rs = ps.executeQuery();
				if(!rs.next()) {
					System.out.println("Non esiste machine corrispondente nel DB");
					return false;
				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return true;
		
	}
	
	/**
	 * Inserisce nel DB la production ricevuta in input dall'form
	 * @param machineid
	 * @param machinename
	 * @param location
	 * @param make
	 */
	private void insertProduction(String machineid, String machinename, String location, Integer make) {
		try ( java.sql.Connection c =  DriverManager.getConnection(URL, "root", "password");
				 Statement s = c.createStatement();
				){
		    	PreparedStatement ps = c.prepareStatement("INSERT INTO production (location, hoursofday, production_number, machine) values (?,?,?,?)");
				ps.setString(1, location);
				ps.setInt(2, getHour());			
				ps.setInt(3, make);
				ps.setString(4, machineid);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}

}

