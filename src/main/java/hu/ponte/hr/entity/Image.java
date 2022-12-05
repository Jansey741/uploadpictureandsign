package hu.ponte.hr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author zoltan
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Image
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] imageData;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "image_meta_id")
	private ImageMeta imageMeta;

	public void setImageMeta(ImageMeta imageMeta) {
		this.imageMeta = imageMeta;
	}
}