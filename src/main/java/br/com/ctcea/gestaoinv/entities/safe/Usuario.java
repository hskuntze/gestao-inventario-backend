package br.com.ctcea.gestaoinv.entities.safe;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.ctcea.gestaoinv.components.UsuarioListener;

@Entity
@EntityListeners(UsuarioListener.class)
@Table(name = "fdn_usertenant")
public class Usuario implements Serializable, UserDetails {
	private static final long serialVersionUID = 6700599488702394076L;
	
	@Id
	@Column(name = "USER_TENANT_ID")
	private Long userTenantId;
	private String email;
	@Column(name = "FIRST_ACCESS")
	private Integer firstAccess;
	@Column(name = "idp_id")
	private String idpId;
	@Column(name = "LAST_UPDATE_DATE")
	private LocalDateTime lastUpdateDate;
	@Column(name = "LOCATION_ID")
	private Integer locationId;
	private String login;
	private String password;
	@Column(name = "TENANT_ID")
	private Integer tenantId;
	@Column(name = "USER_CODE")
	private String userCode;
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "USER_STATE")
	private Integer userState;
	@Column(name = "USER_UUID")
	private String userUuid;
	@Column(name = "ANONYMIZATION_USER_ID")
	private Integer anonymizationUserId;
	@Column(name = "ANONYMIZATION_DATE")
	private LocalDateTime anonymizationDate;
	@Column(name = "LAST_SECURITY_UPDATE_DATE")
	private LocalDateTime lastSecurityUpdateDate;
	
	@Transient
	private List<String> associacoes = new ArrayList<>();
	
	public Usuario() {
	}

	public Long getUserTenantId() {
		return userTenantId;
	}

	public void setUserTenantId(Long userTenantId) {
		this.userTenantId = userTenantId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFirstAccess() {
		return firstAccess;
	}

	public void setFirstAccess(Integer firstAccess) {
		this.firstAccess = firstAccess;
	}

	public String getIdpId() {
		return idpId;
	}

	public void setIdpId(String idpId) {
		this.idpId = idpId;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public Integer getAnonymizationUserId() {
		return anonymizationUserId;
	}

	public void setAnonymizationUserId(Integer anonymizationUserId) {
		this.anonymizationUserId = anonymizationUserId;
	}

	public LocalDateTime getAnonymizationDate() {
		return anonymizationDate;
	}

	public void setAnonymizationDate(LocalDateTime anonymizationDate) {
		this.anonymizationDate = anonymizationDate;
	}

	public LocalDateTime getLastSecurityUpdateDate() {
		return lastSecurityUpdateDate;
	}

	public void setLastSecurityUpdateDate(LocalDateTime lastSecurityUpdateDate) {
		this.lastSecurityUpdateDate = lastSecurityUpdateDate;
	}

	public List<String> getAssociacoes() {
		return associacoes;
	}

	public void setAssociacoes(List<String> associacoes) {
		this.associacoes = associacoes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return associacoes.stream()
                .map(g -> new SimpleGrantedAuthority(g))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}