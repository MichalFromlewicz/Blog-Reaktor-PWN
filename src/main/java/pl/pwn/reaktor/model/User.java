package pl.pwn.reaktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty(message="{pl.reaktor.notEmpty}")
	@Email(message="{pl.reaktor.email}")
	@Column(unique=true)
	private String mail;
	
	@Length(min=5, message="{pl.reaktor.password.lenght}")
	private String password;
	
	@NotEmpty(message="{pl.reaktor.notEmpty}")
	private String name;
	
	@NotEmpty(message="{pl.reaktor.notEmpty}")
	private String lastName;
	
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User(long id, String mail, String password, String name, String lastName, boolean active, Role role) {
		super();
		this.id = id;
		this.mail = mail;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.active = active;
		this.role = role;
	}

	public User() {
		super();
	}

	public User(String mail, String password, String name, String lastName, boolean active, Role role) {
		super();
		this.mail = mail;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.active = active;
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", mail=" + mail + ", password=" + password + ", name=" + name + ", lastName="
				+ lastName + ", active=" + active + ", role=" + role + "]";
	}
	
	

}
