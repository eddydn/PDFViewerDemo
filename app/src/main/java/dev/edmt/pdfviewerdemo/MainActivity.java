package dev.edmt.pdfviewerdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfView = (PDFView)findViewById(R.id.pdfView);
        //This is function read PDF from Assets
        //pdfView.fromAsset("sample.pdf").load();

        //This is function read PDF from URL
        //new RetrievePDFStream().execute("http://ancestralauthor.com/download/sample.pdf"); // Or any url direct PDF from internet

        //This is function read PDF from bytes
        new RetrievePDFBytes().execute("http://ancestralauthor.com/download/sample.pdf");


    }


    class RetrievePDFBytes extends AsyncTask<String,Void,byte[]>
    {

        @Override
        protected byte[] doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200)
                {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e)
            {
                return null;
            }
            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            pdfView.fromBytes(bytes).load();
        }
    }

//    class RetrievePDFStream extends AsyncTask<String,Void,InputStream>
//    {
//
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            InputStream inputStream = null;
//            try{
//                URL url = new URL(strings[0]);
//                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//                if(urlConnection.getResponseCode() == 200)
//                {
//                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                }
//            }
//            catch (IOException e)
//            {
//                return null;
//            }
//            return inputStream;
//        }
//
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//            pdfView.fromStream(inputStream).load();
//        }
//    }
}
