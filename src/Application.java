import java.util.*;

public class Application {
    private ArrayList<Company> companyList;
    private ArrayList<User> userList;
    private static Application obj = null;

    private Application() {
        companyList = new ArrayList<Company>();
        userList = new ArrayList<User>();
    }

    public static Application getInstance() {
        if (obj == null)
            obj = new Application();
        return obj;
    }

    public void setCompanyList(ArrayList<Company> companyList) {
        this.companyList = companyList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<Company> getCompanyList() {
        return companyList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public Company getCompany(String name) {
        int i;

        for (i = 0; i < companyList.size(); i++)
            if (companyList.get(i).getCompanyName().equals(name))
                return companyList.get(i);

        return null;
    }

    public void add(Company company) {
        companyList.add(company);
    }

    public void add(User user) {
        userList.add(user);
    }

    public boolean remove(Company company) {
        return companyList.remove(company);
    }

    public boolean remove(User user) {
        return userList.remove(user);
    }

    public ArrayList<Job> getJobs(ArrayList<Company> companies) {
        int i, j, k;
        ArrayList<Job> openJobs = new ArrayList<Job>();


        for (i = 0; i < companies.size(); i++) {
            for (j = 0; j < companies.get(i).getDepartments().size(); j++)
                for (k = 0; k < companies.get(i).getDepartments().get(j).getJobs().size(); k++)
                    if (companies.get(i).getDepartments().get(j).getJobs().get(k).getValability())
                        openJobs.add(companies.get(i).getDepartments().get(j).getJobs().get(k));
        }

        return openJobs;
    }

    public String toString() {
        return "COMPANII: \n" + companyList.toString() + "\nUSERI: \n" + userList.toString() + "\nJOBURI: \n" +
                getJobs(companyList);
    }
}
