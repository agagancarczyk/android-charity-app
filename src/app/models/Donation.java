package app.models;

public class Donation implements Comparable<Donation>
{
  public Long   id;
  public Charity charity;
  public int    amount;
  public String method;
  
  public User  from;
  
  Donation()
  {}
  
  public Donation (Charity charity, int amount, String method) 
  {
    this.charity = charity;
    this.amount = amount;
    this.method = method;
  }
  
  @Override
public int compareTo(Donation another) {
    return this.charity.name.compareTo(another.charity.name);
}

public String toString()
  {
    return charity + ", " + amount + ", " + method;
  }



}
