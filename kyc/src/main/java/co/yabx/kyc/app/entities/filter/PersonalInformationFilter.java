package co.yabx.kyc.app.entities.filter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Asad.ali
 *
 */
@Entity
@DiscriminatorValue("PERSONAL_INFO")
public class PersonalInformationFilter extends Filters {
	@Column(length = 5000)
	private String fields;

	@Override
	public boolean filter(String filedName) {
		if (filedName != null) {
			List<String> field = Arrays.asList(fields.split(","));
			if (field.contains(filedName)) {
				return true;
			}
		}
		return false;
	}
}
