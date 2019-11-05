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

	@Column(name = "other_organisation")
	private String liabilityFromOtherOrganization;

	@Column(name = "loan_amount_from_other_organisation")
	private double loanAmountFromOtherOrganization;

	@Column(name = "liability_type")
	private LiabilityType liabilityType;

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

	public String getLiabilityFromOtherOrganization() {
		return liabilityFromOtherOrganization;
	}

	public void setLiabilityFromOtherOrganization(String liabilityFromOtherOrganization) {
		this.liabilityFromOtherOrganization = liabilityFromOtherOrganization;
	}

	public double getLoanAmountFromOtherOrganization() {
		return loanAmountFromOtherOrganization;
	}

	public void setLoanAmountFromOtherOrganization(double loanAmountFromOtherOrganization) {
		this.loanAmountFromOtherOrganization = loanAmountFromOtherOrganization;
	}

	public LiabilityType getLiabilityType() {
		return liabilityType;
	}

	public void setLiabilityType(LiabilityType liabilityType) {
		this.liabilityType = liabilityType;
	}

	@Override
	public String toString() {
		return "LiabilitiesDetails [id=" + id + ", loanAmount=" + loanAmount + ", bankOrNbfiName=" + bankOrNbfiName
				+ ", liabilityFromOtherOrganization=" + liabilityFromOtherOrganization
				+ ", loanAmountFromOtherOrganization=" + loanAmountFromOtherOrganization + ", liabilityType="
				+ liabilityType + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankOrNbfiName == null) ? 0 : bankOrNbfiName.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((liabilityFromOtherOrganization == null) ? 0 : liabilityFromOtherOrganization.hashCode());
		result = prime * result + ((liabilityType == null) ? 0 : liabilityType.hashCode());
		long temp;
		temp = Double.doubleToLongBits(loanAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(loanAmountFromOtherOrganization);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiabilitiesDetails other = (LiabilitiesDetails) obj;
		if (bankOrNbfiName == null) {
			if (other.bankOrNbfiName != null)
				return false;
		} else if (!bankOrNbfiName.equals(other.bankOrNbfiName))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (liabilityFromOtherOrganization == null) {
			if (other.liabilityFromOtherOrganization != null)
				return false;
		} else if (!liabilityFromOtherOrganization.equals(other.liabilityFromOtherOrganization))
			return false;
		if (liabilityType != other.liabilityType)
			return false;
		if (Double.doubleToLongBits(loanAmount) != Double.doubleToLongBits(other.loanAmount))
			return false;
		if (Double.doubleToLongBits(loanAmountFromOtherOrganization) != Double
				.doubleToLongBits(other.loanAmountFromOtherOrganization))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
