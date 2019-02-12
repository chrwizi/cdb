package app.projetCdb;

import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;
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
	/*Menu selections */
	private final static int LIST_COMPUTERS=1;
	private final static int LIST_COMPANIES=2;
	private final static int CREATE_COMPUTER=3;
	private final static int UPDATE_COMPUTER=4;
	private final static int DELETE_COMPUTER=5;
	private final static int SHOW_COMPUTER_DETAILS=6;
	private final static int EXIT=7;

	public static void main( String[] args ){
		try {
			DbAccess.loadDriver();
			Company company=new Company("Keolys");
			CompanyDao companyDao=new CompanyDao(DbAccess.getInstance());
			companyDao.delete((long) 1001);
			showCdbUi(companyDao);

		} catch (SQLException | ClassNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void showCdbUi(CompanyDao companyDao) {
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

				break;
			case LIST_COMPANIES:
				listCompaniesHandler(companyDao);
				break;

			case CREATE_COMPUTER:

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

	public static void listCompaniesHandler(CompanyDao dao) {
		try {
			List<Company> companies=dao.findAll();
			ListIterator<Company> iterator=companies.listIterator();
			int nbCompniesToPrintByLine=5,i=0;
			if(companies.isEmpty())System.out.println("Aucune company disponible");
			else {
				while(iterator.hasNext()) {
					if((i++)%nbCompniesToPrintByLine<nbCompniesToPrintByLine-1) {
						System.out.print(iterator.next()+"\t");
					}
					else {
						System.out.println(iterator.next());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
