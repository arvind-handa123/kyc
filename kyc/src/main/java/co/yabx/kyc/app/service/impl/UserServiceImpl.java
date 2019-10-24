package co.yabx.kyc.app.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.PagesDTO;
import co.yabx.kyc.app.dto.SectionsDTO;
import co.yabx.kyc.app.dto.dtoHelper.AppPagesDynamicDtoHeper;
import co.yabx.kyc.app.entities.Pages;
import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.enums.UserStatus;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.UserRelationships;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.fullKyc.repository.RetailersRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRelationshipsRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.PagesRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.AppPagesSectionService;
import co.yabx.kyc.app.service.UserService;
import co.yabx.kyc.app.util.SpringUtil;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private RetailersRepository retailersRepository;

	@Autowired
	private DSRUserRepository dsrUserRepository;

	@Autowired
	private AppPagesSectionService appPagesSectionService;

	@Autowired
	private UserRelationshipsRepository userRelationshipsRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	@Transactional
	public void persistYabxTokenAndSecretKey(User user, String yabxToken, String aPI_SECRET_KEY) {
		/*
		 * if (user != null) { user.setYabxToken(yabxToken);
		 * user.setSecretKey(aPI_SECRET_KEY); userRepository.save(user); }
		 */}

	@Override
	public List<PagesDTO> getUserDetails(User user, String type) {

		List<Pages> appPages = SpringUtil.bean(PagesRepository.class).findByPageType(type);
		if (appPages == null)
			return null;

		// Nominee Personal Info
		User nominee = null;
		Set<AddressDetails> userAddressDetailsSet = null;
		Set<AddressDetails> nomineeAddressDetailsSet = null;
		Set<AddressDetails> businessAddressDetailsSet = new HashSet<AddressDetails>();
		Set<BankAccountDetails> userBankAccountDetailsSet = null;
		Set<BankAccountDetails> nomineeBankAccountDetailsSet = null;
		Set<BankAccountDetails> businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
		/*
		 * Set<LiabilitiesDetails> liabilitiesDetailsSet = null;
		 * Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet = null;
		 * Set<WorkEducationDetails> workEducationDetailsSet = null;
		 * Set<IntroducerDetails> introducerDetailsSet = null;
		 */
		List<PagesDTO> appPagesDTOList = new ArrayList<PagesDTO>();
		if (user != null) {
			List<UserRelationships> userRelationships = userRelationshipsRepository
					.findByMsisdnAndRelationship(user.getMsisdn(), Relationship.NOMINEE);
			nominee = userRelationships != null && !userRelationships.isEmpty() ? userRelationships.get(0).getRelative()
					: null;
			userAddressDetailsSet = user.getAddressDetails();
			nomineeAddressDetailsSet = nominee != null ? nominee.getAddressDetails() : null;
			userBankAccountDetailsSet = user.getBankAccountDetails();
			nomineeBankAccountDetailsSet = nominee != null ? nominee.getBankAccountDetails() : null;
			/*
			 * liabilitiesDetailsSet = user.getLiabilitiesDetails();
			 * monthlyTransactionProfilesSet = user.getMonthlyTransactionProfiles();
			 * workEducationDetailsSet = user.getWorkEducationDetails();
			 * introducerDetailsSet = user.getIntroducerDetails();
			 */
			if (user.getBusinessDetails() != null) {
				user.getBusinessDetails().forEach(f -> {
					businessAddressDetailsSet.addAll(f.getAddressDetails());
				});
				user.getBusinessDetails().forEach(f -> {
					businessBankAccountDetailsSet.addAll(f.getBankAccountDetails());
				});
			}
		}
		for (Pages pages : appPages) {
			appPagesDTOList.add(AppPagesDynamicDtoHeper.prepareAppPagesDto(pages, user, nominee, userAddressDetailsSet,
					nomineeAddressDetailsSet, businessAddressDetailsSet, userBankAccountDetailsSet,
					nomineeBankAccountDetailsSet, businessBankAccountDetailsSet, type));

		}

		return appPagesDTOList;

	}

	@Override
	public DSRUser getDSRByMsisdn(String dsrMsisdn) {
		if (dsrMsisdn != null && !dsrMsisdn.isEmpty())
			return dsrUserRepository.findByMsisdn(dsrMsisdn);
		return null;
	}

	@Override
	public Retailers getRetailerById(Long retailerId) {
		if (retailerId != null) {
			Optional<Retailers> retailer = retailersRepository.findById(retailerId);
			if (retailer.isPresent() && retailer.get().getUserType().equals(UserType.RETAILERS.name()))
				return retailer.get();
		}
		return null;
	}

	@Override
	public User persistOrUpdateUserInfo(PagesDTO appPagesDTO, User dsrUser, User retailer) throws Exception {

		if (appPagesDTO != null && dsrUser != null) {
			Boolean isNew = false;
			Boolean isDsrUser = false;
			User nominees = null;
			Set<AddressDetails> userAddressDetailsSet = null;
			Set<AddressDetails> nomineeAddressDetailsSet = null;
			Set<AddressDetails> businessAddressDetailsSet = new HashSet<AddressDetails>();
			Set<BankAccountDetails> userBankAccountDetailsSet = new HashSet<BankAccountDetails>();
			Set<BankAccountDetails> nomineeBankAccountDetailsSet = null;
			Set<BankAccountDetails> businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
			Set<BusinessDetails> businessDetailsSet = null;
			Set<LiabilitiesDetails> liabilitiesDetailsSet = null;
			Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet = null;
			Set<WorkEducationDetails> workEducationDetailsSet = null;
			Set<IntroducerDetails> introducerDetailsSet = null;
			List<UserRelationships> nomineeRelationship = null;

			if (retailer == null) {
				// It means DSR profile need to be persisted
				retailer = dsrUser;
				isDsrUser = true;
				userAddressDetailsSet = dsrUser.getAddressDetails() != null ? dsrUser.getAddressDetails()
						: new HashSet<AddressDetails>();
				userBankAccountDetailsSet = dsrUser.getBankAccountDetails() != null ? dsrUser.getBankAccountDetails()
						: new HashSet<BankAccountDetails>();
				liabilitiesDetailsSet = dsrUser.getLiabilitiesDetails() != null ? dsrUser.getLiabilitiesDetails()
						: new HashSet<LiabilitiesDetails>();
				userBankAccountDetailsSet = dsrUser.getBankAccountDetails() != null ? dsrUser.getBankAccountDetails()
						: new HashSet<BankAccountDetails>();
				nomineeRelationship = userRelationshipsRepository.findByMsisdnAndRelationship(dsrUser.getMsisdn(),
						Relationship.NOMINEE);
				nominees = nomineeRelationship != null && !nomineeRelationship.isEmpty()
						? nomineeRelationship.get(0).getRelative()
						: null;
				if (nominees != null) {
					nomineeAddressDetailsSet = nominees.getAddressDetails() != null ? nominees.getAddressDetails()
							: new HashSet<AddressDetails>();
					nomineeBankAccountDetailsSet = nominees.getBankAccountDetails() != null
							? nominees.getBankAccountDetails()
							: new HashSet<BankAccountDetails>();
				} else {
					isNew = true;
				}

				businessDetailsSet = dsrUser.getBusinessDetails() != null ? dsrUser.getBusinessDetails()
						: new HashSet<BusinessDetails>();
				if (businessDetailsSet != null && !businessDetailsSet.isEmpty()) {
					for (BusinessDetails businessDetails : businessDetailsSet) {
						businessAddressDetailsSet.addAll(businessDetails.getAddressDetails());
						businessBankAccountDetailsSet.addAll(businessDetails.getBankAccountDetails());
					}
				} else {
					businessDetailsSet = businessDetailsSet == null ? new HashSet<BusinessDetails>()
							: businessDetailsSet;
					businessAddressDetailsSet = new HashSet<AddressDetails>();
					businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
				}
				workEducationDetailsSet = dsrUser.getWorkEducationDetails();

			} else {
				userBankAccountDetailsSet = retailer.getBankAccountDetails() != null ? retailer.getBankAccountDetails()
						: new HashSet<BankAccountDetails>();
				userAddressDetailsSet = retailer.getAddressDetails() != null ? retailer.getAddressDetails()
						: new HashSet<AddressDetails>();
				businessDetailsSet = retailer.getBusinessDetails() != null ? retailer.getBusinessDetails()
						: new HashSet<BusinessDetails>();
				liabilitiesDetailsSet = retailer.getLiabilitiesDetails() != null ? retailer.getLiabilitiesDetails()
						: new HashSet<LiabilitiesDetails>();
				nomineeRelationship = userRelationshipsRepository.findByMsisdnAndRelationship(retailer.getMsisdn(),
						Relationship.NOMINEE);
				nominees = nomineeRelationship != null && !nomineeRelationship.isEmpty()
						? nomineeRelationship.get(0).getRelative()
						: null;
				if (nominees != null) {
					nomineeAddressDetailsSet = nominees.getAddressDetails() != null ? nominees.getAddressDetails()
							: new HashSet<AddressDetails>();
					nomineeBankAccountDetailsSet = nominees.getBankAccountDetails() != null
							? nominees.getBankAccountDetails()
							: new HashSet<BankAccountDetails>();
				} else {
					isNew = true;
					nominees = new Nominees();
				}

				if (businessDetailsSet != null && !businessDetailsSet.isEmpty()) {
					for (BusinessDetails businessDetails : businessDetailsSet) {
						businessAddressDetailsSet.addAll(businessDetails.getAddressDetails());
						businessBankAccountDetailsSet.addAll(businessDetails.getBankAccountDetails());
					}
				} else {
					businessDetailsSet = businessDetailsSet == null ? new HashSet<BusinessDetails>()
							: businessDetailsSet;
					businessAddressDetailsSet = new HashSet<AddressDetails>();
					businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
				}
				monthlyTransactionProfilesSet = retailer.getMonthlyTransactionProfiles();
				workEducationDetailsSet = retailer.getWorkEducationDetails();
				introducerDetailsSet = retailer.getIntroducerDetails();

			}
			List<SectionsDTO> appPagesSectionsDTOList = appPagesDTO.getSections();
			if (appPagesSectionsDTOList != null && !appPagesSectionsDTOList.isEmpty()) {
				appPagesSectionService.prepareUserDetails(appPagesSectionsDTOList, retailer, nominees,
						userAddressDetailsSet, userBankAccountDetailsSet, nomineeAddressDetailsSet,
						nomineeBankAccountDetailsSet, businessDetailsSet, businessAddressDetailsSet,
						businessBankAccountDetailsSet, liabilitiesDetailsSet, workEducationDetailsSet,
						introducerDetailsSet, monthlyTransactionProfilesSet);
				return persistUser(retailer, nominees, userAddressDetailsSet, userBankAccountDetailsSet,
						liabilitiesDetailsSet, isNew, nomineeRelationship, nomineeAddressDetailsSet, isDsrUser,
						businessDetailsSet, nomineeBankAccountDetailsSet, monthlyTransactionProfilesSet,
						workEducationDetailsSet, introducerDetailsSet);
			}
		}
		return dsrUser;

	}

	@Transactional
	private User persistUser(User user, User nominees, Set<AddressDetails> userAddressDetailsSet,
			Set<BankAccountDetails> userBankAccountDetailsSet, Set<LiabilitiesDetails> liabilitiesDetails,
			Boolean isNew, List<UserRelationships> nomineeRelationship, Set<AddressDetails> nomineeAddressDetailsSet,
			Boolean isDsrUser, Set<BusinessDetails> businessDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet,
			Set<WorkEducationDetails> workEducationDetailsSet, Set<IntroducerDetails> introducerDetailsSet)
			throws Exception {
		if (isDsrUser) {
			user.setUserType(UserType.DISTRIBUTORS.name());
			user.setUserStatus(UserStatus.ACTIVE);
		} else
			user.setUserType(UserType.RETAILERS.name());
		if (neitherNullNorEmpty(userAddressDetailsSet))
			user.setAddressDetails(userAddressDetailsSet);
		if (neitherNullNorEmpty(userBankAccountDetailsSet))
			user.setBankAccountDetails(userBankAccountDetailsSet);
		if (neitherNullNorEmpty(businessDetailsSet))
			user.setBusinessDetails(businessDetailsSet);
		if (neitherNullNorEmpty(liabilitiesDetails))
			user.setLiabilitiesDetails(liabilitiesDetails);
		if (neitherNullNorEmpty(workEducationDetailsSet))
			user.setWorkEducationDetails(workEducationDetailsSet);
		if (neitherNullNorEmpty(monthlyTransactionProfilesSet))
			user.setMonthlyTransactionProfiles(monthlyTransactionProfilesSet);
		if (neitherNullNorEmpty(introducerDetailsSet))
			user.setIntroducerDetails(introducerDetailsSet);
		user = userRepository.save(user);
		if (nominees != null) {
			nominees.setUserType(UserType.NOMINEES.name());
			if (nomineeAddressDetailsSet != null)
				nominees.setAddressDetails(nomineeAddressDetailsSet);
			if (nomineeBankAccountDetailsSet != null)
				nominees.setBankAccountDetails(nomineeBankAccountDetailsSet);
			nominees = userRepository.save(nominees);
			persistUserRelationship(user, nominees, UserType.NOMINEES, isNew, nomineeRelationship);
		}
		return user;
	}

	private boolean neitherNullNorEmpty(Set<?> set) {
		return set != null && !set.isEmpty();
	}

	private void persistUserRelationship(User retailer, User nominees, UserType nominees2, Boolean isNew,
			List<UserRelationships> nomineeRelationship) {
		UserRelationships userRelationships = null;
		if (isNew) {
			userRelationships = new UserRelationships();
		} else {
			userRelationships = nomineeRelationship.get(0);
		}
		userRelationships.setMsisdn(retailer.getMsisdn());
		userRelationships.setRelative(nominees);
		userRelationships.setRelationship(Relationship.NOMINEE);
		userRelationshipsRepository.save(userRelationships);
	}

}
