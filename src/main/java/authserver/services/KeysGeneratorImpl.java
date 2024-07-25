package authserver.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class KeysGeneratorImpl implements KeyGenerators {
 
	private static KeyPair keypair;
	
	static {
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
			keypair = kpg.generateKeyPair();
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}
	}
	
	public KeyPair getKeyPair() {
		return keypair;
	}
}
