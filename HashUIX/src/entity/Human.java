package entity;

public class Human{
    
    private String id;
    private String name;
    private String lastname;
    private String gender;

    public Human(String id){
        this.id = id;
        this.name = null;
        this.lastname = null;
        this.gender = null;
    }

    public Human(String id, String name, String lastname, String gender){
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return  " Id: " + id +
                ", Name: '" + name +
                ", Lastname: '" + lastname  +
                ", Gender: '" + gender;
    }
}
