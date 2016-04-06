package app.models;

public class Charity 
{
    public Long   id;
    public String name;
    public String office;

    public Charity() 
    {}

    public Charity(String name, String office) {
        this.name = name;
        this.office = office;
    }

    public String toString() {
        return name + ", " + office;
    }
}
