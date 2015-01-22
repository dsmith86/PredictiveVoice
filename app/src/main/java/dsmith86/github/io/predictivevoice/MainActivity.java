package dsmith86.github.io.predictivevoice;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private Button mainButton;
    private TextView currentSpeechTextView;
    private ListView suggestionListView;
    private SpeechRecognizer speechRecognizer;
    Intent speechRecognitionIntent;

    Boolean listening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configure();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configure() {
        listening = false;

        mainButton = (Button)findViewById(R.id.mainButton);
        currentSpeechTextView = (TextView)findViewById(R.id.currentSpeechTextView);
        suggestionListView = (ListView)findViewById(R.id.suggestionListView);

        mainButton.setOnClickListener(mainButtonOnClickListener);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(recognitionListener);

        speechRecognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
    }

    private void toggleSpeechState() {
        listening = !listening;

        if (listening) {
            mainButton.setText(getResources().getString(R.string.button_stopListening));
            speechRecognizer.startListening(speechRecognitionIntent);
        } else {
            mainButton.setText(getResources().getString(R.string.button_listen));
            speechRecognizer.stopListening();
        }
    }

    private Button.OnClickListener mainButtonOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleSpeechState();
        }
    };

    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d("debug", "ready for speech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d("debug", "beginning speech");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d("debug", "buffer received");
        }

        @Override
        public void onEndOfSpeech() {
            toggleSpeechState();
        }

        @Override
        public void onError(int error) {
            switch (error) {
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    if (listening) {
                        currentSpeechTextView.setText(getResources().getString(R.string.status_nudge));
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> resultsArray = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            currentSpeechTextView.setText(resultsArray.get(0));
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            ArrayList<String> resultsArray = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            currentSpeechTextView.setText(resultsArray.get(0));
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d("debug", "speech event");
        }
    };
}
