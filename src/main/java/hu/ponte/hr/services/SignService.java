package hu.ponte.hr.services;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
@NoArgsConstructor
@Slf4j
public class SignService {

    @Value("classpath:config/keys/key.private")
    private Resource privateKeyResource;

    @Autowired
    private ResourceLoader resourceLoader;

    public String signPicture(MultipartFile file) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException,
             InvalidKeyException, SignatureException {

        PrivateKey privateKey = getPrivateKey();
        byte[] signatureBytes = getSignature(file, privateKey);
        return getSignatureEncodedString(signatureBytes);
    }

    private static String getSignatureEncodedString(byte[] signatureBytes) {
        byte[]            signatureEncodedBytes  = Base64.getEncoder().encode(signatureBytes);
        String            signatureEncodedString = new String(signatureEncodedBytes);
        log.info("SIGNATURE = {}", signatureEncodedString);
        return signatureEncodedString;
    }

    private byte[] getSignature(MultipartFile file, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        Signature         signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(file.getBytes());
        return signature.sign();
    }

    private PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyResource.getURI()));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

}
