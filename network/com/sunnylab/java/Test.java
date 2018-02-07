package com.sunnylab.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Test {
	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.google.com");
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			ReadableByteChannel channel = Channels.newChannel(inputStream);
			ByteBuffer buffer = ByteBuffer.allocate(64);
			//String line = null;
			while (channel.read(buffer) > 0) {
				System.out.println(new String(buffer.array()));
				buffer.clear();
			}
			channel.close();
		} catch (IOException ex) { // Handle exceptions }
			System.err.println("Error connecting");
		}
	}
}
