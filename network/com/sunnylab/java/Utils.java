package com.sunnylab.java;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;

public class Utils {

	public static void main(String[] args) {
		InetAddress address;
		try {
			address = InetAddress.getByName("www.google.com");
			displayInetAddressInformation(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		displayReachableAddresses("www.google.com");

	}

	public static void displayURI(URI uri) {
		System.out.println("getAuthority: " + uri.getAuthority());
		System.out.println("getScheme: " + uri.getScheme());
		System.out.println("getSchemeSpecificPart: " + uri.getSchemeSpecificPart());
		System.out.println("getHost: " + uri.getHost());
		System.out.println("getPath: " + uri.getPath());
		System.out.println("getQuery: " + uri.getQuery());
		System.out.println("getFragment: " + uri.getFragment());
		System.out.println("getUserInfo: " + uri.getUserInfo());
		System.out.println("normalize: " + uri.normalize());
	}

	public static void displayURL(URL url) {
		System.out.println("URL: " + url);
		System.out.printf("  Protocol: %-32s  Host: %-32s\n", url.getProtocol(), url.getHost());
		System.out.printf("      " + "Port: %-32d  Path: %-32s\n", url.getPort(), url.getPath());
		System.out.printf(" Reference: %-32s  File: %-32s\n", url.getRef(), url.getFile());
		System.out.printf(" Authority: %-32s Query: %-32s\n", url.getAuthority(), url.getQuery());
		System.out.println(" User Info: " + url.getUserInfo());
	}

	public static void displayInetAddressInformation(InetAddress address) {
		System.out.println(address);
		System.out.println("CanonicalHostName: " + address.getCanonicalHostName());
		System.out.println("HostName: " + address.getHostName());
		System.out.println("HostAddress: " + address.getHostAddress());
	}

	public static void displayReachableAddresses(String URLAddress) {
		InetAddress[] addresses = new InetAddress[0];
		try {
			addresses = InetAddress.getAllByName(URLAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		for (InetAddress inetAddress : addresses) {
			try {
				if (inetAddress.isReachable(10000)) {
					System.out.println(inetAddress + " is reachable");
				} else {
					System.out.println(inetAddress + " is not reachable");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
