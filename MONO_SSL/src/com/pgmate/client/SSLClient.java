package com.pgmate.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import biz.trustnet.common.util.Property;

/**
 * @author Administrator
 *
 */
public class SSLClient {

	public SSLClient(){
	}
	
	public void start(){
		 try {
			 System.setProperty("javax.net.ssl.keyStore","/home/lifeone/MONO_SSL/conf/pgmatekeystore");
			System.setProperty("javax.net.ssl.keyStorePassword","pgmate123456");
			 System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
	            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("112.175.48.41", 9999);
	            sslsocket.startHandshake();  
	            String contents = "0004TEST";
	            InputStream inputstream = new ByteArrayInputStream(contents.getBytes("UTF-8")); 
	            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
	            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

	            OutputStream outputstream = sslsocket.getOutputStream();
	            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
	            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

	            String string = null;
	            while ((string = bufferedreader.readLine()) != null) {
	                bufferedwriter.write(string + '\n');
	                bufferedwriter.flush();
	            }
	            System.out.println(bufferedwriter.toString());
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }

		
	}
	
	
	public static void main(String[] args){
		com.pgmate.client.SSLClient ssl = new com.pgmate.client.SSLClient();
		ssl.start();
	}
}
