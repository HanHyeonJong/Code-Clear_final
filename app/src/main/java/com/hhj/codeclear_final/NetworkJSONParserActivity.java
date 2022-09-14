package com.hhj.codeclear_final;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

// JSON표기법 : xml 표현을 경량화시켜 정보만을 표현함으로 파싱의 속도 향상

/*


{
currentCount: 10,
data: [
{
경도: "127.0738885",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 덕진구 기린대로 1030 (여의동)",
병상수: 0,
사업장명: "(사)대한산업보건협회전북산업보건센타 전북의원",
상세영업상태명: "영업중",
소재지전화: "063-225-1242",
업태구분명: "의원",
위도: "35.86839875",
의료기관종별명: "의원",
의료인수: 32,
인허가일자: "1989-06-03",
입원실수: 0,
지번주소: "전라북도 전주시 덕진구 여의동 654-1",
진료과목내용명: "내과+외과+흉부외과+영상의학과+직업환경의학과",
총면적: "1468.00"
},
{
경도: "127.1477319",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 덕진구 백제대로 720 (인후동2가)",
병상수: 17,
사업장명: "(사)인구보건복지협회전북지회가족보건의원",
상세영업상태명: "영업중",
소재지전화: "063-240-2345",
업태구분명: "의원",
위도: "35.84485080",
의료기관종별명: "의원",
의료인수: 10,
인허가일자: "1974-01-16",
입원실수: 4,
지번주소: "전라북도 전주시 덕진구 인후동2가 1557-1",
진료과목내용명: "내과+산부인과+소아청소년과+영상의학과+진단검사의학과+가정의학과",
총면적: "1493.56"
},
{
경도: "127.1477319",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 덕진구 백제대로 720 (인후동2가)",
병상수: 0,
사업장명: "(사)인구보건복지협회전북지회가족치과의원",
상세영업상태명: "영업중",
소재지전화: "063-240-2331",
업태구분명: "치과의원",
위도: "35.84485080",
의료기관종별명: "치과의원",
의료인수: 13,
인허가일자: "2007-11-23",
입원실수: 0,
지번주소: "전라북도 전주시 덕진구 인후동2가 1557-1",
진료과목내용명: "치과+예방치과",
총면적: "120.00"
},
{
경도: "127.1216206",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 덕진구 사평로 40, 5층 (덕진동1가, 건강검진센터)",
병상수: 0,
사업장명: "(사)한국건강관리협회 건강치과의원",
상세영업상태명: "영업중",
소재지전화: "063-259-8896",
업태구분명: "치과의원",
위도: "35.84115878",
의료기관종별명: "치과의원",
의료인수: 2,
인허가일자: "2013-05-02",
입원실수: 0,
지번주소: "전라북도 전주시 덕진구 덕진동1가 1408-3",
진료과목내용명: "치과",
총면적: "146.00"
},
{
경도: "127.1216206",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 덕진구 사평로 40 (덕진동1가)",
병상수: 18,
사업장명: "(사)한국건강관리협회건강증진의원",
상세영업상태명: "영업중",
소재지전화: "063-259-8900",
업태구분명: "의원",
위도: "35.84115878",
의료기관종별명: "의원",
의료인수: 44,
인허가일자: "1982-12-10",
입원실수: 2,
지번주소: "전라북도 전주시 덕진구 덕진동1가 1408-3",
진료과목내용명: "내과+외과+산부인과+영상의학과+진단검사의학과",
총면적: "4907.79"
},
{
경도: "127.1441029",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 완산구 팔달로 255 (서노송동)",
병상수: 0,
사업장명: "21세기한의원",
상세영업상태명: "영업중",
소재지전화: "063-273-1075",
업태구분명: "한의원",
위도: "35.82481331",
의료기관종별명: "한의원",
의료인수: 1,
인허가일자: "1995-10-17",
입원실수: 0,
지번주소: "전라북도 전주시 완산구 서노송동 639-36",
진료과목내용명: "한방내과+한방부인과+한방소아과+한방안·이비인후·피부과+한방신경정신과+한방재활의학과+사상체질과+침구과",
총면적: "95.38"
},
{
경도: "127.1302290",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 완산구 모악로 4689, 2층 201,202일부호 (평화동2가)",
병상수: 9,
사업장명: "365평화한의원",
상세영업상태명: "영업중",
소재지전화: "063-717-8875",
업태구분명: "한의원",
위도: "35.78812272",
의료기관종별명: "한의원",
의료인수: 5,
인허가일자: "2020-10-05",
입원실수: 5,
지번주소: "전라북도 전주시 완산구 평화동2가 312-5",
진료과목내용명: "한방내과+한방부인과+한방소아과+한방안·이비인후·피부과+한방신경정신과+한방재활의학과+사상체질과+침구과",
총면적: "368.96"
},
{
경도: "127.1234387",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 완산구 백제대로 250, 3,4층 (중화산동2가)",
병상수: 6,
사업장명: "PSI한솔비뇨기과의원",
상세영업상태명: "영업중",
소재지전화: "063-227-7575",
업태구분명: "의원",
위도: "35.81536127",
의료기관종별명: "의원",
의료인수: 4,
인허가일자: "2016-01-20",
입원실수: 4,
지번주소: "전라북도 전주시 완산구 중화산동2가 649-8",
진료과목내용명: "비뇨의학과",
총면적: "667.78"
},
{
경도: "127.1316346",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 완산구 소대배기로 14 (평화동2가)",
병상수: 29,
사업장명: "가온의료소비자생활협동조합 세나여성의원",
상세영업상태명: "영업중",
소재지전화: "063-229-8245",
업태구분명: "의원",
위도: "35.78713241",
의료기관종별명: "의원",
의료인수: 4,
인허가일자: "2002-11-30",
입원실수: 26,
지번주소: "전라북도 전주시 완산구 평화동2가 890-3",
진료과목내용명: "마취통증의학과+산부인과",
총면적: "2125.92"
},
{
경도: "127.1068060",
데이터기준일자: "2022-06-30",
도로명주소: "전라북도 전주시 완산구 범안3길 11-4 (효자동2가)",
병상수: 0,
사업장명: "가족사랑의원",
상세영업상태명: "영업중",
소재지전화: "063-274-0904",
업태구분명: "의원",
위도: "35.81212193",
의료기관종별명: "의원",
의료인수: 1,
인허가일자: "2004-03-02",
입원실수: 0,
지번주소: "전라북도 전주시 완산구 효자동2가 1166-4",
진료과목내용명: "내과+소아청소년과+병리과+가정의학과",
총면적: "62.73"
}
],
matchCount: 1005,
page: 1,
perPage: 10,
totalCount: 1005
} */

