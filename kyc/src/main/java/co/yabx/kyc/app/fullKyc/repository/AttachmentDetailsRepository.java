package co.yabx.kyc.app.fullKyc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.enums.AttachmentType;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("attachmentDetailsRepository")
public interface AttachmentDetailsRepository extends CrudRepository<AttachmentDetails, Long> {

	AttachmentDetails findByUserAndDocumentType(User user, String documentType);

	List<AttachmentDetails> findByUser(User user);

	AttachmentDetails findByUserAndDocumentTypeAndAttachmentType(User user, String documentType,
			AttachmentType attachmentType);

}
