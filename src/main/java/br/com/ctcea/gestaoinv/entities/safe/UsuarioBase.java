package br.com.ctcea.gestaoinv.entities.safe;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fdn_user")
public class UsuarioBase implements Serializable {
	private static final long serialVersionUID = 9000690044485296813L;

	@Id
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "FULL_NAME")
	private String fullName;
	
	public UsuarioBase() {
	}

	public UsuarioBase(String firstName, String lastName, String fullName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioBase other = (UsuarioBase) obj;
		return Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "UsuarioBase [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName="
				+ fullName + "]";
	}
}