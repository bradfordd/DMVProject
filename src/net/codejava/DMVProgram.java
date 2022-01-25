package net.codejava;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.*;
import java.time.*;
import java.time.DayOfWeek;

public class DMVProgram {
	
	static String jdbcURL = "jdbc:postgresql://localhost:5432/DMV";
	static String username = "postgres";
	static String password = "password";
	static Scanner scanner = new Scanner(System.in);
	static String loggedUsername = "";
	
	static LocalDate addTenYearsToDate(LocalDate currDate) {
		return currDate.plusYears(10);
	}
	
	static void technicianView(String currentUser)
	{
		System.out.println("Technician View");
		System.out.println("[1] Issue licenses");
		System.out.println("[2] Register Vehicles");
		System.out.println("[3] Add New Technicians to the database");
		System.out.println("[4] Back to Login");
		String buffer = scanner.nextLine();
		if (buffer.isEmpty()) {
			System.out.println("Please make a selection.");
			technicianView(currentUser);
			return;
		}
		int bufferInt = -1;
		try {
			bufferInt = Integer.parseInt(buffer);
		}
		catch (NumberFormatException ex){
	          System.out.println("Please Enter an Integer.");
	          technicianView(currentUser);
	          return;
	    }
		if (bufferInt > 4 || bufferInt < 1) {
			System.out.println("Please Enter an Integer within range.");
			technicianView(currentUser);
			return;
		}
		if(bufferInt== 1)
		{
			issueLicense(currentUser);
		}
		if (bufferInt == 2) 
		{
			registerVehicles( currentUser);
		}
		if (bufferInt == 3)
		{
			addTechnician(currentUser);
		}
		if (bufferInt == 4)
		{
			loggedUsername = "";
			psuedomain();
			return;
		}
		
	}
	