// JSONArray 객체, JSONObject 객체
public class NetworkJSONParserActivity extends Activity {
	TextView tvJson;
	Button btnParser1;

	String url = "https://api.odcloud.kr/api/15102193/v1/uddi:dfde0161-919f-4d7c-b9a4-e11381958e12";
	String serviceKey = "hxLx4qhI7iilwcT1jaRXtEgNfXTQpcrRGaz25qKsXNy3Ey3dTFgKB3TNMmskHY480C9DSzYiP4jhTdG8CRXEpg%3D%3D";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_json_parser);

		tvJson = (TextView) findViewById(R.id.tvJson);
		btnParser1 = findViewById(R.id.btnParser1);

		btnParser1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//네트워크에 접속하여 다운로드 하는 쓰레드를 작동시킨다.
				HospitalTask hospitalTask = new HospitalTask();
				hospitalTask.execute(url, serviceKey);
			}
		});

	}

	class Hospital{
		String title; //사업장명: "PSI한솔비뇨기과의원",
		String latitude;  //위도: "35.81536127",
		String longitude; //경도: "127.1234387",
		String address; // 도로명주소: "전라북도 전주시 완산구 백제대로 250, 3,4층 (중화산동2가)",
		String tel;     //소재지전화: "063-227-7575",

		public Hospital() {
		}

		public Hospital(String title, String latitude, String longitude, String address, String tel) {
			this.title = title;
			this.latitude = latitude;
			this.longitude = longitude;
			this.address = address;
			this.tel = tel;
		}

		@Override
		public String toString() {
			return "Hospital{" +
					"title='" + title + '\'' +
					", latitude='" + latitude + '\'' +
					", longitude='" + longitude + '\'' +
					", address='" + address + '\'' +
					", tel='" + tel + '\'' +
					'}';
		}
	}

	//실제 Open API 서버에 접속하여 다운로드하는 코드
	class HospitalTask extends AsyncTask<String, Integer, String> {
		ArrayList<Hospital> hospitalArrayList = new ArrayList<Hospital>();

		@Override
		protected void onPreExecute() {
			Log.d("TAG", "xml문서를 다운받기전의 사전동작");
		}

		@Override
		protected String doInBackground(String... urls) {
			String result = "Ok"; //네트웍작업과 파싱이 정상적으로 수행되면 "Ok", 중간에 오류가 발생하면 "Error"

			Log.d("TAG", "xml문서를 다운받기 시작");
			//가변인자는 내부적으로 배열로 접근하열 사용한다. 배열:urls, urls[0]

			//다운받는 xml문서를 임시저장
			StringBuilder sbJson = new StringBuilder();
		    String strJson = "";
			try {
				//urls는 가변인자이므로 execute(url)값이 배열의 0번째에 있다.
				String url = urls[0];
				String serviceKey = urls[1];
				String fullUrl = url + "?page=1&perPage=10&serviceKey=" + serviceKey;
				//Log.d("TAG", "최초요청 url : " + fullUrl);

				URL hUrl = new URL(fullUrl);

				HttpURLConnection connection = (HttpURLConnection) hUrl.openConnection();
				// 서버에 접속해서 최초응답을 받았다.
				if( connection != null){ // 정상적인 상태
					connection.setConnectTimeout(10000);
					connection.setUseCaches(false);
					if( connection.getResponseCode() == HttpURLConnection.HTTP_OK){
						BufferedReader br = new BufferedReader(new InputStreamReader( connection.getInputStream()));
						String jsonLine = null;

						//Log.d("TAG", "다운받은 xml문서내용-시작--------------");
						int lineNumber = 1;
						while(true){
							jsonLine = br.readLine();
							if( jsonLine == null){
								break;
							}
							//Log.d("TAG", lineNumber + " " + jsonLine + "\n");
							lineNumber++;

							sbJson.append(jsonLine);
						}
						br.close();
						//Log.d("TAG", "다운받은 xml문서내용--끝---------------");

					}
					connection.disconnect();
				}

				//첫번째 요청에 의해 다운받은 json문서 내용이다.
				//첫번째 요청 결과에서 전체 데이타 건수를 얻어내야 한다.

				strJson = sbJson.toString();

				JSONObject hospitals = null;
				int totalCount = 0;
				try {
					//최초요청응답내용에서 전체데이타 갯수만 구한다.
					hospitals = new JSONObject(strJson);
					totalCount = hospitals.getInt("totalCount");
					//Log.d("TAG", "앞으로 다운받을 전체 데이타 갯수 : " + totalCount);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				//앞으로 요청해야할 횟수를 구했으므로 반복해서 요청을 하자//
				int perPage = 10;
				int endPage = totalCount / perPage;     //마지막 페이지 번호가 1005 / 10 ==> 100
				if( totalCount % perPage > 0 ){
					endPage++;
				}

				for(int page = 1; page < endPage; page ++){
					sbJson = new StringBuilder();

					fullUrl = url + "?page=" + page + "&perPage=10&serviceKey=" + serviceKey;
					//Log.d("TAG", page + " 번째요청 url : " + fullUrl);

					hUrl = new URL(fullUrl);

					connection = (HttpURLConnection) hUrl.openConnection();
					// 서버에 접속해서 최초응답을 받았다.
					if( connection != null){ // 정상적인 상태
						connection.setConnectTimeout(10000);
						connection.setUseCaches(false);
						if( connection.getResponseCode() == HttpURLConnection.HTTP_OK){
							BufferedReader br = new BufferedReader(new InputStreamReader( connection.getInputStream()));
							String jsonLine = null;

							//Log.d("TAG", "다운받은 xml문서내용-시작--------------");
							int lineNumber = 1;
							while(true){
								jsonLine = br.readLine();
								if( jsonLine == null){
									break;
								}
								Log.d("TAG", lineNumber + " " + jsonLine + "\n");
								lineNumber++;

								sbJson.append(jsonLine);
							}
							br.close();
							//Log.d("TAG", "다운받은 xml문서내용--끝---------------");

						}
						connection.disconnect();
					}

					//첫번째 요청에 의해 다운받은 json문서 내용이다.
					//첫번째 요청 결과에서 전체 데이타 건수를 얻어내야 한다.

					strJson = sbJson.toString();
					try {
						//최초요청응답내용에서 전체데이타 갯수만 구한다.
						hospitals = new JSONObject(strJson);
						int curPage = hospitals.getInt("page");
						//Log.d("TAG", curPage + " 페이지에서 정보를 추출한다.");

						//현재페이지에 저장된 병원정보의 갯수
						int count = hospitals.getInt("currentCount");

						//병원정보배열
						JSONArray hospitalArray = hospitals.getJSONArray("data");

						for( int i=0; i < count;  i++){
							JSONObject hospitalObject = hospitalArray.getJSONObject(i);
							String title = hospitalObject.getString("사업장명"); //: "(사)대한산업보건협회전북산업보건센타 전북의원",
							//병원명에서 검색
							if( title.contains("정신건강의학") ){
								String latitude = hospitalObject.getString("위도");  //"35.81536127",
								String longitude = hospitalObject.getString("경도");  //경도: "127.1234387",
								String address = hospitalObject.getString("도로명주소");// 도로명주소: "전라북도 전주시 완산구 백제대로 250, 3,4층 (중화산동2가)",
								String tel = hospitalObject.getString("소재지전화");  //소재지전화: "063-227-7575",

								Hospital hospital = new Hospital(title, latitude, longitude, address, tel);

								hospitalArrayList.add(hospital);
							}
						}

						//Log.d("TAG", curPage + " 페이지 파싱 왼료.");

					} catch (JSONException e) {
						result = "Error";
						e.printStackTrace();
					}
				}

			} catch (MalformedURLException e) {
				result = "Error";
				e.printStackTrace();
			} catch (NetworkOnMainThreadException e) {
				result = "Error";
				Log.w("TAG", "메인쓰레드 네트워크 작업 오류 : " + e.getMessage());
			} catch (IOException e) {
				result = "Error";
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			//UI 갱신 코드
			Log.d("TAG", "onProgressUdpate(...) xml문서를 다운받는 과정중에 UI변경할 것이 있다면 여기에 작성");
		}

		@Override
		protected void onPostExecute(String result){
			Log.d("TAG", "onPostExecute() xml문서를 다운이 완료되었으므로 다운받는 내용을 UI에 보여준다.");
			if (result.equals("Ok")){
				tvJson.setText(hospitalArrayList.toString());
			}else{
				tvJson.setText("네트웍요청이나 파싱중 오류가 발생했습니다.");
			}
		}

		@Override
		protected void onCancelled() {
			Log.d("TAG", "onCancelled() xml문서를 다운을 중간에 취소했을때 작동");
		}
	}
}
