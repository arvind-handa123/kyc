package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import co.yabx.kyc.app.dto.FieldsDTO;
import co.yabx.kyc.app.dto.Functionality;
import co.yabx.kyc.app.dto.GroupsDTO;
import co.yabx.kyc.app.entities.Fields;
import co.yabx.kyc.app.entities.Groups;
import co.yabx.kyc.app.entities.SectionGroupRelationship;
import co.yabx.kyc.app.entities.Sections;
import co.yabx.kyc.app.entities.filter.Filters;
import co.yabx.kyc.app.entities.filter.SubGroups;
import co.yabx.kyc.app.enums.FunctionalityType;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.repositories.SectionGroupRelationshipRepository;
import co.yabx.kyc.app.util.SpringUtil;

public class GroupDtoHelper implements Serializable {
	public static List<GroupsDTO> getGroups(User retailers, Map<String, Integer> filledVsUnfilled,
			Sections appPagesSections, User nominee, Set<AddressDetails> userAddressDetailsSet,
			Set<AddressDetails> nomineeAddressDetailsSet, Set<AddressDetails> businessAddressDetailsSet,
			Set<BankAccountDetails> userBankAccountDetailsSet, Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet) {
		List<SectionGroupRelationship> sectionGroupRelationships = SpringUtil
				.bean(SectionGroupRelationshipRepository.class).findBySectionId(appPagesSections.getSectionId());
		List<GroupsDTO> appPagesSectionGroupSet = new ArrayList<GroupsDTO>();
		for (SectionGroupRelationship sectionGroupRelationship : sectionGroupRelationships) {
			if (sectionGroupRelationship.isActive()) {
				Groups appPagesSectionGroups = sectionGroupRelationship.getGroups();
				Set<SubGroups> subGroups = sectionGroupRelationship.getSubGroups();
				Set<Filters> filters = sectionGroupRelationship.getFilters();
				if (subGroups != null && !subGroups.isEmpty()) {
					for (SubGroups subGropus : subGroups) {
						prepareGroups(subGropus, retailers, appPagesSections, nominee, userAddressDetailsSet,
								nomineeAddressDetailsSet, businessAddressDetailsSet, userBankAccountDetailsSet,
								nomineeBankAccountDetailsSet, businessBankAccountDetailsSet, appPagesSectionGroups,
								appPagesSectionGroupSet, filledVsUnfilled, filters);
					}

				} else {
					prepareGroups(null, retailers, appPagesSections, nominee, userAddressDetailsSet,
							nomineeAddressDetailsSet, businessAddressDetailsSet, userBankAccountDetailsSet,
							nomineeBankAccountDetailsSet, businessBankAccountDetailsSet, appPagesSectionGroups,
							appPagesSectionGroupSet, filledVsUnfilled, filters);
				}
			}
		}
		return appPagesSectionGroupSet;
	}

