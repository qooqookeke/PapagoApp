package com.qooke.papagoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.qooke.papagoapp.adapter.TextAdapter;
import com.qooke.papagoapp.config.Config;
import com.qooke.papagoapp.model.History;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText editText;
    Button button;
    TextView txtResult;

    String sentence;
    String result;
    String target;

    ArrayList<History> historyArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바 보이기
        getSupportActionBar().setTitle("Papago App");

        radioGroup = findViewById(R.id.radioGroup);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        txtResult = findViewById(R.id.txtResult);

        // 번역하기 버튼을 눌렀을때 실행할 코드
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 어떤 언어로 번역할지의 정보를 가져온다.(라디오 버튼)
                int radioButtonId = radioGroup.getCheckedRadioButtonId();

                target = "";

                if(radioButtonId == R.id.radioBtn1) {
                    // 영어
                    target = "en";
                } else if (radioButtonId == R.id.radioBtn2) {
                    // 중국어 간체
                    target = "zh-CN";
                } else if (radioButtonId == R.id.radioBtn3) {
                    // 중국어 번체
                    target = "zh-TW";
                } else if (radioButtonId == R.id.radioBtn4) {
                    // 태국어
                    target = "th";
                } else {
                    Toast.makeText(MainActivity.this, "번역할 언어를 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. 유저가 입력한 번역할 문장을 가져온다.(에디트 텍스트)
                sentence = editText.getText().toString().trim();

                if(sentence.isEmpty()) {
                    Toast.makeText(MainActivity.this, "번역할 문장을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. 파파고 API 호출(네트워크)
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                String url = "https://openapi.naver.com/v1/papago/n2mt";

                JSONObject body = new JSONObject();

                try {
                    body.put("source", "ko");
                    body.put("target", target);
                    body.put("text", sentence);
                } catch (JSONException e) {
                    //유저한테 알려주기
                    return;
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            result = response.getJSONObject("message").getJSONObject("result").getString("translatedText");
                            txtResult.setText(result);

                            // 메모리에 저장(저장 공간 만들기(new) 및 객체생성(생성자), model 패키지에 저장)
                            History history = new History(sentence, result, target);
                            // add함수 사용시(int index, history element)선택으로 역순으로 처리함(오래된게 아래로) index=0
                            historyArrayList.add(0, history);

                        } catch (JSONException e) {
                            // 유저한테 알리기
                            return;
                        }

                    }

                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("X-Naver-Client-Id", Config.X_NAVER_CLIENT_ID);
                        headers.put("X-Naver-Client-Secret", Config.X_NAVER_CLIENT_SECRET);
                        return headers;
                    }
                };

                queue.add(request);

            }
        });
    }

    // 액션바에 아이콘 나오게 하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 액션바 아이콘 눌렀을때 처리할 행동(액티비티 이동하기)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.menuhistory){
            // 새로운 액티비티에 데이터 보내면서 열기
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("historyArrayList", historyArrayList);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
