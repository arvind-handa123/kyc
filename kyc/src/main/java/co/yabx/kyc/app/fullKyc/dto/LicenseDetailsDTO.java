package co.yabx.kyc.app.fullKyc.dto;

import java.io.Serializable;
import java.util.Date;

import co.yabx.kyc.app.enums.LicenseType;

public class LicenseDetailsDTO implements Serializable {

	private Long id;

	private String licenseNumber;

	private Long licenseExpiryDate;

	private String licenseIssuingAuthority;

	private LicenseType licenseType;

	private Date createdAt;

	private Date updatedAt;

	private String createdBy;

	private String updatedBy;

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

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Long getLicenseExpiryDate() {
		return licenseExpiryDate;
	}

	public void setLicenseExpiryDate(Long licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}

	public String getLicenseIssuingAuthority() {
		return licenseIssuingAuthority;
	}

	public void setLicenseIssuingAuthority(String licenseIssuingAuthority) {
		this.licenseIssuingAuthority = licenseIssuingAuthority;
	}

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType licenseType) {
		this.licenseType = licenseType;
	}

}