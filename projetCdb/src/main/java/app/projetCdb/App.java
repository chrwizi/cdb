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
				try {
					deleteComputerHandler(computerDao);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case SHOW_COMPUTER_DETAILS:
				try {
					showComputerDetailsHandler(computerDao);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case EXIT:

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
		System.out.println("6- Exit"); 
		System.out.println("Votre choix :"); 
	}

	private static void showComputerDetailsHandler(ComputerDao computerDao) throws SQLException {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Veuillez choisir le numéro de l'ordinateur ");
		List<Computer> computers=computerDao.findAll();
		ListIterator<Computer> listIterator=computers.listIterator();
		Computer current;int index=0;
		while(listIterator.hasNext()) {
			current=listIterator.next();
			System.out.println((index++)+":"+current);
		}
		System.out.println("compagnie entre 0 et "+(computers.size()-1)+" >");
		index=scanner.nextInt();
		current=computers.get(index-1);
		System.out.println("**** detailles ****");
		System.out.println("[nom: "+current.getName()
				+" date de creation: "+current.getIntroduced().toString()
				+" date de retrait: "+current.getDiscontinued().toString()+"]");
		scanner.close();
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
		scanner.close();
		return computer;
	}
	
	public static void deleteComputerHandler(ComputerDao computerDao) throws SQLException {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Veuillez sélectionner le numéro de l'ordinateur à supprimer");
		List<Computer> computers=computerDao.findAll();
		ListIterator<Computer> listIterator=computers.listIterator();
		Computer current;int index=0;
		while(listIterator.hasNext()) {
			current=listIterator.next();
			System.out.println((index++)+":"+current);
		}
		System.out.println("compagnie entre 0 et "+(computers.size()-1)+" >");
		index=scanner.nextInt();
		computerDao.delete(computers.get(index).getId());
		scanner.close();
	}
	
}
