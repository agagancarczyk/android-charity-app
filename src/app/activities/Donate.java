package app.activities;

import java.util.ArrayList;
import java.util.List;

import app.charity.R;
import app.http.Response;
import app.main.CharityApp;
import app.models.Charity;
import app.models.Donation;
import app.models.CharityServiceAPI;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Donate extends Activity implements Response<Donation> {
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private TextView amountText;
    private TextView amountTotal;
    private Spinner charityDonated;
    private CharityApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        app = (CharityApp) getApplication();

        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        amountText = (TextView) findViewById(R.id.amountText);
        amountTotal = (TextView) findViewById(R.id.amountTotal);
        charityDonated = (Spinner) findViewById(R.id.selectCharity);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        for (Charity c : app.charities) {
            spinnerArray.add(c.id + ". " + c.toString());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        charityDonated.setAdapter(spinnerArrayAdapter);
        progressBar.setMax(app.target);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuReport:
            startActivity(new Intent(this, Reports.class));
            break;
        case R.id.menuLogout:
            startActivity(new Intent(this, Login.class));
            break;
        case R.id.menuEditDetails:
            startActivity(new Intent(this, Edit.class));
            break;
        }
        return true;
    }

    public void donateButtonPressed(View view) {
        String method = paymentMethod.getCheckedRadioButtonId() == R.id.PayPal ? "PayPal"
                : "Direct";
        
       String text = amountText.getText().toString();
       int donatedAmount = Integer.parseInt(text);
    		   
        if (donatedAmount > 0) {
            int id = charityDonated.getSelectedItemPosition();
            Charity charity = app.charities.get(id);
            CharityServiceAPI.createDonation(this, app.currentUser, this,
                    "Registering new donation...", new Donation(charity,
                            donatedAmount, method));
        }
    }

    @Override
    public void setResponse(Donation acceptedDonation) {
        Toast toast = Toast.makeText(this, "Donation Accepted",
                Toast.LENGTH_SHORT);
        toast.show();
        app.newDonation(acceptedDonation);
        progressBar.setProgress(app.totalDonated);
        String totalDonatedStr = "$" + app.totalDonated;
        amountTotal.setText(totalDonatedStr);
        amountText.setText("");
    }

    @Override
    public void errorOccurred(Exception e) {
        Toast toast = Toast.makeText(this,
                "Donation Service Unavailable. Try again later",
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void setResponse(List<Donation> aList) {
    }
}
