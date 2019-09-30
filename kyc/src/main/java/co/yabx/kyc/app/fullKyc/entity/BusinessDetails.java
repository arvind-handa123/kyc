package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "business_details", indexes = { @Index(name = "business_type", columnList = "business_type"),
		@Index(name = "business_name", columnList = "business_name"),
		@Index(name = "license_details", columnList = "license_details") })
public class BusinessDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "business_phone_number")
	private String businessPhone;

	@Column(name = "business_name")
	private String businessName;

	/**
	 * NAME OF THE PROPRIETOR/PARTNERS/MD/DIRECTOR
	 */
	@Column(name = "partner_or_director_name")
	private String directorOrPartnerName;

	@Column(name = "facility_details")
	private String facilityDetails;

	@Column(name = "facility_type")
	private String facilityType;

	@Column(name = "fixed_asset_purchase")
	private String fixedAssetPurchase;

	@Column(name = "fixed_asset_name")
	private String fixedAssetName;

	@Column(name = "price")
	private double price;

	@Column(name = "origin")
	private String origin;

	@Column(name = "proposed_collateral")
	private String proposedCollateral;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "sector")
	private String sector;

	@Column(name = "detail_of_busness")
	private String detailOfBusness;

	@Column(name = "initial_capital")
	private double initialCapital;

	@Column(name = "fund_source")
	private String fundSource;

	@Column(name = "vat_registration_number")
	private String vatRegistrationNumber;

	@Column(name = "business_start_date")
	private Long businessStartDate;

	@Column(name = "business_tin")
	private String businessTin;

	@Column(name = "annual_sales")
	private double annualSales;

	@Column(name = "annual_gross_profit")
	private double annualGrossProfit;

	@Column(name = "annual_expenses")
	private double annualExpenses;

	@Column(name = "fixed_assets_value")
	private double valueOfFixedAssets;

	@Column(name = "employees")
	private short numberOfEmployees;

	@Column(name = "stock_value")
	private double stockValue;

	@Column(name = "monthly_turn_over")
	private double monthlyTurnOver;

	@Column(name = "deposits")
	private double deposits;

	@Column(name = "withdrawls")
	private double withdrawls;

	@Column(name = "initial_deposits")
	private double initialDeposit;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "license_details", referencedColumnName = "id")
	private LicenseDetails licenseDetails;

	@OneToMany(mappedBy = "businessDetails", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AddressDetails> addressDetails;

	@OneToMany(mappedBy = "businessDetails", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BankAccountDetails> bankAccountDetails;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getDirectorOrPartnerName() {
		return directorOrPartnerName;
	}

	public void setDirectorOrPartnerName(String directorOrPartnerName) {
		this.directorOrPartnerName = directorOrPartnerName;
	}

	public String getFacilityDetails() {
		return facilityDetails;
	}

	public void setFacilityDetails(String facilityDetails) {
		this.facilityDetails = facilityDetails;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getFixedAssetPurchase() {
		return fixedAssetPurchase;
	}

	public void setFixedAssetPurchase(String fixedAssetPurchase) {
		this.fixedAssetPurchase = fixedAssetPurchase;
	}

	public String getFixedAssetName() {
		return fixedAssetName;
	}

	public void setFixedAssetName(String fixedAssetName) {
		this.fixedAssetName = fixedAssetName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getProposedCollateral() {
		return proposedCollateral;
	}

	public void setProposedCollateral(String proposedCollateral) {
		this.proposedCollateral = proposedCollateral;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDetailOfBusness() {
		return detailOfBusness;
	}

	public void setDetailOfBusness(String detailOfBusness) {
		this.detailOfBusness = detailOfBusness;
	}

	public double getInitialCapital() {
		return initialCapital;
	}

	public void setInitialCapital(double initialCapital) {
		this.initialCapital = initialCapital;
	}

	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getVatRegistrationNumber() {
		return vatRegistrationNumber;
	}

	public void setVatRegistrationNumber(String vatRegistrationNumber) {
		this.vatRegistrationNumber = vatRegistrationNumber;
	}

	public Long getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(Long businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	public String getBusinessTin() {
		return businessTin;
	}

	public void setBusinessTin(String businessTin) {
		this.businessTin = businessTin;
	}

	public double getAnnualSales() {
		return annualSales;
	}

	public void setAnnualSales(double annualSales) {
		this.annualSales = annualSales;
	}

	public double getAnnualGrossProfit() {
		return annualGrossProfit;
	}

	public void setAnnualGrossProfit(double annualGrossProfit) {
		this.annualGrossProfit = annualGrossProfit;
	}

	public double getAnnualExpenses() {
		return annualExpenses;
	}

	public void setAnnualExpenses(double annualExpenses) {
		this.annualExpenses = annualExpenses;
	}

	public double getValueOfFixedAssets() {
		return valueOfFixedAssets;
	}

	public void setValueOfFixedAssets(double valueOfFixedAssets) {
		this.valueOfFixedAssets = valueOfFixedAssets;
	}

	public short getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(short numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public double getStockValue() {
		return stockValue;
	}

	public void setStockValue(double stockValue) {
		this.stockValue = stockValue;
	}

	public double getMonthlyTurnOver() {
		return monthlyTurnOver;
	}

	public void setMonthlyTurnOver(double monthlyTurnOver) {
		this.monthlyTurnOver = monthlyTurnOver;
	}

	public double getDeposits() {
		return deposits;
	}

	public void setDeposits(double deposits) {
		this.deposits = deposits;
	}

	public double getWithdrawls() {
		return withdrawls;
	}

	public void setWithdrawls(double withdrawls) {
		this.withdrawls = withdrawls;
	}

	public double getInitialDeposit() {
		return initialDeposit;
	}

	public void setInitialDeposit(double initialDeposit) {
		this.initialDeposit = initialDeposit;
	}

	public LicenseDetails getLicenseDetails() {
		return licenseDetails;
	}

	public void setLicenseDetails(LicenseDetails licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public Set<AddressDetails> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(Set<AddressDetails> addressDetails) {
		this.addressDetails = addressDetails;
	}

	public Set<BankAccountDetails> getBankAccountDetails() {
		return bankAccountDetails;
	}

	public void setBankAccountDetails(Set<BankAccountDetails> bankAccountDetails) {
		this.bankAccountDetails = bankAccountDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

}