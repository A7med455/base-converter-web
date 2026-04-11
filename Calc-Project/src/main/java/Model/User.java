package Model;

public class User {
    private String Fname;
    private String Lname;
    private String UserName;
    private int age;
    private String password;

    // Constructor for SignUp
    public User(String Fname, String Lname, int age, String password) {
        this.setFname(Fname);
        this.setLname(Lname);
        this.setAge(age);
        this.setPassword(password);
        this.UserName = (this.Fname + this.Lname).toLowerCase();
    }

    // Constructor for Login
    public User(String UserName, String password) {
        this.UserName = UserName;
        this.password = password;
    }

    // Default constructor (needed for DataBase.java)
    public User() {
    }

    public void setFname(String Fname) {
        if (Fname == null || Fname.isEmpty()) {
            throw new IllegalArgumentException("FirstName Cannot be Empty or NULL");
        }
        Fname = Fname.trim();
        boolean valid = true;
        for (int i = 0; i < Fname.length(); i++) {
            char c = Fname.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                valid = false;
                break;
            }
        }
        if (valid) {
            this.Fname = Fname;
        } else {
            throw new IllegalArgumentException("Invalid Name, Try again");
        }
    }

    public String getFname() {
        return Fname;
    }

    public void setLname(String Lname) {
        if (Lname == null || Lname.isEmpty()) {
            throw new IllegalArgumentException("LastName Cannot be Empty or NULL");
        }
        Lname = Lname.trim();
        boolean valid = true;
        for (int i = 0; i < Lname.length(); i++) {
            char c = Lname.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                valid = false;
                break;
            }
        }
        if (valid) {
            this.Lname = Lname;
        } else {
            throw new IllegalArgumentException("Invalid Name, Try again");
        }
    }

    public String getLname() {
        return Lname;
    }

    public String getUserName() {
        return UserName;
    }

    public void setAge(int age) {
        if (age <= 0 || age >= 120) {
            throw new IllegalArgumentException("Age is Wrong, Try Again");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password Cannot be Empty");
        } else {
            password = password.trim();
            this.password = password;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean ValidateCredentials(String UserName, String Password) {
        return this.UserName.equals(UserName) && this.password.equals(Password);
    }
}