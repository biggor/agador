package com.biggor.dropbox.test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Locale;

import com.dropbox.core.DbxAccountInfo;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader.FileLoadException;

public class DropboxAccountInfo {

	public static void main(String[] args) throws FileLoadException, DbxException {

		final String AUTH_FILE = "authfile.txt";
		
		DbxAuthInfo authInfo = DbxAuthInfo.Reader.readFromFile(AUTH_FILE);
		
		DbxRequestConfig config = new DbxRequestConfig("Agador/1.0", Locale.getDefault().toString(), new AppengineHttpRequestor(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.61.128.178", 8080))));
        DbxClient client = new DbxClient(config, authInfo.accessToken, authInfo.host);
        
        DbxAccountInfo accountInfo = client.getAccountInfo();
        System.out.println(accountInfo.toStringMultiline());
	}

}
