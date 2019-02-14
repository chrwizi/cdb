package app.projetCdb;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import app.projetCdb.dao.CompanyDao;
import app.projetCdb.dao.ComputerDao;
import app.projetCdb.dao.DbAccess;
import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;
import app.projetCdb.services.DeleteComputerService;
import app.projetCdb.services.IDeleteService;
import app.projetCdb.services.IListCompaniesService;
import app.projetCdb.services.IListComputersService;
import app.projetCdb.services.ListCompaniesService;
import app.projetCdb.services.ListComputersService;



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
			showMenu();
			userChoice=scanner.nextInt();
			while (userChoice<1 || userChoice>7) { 
				System.out.print("précisez votre choix >");
				userChoice=scanner.nextInt();
			}
			System.out.println(" votre choix est : "+userChoice);
			switch(userChoice) {
			case LIST_COMPUTERS:
				listComputersHandler(computerDao);
				break;
			case LIST_COMPANIES:
				listCompaniesHandler(companyDao);
				break;
			case CREATE_COMPUTER:
				try {
					Computer computer=getComputerParamettersFromUser(companyDao);
					computerDao.add(computer);
				} 
				catch (ParseException e) {
					e.printStackTrace();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				} catch (IDCompanyNotFoundException e) {
					e.printStackTrace();
				}
				break;

			case UPDATE_COMPUTER:

				break;
			case DELETE_COMPUTER:
					deleteComputerHandler(computerDao);
				break;
			case SHOW_COMPUTER_DETAILS:
					showComputerDetailsHandler(computerDao);
				break;
			case EXIT:
				fin=false;
				break;
			default:

				break;
			}
		}
		scanner.close();
	}



	private static void showMenu() {
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
		System.out.println("6- show computer details"); 
		System.out.println("7- Exit"); 
		System.out.println("Votre choix :"); 
	}

	private static void showComputerDetailsHandler(ComputerDao computerDao){
		IListComputersService listComputersService=new ListComputersService(computerDao);
		List<Computer> computers;
		try {
			//get list of computers
			computers = listComputersService.getAll();
			
			System.out.println("Veuillez choisir le numéro de l'ordinateur ");
			printComputerList(computers, true);
			
			Scanner scanner=new Scanner(System.in);
			System.out.println("choisir un numéro entre 0 et "+(computers.size()-1)+" >");
			int index=scanner.nextInt();
			
			Computer selectedComputer=computers.get(index); 
			
			System.out.println("**** detailles ****");
			System.out.println(selectedComputer);
			System.out.println("[nom: "+selectedComputer.getName()
			+" date de creation: "+selectedComputer.getIntroduced()
			+" date de retrait: "+selectedComputer.getDiscontinued()+"]");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * print list of all companies  in database
	 * @param dao: object given access to companies table in database  
	 */
	public static void listCompaniesHandler(CompanyDao dao) {
		IListCompaniesService lisctCompaniesService = new ListCompaniesService(dao);
		List<Company> companies;
		try {
			companies = lisctCompaniesService.getAll();
			printCompanies(companies);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void printCompanies(List<Company> companies) {
		ListIterator<Company> listIterator=companies.listIterator();
		while(listIterator.hasNext()) {
			System.out.println(listIterator.next());
		}
		
	}

	/**
	 * print list of all computers in database 
	 * @param dao : object given access to computers table in database
	 */
	public static void listComputersHandler(ComputerDao dao) {
		IListComputersService listComputersService=new ListComputersService(dao);
		List<Computer> computers;
		try {
			computers = listComputersService.getAll();
			printComputerList(computers,false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param computers
	 */
	private static void printComputerList(List<Computer> computers,boolean withIndex) {
		ListIterator<Computer> listIterator=computers.listIterator();
		int index=0;
		while(listIterator.hasNext()) {
			if(withIndex)System.out.println("["+(index++)+"]"+" : "+listIterator.next());
			else System.out.println(listIterator.next());
		}
	}

	/**
	 * 
	 * @param companyDao
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static Computer getComputerParamettersFromUser(CompanyDao companyDao) throws ParseException, SQLException {
		//TODO Refactoring
		Scanner scanner=new Scanner(System.in);
		System.out.println("Veuillez renseignez les informations suivantes :");
		System.out.print("nom >");
		String name=scanner.nextLine();
		System.out.print("date de creation au format yyyy-mm-dd >");
		String strDate=scanner.next().concat(" 0:0:0.0");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Timestamp introducedTimestamp=CdbUtil.strDateToTimestamp(strDate, dateFormat);
		System.out.print("date de Production au format yyyy-mm-dd >");
		strDate=scanner.next().concat(" 0:0:0.0");
		Timestamp discontunedTimestamp=CdbUtil.strDateToTimestamp(strDate, dateFormat);
		System.out.println("veuillez indiquer le numéro de la compagnie");
		List<Company> companies=companyDao.findAll();
		ListIterator<Company> listIterator=companies.listIterator();
		Company current;int index=0;
		while(listIterator.hasNext()) {
			current=listIterator.next();
			System.out.println((index++)+":"+current);
		}
		System.out.println("compagnie entre 0 et "+(companies.size()-1)+" >");
		index=scanner.nextInt();
		Computer computer=new Computer(0L, name, introducedTimestamp, discontunedTimestamp,companies.get(index).getId());
		return computer;
	}

	/**
	 * 
	 * @param computerDao
	 */
	public static void deleteComputerHandler(ComputerDao computerDao){
		IListComputersService listComputersService=new ListComputersService(computerDao);
		IDeleteService deleteComputerService= new DeleteComputerService(computerDao);
		List<Computer> computers;
		try {
			//get list of computers in database
			computers = listComputersService.getAll();
			System.out.println("Veuillez sélectionner le numéro de l'ordinateur à supprimer");
			//shwo user list of computers with index for each computer
			printComputerList(computers, true);
			System.out.println("Numéro compris entre 0 et "+(computers.size()-1)+" >");
			//get the position of selected computer
			Scanner scanner=new Scanner(System.in);
			int index=scanner.nextInt();
			//delete selected  computer from database
			deleteComputerService.delete(computers.get(index).getId());
			//scanner.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
