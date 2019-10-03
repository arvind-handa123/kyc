package co.yabx.kyc.app.fullKyc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.LiabilityType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiabilitiesDetailsDTO implements Serializable {

	private Long id;

	private String retailerId;

	private List<LiabilitiesDetailsDTO> liabilities;

	private double loanAmount;

	private String nameOfTheOrganization;

	private String liabilityFrom;

	private LiabilityType typeOfLiablity;

	private Date createdAt;

	private Date updatedAt;

	private String createdBy;

	private String updatedBy;

	private UserDTO user;

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
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

	public String getNameOfTheOrganization() {
		return nameOfTheOrganization;
	}

	public void setNameOfTheOrganization(String nameOfTheOrganization) {
		this.nameOfTheOrganization = nameOfTheOrganization;
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

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public List<LiabilitiesDetailsDTO> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<LiabilitiesDetailsDTO> liabilities) {
		this.liabilities = liabilities;
	}

}
