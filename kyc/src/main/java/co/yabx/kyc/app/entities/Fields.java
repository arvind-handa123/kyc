package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import co.yabx.kyc.app.entities.filter.SubFields;
import co.yabx.kyc.app.entities.filter.SubGroups;
import co.yabx.kyc.app.enums.ControlType;
import co.yabx.kyc.app.enums.DataType;

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "fields", indexes = { @Index(name = "field_id", columnList = "field_id"),
		@Index(name = "field_name", columnList = "field_name") })
@NamedQuery(name = "Fields.findAll", query = "SELECT f FROM Fields f")
public class Fields implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "control_type", length = 100, nullable = false, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private ControlType type;

	@Column(name = "field_id")
	private String fieldId;

	@Column(name = "data_type", length = 100, nullable = false, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private DataType dataType;

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "placeholder_text")
	private String placeHolderText;

	@Transient
	private String savedData;

	@Column(name = "validation")
	private String validation;

	@Column(name = "default_value")
	private String defaultValue;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "is_camera", nullable = false, columnDefinition = "boolean default false")
	private boolean camera;

	@Column(name = "is_editable", nullable = false, columnDefinition = "boolean default true")
	private boolean editable;

	@Column(name = "mandaory", nullable = false, columnDefinition = "boolean default true")
	private boolean mandatory;

	private Integer hash;

	@Column(name = "display_order")
	private Integer displayOrder;

	/**
	 * options field is non persistent field, and will be used only while returning
	 * options of a radio or check box input types.
	 */
	@Transient
	private List<String> options;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Groups.class)
	private Groups groups;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<SubFields> subFields;

	@OneToMany(mappedBy = "fields", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<SubGroups> subGroups;

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

	public Integer getHash() {
		return hash;
	}

	public void setHash(Integer hash) {
		this.hash = hash;
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

	public String getSavedData() {
		return savedData;
	}

	public void setSavedData(String savedData) {
		this.savedData = savedData;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "AppDynamicFields [id=" + id + ", type=" + type + ", fieldId=" + fieldId + ", dataType=" + dataType
				+ ", fieldName=" + fieldName + ", placeHolderText=" + placeHolderText + ", savedData=" + savedData
				+ ", validation=" + validation + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", camera="
				+ camera + ", editable=" + editable + ", mandatory=" + mandatory + ", hash=" + hash + ", displayOrder="
				+ displayOrder + ", options=" + options + ", groups=" + groups + "]";
	}

	public Set<SubFields> getSubFields() {
		return subFields;
	}

	public void setSubFields(Set<SubFields> subFields) {
		this.subFields = subFields;
	}

	public Set<SubGroups> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(Set<SubGroups> subGroups) {
		this.subGroups = subGroups;
	}

}