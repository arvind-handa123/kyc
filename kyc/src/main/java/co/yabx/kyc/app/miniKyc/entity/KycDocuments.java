package co.yabx.kyc.app.miniKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * 
 * @author Asad Ali
 *
 */
@Entity
@Table(name = "kyc_documents", indexes = { @Index(name = "msisdn", columnList = "msisdn"),
		@Index(name = "document_number", columnList = "document_number"),
		@Index(name = "document_type", columnList = "document_type") })
public class KycDocuments implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "document_side")
	private String documentSide;

	@Column(name = "document_url")
	private String documentUrl;

	@Column(name = "snap_time")
	private Date snapTime;

	@Column(name = "is_Selfie")
	private boolean isSelfie;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "document_number")
	private String documentNumber;

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "document_issue_date")
	private Long documentIssueDate;

	@Column(name = "issued_at_place")
	private String placeOfIssue;

	@Column(name = "document_expiry_date")
	private Long documentExpiryDate;

	public Date getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}

	public boolean isSelfie() {
		return isSelfie;
	}

	public void setSelfie(boolean isSelfie) {
		this.isSelfie = isSelfie;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	protected void insertDates() {
		if (createdAt == null) {
			createdAt = new Date();
		}
		if (updatedAt == null) {
			updatedAt = new Date();
		}
	}

	@PreUpdate
	protected void updateTime() {
		updatedAt = new Date();
	}

	public String getDocumentSide() {
		return documentSide;
	}

	public void setDocumentSide(String documentSide) {
		this.documentSide = documentSide;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Long getDocumentIssueDate() {
		return documentIssueDate;
	}

	public void setDocumentIssueDate(Long documentIssueDate) {
		this.documentIssueDate = documentIssueDate;
	}

	public Long getDocumentExpiryDate() {
		return documentExpiryDate;
	}

	public void setDocumentExpiryDate(Long documentExpiryDate) {
		this.documentExpiryDate = documentExpiryDate;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	@Override
	public String toString() {
		return "KycDocuments [id=" + id + ", msisdn=" + msisdn + ", documentSide=" + documentSide + ", documentUrl="
				+ documentUrl + ", snapTime=" + snapTime + ", isSelfie=" + isSelfie + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", documentNumber=" + documentNumber + ", documentType=" + documentType
				+ ", documentIssueDate=" + documentIssueDate + ", placeOfIssue=" + placeOfIssue
				+ ", documentExpiryDate=" + documentExpiryDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((documentExpiryDate == null) ? 0 : documentExpiryDate.hashCode());
		result = prime * result + ((documentIssueDate == null) ? 0 : documentIssueDate.hashCode());
		result = prime * result + ((documentNumber == null) ? 0 : documentNumber.hashCode());
		result = prime * result + ((documentSide == null) ? 0 : documentSide.hashCode());
		result = prime * result + ((documentType == null) ? 0 : documentType.hashCode());
		result = prime * result + ((documentUrl == null) ? 0 : documentUrl.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isSelfie ? 1231 : 1237);
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result + ((placeOfIssue == null) ? 0 : placeOfIssue.hashCode());
		result = prime * result + ((snapTime == null) ? 0 : snapTime.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KycDocuments other = (KycDocuments) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (documentExpiryDate == null) {
			if (other.documentExpiryDate != null)
				return false;
		} else if (!documentExpiryDate.equals(other.documentExpiryDate))
			return false;
		if (documentIssueDate == null) {
			if (other.documentIssueDate != null)
				return false;
		} else if (!documentIssueDate.equals(other.documentIssueDate))
			return false;
		if (documentNumber == null) {
			if (other.documentNumber != null)
				return false;
		} else if (!documentNumber.equals(other.documentNumber))
			return false;
		if (documentSide == null) {
			if (other.documentSide != null)
				return false;
		} else if (!documentSide.equals(other.documentSide))
			return false;
		if (documentType == null) {
			if (other.documentType != null)
				return false;
		} else if (!documentType.equals(other.documentType))
			return false;
		if (documentUrl == null) {
			if (other.documentUrl != null)
				return false;
		} else if (!documentUrl.equals(other.documentUrl))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isSelfie != other.isSelfie)
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (placeOfIssue == null) {
			if (other.placeOfIssue != null)
				return false;
		} else if (!placeOfIssue.equals(other.placeOfIssue))
			return false;
		if (snapTime == null) {
			if (other.snapTime != null)
				return false;
		} else if (!snapTime.equals(other.snapTime))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

}
