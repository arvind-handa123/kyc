package co.yabx.kyc.app.entities;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.entities.filter.Filters;

@Entity
@Table(name = "section_group_relationships", indexes = { @Index(name = "section_id", columnList = "section_id") })
public class SectionGroupRelationship implements Serializable {

	private static final long serialVersionUID = -6901153936343185L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "section_id")
	private Long sectionId;

	@JoinColumn(name = "group_id")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Groups.class)
	private Groups appPagesSectionGroups;

	@Column(name = "is_multiple", columnDefinition = "boolean default false")
	private boolean multiple;

	@Column(name = "multiple_title")
	private String multipleTitlle;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "active", columnDefinition = "boolean default true")
	private boolean active;

	@OneToMany(mappedBy = "sectionGroupRelationship", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Filters> filters;

	public Set<Filters> getFilters() {
		return filters;
	}

	public void setFilters(Set<Filters> filters) {
		this.filters = filters;
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

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Groups getAppPagesSectionGroups() {
		return appPagesSectionGroups;
	}

	public void setAppPagesSectionGroups(Groups appPagesSectionGroups) {
		this.appPagesSectionGroups = appPagesSectionGroups;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getMultipleTitlle() {
		return multipleTitlle;
	}

	public void setMultipleTitlle(String multipleTitlle) {
		this.multipleTitlle = multipleTitlle;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "SectionGroupRelationship [id=" + id + ", sectionId=" + sectionId + ", appPagesSectionGroups="
				+ appPagesSectionGroups + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
