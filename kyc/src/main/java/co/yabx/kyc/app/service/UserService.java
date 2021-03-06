package co.yabx.kyc.app.service;

import java.util.List;

import co.yabx.kyc.app.dto.PagesDTO;
import co.yabx.kyc.app.enums.PageType;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;

public interface UserService {

	void persistYabxTokenAndSecretKey(User user, String yabxToken, String aPI_SECRET_KEY);

	List<PagesDTO> getUserDetails(User dsrUser, PageType pageType, String locale);

	DSRUser getDSRByMsisdn(String dsrMsisdn);

	Retailers getRetailerById(Long retailerId);

	User persistOrUpdateUserInfo(PagesDTO appPagesDTO, User dsrUser, User retailer) throws Exception;

	User getRetailerByMsisdn(String retailerMsisdn);

	List<User> getDSRByRetailer(User retailer);

}
