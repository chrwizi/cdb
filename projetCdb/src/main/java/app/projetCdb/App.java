package app.projetCdb;
/**
 * cdbProject 
 */

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Scanner;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;
import app.projetCdb.persistance.CompanyDao;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.services.ICompanyServices;
import app.projetCdb.services.IComputerService;
import app.projetCdb.services.CompanyService;
import app.projetCdb.services.ComputerServices;

public class App {
	/* Menu selections */
	private final static int LIST_COMPUTERS = 1;
	private final static int LIST_COMPANIES = 2;
	private final static int CREATE_COMPUTER = 3;
	private final static int UPDATE_COMPUTER = 4;
	private final static int DELETE_COMPUTER = 5;
	private final static int SHOW_COMPUTER_DETAILS = 6;
	private final static int EXIT = 7;

	public static void main(String[] args) {
		// objects to interact with database
		CompanyDao companyDao = new CompanyDao(DbAccess.getInstance());
		ComputerDao computerDao = new ComputerDao(DbAccess.getInstance());
		// show user interface
		showCdbUi(companyDao, computerDao);

	}

	/**
	 * 
	 * @param companyDao
	 * @param computerDao
	 */
	private static void showCdbUi(CompanyDao companyDao, ComputerDao computerDao) {
		boolean fin = false;
		int userChoice = 0;
		Scanner scanner = new Scanner(System.in);
		while (!fin) {
			// show user selection menu
			showMenu();
			userChoice = scanner.nextInt();
			while (userChoice < 1 || userChoice > 7) {
				System.out.print("précisez votre choix >");
				userChoice = scanner.nextInt();
			}
			System.out.println(" votre choix est : " + userChoice);
			switch (userChoice) {
			case LIST_COMPUTERS:
				listComputersHandler(computerDao);
				break;
			case LIST_COMPANIES:
				listCompaniesHandler(companyDao);
				break;
			case CREATE_COMPUTER:
				createComputerHandler(companyDao, computerDao);
				break;
			case UPDATE_COMPUTER:
				// Uncompleted feature
				System.out.println("fonctionnalité non implémenté totalement");
				break;
			case DELETE_COMPUTER:
				deleteComputerHandler(computerDao);
				break;
			case SHOW_COMPUTER_DETAILS:
				showComputerDetailsHandler(computerDao);
				break;
			case EXIT:
				fin = true;
				break;
			default:

				break;
			}
		}
		scanner.close();
	}

	/**
	 * show the selection menu in console
	 */
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

