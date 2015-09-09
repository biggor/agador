package com.biggor.dropbox.test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Locale;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxDelta;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader.FileLoadException;

public class DropboxDelta {

	public static void main(String[] args) throws FileLoadException, DbxException, InterruptedException {
		final String AUTH_FILE = "authfile.txt";
		
		DbxAuthInfo authInfo = DbxAuthInfo.Reader.readFromFile(AUTH_FILE);
		
		DbxRequestConfig config = new DbxRequestConfig("Agador/1.0", Locale.getDefault().toString(), new AppengineHttpRequestor(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.61.128.178", 8080))));
        DbxClient client = new DbxClient(config, authInfo.accessToken, authInfo.host);
        
        String cursor = null;
        
        while (true) {
			DbxDelta<DbxEntry> result = client.getDeltaWithPathPrefix(cursor, "/s/stest");
			cursor = result.cursor;
			if (result.reset) {
				System.out.println("Reset!");
			}
			for (DbxDelta.Entry entry : result.entries) {
				if (entry.metadata == null) {
					System.out.println("Deleted: " + entry.lcPath);
				} else {
					System.out.println("Added or modified: " + entry.lcPath);
				}
			}
			
			if (!result.hasMore) {
				Thread.sleep(1000);
			}
		}
 	}

}
