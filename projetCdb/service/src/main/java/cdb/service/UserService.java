package cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdb.core.models.Role;
import cdb.core.models.User;
import cdb.persistence.dao.RoleDao;
import cdb.persistence.dao.UserDao;
import cdb.security.service.BCryptManagerUtil;

@Service
public class UserService {
	private UserDao userDao;
	private RoleDao roleDao;

	public UserService(UserDao userDao, RoleDao roleDao) {
		this.userDao = userDao;
		this.roleDao = roleDao;
	}

	@Transactional
	public void createUser(String username, String password, Role role) {
		User user = new User(username,BCryptManagerUtil.passwordencoder().encode(password), role);
		userDao.createUser(user);
	}

	public Role findRoleById(Long idRole) {
		Optional<Role> optionalRole=roleDao.findById(idRole);
		return !optionalRole.isPresent()?null:optionalRole.get();
	}

	public List<Role> findRoles() {
		return roleDao.findAll();
	}
}
