import java.util.*;

public class Employee extends Consumer {
    private String companyName;
    private Double wage;
    private Department department;
    private Job job;

    public Employee() {
        wage = 0.0;
        companyName = "";
    }

    //returneaza daca un angajat are sau nu sub un an de experienta
    public boolean experienceUnderOneYear() {
        int i;
        Date ending_date, starting_date;

        for (i = 0; i < getResume().getExperienceList().size(); i++)
            if (getResume().getExperienceList().get(i).getCompanyName().compareTo(getCompanyName()) == 0) {
                ending_date = getResume().getExperienceList().get(i).getEnding_date();
                starting_date = getResume().getExperienceList().get(i).getStarting_date();
                if(ending_date == null)
                    return starting_date.getYear() >= 2020;
                if (ending_date.getYear() == starting_date.getYear())
                    return true;
                if (ending_date.getYear() == starting_date.getYear() + 1 &&
                        ending_date.getMonth() < starting_date.getMonth())
                    return true;
            }

        return false;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Double getWage() {
        return wage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Department getDepartment() {
        return department;
    }

    public Job getJob() {
        return job;
    }

    public String showEmployeeDetails() {
        String string = "Company name: " + companyName + "\n" + "Department: " +
                department.getDepartmentName() + "\n" + "Job: " + getJob().getJobName() + "\n" +
                "Wage: " + wage + "\n\n" + "Birthday date: " + getResume().getInformation().getBirthday_date().getDate() +
                "." + getResume().getInformation().getBirthday_date().getMonth() +
                "." + getResume().getInformation().getBirthday_date().getYear() + "\n" +
                "Phone number: " + getResume().getInformation().getPhone_number() + "\n" +
                "Email: " + getResume().getInformation().getEmail() + "\n" +
                "Gender: " + getResume().getInformation().getGender() + "\n";

        String language_string = "";

        for (int i = 0; i < getResume().getInformation().getForeignLanguages().size(); i++) {
            language_string = language_string + getResume().getInformation().getForeignLanguages().get(i).toString() + "\n";
        }

        string = string + "Known languages and their level: " + "\n" + language_string;

        return string;
    }

    public String toString() {
        return getFullName() + "\n";
    }
}
