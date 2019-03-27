package cdb.persistence.dao;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import cdb.core.models.User;

@Repository
public class UserDao {
	private LocalSessionFactoryBean sessionFactory;
	private CriteriaBuilder criteriaBuilder;
	private Root<User> root;
	//
	private final String FIELD_USER_NAME="username";
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	public UserDao(LocalSessionFactoryBean sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public Optional<User> findByUsername(String username) {
		Optional<User> optionalUser = Optional.empty();
		if (username == null) {
			return optionalUser;
		}
		
		try (Session session = sessionFactory.getObject().openSession()) {
			criteriaBuilder=session.getCriteriaBuilder();
			CriteriaQuery<User>findCriteria=criteriaBuilder.createQuery(User.class);
			root=findCriteria.from(User.class);
			findCriteria.select(root).where(criteriaBuilder.equal(root.get(FIELD_USER_NAME), username));
			optionalUser=session.createQuery(findCriteria).uniqueResultOptional();
		} 		
		catch (HibernateException e) {
			logger.debug("Erreur sur find user by username: " + e.getMessage());
		}
		
		return optionalUser;
	}
	
	public void createUser(User user) {
		Long id=-1L;
		try(Session session=sessionFactory.getObject().openSession()){
			Transaction transaction=session.beginTransaction();
			session.persist(user);
			transaction.commit();
			
		}
		catch (HibernateException e) {
			logger.debug("Erreur sur create User : "+e.getMessage());
		}
	}
	
}