	/**
	 * show details of a selected computer on console
	 * 
	 * @param computerDao
	 */
	private static void showComputerDetailsHandler(ComputerDao computerDao) {
		IComputerService listComputersService = new ComputerServices(computerDao);
		List<Computer> computers;
		try {
			// get list of computers
			computers = listComputersService.getAll();
			System.out.println("Veuillez choisir le numéro de l'ordinateur ");
			printComputerList(computers, true);

			Scanner scanner = new Scanner(System.in);
			System.out.println("choisir un numéro entre 0 et " + (computers.size() - 1) + " >");
			int index = scanner.nextInt();

			Computer selectedComputer = computers.get(index);

			System.out.println("**** détailles ****");
			System.out.println(selectedComputer);
			System.out.println(
					"[nom: " + selectedComputer.getName() + " date de creation: " + selectedComputer.getIntroduced()
							+ " date de retrait: " + selectedComputer.getDiscontinued() + "]");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * print list of all companies in database
	 * 
	 * @param dao: object given access to companies table in database
	 */
	public static void listCompaniesHandler(CompanyDao dao) {
		ICompanyServices lisctCompaniesService = new CompanyService(dao);
		Optional<List<Company>> companies;
		companies = lisctCompaniesService.getAll();
		printCompaniesWithIndex(companies.get(), false);

	}

	/**
	 * print list of all computers in database
	 * 
	 * @param dao : object given access to computers table in database
	 */
	public static void listComputersHandler(ComputerDao dao) {
		IComputerService listComputersService = new ComputerServices(dao);
		List<Computer> computers;
		try {
			computers = listComputersService.getAll();
			printComputerList(computers, false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * print computers in console
	 * 
	 * @param withIndex indicates if the position of each computer must be
	 *                  concatenate with his string representation
	 * @param computers list of computers
	 */
	private static void printComputerList(List<Computer> computers, boolean withIndex) {
		ListIterator<Computer> listIterator = computers.listIterator();
		int index = 0;
		while (listIterator.hasNext()) {
			if (withIndex)
				System.out.println("[" + (index++) + "]" + " : " + listIterator.next());
			else
				System.out.println(listIterator.next());
		}
	}

	/**
	 * print companies in console
	 * 
	 * @param companies
	 * @param withIndex indicates if the position of each company must be
	 *                  concatenate with his string representation
	 */
	private static void printCompaniesWithIndex(List<Company> companies, boolean withIndex) {
		ListIterator<Company> listIterator = companies.listIterator();
		int index = 0;
		while (listIterator.hasNext()) {
			if (withIndex)
				System.out.println("[" + (index++) + "]" + " : " + listIterator.next());
			else
				System.out.println(listIterator.next());
		}

	}

	/**
	 * Add a new computer in database
	 * 
	 * @param companyDao
	 * @throws ParseException
	 */
	public static void createComputerHandler(CompanyDao companyDao, ComputerDao computerDao) {
		ICompanyServices listCompaniesService = new CompanyService(companyDao);
		IComputerService computerService = new ComputerServices(computerDao);
		Scanner scanner = new Scanner(System.in);
		Optional<List<Company>> companies;
		try {
			// get computer informations
			System.out.println("Veuillez renseignez les informations suivantes :");
			System.out.print("nom >");
			String name = scanner.nextLine();
			// get introduced date
			System.out.print("date de creation au format yyyy-mm-dd >");
			String strDate = scanner.next().concat(" 0:0:0.0");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Timestamp introducedTimestamp = CdbUtil.strDateToTimestamp(strDate, dateFormat);
			System.out.print("date de Production au format yyyy-mm-dd >");
			// get discontuned date
			strDate = scanner.next().concat(" 0:0:0.0");
			Timestamp discontunedTimestamp = CdbUtil.strDateToTimestamp(strDate, dateFormat);
			// get company of computer
			System.out.println("veuillez indiquer le numéro de la compagnie");
			companies = listCompaniesService.getAll();
			printCompaniesWithIndex(companies.get(), true);
			System.out.println("compagnie entre 0 et " + (companies.get().size() - 1) + " >");
			int index = scanner.nextInt();
			// creation of new computer
			Computer computer = new Computer(0L, name, introducedTimestamp, discontunedTimestamp,
					new Company(companies.get().get(index).getId(), companies.get().get(index).getName()));
			// add computer in database
			computerService.createComputer(computer);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IDCompanyNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("la date n'est pas ne correspond pas au format requis ");
			e.printStackTrace();
		}
	}

	/**
	 * delete a computer from database
	 * 
	 * @param computerDao
	 */
	public static void deleteComputerHandler(ComputerDao computerDao) {
		IComputerService listComputersService = new ComputerServices(computerDao);
		IComputerService deleteComputerService = new ComputerServices(computerDao);
		List<Computer> computers;
		try {
			// get list of computers in database
			computers = listComputersService.getAll();
			System.out.println("Veuillez sélectionner le numéro de l'ordinateur à supprimer");
			// show user list of computers with index for each computer
			printComputerList(computers, true);
			System.out.println("Numéro compris entre 0 et " + (computers.size() - 1) + " >");
			// get the position of selected computer
			Scanner scanner = new Scanner(System.in);
			int index = scanner.nextInt();
			// delete selected computer from database
			deleteComputerService.delete(computers.get(index).getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
