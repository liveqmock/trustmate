package com.pgmate.payment.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import biz.trustnet.common.comm.client.Socket;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.util.URLEncDec;

/**
 * @author Administrator
 *
 */

	public class Web implements Socket{


		private String METHOD 			= "GET";	//전달방법
		private String URL				= "";		//URL 주소
		private String contentType		= "";
		private boolean INPUT			= true;		//전송 파라미터 여부
		private boolean OUTPUT 			= true;		//수신 파라미터 여부
		private boolean connect 		= false;	//URL 정상접속 여부
		public int HTTP_CODE			= 0;		//URL 응답 코드
		public String HTTP_STATUS 		= "";		//HTTP 상태값
		public StringBuffer HTTP_MSG 	= new StringBuffer();	//HTTP 수신 파리미터 버퍼

		private HttpURLConnection conn 	= null;
	    private URL url					= null;
	    private int timeout				= 50000;


		public Web(){
		}

		/**
		 * URL 및 METHOD 에 대한 설정 METHOD 는 기본적으로 POST로 설정된다.
		 * @param URL
		 * @param METHOD
		 */
		public void setFormAction(String URL,String METHOD){
			this.URL 	= URL;
			this.METHOD = METHOD;
		}

		/**
		 * URL 을 통한 파라미터 전달 및 수신에 대한 설정 true 인 경우 있음으로 설정됨.
		 * 기본값은 모두 true 로 설정되어 있다.
		 * @param INPUT
		 * @param OUTPUT
		 */
		public void setInOut(boolean INPUT,boolean OUTPUT){
			this.INPUT 	= INPUT;
			this.OUTPUT = OUTPUT;
		}

		public void setContentType(String contentType){
			this.contentType = contentType;
		}

		public void setTimeout(int timeout){
			this.timeout = timeout;
		}

		/**
		 * request 는 name&value pair 로 구성되어야 한다.
		 * GET 방식 접속시는 URL+?+request 로 구성하여 전송한다.
		 */
		public String connect(String request)throws Exception{
			initURL(request);
			try{
				url = new URL(URL);

				if(URL.toLowerCase().startsWith("https://"))
					conn = (HttpsURLConnection) url.openConnection();
				else
					conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod(METHOD);

				conn.setConnectTimeout(timeout);
				if(METHOD.equals("POST")){
					if(contentType.equals("")){
						conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					}else{
						conn.setRequestProperty("Content-Type", contentType);
					}
					conn.setRequestProperty("Content-Length",CommonUtil.toString(request.getBytes().length));
				}else{
					conn.setRequestProperty("Content-Type", "text/plain; charset=Shift_JIS");
				}
				conn.setDoOutput(OUTPUT);
			    conn.setDoInput(INPUT);

			    if(OUTPUT){
			    	send(request);
			    }
			    getHTTPEnv();
			    if(redirect(HTTP_CODE)) moveURL(conn.getHeaderField("Location"));
			    if(INPUT) 	recv();

			    conn.disconnect();
			}catch(Exception e){
				Log.debug("log.root",CommonUtil.getExceptionMessage(e),this);
				throw new Exception(e);
			}

			return HTTP_MSG.toString();
		}



		/**
		 * BufferedWriter 방식을 통한 파라미터 전달
		 * @param request
		 * @throws Exception
		 */
		private void send(String request)throws Exception{

			BufferedWriter out = null;
			try{
				out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				out.write(request);
				out.flush();
				out.close();
			}catch(Exception e){
				Log.debug("log.root",CommonUtil.getExceptionMessage(e),this);
				throw new Exception(e);
			}
		}

		/**
		 * BufferedReader 를 통한 파라미터 및 응답 메세지 수신
		 * @throws Exception
		 */
		private void recv()throws Exception{
			BufferedReader in 	= null;
		    try{
		    	in = new BufferedReader( new InputStreamReader( conn.getInputStream()));
		    	String inputLine 	= "";
		        while ((inputLine = in.readLine()) != null) {
		        	HTTP_MSG.append(inputLine+"\n");
		        }
		    }catch (IOException e) {
		    	Log.debug("log.root",CommonUtil.getExceptionMessage(e),this);
		         throw new Exception(e);
		    }
		}

		/**
		 * HTTP_MEVED_PERM || HTTP_MOVED_TEMP 수신시 redirect
		 * @param code
		 * @return
		 */
		private boolean redirect(int code) {
			if (code == HttpURLConnection.HTTP_MOVED_PERM ||code == HttpURLConnection.HTTP_MOVED_TEMP)
		        return true;
			else
		        return false;
		}

		/**
		 * redirect 시 응답 location 에 따른 Connection 전환
		 * @param location
		 * @throws IOException
		 */
		private void moveURL(String location)throws IOException {
			url = new URL(location);

			if(location.toLowerCase().startsWith("https://"))
				conn = (HttpsURLConnection) url.openConnection();
			else
				conn = (HttpURLConnection) url.openConnection();
		}


		/**
		 * GET,POST 에 따른 request 형식 생성 및 http:// 프로토콜 입력 여부에 따른 값 변경
		 * @param request
		 */
		private void initURL(String request){
			if(!URL.toLowerCase().startsWith("https://")){
				if(!URL.toLowerCase().startsWith("http://")){
					URL = "http://"+URL;
				}
			}

			if(METHOD.equals("GET")){
				OUTPUT = false;
				if(!URL.endsWith(request)){
					URL = URL+"?"+request;
				}
			}

		}

		/**
		 * HTTP 연결에 대한 응답 코드 및 응답 메세지 확인
		 */
		private void getHTTPEnv(){

			try{
				HTTP_CODE = conn.getResponseCode();
				HTTP_STATUS += "HTTP_RESPONSE_CODE="+HTTP_CODE;
				HTTP_STATUS += "&HTTP_RESPONSE_MESSAGE="+URLEncDec.encode(conn.getResponseMessage(),"utf-8");

				Log.debug("log.root","HTTP_STATUS="+HTTP_STATUS,this);
				//System.out.println(HTTP_STATUS);

			}catch(Exception e){
				Log.debug("log.root",CommonUtil.getExceptionMessage(e),this);

			}
		}

}
