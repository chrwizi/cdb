package cdb.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import cdb.core.models.Role;

@Repository
public class RoleDao {
	private LocalSessionFactoryBean sessionFactory;
	private CriteriaBuilder criteriaBuilder;
	private Root<Role> root;
	//
	private final String FIELD_ID="id";
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	public RoleDao(LocalSessionFactoryBean sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Optional<Role>findById(Long id){
		Optional<Role>optionalRole=Optional.empty();
		
		if(id!=null) {
			try(Session session=sessionFactory.getObject().openSession()){
				criteriaBuilder=session.getCriteriaBuilder();
				CriteriaQuery<Role> findCriteria=criteriaBuilder.createQuery(Role.class);
				root=findCriteria.from(Role.class);
				findCriteria.select(root).where(criteriaBuilder.equal(root.get(FIELD_ID), id));
				optionalRole=session.createQuery(findCriteria).uniqueResultOptional();
			}
			catch (HibernateException e) {
				logger.debug("Erreur sur find role by Id "+e.getMessage());
			}
		}
		
		return optionalRole;
	}
	
	public List<Role> findAll(){
		List<Role> roles=new ArrayList<Role>();
		
		try (Session session = sessionFactory.getObject().openSession()) {

			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Role> findAllCriteria = criteriaBuilder.createQuery(Role.class);
			root = findAllCriteria.from(Role.class);
			findAllCriteria.select(root);
			Query<Role> query = session.createQuery(findAllCriteria);
			roles=query.getResultList();
		}
		catch (HibernateException e) {
			logger.debug("Erreur sur find all roles : "+e.getMessage());
		}
		
		return roles;
	}
}
