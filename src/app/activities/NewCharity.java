package app.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import app.charity.R;
import app.http.Response;
import app.main.CharityApp;
import app.models.Charity;
import app.models.CharityServiceAPI;


public class NewCharity extends Activity implements Response<Charity> {
    
    private CharityApp app;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
      getMenuInflater().inflate(R.menu.create, menu);
      return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
      switch (item.getItemId())
      {
        case R.id.menuDashboard : startActivity (new Intent(this, Dashboard.class));
                               break;  
        case R.id.menuLogout : startActivity (new Intent(this, Login.class));
                               break;                               
      }
      return true;
    }  
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create);
      
      app = (CharityApp) getApplication();
    }
    
    public void createCharityPressed(View view) {
        TextView charName = (TextView) findViewById(R.id.charName);
        TextView charOffice = (TextView) findViewById(R.id.charOffice);

        Charity charity = new Charity(
                charName.getText().toString(), charOffice.getText().toString());
        
        CharityServiceAPI.createCharity(this, this, "Creating new charity", charity);
     
    }

    public static void createCharity(String name, String office)
    {
        Charity createdChar = new Charity(name, office);
        Log.i(createdChar.toString(), "New charity created");
    }
    
    @Override
    public void setResponse(List<Charity> aList) 
    {
    }

    @Override
    public void setResponse(Charity charity) {
        app.charities.add(charity);
        startActivity(new Intent(this, NewCharity.class));
    }

    @Override
    public void errorOccurred(Exception e) {
        app.donationServiceAvailable = false;
        Toast toast = Toast.makeText(this,
                "Donation Service Unavailable. Try again later",
                Toast.LENGTH_LONG);
        toast.show();
        startActivity(new Intent(this, Login.class));
    }
}
