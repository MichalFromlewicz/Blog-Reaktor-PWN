package pl.pwn.reaktor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hamcrest.collection.IsEmptyCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.IfProfileValue;

import com.mysql.jdbc.util.ResultSetUtil;

import pl.pwn.reaktor.model.Role;
import pl.pwn.reaktor.model.User;
import pl.pwn.reaktor.model.UserFilter;
import pl.pwn.reaktor.repository.RoleRepository;
import pl.pwn.reaktor.repository.UserRepsitory;

@Service
public class UserService {
	
	private UserRepsitory userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private RoleRepository roleRepository;
	
	private EntityManager entityManager;
	
	@Autowired
	public UserService(UserRepsitory userRepsitory, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, EntityManager entityManager) {
		this.userRepository = userRepsitory;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.roleRepository = roleRepository;
		this.entityManager = entityManager;
		
	}
	
	public User addUserWithRoleUser(User user) {
		
		user.setActive(true);
		
		Role role = getUserRole();
		
		user.setRole(role);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}

	private Role getUserRole() {
		Role role = roleRepository.findByRole("User");
		if(Objects.isNull(role)) {
			role = roleRepository.save(new Role("User"));
		}
		return role;
	}
	
	
	public Page<User> getAll(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	
	public User getUserById(long id) {
		return userRepository.findOne(id);
	}
	
	public User editUser(User user) {
		User userById = getUserById(user.getId());
		user.setPassword(userById.getPassword());
		return userRepository.save(user);
	}
	
	public User changePassword(User user) {
		User userById = getUserById(user.getId());
		userById.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(userById);
	}
	
	public void delete (long id) {
		userRepository.delete(id);
	}

	public Page<User> getByFilter(UserFilter userFilter, Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> user =query.from(User.class);
		
		List<Predicate> criteria = new ArrayList<Predicate>();
		Optional.ofNullable(userFilter.getName())
			.filter(name -> !name.isEmpty())
			.ifPresent(name -> criteria.add(criteriaBuilder.like(user.get("name"), "%" + name + "%")));
		
		if(userFilter.getLastName()!=null && (!userFilter.getLastName().isEmpty())) {
			criteria.add(criteriaBuilder.like(user.get("lastName"), "%" + userFilter.getLastName()+ "%"));
			
		}
		
		if(Objects.nonNull(userFilter.getEmail()) && !userFilter.getEmail().isEmpty()){
			criteria.add(criteriaBuilder.like(user.get("mail"), "%" + userFilter.getEmail()+"%"));
		}
		
		CriteriaQuery<User> select = query.select(user).where(criteria.toArray(new Predicate[criteria.size()]));
		
		TypedQuery<User> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(pageable.getPageNumber());
		typedQuery.setMaxResults(pageable.getPageSize());
		
		List<User> resultUsers = typedQuery.getResultList();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(User.class)))
			.where(criteria.toArray(new Predicate[criteria.size()]));
		
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		
		Page<User> page = new PageImpl<>(resultUsers, pageable, total);
		return page;
	}
}
