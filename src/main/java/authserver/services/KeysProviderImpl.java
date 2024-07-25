package authserver.services;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeysProviderImpl implements KeysProvider {

	
	@Autowired
	private KeyGenerators keyGenerators;
//	private KeyPair keyPair;
	
//	public KeysProviderImpl(KeyGenerators keyGenerators) {
//		this.keyGenerators = keyGenerators;
//		try {
//			this.keyPair = this.keyGenerators.generateKeys();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//	}
	
	public PrivateKey getPrivateKey() throws NoSuchAlgorithmException {
		return keyGenerators.getKeyPair().getPrivate();
	}

	public PublicKey getPublicKey() throws NoSuchAlgorithmException {
		return keyGenerators.getKeyPair().getPublic();
	}
}
