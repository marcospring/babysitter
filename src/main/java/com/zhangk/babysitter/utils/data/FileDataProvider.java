package com.zhangk.babysitter.utils.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileDataProvider {

	private static StringBuffer BASE_PATH = new StringBuffer("E:\\workEleven\\babysitter\\data\\");

	private static String USER_NAME = "username.txt";
	private static String NAME = "name.txt";
	private static String USER_PHONE = "phone.txt";
	private static String USER_EMAIL = "email.txt";
	private List<String> userName = null;
	private List<String> name = null;
	private List<String> userPhone = null;
	private List<String> userEmail = null;

	public enum Type {
		USERNAME, NAME, PHONE, EMAIL
	}

	public String getValue(Type typeName) {
		Random random = new Random();
		switch (typeName) {
		case USERNAME:
			if (userName == null)
				userName = initList(USER_NAME);
			return userName.get(random.nextInt(userName.size()));
		case NAME:
			if (name == null)
				name = initList(NAME);
			return name.get(random.nextInt(name.size()));
		case PHONE:
			if (userPhone == null)
				userPhone = initList(USER_PHONE);
			return userPhone.get(random.nextInt(userPhone.size()));
		case EMAIL:
			if (userEmail == null)
				userEmail = initList(USER_EMAIL);
			return userEmail.get(random.nextInt(userEmail.size()));
		}
		return null;
	}

	private List<String> initList(String fileName) {
		List<String> values = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = initFile(fileName);
			while (reader.readLine() != null) {
				values.add(reader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return values;
	}

	private BufferedReader initFile(String fileName) throws IOException {
		BASE_PATH.replace(BASE_PATH.lastIndexOf("\\") + 1, BASE_PATH.length(), "");
		return new BufferedReader(new FileReader(BASE_PATH.append(fileName).toString()));
	}
}