	static void addInstructor()
	{
		System.out.println("Please enter the username you would like the instructor to have for the account: ");
		String newUsername = "";
		newUsername = scanner.nextLine();
		if (!checkUsernameAndPasswordValidity(newUsername)) {
			addInstructor();
			return;
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT username FROM \"users\"";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String indexedUsername = rs.getString(1);
		        if (indexedUsername.equals(newUsername)) {
		        	System.out.println("A User with this username already exists");
		        	addInstructor();
		        	return;
		        }
		    }
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		System.out.println("Please enter your gender(M/F/N/P (Prefer not to answer)): ");
		char gender = '\0';
		String genderString = scanner.nextLine();
		gender = genderString.charAt(0);
		if (gender != '\0') {
			if (gender == '\"' || gender == ';' || gender == ':' || gender == '\'') {
				System.out.println("Gender cannot be a special character (i.e. :, ', or ;.");
				addInstructor();
				return;
			}
		}
		else {
			System.out.println("Please Enter a gender.");
			addInstructor();
			return;
		}
		System.out.println("Please enter the firstName: ");
		String firstName = "";
		firstName = scanner.nextLine();
		if (firstName.isEmpty()) {
			System.out.println("please enter the first name");
			addInstructor();
			return;
		}
		else {
			if (firstName.contains(";") || firstName.contains("\"") || firstName.contains(":") || firstName.contains("\'")) {
				System.out.println("first name cannot contain special characters ';', ', '\"', or ':' ");
				addInstructor();
				return;
			}
		}
		System.out.println("Please enter the last name: ");
		String lastName = "";
		lastName = scanner.nextLine();
		if (lastName.isEmpty()) {
			System.out.println("please enter the last name");
			addInstructor();
			return;
		}
		else {
			if (lastName.contains(";") || lastName.contains("\"") || lastName.contains(":") || lastName.contains("\'")) {
				System.out.println("last name cannot contain special characters ';', ', '\"', or ':' ");
				addInstructor();
				return;
			}
		}
		System.out.println("Please initialize the accounts password: ");
		String accountPassword = "";
		accountPassword = scanner.nextLine();
		if (!checkUsernameAndPasswordValidity(accountPassword)) {
			addInstructor();
			return;
		}
		System.out.println("Please enter the address: ");
		String address = "";
		address = scanner.nextLine();
		if (address.isEmpty()) {
			System.out.println("please enter the address");
			addInstructor();
			return;
		}
		else {
			if (address.contains(";") || address.contains("\"") || address.contains(":") || address.contains("\'")) {
				System.out.println("address cannot contain special characters ';', ', '\"', or ':' ");
				addInstructor();
				return;
			}
		}
		System.out.println("Please enter the year the instructor was born: ");
		String buffer = scanner.nextLine();
		int bufferInt = -1;
		try {
			bufferInt = Integer.parseInt(buffer);
		}
		catch (NumberFormatException ex){
          System.out.println("Please Enter an Integer.");
          addInstructor();
          return;
        }
		if (bufferInt > 3000 || bufferInt < 1000) {
			System.out.println("Please Enter a year the instructor was born:.");
			addInstructor();
		}
		int year = bufferInt;

		System.out.println("Please enter the month of the year the instructor was born: as a digit (i.e. 1 as January and 12 as December: ");
		buffer = scanner.nextLine();
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       return;
	    }
		int month = bufferInt;
		if (month > 12 || month < 1) {
			System.out.println("Please enter a valid month.");
		}
		System.out.println("Please enter the day of the year the instructor was born:: ");
		buffer = scanner.nextLine();
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       return;
	    }
		int day = bufferInt;
		if (day > 31 || day < 1) {
			System.out.println("Please enter a valid day of the month.");
		}
		String date = formatDate(year, month, day);
		
		System.out.println("Please enter your the Instructors Credentials: ");
		String credentials = "";
		credentials = scanner.nextLine();
		if (credentials.isEmpty()) {
			System.out.println("please enter credentials:");
			addInstructor();
			return;
		}
		else {
			if (credentials.contains(";") || credentials.contains("\"") || credentials.contains(":") || credentials.contains("\'")) {
				System.out.println("Credentials cannot contain special characters ';', ', '\"', or ':' ");
				addInstructor();
				return;
			}
		}
		
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO \"users\" (\"gender\", \"firstname\", \"lastname\", \"username\", \"password\", \"address\", \"dob\") "
					+ "VALUES ('" + gender + "', '" + firstName + "', '" + lastName + "', '" +  newUsername + "', '" + accountPassword + "', '" 
					+ address + "', '" + date + "');";      
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(query);
			if (rows > 0) {
				System.out.println("Account Successfully created!");
			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO \"instructor\" (\"gender\", \"firstname\", \"lastname\", \"username\", \"password\", \"address\", \"dob\", \"credentials\") "
					+ "VALUES ('" + gender + "', '" + firstName + "', '" + lastName + "', '" +  newUsername + "', '" + accountPassword + "', '" 
					+ address + "', '" + date + "', '" + credentials + "');";      
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(query);
			if (rows > 0) {
				System.out.println("Account Successfully created!");
			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		
		//instructView();
	}
	
	
	static void addTechnician(String currentUser)
	{
		System.out.println("Please enter the username you would like the technician to have for the account: ");
		String newUsername = "";
		newUsername = scanner.nextLine();
		if (!checkUsernameAndPasswordValidity(newUsername)) {
			addTechnician(currentUser);
			return;
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT username FROM \"users\"";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String indexedUsername = rs.getString(1);
		        if (indexedUsername.equals(newUsername)) {
		        	System.out.println("A User with this username already exists");
		        	addTechnician(currentUser);
		        	return;
		        }
		    }
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		System.out.println("Please enter the gender(M/F/N/P (Prefer not to answer)) of the technician: ");
		char gender = '\0';
		String genderString = scanner.nextLine();
		gender = genderString.charAt(0);
		if (gender != '\0') {
			if (gender == '\"' || gender == ';' || gender == ':' || gender == '\'') {
				System.out.println("Gender cannot be a special character (i.e. :, ', or ;.");
				addTechnician(currentUser);
				return;
			}
		}
		else {
			System.out.println("Please Enter a gender.");
			addTechnician(currentUser);
			return;
		}
		System.out.println("Please enter their firstName: ");
		String firstName = "";
		firstName = scanner.nextLine();
		if (firstName.isEmpty()) {
			System.out.println("please enter their first name");
			addTechnician(currentUser);
			return;
		}
		else {
			if (firstName.contains(";") || firstName.contains("\"") || firstName.contains(":") || firstName.contains("\'")) {
				System.out.println("first name cannot contain special characters ';', ', '\"', or ':' ");
				addTechnician(currentUser);
				return;
			}
		}
		System.out.println("Please enter their lastName: ");
		String lastName = "";
		lastName = scanner.nextLine();
		if (lastName.isEmpty()) {
			System.out.println("please enter their last name");
			addTechnician(currentUser);
			return;
		}
		else {
			if (lastName.contains(";") || lastName.contains("\"") || lastName.contains(":") || lastName.contains("\'")) {
				System.out.println("last name cannot contain special characters ';', ', '\"', or ':' ");
				addTechnician(currentUser);
				return;
			}
		}
		System.out.println("Please initialize the account's password: ");
		String accountPassword = "";
		accountPassword = scanner.nextLine();
		if (!checkUsernameAndPasswordValidity(accountPassword)) {
			addTechnician(currentUser);
			return;
		}
		System.out.println("Please enter their address: ");
		String address = "";
		address = scanner.nextLine();
		if (address.isEmpty()) {
			System.out.println("please enter their address");
			addTechnician(currentUser);
			return;
		}
		else {
			if (address.contains(";") || address.contains("\"") || address.contains(":") || address.contains("\'")) {
				System.out.println("last name cannot contain special characters ';', ', '\"', or ':' ");
				addTechnician(currentUser);
				return;
			}
		}
		System.out.println("Please enter the year they were born: ");
		String buffer = scanner.nextLine();
		int bufferInt = -1;
		try {
			bufferInt = Integer.parseInt(buffer);
		}
		catch (NumberFormatException ex){
          System.out.println("Please Enter an Integer.");
          addTechnician(currentUser);
          return;
        }
		if (bufferInt > 3000 || bufferInt < 1000) {
			System.out.println("Please Enter a year that they were born.");
			addTechnician(currentUser);
		}
		int year = bufferInt;

		System.out.println("Please enter the month of the year they were born as a digit (i.e. 1 as January and 12 as December: ");
		buffer = scanner.nextLine();
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       return;
	    }
		int month = bufferInt;
		if (month > 12 || month < 1) {
			System.out.println("Please enter a valid month.");
			addTechnician(currentUser);
		}
		System.out.println("Please enter the day of the year they were born: ");
		buffer = scanner.nextLine();
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       return;
	    }
		int day = bufferInt;
		if (day > 31 || day < 1) {
			System.out.println("Please enter a valid day of the month.");
			addTechnician(currentUser);
		}
		String date = formatDate(year, month, day);
		if (date == "01-01-1000") {
			System.out.println("Please enter a date that actually happens.");
			addTechnician(currentUser);
		}
		System.out.println("Please enter your the technician's shift type: ");
		String shift = "";
		shift = scanner.nextLine();
		if (shift.isEmpty()) {
			System.out.println("please enter the shift-type");
			addTechnician(currentUser);
			return;
		}
		else {
			if (shift.contains(";") || shift.contains("\"") || shift.contains(":") || shift.contains("\'")) {
				System.out.println("shift cannot contain special characters ';', ', '\"', or ':' ");
				addTechnician(currentUser);
				return;
			}
		}
		
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO \"users\" (\"gender\", \"firstname\", \"lastname\", \"username\", \"password\", \"address\", \"dob\") "
					+ "VALUES ('" + gender + "', '" + firstName + "', '" + lastName + "', '" +  newUsername + "', '" + accountPassword + "', '" 
					+ address + "', '" + date + "');";      
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(query);
			if (rows > 0) {
				System.out.println("Account Successfully created!");
			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO \"technician\" (\"gender\", \"firstname\", \"lastname\", \"username\", \"password\", \"address\", \"dob\", \"shifttype\") "
					+ "VALUES ('" + gender + "', '" + firstName + "', '" + lastName + "', '" +  newUsername + "', '" + accountPassword + "', '" 
					+ address + "', '" + date + "', '" + shift + "');";      
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(query);
//			if (rows > 0) {
//				System.out.println("Account Successfully created!");
//			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		
		technicianView(currentUser);
	}
	static void registerVehicles(String currentUser)
	{
		int numChoices = 1;
		Vector<String> selections = new Vector(0);
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM assignvehicles;";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String motorist = rs.getString(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				System.out.println("[" + numChoices + "]" +  motorist + " " + firstName + " " + lastName);
				numChoices++;
				String entry = motorist;
				selections.add(entry);
				
			}
			connection.close();
		}
		catch(SQLException e)
		{
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		
		System.out.println("Which motorist would you like to register a vehicle to:");
		String buffer = scanner.nextLine();
		int bufferInt = 0;
		try {
			bufferInt = Integer.parseInt(buffer);
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Please Enter an Integer.");
			issueLicense(username);
			return;
		}
		if(!(bufferInt < 1 || bufferInt > numChoices))
		{
			System.out.println("Motorist Selected.");
		}
		
		String vid = "";
		String user = selections.get(bufferInt - 1);
		String manu = "";
		int oduo = -1;
		String model = "";
		int year = -1;
		
		
		char choice = '\0';
		boolean completed = false;
		do {
			System.out.println("Would you like to specify a VID for this vehicle? (Y/N)");
			buffer = scanner.nextLine();
			if (buffer.isEmpty()) {
				System.out.println("Please make a selection");
				continue;
			}
			choice = buffer.charAt(0);
			Character.toUpperCase(choice);
			if (choice != 'Y' && choice != 'N') {
				System.out.println("Please make a selection");
				continue;
			}
			completed = true;
		} while (!completed);
		completed = false;
		
		if (choice == 'N') {
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				String query = "SELECT uuid_generate_v4()::text;";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next())
				{
					vid = rs.getString(1);
				}
				
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error in connecting to PostgreSQL server");
				e.printStackTrace();
			}
			
		} 
		if (choice == 'Y') {
			do {
				System.out.println("Please specify the VID you would like to have for this vehicle.");
				buffer = scanner.nextLine();
				if (buffer.isEmpty()) {
					System.out.println("Please enter the VID");
					continue;
				}
				if (checkForSQLInjection(buffer)) {
					boolean repeat = false;
					try {
						Connection connection = DriverManager.getConnection(jdbcURL, username, password);
						String query = "SELECT * FROM vehicle WHERE VID = '" + buffer + "';";
						Statement stmt = connection.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						if (rs.next())
						{
							System.out.println("VID already exists in database.");
							repeat = true;
						}
						
						connection.close();
					}
					catch(SQLException e)
					{
						System.out.println("Error in connecting to PostgreSQL server");
						e.printStackTrace();
					}
					if (repeat) {
						continue;
					}
				}
				else {
					continue;
				}
				vid = buffer;
				completed = true;
			} while (!completed);
			completed = false;
		}
		do {
			System.out.println("please enter the manufacturer");
			buffer = scanner.nextLine();
			if (buffer.isEmpty()) {
				continue;
			}
			if (!checkForSQLInjection(buffer)) { 
				continue;
			} 
			manu = buffer;
			completed = true;
		} while (!completed); 
		completed = false;
		do {
			System.out.println("please enter the model");
			buffer = scanner.nextLine();
			if (buffer.isEmpty()) {
				continue;
			}
			if (!checkForSQLInjection(buffer)) { 
				continue;
			} 
			model = buffer;
			completed = true;
		} while (!completed); 
		completed = false;
		do {
			System.out.println("please enter the year");
			buffer = scanner.nextLine();
			bufferInt = 0;
			if (buffer.isEmpty()) {
				continue;
			}
			if (!checkForSQLInjection(buffer)) { 
				continue;
			} 
			try {
				bufferInt = Integer.parseInt(buffer);
			}
			catch(NumberFormatException ex)
			{
				System.out.println("Please Enter an Integer.");
				continue;
			}
			if (bufferInt >= 2030 || bufferInt < 1900) {
				System.out.println("Please Enter a valid year.");
				continue;
			}
			year = bufferInt;
			completed = true;
		} while (!completed); 
		completed = false;
		do {
			System.out.println("please enter the reading on the odometer.");
			buffer = scanner.nextLine();
			bufferInt = 0;
			if (buffer.isEmpty()) {
				continue;
			}
			if (!checkForSQLInjection(buffer)) { 
				continue;
			} 
			try {
				bufferInt = Integer.parseInt(buffer);
			}
			catch(NumberFormatException ex)
			{
				System.out.println("Please Enter an Integer.");
				continue;
			}
			if (bufferInt < 0) {
				System.out.println("Please Enter a valid reading.");
				continue;
			}
			oduo = bufferInt;
			completed = true;
		} while (!completed); 
		completed = false;
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO vehicle (VID,owner,technician,manufacturer,odometer,model,year) VALUES (";
			query += "'" + vid + "',";
			query += "'" + user + "',";
			query += "'" + currentUser + "',";
			query += "'" + manu + "',";
			query += oduo + ",";
			query += "'" + model + "',";
			query += year + ");";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(query);
			if (rows > 0) {
				System.out.println("New Vehicle Added! \n\n");
			}
			
			connection.close();
		}
		catch(SQLException e)
		{
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		
		technicianView(currentUser);
		
	}
	//Issues License to motorists who have completed the driving test with a passing grade
		static void issueLicense(String currentUser)
		{
			
			int numChoices = 1;
			Vector<String> selections = new Vector(0);
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				String query = "SELECT * FROM completedtest WHERE pass = 'Y';";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next())
				{
					String motorist = rs.getString(6);
					String licenceClass = rs.getString(2);
					System.out.println("[" + numChoices + "] Motorist: " +  motorist + " Class: " + licenceClass);
					numChoices++;
					String entry = motorist + " " + licenceClass;
					selections.add(entry);
					
				}
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error in connecting to PostgreSQL server");
				e.printStackTrace();
			}
			
			//choose motorist
			System.out.println("Which motorist would you like to issue a license too:");
			String buffer = scanner.nextLine();
			int bufferInt = 0;
			try {
				bufferInt = Integer.parseInt(buffer);
			}
			catch(NumberFormatException ex)
			{
				System.out.println("Please Enter an Integer.");
				issueLicense(username);
				return;
			}
			if(!(bufferInt < 1 || bufferInt > numChoices))
			{
				System.out.println("Motorist Selected.");
			}
			String selection = selections.get(bufferInt - 1);
			String[] selectionArray = selection.split(" ", 0);
			
			
			//chose license number
			String licenseNum = "";
			
			char choice0 = '\0';
			boolean completed0 = false;
			do {
				System.out.println("Would you like to specify a Licence Plate Number for this license? (Y/N)");
				buffer = scanner.nextLine();
				if (buffer.isEmpty()) {
					System.out.println("Please make a selection");
					continue;
				}
				choice0 = buffer.charAt(0);
				Character.toUpperCase(choice0);
				if (choice0 != 'Y' && choice0 != 'N') {
					System.out.println("Please make a selection");
					continue;
				}
				completed0 = true;
			} while (!completed0);
			completed0 = false;
			
			if (choice0 == 'N') {
				try {
					Connection connection = DriverManager.getConnection(jdbcURL, username, password);
					String query = "SELECT uuid_generate_v4()::text;";
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next())
					{
						licenseNum = rs.getString(1);
					}
					
					connection.close();
				}
				catch(SQLException e)
				{
					System.out.println("Error in connecting to PostgreSQL server");
					e.printStackTrace();
				}
				
			} 
			if (choice0 == 'Y') {
				do {
					System.out.println("Please specify the License Number you would like to have for this vehicle.");
					buffer = scanner.nextLine();
					if (buffer.isEmpty()) {
						System.out.println("Please enter the License Number");
						continue;
					}
					if (checkForSQLInjection(buffer)) {
						boolean repeat = false;
						try {
							Connection connection = DriverManager.getConnection(jdbcURL, username, password);
							String query = "SELECT * FROM license WHERE licensenum = '" + buffer + "';";
							Statement stmt = connection.createStatement();
							ResultSet rs = stmt.executeQuery(query);
							if (rs.next())
							{
								System.out.println("License Number already exists in database.");
								repeat = true;
							}
							
							connection.close();
						}
						catch(SQLException e)
						{
							System.out.println("Error in connecting to PostgreSQL server");
							e.printStackTrace();
						}
						if (repeat) {
							continue;
						}
					}
					else {
						continue;
					}
					licenseNum = buffer;
					completed0 = true;
				} while (!completed0);
				completed0 = false;
			}
			
			
			//choose restrictions
			String restrictions = "";
			char choice1 = '\0';
			boolean completed1 = false;
			do {
				System.out.println("Would you like to specify restrictions for this license? (Y/N)");
				buffer = scanner.nextLine();
				if (buffer.isEmpty()) {
					System.out.println("Please make a selection");
					continue;
				}
				choice1 = buffer.charAt(0);
				Character.toUpperCase(choice1);
				if (choice1 != 'Y' && choice1 != 'N') {
					System.out.println("Please make a selection");
					continue;
				}
				completed1 = true;
			} while (!completed1);
			completed1 = false;
			
			if (choice1 == 'N') {
				restrictions = "NONE";
				
			} 
			if (choice1 == 'Y') {
				do {
					System.out.println("Please specify the restrictions you would like to give this license:");
					buffer = scanner.nextLine();
					//System.out.println(buffer);
					if (buffer.isEmpty()) {
						System.out.println("Please enter the restrictions:");
						continue;
					}
					if (!checkForSQLInjection(buffer)) {
						continue;
					}
					restrictions = buffer;
					completed1 = true;
				} while (!completed1);
				completed1 = false;
			}
			
			
			
			//get date issued
			LocalDate issueDate = LocalDate.now();
			
			//expiration date
			LocalDate exDate = addTenYearsToDate(issueDate);
			
			//class
			String cl = selectionArray[1];
			
			//motorist to issue license
			String user = selectionArray[0];
			
			//Delete CompletedTest
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				String query = "DELETE FROM completedTest WHERE motorist= '" + user + "' AND class = '" + cl + "';";
				//System.out.println(query);
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(query);
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error in connecting to PostgreSQL server");
				e.printStackTrace();
			}
			
			//Delete old license
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				String query = "DELETE FROM license WHERE motorist= '" + user + "';";
				//System.out.println(query);
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(query);
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error in connecting to PostgreSQL server");
				e.printStackTrace();
			}
			//create insert query
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				String query = "INSERT INTO license (licensenum,issuedate,expirationdate,restrictions,class,motorist,technician) VALUES (";
				query += "'" + licenseNum + "',";
				query += "'" + issueDate + "',";
				query += "'" + exDate + "',";
				query += "'" + restrictions + "',";
				query += "'" + cl + "',";
				query += "'" + user + "',";
				query += "'" + currentUser + "');";
				//System.out.println(query);
				Statement stmt = connection.createStatement();
				int rows = stmt.executeUpdate(query);
				if (rows > 0) {
					System.out.println("Licence Created \n\n");
				}
				
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error in connecting to PostgreSQL server");
				e.printStackTrace();
			}
			
			technicianView(currentUser);
			
			
		}
	static void yourAppointmentArchive() {
		//For this one, I'm going to have to learn to compare future vs past
		//String query = "SELECT testid, time, date, class, motorist FROM \"drivinglesson\" WHERE date >= NOW() AND instructor = '" + loggedUsername + "' ;";
		System.out.println("Here is a list of appointments you have already completed, tests that have been graded will have their grade shown as well:");
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"drivingtest\" WHERE motorist= '" + loggedUsername + "\' AND date < NOW() UNION SELECT * FROM drivinglesson WHERE motorist = '" + loggedUsername + "' AND date < NOW();";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String licenseClass = rs.getString(2);
				String time = rs.getString(3);
				Date appointmentDate = rs.getDate(4);
				System.out.println("Completion Date: " + appointmentDate + " Time: " + time + " Class: " + licenseClass);
			}
			connection.close();
		}
		catch(SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"completedtest\" WHERE motorist =  '" + loggedUsername + "';" ;
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String licenseClass = rs.getString(2);
				String time = rs.getString(3);
				Date appointmentDate = rs.getDate(4);
				int score = rs.getInt(7);
				System.out.println("Completion Date: " + appointmentDate + " Time: " + time + " Class: " + licenseClass  + " Type: Test " + "Score: " + score );
			}
			connection.close();
		}
		catch(SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		
		System.out.println("Here is a list of appointments you have yet to complete:");
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"drivingtest\" WHERE motorist =  '" + loggedUsername + "' AND date >= NOW() UNION SELECT * FROM drivinglesson WHERE motorist = '" + loggedUsername + "' AND date >= NOW();";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String licenseClass = rs.getString(2);
				String time = rs.getString(3);
				Date appointmentDate = rs.getDate(4);
				System.out.println("Appointment Date: " + appointmentDate + " Time: " + time + " Class: " + licenseClass);
			}
			connection.close();
		}
		catch(SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		
		System.out.println("Press Enter to return to the motorist View.");
		scanner.nextLine();
		for (int i = 0; i < 20; ++i) 
			System.out.println(); 
		motoristView();
	}
	static void submitGradesTableEdits(Vector<String> tuple, String score) {
		//remove from one drivingtest
		char pass;
		if (Integer.parseInt(score) >= 70) {
				pass = 'Y';
		}
		else {
			pass = 'N';
		}
		
		try {
			//delete based on value of the testID
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "DELETE FROM \"drivingtest\" WHERE testid = '" + tuple.elementAt(0) + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			connection.close();
			System.out.println("Tuple deleted from table drivingTest based on testID" + tuple.elementAt(0));
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: tableEdits");
			e.printStackTrace();
		}
		
		//add to completedtest
		try {
			//add passed tuple to completedtest table
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			
			String query = "INSERT INTO completedtest (testID, class, time, date, instructor, motorist, score, pass)"
			+ "VALUES ('" + tuple.elementAt(0) + "', '" + tuple.elementAt(1) + "', '" + tuple.elementAt(2) + "', '" + tuple.elementAt(3) + "', '" + 
			tuple.elementAt(4) + "', '" + tuple.elementAt(5) + "', '" + Integer.parseInt(score) + "', '" + pass + "');";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			connection.close();
			System.out.println("Tuple added to completedtest table based on testID" + tuple.elementAt(0));
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: tableEdits");
			e.printStackTrace();
		}
		
		return;
	}
	
	static void submitGrades() {
		//container for un-taken tests
		Vector<Vector<String>> Tests = new Vector<Vector<String>>(0);
		
		//query for tests that have already been taken
		try {
			//System.out.println(Tests);
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"drivingtest\" WHERE date < NOW() AND instructor ='" + loggedUsername + "';";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			//iterate through each tuple
			System.out.println("Tests you have yet to grade: ");
			
//			//container for information from each tuple
//			Vector<String> tuple = new Vector<String>();
//			//populate tuple with emptiness
//			for(int i = 0; i < 6; i++) {
//				tuple.add("");
//			}
			
			//iterate until no more tuples can be parsed
			while (rs.next()) {
				//container for information from each tuple
				Vector<String> tuple = new Vector<String>();
				//populate tuple with emptiness
				for(int i = 0; i < 6; i++) {
					tuple.add("");
				}
				//iterate through each column in a tuple
				for (int i = 1; i < 7; i++) {
					//add to front
					tuple.set(i-1, rs.getString(i));
				}
				//add new tuple
				//System.out.println(Tests);
				Tests.add(tuple);
				//System.out.println(Tests);
			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: submitgrades");
			e.printStackTrace();
		}
		
		//print out tests and have user select one (until they want to quit)
		if (Tests.size() == 0) {
			System.out.println("(empty)");
			return;
		}
		
		for (int i = 0; i < Tests.size(); i++) {
			int adjustedI = i+1;
			System.out.print("[" + adjustedI + "] : ");
			for (int j = 0; j < 6; j++) {
				//print each part of the tuple
				System.out.print(Tests.elementAt(i).elementAt(j) + " ");
			}
			System.out.println();
		}
		
		//get user input for which test to grade
		System.out.println("Choose a test to grade (or press q/Q to quit): ");
		String choice = scanner.nextLine();
		
		//quit if desired
		if (choice.equals("q") || choice.equals("Q")) {
			return;
		}
		
		try {
			Integer.parseInt(choice);
		}
		catch (NumberFormatException ex){
	          System.out.println("Please Enter an Integer.");
	          submitGrades();
	          return;
	    }
		//make sure input is valid
		while (choice.isEmpty() || Integer.parseInt(choice) > Tests.size() || Integer.parseInt(choice) <= 0) {
			//quit function
			if (choice.equals("q") || choice.equals("Q")) {
				return;
			}
			//wait for valid input from user
			System.out.println("Choose a VALID test to grade (or press q/Q to quit): ");
			choice = scanner.nextLine();
		}
		
		System.out.println("What is the score for this motorist?: ");
		String score = scanner.nextLine();
		try {
			Integer.parseInt(score);
		}
		catch (NumberFormatException ex){
	          System.out.println("Please Enter an Integer.");
	          submitGrades();
	          return;
	    }
		//give selected tuple to this function to remove from drivingtest and add to completed test
		submitGradesTableEdits(Tests.elementAt(Integer.parseInt(choice) - 1), score);
		
	}
	
	static void scheduledLessonsTests(){
		//look up scheduled tests and lessons for instructors
		
		//get future driving lessons
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT testid, time, date, class, motorist FROM \"drivinglesson\" WHERE date >= NOW() AND instructor = '" + loggedUsername + "' ;";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			//iterate through each tuple
			System.out.println("Upcoming Lessons: ");
			
			while (rs.next()) {
				
				//iterate through each column in a tuple
				for (int i = 1; i < 5; i++) {
					//string to hold values from a column in a tuple
					String tupleColumn = rs.getString(i);
					System.out.print(tupleColumn + " ");
				}
				
				//new line for formatting between tuples
				System.out.println();
			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: determineView");
			e.printStackTrace();
		}
		
		//get future driving tests
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT testid, class, time, date, motorist FROM \"drivingtest\" WHERE date >= NOW() AND instructor = '" + loggedUsername + "' ;";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			//iterate through each tuple
			System.out.println("Upcoming Tests: ");
			System.out.println("Format Order: LessonID, class, time, date, motorist");
			
			while (rs.next()) {
				
				//iterate through each column in a tuple
				for (int i = 1; i < 6; i++) {
					//string to hold values from a column in a tuple
					String tupleColumn = rs.getString(i);
					System.out.print(tupleColumn + " ");
				}
				
				//new line for formatting between tuples
				System.out.println();
			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: determineView");
			e.printStackTrace();
		}
		
		return;
	}
	
	static void instructorView() {
		System.out.println("Welcome to the instructor View!");
		System.out.println("[1] View upcoming scheduled lessons and tests you must proctor");
		System.out.println("[2] Submit grades for tests that you have proctored");
		System.out.println("[3] Return to login");
		//get user input
		String buffer = scanner.nextLine();
		if (buffer.isEmpty()) {
			System.out.println("Please make a selection.");
			instructorView();
			return;
		}
		//check if user input is a number
		int bufferInt = -1;
		try {
			bufferInt = Integer.parseInt(buffer);
		}
		catch (NumberFormatException ex){
	          System.out.println("Please Enter an Integer.");
	          instructorView();
	          return;
	    }
		
		//check if user input is in range
		switch (bufferInt) {
		case 1:
			scheduledLessonsTests();
			break;
		case 2:
			submitGrades();
			break;
		case 3:
			loggedUsername = "";
			psuedomain();
			return;
		default:
			System.out.println("Please Enter an Integer within range.");
			instructorView();
			return;
		};
		instructorView();
		return;
	}
	static void viewYourLicenses() {
		System.out.println("Here is a list of your licenses:");
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"license\" WHERE motorist= '" + loggedUsername + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String licensenum = rs.getString(1);
				Date issuedate = rs.getDate(2);
				Date expdate = rs.getDate(3);
				String restrictions = rs.getString(4);
				String licenseClass = rs.getString(5);
				System.out.println("VID: [" + licensenum + "]  IssueDate: " + issuedate + " ExpDate: " + expdate + " restrictions: " + restrictions + " class: " + licenseClass);
			}
			connection.close();
		}
		catch(SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		System.out.println("Press Enter to return to the motorist View.");
		scanner.nextLine();
		for (int i = 0; i < 20; ++i) 
			System.out.println(); 
		motoristView();
	}
	static void viewYourVehicles() {
		System.out.println("Here is a list of your vehicles:");
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"vehicle\" WHERE owner= '" + loggedUsername + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String vid = rs.getString(1);
				String manufacturer = rs.getString(4);
				int odometer = rs.getInt(5);
				String model = rs.getString(6);
				int year = rs.getInt(7);
				System.out.println("VID: [" + vid + "] " + manufacturer + " " + model + " " + String.valueOf(year) + " odometer: " + String.valueOf(odometer));
			}
			connection.close();
			System.out.println("Press Enter to return to the motorist View.");
			scanner.nextLine();
			for (int i = 0; i < 20; ++i) 
				System.out.println(); 
			motoristView();
		}
		
		catch(SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		motoristView();
	}
	static void scheduleDrivingLesson() {
		System.out.println("Please enter the class of license (A, B, C, or E) that you would like to take lessons for. ");
		System.out.println("Or press enter without entering in a license class to return to the motoristView: ");
		char licenseClass = '\0';
		int numOfChoices = 1;
		String licenseString = scanner.nextLine();
		Vector<String> selectableInstructors = new Vector(0);
		if (!licenseString.isEmpty()) {
			licenseClass = licenseString.charAt(0);
			if (Character.isLowerCase(licenseClass)) {
				System.out.println("toUpper");
				licenseClass = Character.toUpperCase(licenseClass);
				System.out.println(licenseClass);
			}
			if (licenseClass != '\0') {
				if (!(licenseClass != 'A' || licenseClass != 'B' || licenseClass == 'C' || licenseClass == 'E')) {
					System.out.println("Please Enter A, B, C, or E");
					scheduleDrivingLesson();
					return;
				}
			}
			else {
				for (int i = 0; i < 20; ++i) 
					System.out.println(); 
				motoristView();
				return;
			}
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			licenseClass = Character.toLowerCase(licenseClass);
			String query = "SELECT * FROM \"drivinglesson\" WHERE class= '" + Character.toUpperCase(licenseClass) + "' AND motorist='" + loggedUsername + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				;
			}
			if (rs.next()) {
				System.out.println("You cannot schedule more than two lessons for a particular license class.");
				connection.close();
				System.out.println("Press Enter to return to the motorist View.");
				scanner.nextLine();
				for (int i = 0; i < 20; ++i) 
					System.out.println(); 
				connection.close();
				motoristView();
				return;
			}
			connection.close();
		}
		
		catch(SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"" + licenseClass + "instructors\";";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet instructors = stmt.executeQuery(query);
			while (instructors.next()) {
				//System.out.println(instructors);
				String instructorFirstName = instructors.getString(1);
				String instructorLastName = instructors.getString(2);
				String instructorCredentials = instructors.getString(3);
				System.out.println("[" + numOfChoices + "] " + instructorFirstName + " " + instructorLastName + " " + instructorCredentials);
				String entry = instructorFirstName + " " + instructorLastName + " " + instructorCredentials;
				//System.out.println(entry);
				selectableInstructors.add(entry);
				numOfChoices++;
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		//System.out.println(selectableInstructors);
		System.out.println("Please select an instructor: ");
		String buffer = scanner.nextLine();
		int bufferInt = 0;
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       scheduleDrivingLesson();
	       return;
	    }
		if (bufferInt < 1 || bufferInt > numOfChoices) {
			scheduleDrivingTest();
		    return;
		}
		int choice = bufferInt;
		System.out.println(choice);
		String selection = selectableInstructors.get(choice - 1);
		String[] selectionArray = selection.split(" ", 0);
		System.out.println(selectionArray[0]);
		System.out.println("Below are all of this instructor's taken appointment times");
		//I'm going to do my query to show the user all that instructors scheduled appointments
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM bookedAppointments WHERE instructor = '" + selectionArray[0] + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String appointmentTime = rs.getString(1);
				Date appointmentDate = rs.getDate(2);
				String appointment = appointmentTime + " " + appointmentDate;
				System.out.println(appointment);
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		System.out.println("Rules for scheduling lessons: ");
		System.out.println("Can only schedule two 2-hour lessons on Monday-Friday during the following times: ");
		System.out.println("9AM, 11AM, 1PM, 3PM");
		System.out.println("Please enter the date that you wish to take the lesson (format: YYYY-MM-DD)");
		buffer = scanner.nextLine();
		LocalDate testDate;
		try {
			testDate = LocalDate.parse(buffer);
		}
		catch(DateTimeParseException e) {
			System.out.println("Please enter a valid date");
			scheduleDrivingLesson();
			return;
		}
		LocalDate currDate = LocalDate.now();
		long daysBetween = ChronoUnit.DAYS.between(currDate, testDate);
		if (daysBetween > 21) {
			System.out.println("Please enter a date that is only three weeks from today.");
			scheduleDrivingLesson();
			return;
		}
		if (daysBetween < 1) {
			System.out.println("Please don't enter today's date or a date that has already passed.");
			scheduleDrivingLesson();
			return;
		}
		DayOfWeek dayOfWeek = DayOfWeek.from(testDate);
		if(dayOfWeek.name() == "SATURDAY" || dayOfWeek.name() == "SUNDAY") {
			System.out.println("Please enter a date that doesn't occur on Saturday or Sunday.");
			scheduleDrivingLesson();
			return;
		}
		System.out.println("Please enter the time in which you want to take the lesson on that day. (9, 11, 1, or 3)");
		buffer = scanner.nextLine();
		String selectedTime;
		if (buffer.contains("9")) {
			selectedTime = "9AM";
		}
		else if (buffer.contains("11")) {
			selectedTime = "11AM";
		}
		else if (buffer.contains("1")) {
			selectedTime = "1PM";
		}
		else if (buffer.contains("3")) {
			selectedTime = "3PM";
		}
		else {
			System.out.println("Please enter a valid time");
			scheduleDrivingLesson();
			return;
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM bookedAppointments WHERE instructor = '" + selectionArray[0] + "'  AND time = '" + selectedTime + "' AND date ='" + testDate + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("Please book a time and appointment at a time and date that are not taken.");
				connection.close();
				scheduleDrivingLesson();
				return;
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO drivinglesson (testID, class, time, date, instructor, motorist)"
			+ "VALUES ( uuid_generate_v4(), '" + Character.toUpperCase(licenseClass) + "','" + selectedTime + "', '" + testDate + "', '" + 
			selectionArray[0] + "', '" + loggedUsername + "');";
			Statement stmt = connection.createStatement();
			//System.out.println(query);
			stmt.executeUpdate(query);
			System.out.println("Lesson Scheduled!");
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		motoristView();
	}
	
	static void scheduleDrivingTest() { //Assume each Driving Test is two hour and testing is from 9-5
		//First thing we must do is determine what class of license the user is trying to get
		System.out.println("Please enter the class of license (A, B, C, or E) that you would like to be tested on. ");
		System.out.println("Or press enter without entering in a license class to return to the motoristView: ");
		char licenseClass = '\0';
		int numOfChoices = 1;
		String licenseString = scanner.nextLine();
		Vector<String> selectableInstructors = new Vector(0);
		if (!licenseString.isEmpty()) {
			licenseClass = licenseString.charAt(0);
			if (Character.isLowerCase(licenseClass)) {
				System.out.println("toUpper");
				licenseClass = Character.toUpperCase(licenseClass);
				System.out.println(licenseClass);
			}
			if (licenseClass != '\0') {
				if (!(licenseClass != 'A' || licenseClass != 'B' || licenseClass == 'C' || licenseClass == 'E')) {
					System.out.println("Please Enter A, B, C, or E");
					scheduleDrivingTest();
					return;
				}
			}
		}
		else {
			for (int i = 0; i < 20; ++i) 
				System.out.println(); 
			motoristView();
			return;
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			licenseClass = Character.toLowerCase(licenseClass);
			String query = "SELECT * FROM \"drivingtest\" WHERE class= '" + Character.toUpperCase(licenseClass) + "' AND motorist='" + loggedUsername + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("You cannot schedule more than one test for a particular license class.");
				System.out.println("Press Enter to return to the motorist View.");
				scanner.nextLine();
				for (int i = 0; i < 20; ++i) 
					System.out.println(); 
				motoristView();
				return;
			}
			connection.close();
		}
		
		catch(SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM \"" + licenseClass + "instructors\";";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet instructors = stmt.executeQuery(query);
			while (instructors.next()) {
				//System.out.println(instructors);
				String instructorFirstName = instructors.getString(1);
				String instructorLastName = instructors.getString(2);
				String instructorCredentials = instructors.getString(3);
				System.out.println("[" + numOfChoices + "] " + instructorFirstName + " " + instructorLastName + " " + instructorCredentials);
				String entry = instructorFirstName + " " + instructorLastName + " " + instructorCredentials;
				selectableInstructors.add(entry);
				numOfChoices++;
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		//We now know what class of license the motorist is testing for
		//System.out.println(selectableInstructors);
		System.out.println("Please select an instructor: ");
		String buffer = scanner.nextLine();
		int bufferInt = 0;
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       scheduleDrivingTest();
	       return;
	    }
		if (bufferInt < 1 || bufferInt > numOfChoices) {
			System.out.println("Instructor Selected.");
			scheduleDrivingTest();
		    return;
		}
		int choice = bufferInt;
		String selection = selectableInstructors.get(choice - 1);
		String[] selectionArray = selection.split(" ", 0);
		//System.out.println(selectionArray[0]);
		System.out.println("Below are all of this instructor's taken appointment times");
		//I'm going to do my query to show the user all that instructors scheduled appointments
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM bookedAppointments WHERE instructor = '" + selectionArray[0] + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String appointmentTime = rs.getString(1);
				Date appointmentDate = rs.getDate(2);
				String appointment = appointmentTime + " " + appointmentDate;
				System.out.println(appointment);
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		System.out.println("Rules for scheduling lessons and tests: ");
		System.out.println("Can only schedule one 2-hour test on Monday-Friday during the following times: ");
		System.out.println("9AM, 11AM, 1PM, 3PM");
		System.out.println("Please enter the date that you wish to take the test (format: YYYY-MM-DD)");
		buffer = scanner.nextLine();
		LocalDate testDate;
		try {
			testDate = LocalDate.parse(buffer);
		}
		catch(DateTimeParseException e) {
			System.out.println("Please enter a valid date");
			scheduleDrivingTest();
			return;
		}
		LocalDate currDate = LocalDate.now();
		long daysBetween = ChronoUnit.DAYS.between(currDate, testDate);
		if (daysBetween > 21) {
			System.out.println("Please enter a date that is only three weeks from today.");
			scheduleDrivingTest();
			return;
		}
		if (daysBetween < 1) {
			System.out.println("Please don't enter today's date or a date that has already passed.");
			scheduleDrivingTest();
			return;
		}
		DayOfWeek dayOfWeek = DayOfWeek.from(testDate);
		if(dayOfWeek.name() == "SATURDAY" || dayOfWeek.name() == "SUNDAY") {
			System.out.println("Please enter a date that doesn't occur on Saturday or Sunday.");
			scheduleDrivingTest();
			return;
		}
		System.out.println("Please enter the time in which you want to take the test on that day. (9, 11, 1, or 3)");
		buffer = scanner.nextLine();
		String selectedTime;
		if (buffer.contains("9")) {
			selectedTime = "9AM";
		}
		else if (buffer.contains("11")) {
			selectedTime = "11AM";
		}
		else if (buffer.contains("1")) {
			selectedTime = "1PM";
		}
		else if (buffer.contains("3")) {
			selectedTime = "3PM";
		}
		else {
			System.out.println("Please enter a valid time");
			scheduleDrivingTest();
			return;
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT * FROM bookedAppointments WHERE instructor = '" + selectionArray[0] + "'  AND time = '" + selectedTime + "' AND date ='" + testDate + "';";
			//System.out.println(query);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("Please book a time and appointment at a time and date that are not taken.");
				connection.close();
				scheduleDrivingTest();
				return;
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO drivingtest (testID, class, time, date, instructor, motorist)"
			+ "VALUES (uuid_generate_v4()::text, '" + Character.toUpperCase(licenseClass) + "','" + selectedTime + "', '" + testDate + "', '" + 
			selectionArray[0] + "', '" + loggedUsername + "');";
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(query);
			if (rows > 0) {
				System.out.println("Test Scheduled!");
			}
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		System.out.println("Press Enter to return to the motorist View.");
		scanner.nextLine();
		for (int i = 0; i < 20; ++i) 
			System.out.println(); 
		motoristView();
	}
	
	static void motoristView() {
		System.out.println("Welcome to the motorist View.");
		System.out.println("[1] View Licenses that you currently possess");
		System.out.println("[2] Schedule Driving Tests");
		System.out.println("[3] Schedule a Driving Lesson");
		System.out.println("[4] View Vehicles that you currently have registered");
		System.out.println("[5] View your scheduled tests and lessons you've previously taken and are scheduled to take.");
		System.out.println("[6] Return to login.");
		System.out.println("Please enter your selection: ");
		String buffer = scanner.nextLine();
		if (buffer.isEmpty()) {
			System.out.println("Please make a selection.");
			motoristView();
			return;
		}
		int bufferInt = -1;
		try {
			bufferInt = Integer.parseInt(buffer);
		}
		catch (NumberFormatException ex){
	          System.out.println("Please Enter an Integer.");
	          motoristView();
	          return;
	    }
		if (bufferInt > 6 || bufferInt < 1) {
			System.out.println("Please Enter an Integer within range.");
			motoristView();
			return;
		}
		if (bufferInt == 1) {
			viewYourLicenses();
		}
		if (bufferInt == 2) {
			scheduleDrivingTest();
		}
		if (bufferInt == 3) {
			scheduleDrivingLesson();
		}
		if (bufferInt == 4) {
			viewYourVehicles();
		}
		if (bufferInt == 5) {
			yourAppointmentArchive();
		}
		if (bufferInt == 6) {
			loggedUsername = "";
			psuedomain();
			return;
		}
	}
	static boolean checkForSQLInjection(String input) {
		if (input.contains(";") || input.contains("\"") || input.contains(":") || input.contains("\'"))
		{
			System.out.println("Input cannot contain special characters ';', ', '\"', or ':' ");
			return false;
		}
		return true;
	}
	static boolean checkUsernameAndPasswordValidity(String input) {
		if ( input.length() < 8 ) {
			System.out.println("Username cannot be shorter than 8 characters. ");
			return false;
		}
		if (input.contains(";") || input.contains("\"") || input.contains(":") || input.contains("\'"))
		{
			System.out.println("Username cannot contain special characters ';', ', '\"', or ':' ");
			return false;
		}
		return true;
	}
	
	static String formatDate(int year, int month, int day) {
		String formattedDate = "";
		formattedDate = String.valueOf(year);
		if (month < 10) {
			formattedDate = formattedDate + "-0" + String.valueOf(month);
		}
		else {
			formattedDate = formattedDate + "-" + String.valueOf(month);
		}
		if (day < 10) {
			formattedDate = formattedDate + "-0" + String.valueOf(day);
		}
		else {
			formattedDate = formattedDate + "-" + String.valueOf(day);
		}
		LocalDate testDate;
		try {
			testDate = LocalDate.parse(formattedDate);
		}
		catch(DateTimeParseException e) {
			//System.out.println("Please enter a valid date");
			return "1000-01-01";
		}
		return formattedDate;
	}
	
	static void Register() {
		System.out.println("Please enter the username you would like to have for your account: ");
		String newUsername = "";
		newUsername = scanner.nextLine();
		if (!checkUsernameAndPasswordValidity(newUsername)) {
			Register();
			return;
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT username FROM \"users\"";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String indexedUsername = rs.getString(1);
		        if (indexedUsername.equals(newUsername)) {
		        	System.out.println("A User with this username already exists");
		        	connection.close();
		        	Register();
		        	return;
		        }
		    }
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		System.out.println("Please enter your gender(M/F/N/P (Prefer not to answer)): ");
		char gender = '\0';
		String genderString = scanner.nextLine();
		gender = genderString.charAt(0);
		if (gender != '\0') {
			if (gender == '\"' || gender == ';' || gender == ':' || gender == '\'') {
				System.out.println("Gender cannot be a special character (i.e. :, ', or ;.");
				Register();
				return;
			}
		}
		else {
			System.out.println("Please Enter a gender.");
			Register();
			return;
		}
		System.out.println("Please enter your firstName: ");
		String firstName = "";
		firstName = scanner.nextLine();
		if (firstName.isEmpty()) {
			System.out.println("please enter your first name");
			Register();
			return;
		}
		else {
			if (firstName.contains(";") || firstName.contains("\"") || firstName.contains(":") || firstName.contains("\'")) {
				System.out.println("first name cannot contain special characters ';', ', '\"', or ':' ");
				Register();
				return;
			}
		}
		System.out.println("Please enter your lastName: ");
		String lastName = "";
		lastName = scanner.nextLine();
		if (lastName.isEmpty()) {
			System.out.println("please enter your last name");
			Register();
			return;
		}
		else {
			if (lastName.contains(";") || lastName.contains("\"") || lastName.contains(":") || lastName.contains("\'")) {
				System.out.println("last name cannot contain special characters ';', ', '\"', or ':' ");
				Register();
				return;
			}
		}
		System.out.println("Please initialize your accounts password: ");
		String accountPassword = "";
		accountPassword = scanner.nextLine();
		if (!checkUsernameAndPasswordValidity(accountPassword)) {
			Register();
			return;
		}
		System.out.println("Please enter your address: ");
		String address = "";
		address = scanner.nextLine();
		if (address.isEmpty()) {
			System.out.println("please enter your address");
			Register();
			return;
		}
		else {
			if (address.contains(";") || address.contains("\"") || address.contains(":") || address.contains("\'")) {
				System.out.println("last name cannot contain special characters ';', ', '\"', or ':' ");
				Register();
				return;
			}
		}
		System.out.println("Please enter the year you were born: ");
		String buffer = scanner.nextLine();
		int bufferInt = -1;
		try {
			bufferInt = Integer.parseInt(buffer);
		}
		catch (NumberFormatException ex){
          System.out.println("Please Enter an Integer.");
          Register();
          return;
        }
		if (bufferInt > 3000 || bufferInt < 1000) {
			System.out.println("Please Enter a year that you were born.");
			Register();
			return;
		}
		int year = bufferInt;

		System.out.println("Please enter the month of the year you were born as a digit (i.e. 1 as January and 12 as December: ");
		buffer = scanner.nextLine();
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       Register();
	       return;
	    }
		int month = bufferInt;
		if (month > 12 || month < 1) {
			System.out.println("Please enter a valid month.");
			Register();
			return;
		}
		System.out.println("Please enter the day of the year you were born: ");
		buffer = scanner.nextLine();
		try{
			bufferInt = Integer.parseInt(buffer);
	    }
	    catch (NumberFormatException ex){
	       System.out.println("Please Enter an Integer.");
	       Register();
	       return;
	    }
		int day = bufferInt;
		if (day > 31 || day < 1) {
			System.out.println("Please enter a valid day of the month.");
			Register();
			return;
		}
		String date = formatDate(year, month, day);
		if (date == "1000-01-01") {
			System.out.println("Please enter a date that actually happened.");
			Register();
			return;
		}
		LocalDate currdate = LocalDate.now();
		LocalDate testdate = LocalDate.parse(date);
		long yearsBetween = ChronoUnit.YEARS.between(testdate, currdate);
		if (yearsBetween < 16) {
			System.out.println("You must be at least 16 years of age.");
			Register();
			return;
		}
		if (yearsBetween > 150) {
			System.out.println("No human being can live longer than 150 years, please enter your actual birthday or contact Guiness Book of World Records.");
			Register();
			return;
		}
		//System.out.println(yearsBetween);
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO \"users\" (\"gender\", \"firstname\", \"lastname\", \"username\", \"password\", \"address\", \"dob\") "
					+ "VALUES ('" + gender + "', '" + firstName + "', '" + lastName + "', '" +  newUsername + "', '" + accountPassword + "', '" 
					+ address + "', '" + date + "');";      
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "INSERT INTO \"motorist\" (\"gender\", \"firstname\", \"lastname\", \"username\", \"password\", \"address\", \"dob\") "
					+ "VALUES ('" + gender + "', '" + firstName + "', '" + lastName + "', '" +  newUsername + "', '" + accountPassword + "', '" 
					+ address + "', '" + date + "');";      
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(query);
			if (rows > 0) {
				System.out.println("Account Successfully created!");
			}
			
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		loggedUsername = newUsername;
		for (int i = 0; i < 20; ++i) 
			System.out.println(); 
		determineView(loggedUsername);
	}
	static void determineView(String acceptedUsername) { //determines what role the user is and selects their view
		boolean foundRole = false;
		try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				String query = "SELECT username FROM \"motorist\" WHERE username = '" + acceptedUsername + "' ;";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next() == true) { 
					foundRole = true; 
				}
				connection.close();
			}
			catch (SQLException e) {
				System.out.println("Error connecting to database: determineView");
				e.printStackTrace();
			}
		if (foundRole == true) {
			loggedUsername = acceptedUsername;
			motoristView();
			return;
		}
		foundRole = false;
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT username FROM \"instructor\" WHERE username = '" + acceptedUsername + "' ;";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next() == true) { 
				foundRole = true;
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: determineView");
			e.printStackTrace();
		}
		if (foundRole == true) {
			loggedUsername = acceptedUsername;
			instructorView();
			return;
		}
		foundRole = false;
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT username FROM \"technician\" WHERE username = '" + acceptedUsername + "' ;";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next() == true) { 
				foundRole = true;
			}
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: determineView");
			e.printStackTrace();
		}
		if (foundRole == true) {
			loggedUsername = acceptedUsername;
			technicianView(loggedUsername);
			return;
		}
	}
	static void Login() {
		System.out.println("Please enter your username: ");
		String loginUsername = "";
		loginUsername = scanner.nextLine();
		if (loginUsername.isEmpty()) {
			System.out.println("Please enter a username:");
			Login();
			return;
		}
		Boolean match = false;
		try { 
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			Statement stmt = connection.createStatement();
			String query = "SELECT username from Users;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String indexedUsername = rs.getString(1);
		        if (indexedUsername.equals(loginUsername)) {
		        	match = true;
		        }
		    }
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		if (match == false) {
			System.out.println("Username not found in database.");
			Login();
			return;
		}
		System.out.println("Please enter your password: ");
		String loginPassword = "";
		loginPassword = scanner.nextLine();
		if (loginPassword.isEmpty()) {
			System.out.println("Please enter a username:");
			Login();
			return;
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			String query = "SELECT password FROM \"users\" WHERE username = '" + loginUsername + "';";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String indexedpassword = rs.getString(1);
		        
				if (indexedpassword.equals(loginPassword)) {
		        	System.out.println("You have sucessfully logged in!");
		        }
				else {
					System.out.println("incorrect password entered.");
					connection.close();
					Login();
					return;
				}
		    }
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		for (int i = 0; i < 20; ++i) 
			System.out.println(); 
		determineView(loginUsername);
	}
	
	public static void psuedomain() {
		String jdbcURL = "jdbc:postgresql://localhost:5432/DMV";
		String username = "postgres";
		String password = "password";
		System.out.println("Welcome to the DMV Website, please select from the options below what you would like to do:");
		System.out.println("[1] Login");
		System.out.println("[2] Create An Account");
		System.out.print("Make a selection: ");
		String selection = scanner.nextLine();
		int selectionInt = -1;
        try{
            selectionInt = Integer.parseInt(selection);
        }
        catch (NumberFormatException ex){
            System.out.println("Please Enter an Integer.");
            return;
        }
		if (selectionInt == 1) { //Login Selected
			Login();
		}
		else if(selectionInt == 2) {
			Register();
		}
		else {
			System.out.println("Please enter a value within range.");
		}
	}
	
	public static void main(String[] args) {
		String jdbcURL = "jdbc:postgresql://localhost:5432/DMV";
		String username = "postgres";
		String password = "password";
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connected to PostgreSQL server");
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to PostgreSQL server");
			e.printStackTrace();
		}
		//issueLicense("bradforda");
		System.out.println("Welcome to the DMV Website, please select from the options below what you would like to do:");
		System.out.println("[1] Login");
		System.out.println("[2] Create An Account");
		System.out.print("Make a selection: ");
		String selection = scanner.nextLine();
		int selectionInt = -1;
        try{
            selectionInt = Integer.parseInt(selection);
        }
        catch (NumberFormatException ex){
            System.out.println("Please Enter an Integer.");
            return;
        }
		if (selectionInt == 1) { //Login Selected
			Login();
		}
		else if(selectionInt == 2) {
			Register();
		}
		else {
			System.out.println("Please enter a value within range.");
		}
	}
}
