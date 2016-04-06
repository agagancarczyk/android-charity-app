package app.models;

public class Administrator extends User
{
    public Long   id;
    public String username;
    
    public Administrator()
    {}
    
    public String toString()
    {
      return username + ", " + password;
    }
}
