package dsmith86.github.io.predictivevoice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private Button mainButton;
    private TextView currentSpeechTextView;
    private ListView suggestionListView;

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
    }

    private Button.OnClickListener mainButtonOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            listening = !listening;

            if (listening) {
                mainButton.setText(getResources().getString(R.string.button_stopListening));
            } else {
                mainButton.setText(getResources().getString(R.string.button_listen));
            }
        }
    };
}
