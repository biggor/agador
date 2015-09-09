package com.biggor.agador.dropbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

public class DropboxAuth {

	public static void main(String[] args) throws IOException, DbxException {
		
		final String APP_KEY = "ecirfzrtc8olbqj";
		final String APP_SECRET = "aq18iw4xklcdxmj";

		final String AUTH_FILE = "authfile.txt";
		
		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
		
		DbxRequestConfig config = new DbxRequestConfig("Agador/1.0", Locale.getDefault().toString(), new AppengineHttpRequestor(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.61.128.178", 8080))));
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
		String authorizeUrl = webAuth.start();
		System.out.println("1. Go to: " + authorizeUrl);
		System.out.println("2. Click\"Allow\" (you might have to log in first)");
		System.out.println("3. Copy the authorization code.");
		String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		
		DbxAuthFinish authFinish = webAuth.finish(code);
		String accessToken = authFinish.accessToken;
		
		DbxClient client = new DbxClient(config, accessToken);
		DbxAuthInfo authInfo = new DbxAuthInfo(accessToken, appInfo.host);
	
		DbxAuthInfo.Writer.writeToFile(authInfo, AUTH_FILE);
		DbxAuthInfo.Writer.writeToStream(authInfo, System.out);
	}

}
