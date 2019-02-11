package app.projetCdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import app.projetCdb.dao.CompanyDao;
import app.projetCdb.dao.DbAccess;
import app.projetCdb.models.Company;



/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ){
		try {
			DbAccess.loadDriver();
			Company company=new Company("Keolys");
			CompanyDao companyDao=new CompanyDao();
			companyDao.delete((long) 1001);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showCdbUi();
	}


	public static void showCdbUi() {
		boolean fin=false; 
		int userChoice=0;
		Scanner scanner=new Scanner(System.in);
		while(!fin){ 
			System.out.println(); 
			System.out.println("---------------------------"); 
			System.out.println("-      Cdb interface      -"); 
			System.out.println("---------------------------"); 
			System.out.println(); 
			System.out.println("1- List computers"); 
			System.out.println("2- list companies"); 
			System.out.println("3- Create a computer"); 
			System.out.println("4- Update a computer"); 
			System.out.println("5- Delete a computer"); 
			System.out.println("5- Exit"); 
			System.out.println("Votre choix :"); 
			userChoice=scanner.nextInt();
			while (userChoice<1 || userChoice>6) { 
				System.out.print("précisez votre choix >");
				userChoice=scanner.nextInt();
			}
			System.out.println(" votre choix est : "+userChoice);
			switch(userChoice) {
			case 1:
				
				break;
			case 2:
				
				break;
				
			case 3:
				
				break;
				
			case 4:
				
				break;
				
			case 5:
				
				break;
			default:
				
				break;
			}
		}
	}
}
