import java.util.*;

public class User extends Consumer implements Observer {
    private ArrayList<String> wantedCompanies, notificationList;

    public User() {
        wantedCompanies = new ArrayList<String>();
        notificationList = new ArrayList<String>();
    }

    public Employee convert(Double wage, String companyName, Job job) {
        Employee newEmployee = new Employee();
        Application newApp = Application.getInstance();

        newEmployee.setResume(getResume());
        newEmployee.setSocialNetwork(getSocialNetwork());
        newEmployee.setWage(wage);
        newEmployee.setCompanyName(companyName);
        newEmployee.setFullName(getResume().getInformation().getFirst_name() + " " +
                getResume().getInformation().getLast_name());
        newEmployee.setDepartment(newApp.getCompany(companyName).getDepartments().get(0));
        newEmployee.setJob(job);

        return newEmployee;
    }

    public void setWantedCompanies(ArrayList<String> wantedCompanies) {
        this.wantedCompanies = wantedCompanies;
    }

    public void setNotificationList(ArrayList<String> notificationList) {
        this.notificationList = notificationList;
    }

    public ArrayList<String> getWantedCompanies() {
        return wantedCompanies;
    }

    public ArrayList<String> getNotificationList() {
        return notificationList;
    }

    //metoda care returneaza anii de experienta ai utilizatorului
    public int getYearsOfExperience() {
        Resume resume = getResume();
        int i, years_of_experience = 0, ending_year, starting_year;

        for (i = 0; i < resume.getExperienceList().size(); i++) {
            ending_year = resume.getExperienceList().get(i).getEnding_date().getYear();
            starting_year = resume.getExperienceList().get(i).getStarting_date().getYear();

            if (ending_year == starting_year)
                years_of_experience++;
            else
                years_of_experience += ending_year - starting_year;
        }

        return years_of_experience;
    }

    public Double getTotalScore() {
        //scorul = numar_ani_experienta * 1.5 + medie_academica;
        //medie_academica = meanGPA;
        return getYearsOfExperience() * 1.5 + meanGPA();
    }

    @Override
    public void update(Notification notification) {
        String str = "New notification from " + notification.getCompany().getCompanyName()
                + ": " + notification.getMessage() + "\n";

        notificationList.add(str);
    }

    public String showUserDetails() {
        String string = "Full name: " + getFullName() + "\n" +
                "Birthday date: " + getResume().getInformation().getBirthday_date().getDate() +
                "." + getResume().getInformation().getBirthday_date().getMonth() +
                "." + getResume().getInformation().getBirthday_date().getYear() + "\n" +
                "Phone number: " + getResume().getInformation().getPhone_number() + "\n" +
                "Email: " + getResume().getInformation().getEmail() + "\n" +
                "Gender: " + getResume().getInformation().getGender() + "\n";

        String language_string = "";

        for (int i = 0; i < getResume().getInformation().getForeignLanguages().size(); i++)
            language_string = language_string + getResume().getInformation().getForeignLanguages().get(i).toString() + "\n";

        string = string + "Known languages and their level: " + "\n" + language_string;

        String wantedComp = "";

        for (int i = 0; i < wantedCompanies.size() - 1; i++)
            wantedComp = wantedComp + wantedCompanies.get(i) + ", ";

        wantedComp = wantedComp + wantedCompanies.get(wantedCompanies.size() - 1);

        string = string + "Companies that this user is interested in: " + wantedComp;

        return string;
    }

}