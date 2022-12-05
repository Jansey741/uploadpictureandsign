package hu.ponte.hr;

import hu.ponte.hr.services.SignService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zoltan
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class SignTest
{
	@Value("classpath:images/cat.jpg")
	private File catFile;

	@Value("classpath:images/enhanced-buzz.jpg")
	private File hedgehogFile;

	@Value("classpath:images/rnd.jpg")
	private File gopherFile;

	@Autowired
	private SignService signService;

	Map<String, String> files = new LinkedHashMap<String, String>() {
		{
			put("cat.jpg","XYZ+wXKNd3Hpnjxy4vIbBQVD7q7i0t0r9tzpmf1KmyZAEUvpfV8AKQlL7us66rvd6eBzFlSaq5HGVZX2DYTxX1C5fJlh3T3QkVn2zKOfPHDWWItdXkrccCHVR5HFrpGuLGk7j7XKORIIM+DwZKqymHYzehRvDpqCGgZ2L1Q6C6wjuV4drdOTHps63XW6RHNsU18wHydqetJT6ovh0a8Zul9yvAyZeE4HW7cPOkFCgll5EZYZz2iH5Sw1NBNhDNwN2KOxrM4BXNUkz9TMeekjqdOyyWvCqVmr5EgssJe7FAwcYEzznZV96LDkiYQdnBTO8jjN25wlnINvPrgx9dN/Xg==");
			put("enhanced-buzz.jpg","tsLUqkUtzqgeDMuXJMt1iRCgbiVw13FlsBm2LdX2PftvnlWorqxuVcmT0QRKenFMh90kelxXnTuTVOStU8eHRLS3P1qOLH6VYpzCGEJFQ3S2683gCmxq3qc0zr5kZV2VcgKWm+wKeMENyprr8HNZhLPygtmzXeN9u6BpwUO9sKj7ImBvvv/qZ/Tht3hPbm5SrDK4XG7G0LVK9B8zpweXT/lT8pqqpYx4/h7DyE+L5bNHbtkvcu2DojgJ/pNg9OG+vTt/DfK7LFgCjody4SvZhSbLqp98IAaxS9BT6n0Ozjk4rR1l75QP5lbJbpQ9ThAebXQo+Be4QEYV/YXf07WXTQ==");
			put("rnd.jpg","lM6498PalvcrnZkw4RI+dWceIoDXuczi/3nckACYa8k+KGjYlwQCi1bqA8h7wgtlP3HFY37cA81ST9I0X7ik86jyAqhhc7twnMUzwE/+y8RC9Xsz/caktmdA/8h+MlPNTjejomiqGDjTGvLxN9gu4qnYniZ5t270ZbLD2XZbuTvUAgna8Cz4MvdGTmE3MNIA5iavI1p+1cAN+O10hKwxoVcdZ2M3f7/m9LYlqEJgMnaKyI/X3m9mW0En/ac9fqfGWrxAhbhQDUB0GVEl7WBF/5ODvpYKujHmBAA0ProIlqA3FjLTLJ0LGHXyDgrgDfIG/EDHVUQSdLWsM107Cg6hQg==");
		}
	};

	@Test
	public void test_01() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
			SignatureException, InvalidKeyException {
		MultipartFile multipartFile = getCatMultipartFile();
		String sign = signService.signPicture(multipartFile);
		Assert.assertEquals(files.get("cat.jpg"), sign);
	}

	@Test
	public void test_02() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
			SignatureException, InvalidKeyException {
		MultipartFile multipartFile = getHedgeHogMultipartFile();
		String sign = signService.signPicture(multipartFile);
		Assert.assertEquals(files.get("enhanced-buzz.jpg"), sign);
	}

	@Test
	public void test_03() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
			SignatureException, InvalidKeyException {
		MultipartFile multipartFile = getGopherMultipartFile();
		String sign = signService.signPicture(multipartFile);
		Assert.assertEquals(files.get("rnd.jpg"), sign);
	}

	private MultipartFile getCatMultipartFile() throws IOException {
		String name = "cat.jpg";
		String originalFileName = name;
		String contentType = "image/jpeg";
		byte[] content = Files.readAllBytes(catFile.toPath());

		MultipartFile multipartFile = new MockMultipartFile(name,
				originalFileName, contentType, content);
		return multipartFile;
	}

	private MultipartFile getHedgeHogMultipartFile() throws IOException {
		String name = "enhanced-buzz.jpg";
		String originalFileName = name;
		String contentType = "image/jpeg";
		byte[] content = Files.readAllBytes(hedgehogFile.toPath());

		MultipartFile multipartFile = new MockMultipartFile(name,
				originalFileName, contentType, content);
		return multipartFile;
	}

	private MultipartFile getGopherMultipartFile() throws IOException {
		String name = "rnd.jpg";
		String originalFileName = name;
		String contentType = "image/jpeg";
		byte[] content = Files.readAllBytes(gopherFile.toPath());

		MultipartFile multipartFile = new MockMultipartFile(name,
				originalFileName, contentType, content);
		return multipartFile;
	}


}
