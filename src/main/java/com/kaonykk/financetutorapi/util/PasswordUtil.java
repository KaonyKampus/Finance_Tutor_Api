package com.kaonykk.financetutorapi.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

	public static Object getEntityManagerFactory() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private PasswordUtil() {
	}

	public static String hash(String plainPassword) {

		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());

	}

	public static boolean verify(String writtenPassword, String hashedPassword) {

		return BCrypt.checkpw(writtenPassword, hashedPassword);

	}
}
