package authserver.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
abstract class Base {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	protected Date createdAt;
	
	@LastModifiedDate
	@Column(nullable = true, updatable = true)
	protected Date updatedAt;
	
	@LastModifiedBy
	@Column(nullable = true, updatable = true)
	protected String updatedBy;
}