	private static void prepareGroups(SubGroups subGroups, User retailers, Sections appPagesSections, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet, Set<BankAccountDetails> businessBankAccountDetailsSet,
			Groups appPagesSectionGroups, List<GroupsDTO> appPagesSectionGroupSet,
			Map<String, Integer> filledVsUnfilled, Set<Filters> filters) {

		if (appPagesSectionGroups != null) {
			Map<String, Integer> groups = new HashMap<String, Integer>();
			groups.put("filledFields", 0);
			groups.put("totalFields", 0);
			GroupsDTO appPagesSectionGroupsDTO = new GroupsDTO();
			Set<Fields> appDynamicFieldsSet = appPagesSectionGroups.getAppDynamicFields();
			Filters filter = fieldsTobeFiltered(filters, appPagesSectionGroups.getGroupName());
			if (appDynamicFieldsSet != null && !appDynamicFieldsSet.isEmpty()) {
				List<FieldsDTO> fields = FieldsDtoHelper.getFields(appDynamicFieldsSet, retailers, groups,
						appPagesSections, nominee, userAddressDetailsSet, nomineeAddressDetailsSet,
						businessAddressDetailsSet, userBankAccountDetailsSet, nomineeBankAccountDetailsSet,
						businessBankAccountDetailsSet, subGroups, filter);
				if (subGroups != null) {
					addFunctionality(subGroups, appPagesSectionGroupSet, fields);
				}
				Set<FieldsDTO> appDynamicFieldsDTOs = fields.stream().sorted(Comparator.comparing(FieldsDTO::getId))
						.collect(Collectors.toSet());
				appPagesSectionGroupsDTO.setFields(appDynamicFieldsDTOs.stream()
						.sorted(Comparator.comparing(FieldsDTO::getDisplayOrder)).collect(Collectors.toList()));
				appPagesSectionGroupsDTO.setEnable(appPagesSectionGroups.isEnable());
				appPagesSectionGroupsDTO
						.setGroupId(subGroups != null ? subGroups.getId() : appPagesSectionGroups.getGroupId());
				appPagesSectionGroupsDTO.setGroupName(appPagesSectionGroups.getGroupName());
				appPagesSectionGroupsDTO
						.setGroupTitle(subGroups != null && subGroups.getGroupType() != null ? subGroups.getGroupType()
								: appPagesSectionGroups.getGroupTitle());
				if (subGroups != null)
					appPagesSectionGroupsDTO.setDisplayOrder(subGroups.getDisplayOrder());
				else
					appPagesSectionGroupsDTO.setDisplayOrder(appPagesSectionGroups.getDisplayOrder());
				appPagesSectionGroupsDTO.setTotalFields(groups.get("totalFields"));
				appPagesSectionGroupsDTO.setFilledFields(groups.get("filledFields"));
			}
			appPagesSectionGroupSet.add(appPagesSectionGroupsDTO);
			filledVsUnfilled.put("filledFields", filledVsUnfilled.get("filledFields") + groups.get("filledFields"));
			filledVsUnfilled.put("totalFields", filledVsUnfilled.get("totalFields") + groups.get("totalFields"));
		}
	}

	private static void addFunctionality(SubGroups subGroups, List<GroupsDTO> appPagesSectionGroupSet,
			List<FieldsDTO> fields) {
		Fields field = subGroups.getFields();
		if (field != null) {
			FieldsDTO fieldsDTO = getAppDynamicFieldDTO(field);
			Functionality functionality = new Functionality();
			functionality.setType(FunctionalityType.Copy);
			functionality.setFromGroup(appPagesSectionGroupSet.get(0).getGroupId());
			functionality.setToGroup(subGroups.getId());
			fieldsDTO.setFunctionality(functionality);
			fields.add(fieldsDTO);
		}
	}

	private static Filters fieldsTobeFiltered(Set<Filters> filters, String groupName) {
		if (filters != null && !filters.isEmpty() && groupName != null && !groupName.isEmpty()) {
			Optional<Filters> filter = filters.stream().filter(f -> groupName.equalsIgnoreCase(f.getGroupName()))
					.findFirst();
			return filter.isPresent() ? filter.get() : null;
		}
		return null;
	}

	private static FieldsDTO getAppDynamicFieldDTO(Fields dynamicFields) {
		FieldsDTO appDynamicFieldsDTO = new FieldsDTO();
		appDynamicFieldsDTO.setCamera(dynamicFields.isCamera());
		appDynamicFieldsDTO.setDataType(dynamicFields.getDataType());
		appDynamicFieldsDTO.setEditable(dynamicFields.isEditable());
		appDynamicFieldsDTO.setFieldId(dynamicFields.getFieldId());
		appDynamicFieldsDTO.setFieldName(dynamicFields.getFieldName());
		appDynamicFieldsDTO.setId(dynamicFields.getId());
		appDynamicFieldsDTO.setMandatory(dynamicFields.isMandatory());
		appDynamicFieldsDTO.setOptions(dynamicFields.getOptions());
		appDynamicFieldsDTO.setPlaceHolderText(dynamicFields.getPlaceHolderText());
		appDynamicFieldsDTO.setSavedData(dynamicFields.getSavedData());
		appDynamicFieldsDTO.setType(dynamicFields.getType());
		appDynamicFieldsDTO.setValidation(dynamicFields.getValidation());
		appDynamicFieldsDTO.setDisplayOrder(dynamicFields.getDisplayOrder());
		return appDynamicFieldsDTO;
	}

}
