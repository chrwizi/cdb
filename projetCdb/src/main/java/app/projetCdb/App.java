package app.projetCdb;

import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import app.projetCdb.dao.CompanyDao;
import app.projetCdb.dao.ComputerDao;
import app.projetCdb.dao.DbAccess;
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;



/**
 * Hello world!
 *
 */
public class App 
{
	/*Menu selections */
	private final static int LIST_COMPUTERS=1;
	private final static int LIST_COMPANIES=2;
	private final static int CREATE_COMPUTER=3;
	private final static int UPDATE_COMPUTER=4;
	private final static int DELETE_COMPUTER=5;
	private final static int SHOW_COMPUTER_DETAILS=6;
	private final static int EXIT=7;

	public static void main( String[] args ){
		//DbAccess.loadDriver();
		Company company=new Company("Keolys");
		CompanyDao companyDao=new CompanyDao(DbAccess.getInstance());
		ComputerDao computerDao=new ComputerDao(DbAccess.getInstance());
		showCdbUi(companyDao,computerDao);

	}
 
	/**
	 * 
	 * @param companyDao
	 * @param computerDao
	 */
	public static void showCdbUi(CompanyDao companyDao,ComputerDao computerDao) {
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
			case LIST_COMPUTERS:
				listComputersHandler(computerDao,5);
				break;
			case LIST_COMPANIES:
				listCompaniesHandler(companyDao,10);
				break;
			case CREATE_COMPUTER:
				getComputerParametters();
				break;

			case UPDATE_COMPUTER:

				break;

			case DELETE_COMPUTER:

				break;
			case SHOW_COMPUTER_DETAILS:

				break;
			case EXIT:

				break;
			default:

				break;
			}
		}
	}

	/**
	 * print list of all companies  in database
	 * @param dao: object given access to companies table in database  
	 */
	public static void listCompaniesHandler(CompanyDao dao,int nbCompniesToPrintByLine) {
		try {
			List<Company> companies=dao.findAll();
			ListIterator<Company> iterator=companies.listIterator();
			int i=0;
			if(companies.isEmpty())System.out.println("Aucune company disponible");
			else {
				while(iterator.hasNext()) {
					if((i++)%nbCompniesToPrintByLine<nbCompniesToPrintByLine-1) {
						System.out.print(iterator.next()+"\t\t\t");
					}
					else System.out.println(iterator.next());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * print list of all computers in database 
	 * @param dao : object given access to computers table in database
	 * @param nbComputerToPrintByLine: number of computer to print in line .
	 */
	public static void listComputersHandler(ComputerDao dao,int nbComputerToPrintByLine) {
		try {
			List<Computer> computers=dao.findAll();
			ListIterator<Computer> listIterator=computers.listIterator();
			if(computers.isEmpty())System.out.println(" Aucun ordinateur disponible");
			else {
				int i=0;
				while(listIterator.hasNext()) {
					if((i++)%nbComputerToPrintByLine<nbComputerToPrintByLine-1) {
						System.out.printf("%-10s ",listIterator.next());
					}else System.out.printf("%-10s\n",listIterator.next());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Computer getComputerParametters() {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Veuillez renseignez les informations suivantes :");
		System.out.print("nom >");
		String name=scanner.next();
		System.out.print("date de creation >");
		scanner.useDelimiter(Pattern.compile("/"));
		String day=scanner.next();
		String month=scanner.next();
		String year=scanner.next();
		System.out.println("day => "+day);
		System.out.println("month => "+month);
		System.out.println("year => "+year);
		
		return null;
	}
}
