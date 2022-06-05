package com.example.httpget;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {
    Button btn_get, btn_query;
    EditText edit_url, edit_query;
    TextView tv_result;
    ScrollView result_area;
    FloatingActionButton fab_scrollToBottom, fab_scrollToTop;
    Document document;
    String fetchedHtml = "";
    OnFetchHtml listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_get = findViewById(R.id.btn_get);
        btn_query = findViewById(R.id.btn_query);
        edit_url = findViewById(R.id.edit_url);
        edit_query = findViewById(R.id.edit_query);
        tv_result = findViewById(R.id.tv_result);
        result_area = findViewById(R.id.result_area);
        fab_scrollToBottom = findViewById(R.id.fab_scrollToBottom);
        fab_scrollToTop = findViewById(R.id.fab_scrollToTop);
        listener = new OnFetchHtml() {
            @Override
            public void onFetchHtml(String html) {
                fetchedHtml = html;
            }
        };

        edit_url.setText("https://yahoo.co.jp");

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUrl = edit_url.getText().toString();
                new AsyncHttpGet(MainActivity.this, listener).execute(inputUrl);
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DocumentBuilder builder;
//                String targetText = tv_result.getText().toString();
//                if(targetText == "") return;
//                // domへ変換
//                // https://blog.kujira-station.com/201406071311
//                try {
//                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                    builder = factory.newDocumentBuilder();
//                    document = builder.parse(targetText);
//                    String resultText = document.getElementsByTagName("h1").item(1).getTextContent().toString();
//                    Log.d("MyLog", resultText);
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                }
                if(fetchedHtml == "" || edit_query.getText().toString().equals("")) return;
                StringBuilder sb = new StringBuilder();
                if(document == null) {
                    document = Jsoup.parse(fetchedHtml);
                }
                Elements result = document.select(edit_query.getText().toString());
                Log.d("MyLog", result.toString());
                for(int i = 0; i < result.size(); i++) {
                    sb.append(result.get(i).toString() + "\n");
                }
                tv_result.setText(sb.toString());

            }
        });

        fab_scrollToBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int maxScroll = result_area.getScrollY();
//                result_area.scrollTo(0, maxScroll);
                result_area.fullScroll(View.FOCUS_DOWN);
            }
        });
        fab_scrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int maxScroll = result_area.getScrollY();
//                result_area.scrollTo(0, maxScroll);
                result_area.fullScroll(View.FOCUS_UP);
            }
        });
    }

}