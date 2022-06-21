package com.ameya326.texttospeecheg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech t1;
    TextView state;
    SpeechRecognizer speechRecognizer;
    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = (TextView)findViewById(R.id.state);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    t1.setLanguage(Locale.US);
                    String str = "2*2";
                    t1.speak(str, TextToSpeech.QUEUE_FLUSH, null);

                    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
                    Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    startActivityForResult(speechRecognizerIntent,SPEECH_REQUEST_CODE);




                }

            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> ans = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (Integer.parseInt(ans.get(0)) == 4) {
                String correct = "You are correct";

                t1.speak(correct, TextToSpeech.QUEUE_FLUSH, null);

            } else {
                t1.speak("You are wrong", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}