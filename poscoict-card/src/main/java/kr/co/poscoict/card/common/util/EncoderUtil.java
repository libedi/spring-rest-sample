package kr.co.poscoict.card.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import com.posco.securyti.StringCrypto;
import com.spit.es.services.crypt.seed.Seed;

/**
 * 암호화 유틸 클래스
 * @author Sangjun, Park
 *
 */
@Component
public class EncoderUtil {
	private final Seed seed = new Seed();
	
	/**
	 * SEED 암호화
	 * @param plainText
	 * @return
	 */
	public String encodeBySeed(String plainText) {
		return this.seed.crypt(plainText);
	}
	
	/**
	 * SEED 복호화
	 * @param encodedText
	 * @return
	 */
	public String decodeBySeed(String encodedText) {
		return this.seed.decrypt(encodedText);
	}
	
	/**
	 * POSCO Family 암호화
	 * @param plainText
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String encodeByFamily(String plainText) throws UnsupportedEncodingException, Exception {
		return URLEncoder.encode(StringCrypto.encrypt(plainText), "UTF-8");
	}
	
	/**
	 * POSCO Family 복호화
	 * @param encodedText
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String decodeByFamily(String encodedText) throws UnsupportedEncodingException, Exception {
		return StringCrypto.decrypt(URLDecoder.decode(encodedText, "UTF-8"));
	}
}
