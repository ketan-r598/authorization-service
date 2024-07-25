package authserver.services;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Service;

@Service
public interface KeysProvider {

	public PrivateKey getPrivateKey() throws NoSuchAlgorithmException;
	public PublicKey  getPublicKey() throws NoSuchAlgorithmException;
}
