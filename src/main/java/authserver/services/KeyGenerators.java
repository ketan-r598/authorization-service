package authserver.services;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public interface KeyGenerators {
	public KeyPair getKeyPair() throws NoSuchAlgorithmException;
}
