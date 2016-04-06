package app.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.charity.R;
import app.http.Response;
import app.main.CharityApp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.models.Charity;
import app.models.Donation;
import app.models.CharityServiceAPI;

public class Reports extends Activity implements Response <Donation>
{
  private ListView        listView;
  private CharityApp     app;
  private DonationAdapter adapter; 
  private Spinner         charityDonated;

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.reports, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case R.id.menuEditDetails : startActivity (new Intent(this, Edit.class));
                             break;  
      case R.id.menuDonate : startActivity (new Intent(this, Donate.class));
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
    setContentView(R.layout.activity_report);

    app = (CharityApp) getApplication();

    listView = (ListView) findViewById(R.id.reportList);
    
    List<Donation>donations = new ArrayList<Donation>();
    donations = app.donations;
    Collections.sort(donations);
    adapter = new DonationAdapter (this, donations);

    listView.setAdapter(adapter);
    
    charityDonated  = (Spinner)  findViewById(R.id.selectCharity); 
    ArrayList<String> spinnerArray = new ArrayList<String>();
    spinnerArray.add("all");
    for(Charity c: app.charities){
        spinnerArray.add(c.toString());
    }
    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
    charityDonated.setAdapter(spinnerArrayAdapter);  
    
    CharityServiceAPI.getDonations(this, app.currentUser, this, "Downloading Donations List..");
  }

  @Override
  public void setResponse(List<Donation> aList)
  {
    app.donations     = aList;
    adapter.donations = aList;
    adapter.notifyDataSetChanged();
  }

  @Override
  public void setResponse(Donation anObject)
  {
  }
  
  @Override
  public void errorOccurred(Exception e)
  {
    Toast toast = Toast.makeText(this, "Donation Service Unavailable!", Toast.LENGTH_LONG);
    toast.show();
    startActivity (new Intent(this, Login.class));
  }
  
  public void applyFilterButtonPressed(View view) 
  {
      List<Donation>donationsForChar = new ArrayList<Donation>();
      
      if(charityDonated.getSelectedItem().toString().equals("all")){
          donationsForChar = app.donations;
      }
      else{
          String selectedChar = charityDonated.getSelectedItem().toString();
          for (Donation d: app.donations){
              if(d.charity.toString().equals(selectedChar)){
                  donationsForChar.add(d);
              }
          }
      }
      Collections.sort(donationsForChar);
      adapter = new DonationAdapter (this, donationsForChar);
      listView.setAdapter(adapter);
  }
}

class DonationAdapter extends ArrayAdapter<Donation>
{
  private Context        context;
  public  List<Donation> donations;

  public DonationAdapter(Context context, List<Donation> donations)
  {
    super(context, R.layout.row_donate, donations);
    this.context   = context;
    this.donations = donations;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
    View     view       = inflater.inflate(R.layout.row_donate, parent, false);
    Donation donation   = donations.get(position);
    TextView firstNameView = (TextView) view.findViewById(R.id.row_name);
    TextView officeView = (TextView) view.findViewById(R.id.row_office);
    TextView amountView = (TextView) view.findViewById(R.id.row_amount);
    TextView methodView = (TextView) view.findViewById(R.id.row_method);
    
    firstNameView.setText(donation.charity.name);
    officeView.setText(donation.charity.office);
    amountView.setText("" + donation.amount);
    methodView.setText(donation.method);
    
    return view;
  }
 
  @Override
  public int getCount()
  {
    return donations.size();
  }
}