package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class FriendDetailActivity extends AppCompatActivity {
    private TextView clumsiness;
    private TextView gymFrequency;
    private TextView awesome;
    private TextView trust;
    private TextView moneyOwed;
    private EditText name;
    private EditText dinero;
    private Switch iAwesome;
    private SeekBar clumsyScale;
    private SeekBar swoleScale;
    private RatingBar trustScale;
    private Button save;
    private Friend friend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frienddetail);

        Intent lastIntent = getIntent();
         friend = lastIntent.getParcelableExtra(FriendListActivity.EXTRA_FRIEND);
        wireWidgets();
        setWidgets(friend);
        onClickListeners();


    }

    private void onClickListeners() {
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        friend.setName(name.getText().toString());
        friend.setAwesomeness(iAwesome.isChecked());
        friend.setGymFrequency(swoleScale.getProgress());
        friend.setClumsiness(clumsyScale.getProgress());
        friend.setTrustworthiness(trustScale.getProgress());
friend.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
        name.setText(name.getText());
        iAwesome.setChecked(iAwesome.isChecked());
        swoleScale.setProgress(swoleScale.getProgress());
        clumsyScale.setProgress(clumsyScale.getProgress());
        trustScale.setProgress(trustScale.getProgress());
        moneyOwed.setText((dinero.getText()));

friend.setMoneyOwed(Double.parseDouble(moneyOwed.getText().toString()));
        Backendless.Persistence.save( friend, new AsyncCallback<Friend>() {
            public void handleResponse( Friend response )
            {
                finish();
            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(FriendDetailActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

});


    }

    private void setWidgets(Friend friend) {
        clumsiness.setText("They trippin?");
        gymFrequency.setText("How swole is they");
        awesome.setText("They chilling?");
        trust.setText("Trust them with le formioli?");
        moneyOwed.setText("Cuanto dinero te owe?");

        if(friend !=null){
            dinero.setText(friend.getMoneyOwed()+"");
        name.setText(friend.getName());
        iAwesome.setChecked(friend.isAwesomeness());
        swoleScale.setProgress((int) friend.getGymFrequency());
        clumsyScale.setProgress(friend.getClumsiness());
        trustScale.setProgress(friend.getTrustworthiness());}
        else{
            this.friend = new Friend();


        }


    }

    private void wireWidgets() {
        clumsiness = findViewById(R.id.textView_main_clumsy);
        gymFrequency = findViewById(R.id.textView_main_conquefrequencia);
        awesome = findViewById(R.id.textView_main_Awesome);
        trust = findViewById(R.id.textView_main_estashonesto);
        moneyOwed = findViewById(R.id.textView_main_tuowesmuchodinero);
        name = findViewById(R.id.editText_main_Name);
        dinero = findViewById(R.id.editText_main_dineroquetumeowes);
        iAwesome = findViewById(R.id.switch_main_awesomeyesno);
        clumsyScale = findViewById(R.id.seekBar_main_Clumsiness);
        swoleScale = findViewById(R.id.seekBar_main_gFrequency);
        trustScale = findViewById(R.id.ratingBar_main_unodostrescuatrocinco);
        save = findViewById(R.id.button_main_save);
    }
}
