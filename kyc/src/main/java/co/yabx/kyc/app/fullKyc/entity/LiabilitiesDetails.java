package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.LiabilityType;

@Entity
@Table(name = "liabilities_details", indexes = { @Index(name = "bank_nbfi_name", columnList = "bank_nbfi_name") })
public class LiabilitiesDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "loan_amount")
	private double loanAmount;

	@Column(name = "bank_nbfi_name")
	private String bankOrNbfiName;

	@Column(name = "liability_from")
	private String liabilityFrom;

	@Column(name = "liability_type")
	private LiabilityType typeOfLiablity;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getBankOrNbfiName() {
		return bankOrNbfiName;
	}

	public void setBankOrNbfiName(String bankOrNbfiName) {
		this.bankOrNbfiName = bankOrNbfiName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@PrePersist
	private void prePersist() {
		if (createdAt == null) {
			createdAt = new Date();
			updatedAt = new Date();
		}
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

	public String getLiabilityFrom() {
		return liabilityFrom;
	}

	public void setLiabilityFrom(String liabilityFrom) {
		this.liabilityFrom = liabilityFrom;
	}

	public LiabilityType getTypeOfLiablity() {
		return typeOfLiablity;
	}

	public void setTypeOfLiablity(LiabilityType typeOfLiablity) {
		this.typeOfLiablity = typeOfLiablity;
	}

}
