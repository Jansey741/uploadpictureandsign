package hu.ponte.hr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class ImageMeta
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String mimeType;
	private long size;
	@Column(length = 500)
	private String digitalSign;
}