package co.yabx.kyc.app.entities.filter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.yabx.kyc.app.entities.Fields;
import co.yabx.kyc.app.entities.SectionGroupRelationship;

/**
 * 
 * @author Asad.ali
 *
 */
@Entity
@Table(name = "sub_groups")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "group_type", discriminatorType = DiscriminatorType.STRING)
public abstract class SubGroups implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "group_type", insertable = false, updatable = false)
	private String groupType;

	@ManyToOne(targetEntity = SectionGroupRelationship.class, fetch = FetchType.LAZY)
	protected SectionGroupRelationship sectionGroupRelationship;

	@JoinColumn(name = "fields")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Fields.class)
	Fields fields;

	@Column(name = "display_order")
	private Integer displayOrder;

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public SectionGroupRelationship getSectionGroupRelationship() {
		return sectionGroupRelationship;
	}

	public void setSectionGroupRelationship(SectionGroupRelationship sectionGroupRelationship) {
		this.sectionGroupRelationship = sectionGroupRelationship;
	}

}