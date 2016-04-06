package app.activities;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import app.charity.R;
import app.http.Response;
import app.main.CharityApp;
import app.models.Administrator;
import app.models.Charity;
import app.models.CharityServiceAPI;
import app.models.User;

public class Login extends Activity implements Response<User> {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuSignup:
            startActivity(new Intent(this, Signup.class));
            break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      app = (CharityApp) getApplication();
      
      CharityServiceAPI.getCharities(this, new MockResponse(), "");
      CharityServiceAPI.getUsers(this, this, "Retrieving list of users and Getting list of charities");
      CharityServiceAPI.getAdmins(this, new MockAdminResponse(), "");
    
    }

    public void signinPressed(View view) {
        CharityApp app = (CharityApp) getApplication();

        TextView email = (TextView) findViewById(R.id.loginEmail);
        TextView password = (TextView) findViewById(R.id.loginPassword);

        if (app.validUser(email.getText().toString(), password.getText()
                .toString())) {
            if (app.currentUser instanceof app.models.Administrator) {
                startActivity(new Intent(this, Dashboard.class));
            } else {
                startActivity(new Intent(this, Donate.class));
            }
        } else {
            Toast toast = Toast.makeText(this, "Invalid Credentials",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    
    CharityApp app;
    class MockResponse implements Response<Charity>{

        @Override
        public void setResponse(List<Charity> aList) {
            app.charities = aList;
        }

        @Override
        public void setResponse(Charity anObject) {
        }

        @Override
        public void errorOccurred(Exception e) {
        }
      }
    class MockAdminResponse implements Response<Administrator>{

        @Override
        public void setResponse(List<Administrator> aList) {
            app.admins = aList;
        }

        @Override
        public void setResponse(Administrator anObject) {
        }

        @Override
        public void errorOccurred(Exception e) {
        }
      }
    
    
    @Override
    public void onResume()
    {
      super.onResume();
      app.currentUser = null;
      CharityServiceAPI.getCharities(this, new MockResponse(), "");
      CharityServiceAPI.getUsers(this, this, "Retrieving list of users and Getting list of charities");
    }
    
    void serviceUnavailableMessage()
    {
      Toast toast = Toast.makeText(this, "Donation Service Unavailable. Try again later", Toast.LENGTH_LONG);
      toast.show();
    }
    
    public void loginPressed (View view) 
    {
      if (app.donationServiceAvailable)
      {
        startActivity (new Intent(this, Login.class));
      }
      else
      {
        serviceUnavailableMessage();
      }
    }

    public void signupPressed (View view) 
    {
      if (app.donationServiceAvailable)
      {
        startActivity (new Intent(this, Signup.class));
      }
      else
      {
        serviceUnavailableMessage();
      }
    }
    
    @Override
    public void setResponse(List<User> aList)
    {
      app.users = aList;
      app.donationServiceAvailable = true;
    }
    
    @Override
    public void errorOccurred(Exception e)
    {
      app.donationServiceAvailable = false;
      serviceUnavailableMessage();
    }
    
    @Override
    public void setResponse(User anObject)
    {}
}