package app.models;

import java.util.List;
import java.util.ArrayList;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class JsonParsers
{
    public static JSONSerializer userSerializer = new JSONSerializer()
    .prettyPrint(true)
    .include("donations")
    .exclude("*.class")
    .exclude("*.persistent")
    .exclude("*.entityId");

public static JSONSerializer donationSerializer = new JSONSerializer()
    .prettyPrint(true)
    .exclude("*.class")
    .exclude("*.persistent")
    .exclude("*.entityId")
    .include("charity");

public static JSONSerializer charitySerializer = new JSONSerializer()
    .prettyPrint(true)
    .exclude("class")
    .exclude("persistent")
    .exclude("entityId");

public static JSONSerializer adminSerializer = new JSONSerializer()
    .prettyPrint(true)
    .exclude("class")
    .exclude("persistent")
    .exclude("entityId");

  public static User json2User(String json)
  {
    return new JSONDeserializer<User>().deserialize(json, User.class); 
  }

  public static List<User> json2Users(String json)
  {
    return new JSONDeserializer<ArrayList<User>>().use("values", User.class)
                                                  .deserialize(json);
  }
  
  public static String user2Json(Object obj)
  {
    return userSerializer.serialize(obj);
  }
  
  public static List<User> users2Json(String json)
  {
    return new JSONDeserializer<ArrayList<User>>().use("values", User.class)
                                                  .deserialize(json);
  } 
    
 
  public static Donation json2Donation(String json)
  {
    return  new JSONDeserializer<Donation>().deserialize(json, Donation.class);    
  }
  
  public static String donation2Json(Object obj)
  {
    return donationSerializer.serialize(obj);
  }  
  
  public static List<Donation> json2Donations(String json)
  {
    return new JSONDeserializer<ArrayList<Donation>>().use("values", Donation.class)
        .deserialize(json);
  }  
  
  public static Charity json2Charity(String json)
  {
    return  new JSONDeserializer<Charity>().deserialize(json, Charity.class);    
  }
  
  public static String charity2Json(Object obj)
  {
    return charitySerializer.serialize(obj);
  }  
  
  public static List<Charity> json2Charities(String json)
  {
    return new JSONDeserializer<ArrayList<Charity>>().use("values", Charity.class).deserialize(json);
  }  
  
  public static List<Charity> charities2Json(String json)
  {
    return new JSONDeserializer<ArrayList<Charity>>().use("values", Charity.class).deserialize(json);
  } 
  
  public static Administrator json2Admin(String json)
  {
    return new JSONDeserializer<Administrator>().deserialize(json, Administrator.class); 
  }

  public static List<Administrator> json2Admins(String json)
  {
    return new JSONDeserializer<ArrayList<Administrator>>().use("values", Administrator.class)
                                                  .deserialize(json);
  }
  
  public static String admin2Json(Object obj)
  {
    return adminSerializer.serialize(obj);
  }
  
  public static List<Administrator> admins2Json(String json)
  {
    return new JSONDeserializer<ArrayList<Administrator>>().use("values", Administrator.class)
                                                  .deserialize(json);
  } 
}