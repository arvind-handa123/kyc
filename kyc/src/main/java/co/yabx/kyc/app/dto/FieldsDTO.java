package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.ControlType;
import co.yabx.kyc.app.enums.DataType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldsDTO implements Serializable {
	private static final long serialVersionUID = 1588962L;

	private Long id;

	private ControlType type;

	private String fieldId;

	private DataType dataType;

	private String fieldName;

	private String placeHolderText;

	private Object savedData;

	private String validation;

	private Date createdAt;

	private Date updatedAt;

	private boolean camera;

	private boolean editable;

	private boolean mandatory;

	private String response;

	private String defaultValue;

	private List<String> options;

	private List<SubFieldsDTO> subFields;

	private Functionality functionality;

	private Integer displayOrder;

	private String help;

	private Boolean enableFutureDates;

	private Boolean enablePastDates;

	private Boolean internationalRepresentation;

	private String remark;

	private String localeText;

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

	public ControlType getType() {
		return type;
	}

	public void setType(ControlType type) {
		this.type = type;
	}

	public String getPlaceHolderText() {
		return placeHolderText;
	}

	public void setPlaceHolderText(String placeHolderText) {
		this.placeHolderText = placeHolderText;
	}

	public Object getSavedData() {
		return savedData;
	}

	public void setSavedData(Object savedData) {
		this.savedData = savedData;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public boolean isCamera() {
		return camera;
	}

	public void setCamera(boolean camera) {
		this.camera = camera;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<SubFieldsDTO> getSubFields() {
		return subFields;
	}

	public void setSubFields(List<SubFieldsDTO> subFields) {
		this.subFields = subFields;
	}

	public Functionality getFunctionality() {
		return functionality;
	}

	public void setFunctionality(Functionality functionality) {
		this.functionality = functionality;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public String toString() {
		return "FieldsDTO [id=" + id + ", type=" + type + ", fieldId=" + fieldId + ", dataType=" + dataType
				+ ", fieldName=" + fieldName + ", placeHolderText=" + placeHolderText + ", savedData=" + savedData
				+ ", validation=" + validation + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", camera="
				+ camera + ", editable=" + editable + ", mandatory=" + mandatory + ", response=" + response
				+ ", defaultValue=" + defaultValue + ", options=" + options + ", subFields=" + subFields
				+ ", functionality=" + functionality + ", displayOrder=" + displayOrder + "]";
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public Boolean getEnableFutureDates() {
		return enableFutureDates;
	}

	public void setEnableFutureDates(Boolean enableFutureDates) {
		this.enableFutureDates = enableFutureDates;
	}

	public Boolean getEnablePastDates() {
		return enablePastDates;
	}

	public void setEnablePastDates(Boolean enablePastDates) {
		this.enablePastDates = enablePastDates;
	}

	public Boolean getInternationalRepresentation() {
		return internationalRepresentation;
	}

	public void setInternationalRepresentation(Boolean internationalRepresentation) {
		this.internationalRepresentation = internationalRepresentation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocaleText() {
		return localeText;
	}

	public void setLocaleText(String localeText) {
		this.localeText = localeText;
	}

}
