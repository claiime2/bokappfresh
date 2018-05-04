package com.example.user.lvhnreadbookapp;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.ObjectMapperSingleton;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadPositionListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class DocsachActivity extends AppCompatActivity implements OnHighlightListener, ReadPositionListener {
    File file;
    private static FolioReader folioReader;
    private static final String LOG_TAG = DocsachActivity.class.getSimpleName();
    SQLiteJson json;
    ArrayList<String> arr=new ArrayList<>();
    private static String[] intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docsach);
        json=SQLiteJson.getHelper(this);
        json.loadData(arr);
        Log.d("sizejson",String.valueOf(arr.size()));
        folioReader=FolioReader.getInstance(getApplicationContext());
        folioReader.setOnHighlightListener(this);
<<<<<<< HEAD
        folioReader.setReadPositionListener(this); // tạo triển khai ẩn danh của ReadPositionListener
=======
        folioReader.setReadPositionListener(this);
>>>>>>> daf5f08cf3bd904fa42babe34153193b6e9058ee
        String path;
        path=getIntent().getStringExtra("path");
        folioReader.openBook(path);
        intent=path.split("/");
        getHighlightsAndSave();
        getLastReadPosition();
    }
    private void getLastReadPosition() {

        new Thread(new Runnable() {
            @Override
            public void run() {
<<<<<<< HEAD
                //Xây dựng việc triển khai ReadPosition
=======
>>>>>>> daf5f08cf3bd904fa42babe34153193b6e9058ee

                ObjectReader objectReader = ObjectMapperSingleton.getObjectMapper().reader();
                ReadPosition readPosition = null;
                ReadPositionImpl readPosition1=new ReadPositionImpl();
                readPosition1.setBookId(json.getBookID(intent[intent.length-1]));
                readPosition1.setChapterId(json.getChapID(intent[intent.length-1]));
                readPosition1.setChapterIndex(json.getChapIndex(intent[intent.length-1]));
                readPosition1.setChapterHref(json.getChapHref(intent[intent.length-1]));
                readPosition1.setUsingId(json.getUsingID(intent[intent.length-1]));
                readPosition1.setValue(json.getValue(intent[intent.length-1]));
                String s=readPosition1.toJson();
                Log.d("position",s);

                try {
                    readPosition = objectReader.forType(ReadPositionImpl.class).readValue(s);
                    Log.d("docreadposition",readPosition.toJson());
                } catch (IOException e) {
                    e.printStackTrace();
                }
<<<<<<< HEAD
                folioReader.setReadPosition(readPosition); // mở sách
=======
                folioReader.setReadPosition(readPosition);
>>>>>>> daf5f08cf3bd904fa42babe34153193b6e9058ee
                Log.d("setposition","qua buoc");
            }
        }).start();
    }
    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<HighLight> highlightList = null;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
<<<<<<< HEAD
                    highlightList = objectMapper.readValue(loadAssetTextAsString("highlights/highlights_data.json"),
                            new TypeReference<List<HighlightData>>() {});
=======
                    highlightList = objectMapper.readValue(loadAssetTextAsString("highlights/highlights_data.json"), new TypeReference<List<HighlightData>>() {});
>>>>>>> daf5f08cf3bd904fa42babe34153193b6e9058ee
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    folioReader.saveReceivedHighLights(highlightList, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }
    @Override
<<<<<<< HEAD
    // Làm điều gì đó với dòng highlight nhận được
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

=======
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
//        Toast.makeText(this, "highlight id = " + highlight.getUUID() + " type = " + type, Toast.LENGTH_SHORT).show();
>>>>>>> daf5f08cf3bd904fa42babe34153193b6e9058ee
    }

    @Override
    public void saveReadPosition(ReadPosition readPosition) {

        json.updateBookID(intent[intent.length-1],readPosition.getBookId());
        json.updateChapID(intent[intent.length-1],readPosition.getChapterId());
        json.updateChapIndex(intent[intent.length-1],readPosition.getChapterIndex());
        json.updateChapHref(intent[intent.length-1],readPosition.getChapterHref());
        json.updateUsingID(intent[intent.length-1],readPosition.isUsingId());
        json.updateValue(intent[intent.length-1],readPosition.getValue());
//        Toast.makeText(this, "ReadPosition = " + readPosition.toJson(), Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "-> ReadPosition = " + readPosition.toJson());
//        FolioReader.clear();
    }
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }
    private String loadAssetTextAsString(String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("HomeActivity", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("HomeActivity", "Error closing asset " + name);
                }
            }
        }
        return null;
    }
}

