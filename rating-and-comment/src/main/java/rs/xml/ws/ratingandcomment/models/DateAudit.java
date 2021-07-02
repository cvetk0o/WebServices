package rs.xml.ws.ratingandcomment.models;

import java.io.Serializable;
import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;

@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
@JsonIgnoreProperties( value =
{ "createdAt" }, allowGetters = true )
@Data
public class DateAudit implements Serializable
{

	private static final long serialVersionUID = -7629602051136766935L;

	@JsonIgnore
	@CreatedDate
	@Column( nullable = false, updatable = false )
	private LocalDateTime createdAt;

}