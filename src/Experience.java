import java.util.*;

public class Experience implements Comparable<Experience> {
    private Date starting_date, ending_date;
    private String position;
    private String companyName;
    private String departmentName;

    //folosim criteriile de comparatie ca in enunt
    @Override
    public int compareTo(Experience exp) {
        if (exp.ending_date == null || ending_date == null)
            return companyName.compareTo(exp.getCompanyName());

        if (exp.ending_date.after(ending_date))
            return 1;
        else if (exp.ending_date.before(ending_date))
            return -1;
        else
            return companyName.compareTo(exp.getCompanyName());
    }

    public Experience() {
        starting_date = null;
        ending_date = null;
        position = "";
        companyName = "";
        departmentName = "";
    }

    public void setStarting_date(Date starting_date) throws InvalidDatesException {
        this.starting_date = starting_date;

        //daca datele nu sunt corecte dpdv cronologic, se arunca exceptia InvalidDatesException
        if (this.ending_date != null && this.ending_date.before(starting_date))
            throw new InvalidDatesException("Starting date / ending date is wrong!");
    }

    public void setEnding_date(Date ending_date) throws InvalidDatesException {
        this.ending_date = ending_date;

        if (this.ending_date == null)
            return;

        //daca datele nu sunt corecte dpdv cronologic, se arunca exceptia InvalidDatesException
        if (this.starting_date != null && this.ending_date.before(starting_date))
            throw new InvalidDatesException("Starting date / ending date is wrong!");
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getStarting_date() {
        return starting_date;
    }

    public Date getEnding_date() {
        return ending_date;
    }

    public String getPosition() {
        return position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String toString() {
        String string =  "Company name: " + companyName + "\n" + "Position/job: " + position + "\n" +
                "Starting date: " + starting_date.getDate() + "." + starting_date.getMonth() +
                "." + starting_date.getYear() + "\n";

        if (ending_date != null)
            string = string + "Ending date: " + ending_date.getDate() +
                    "." + ending_date.getMonth() + "." + ending_date.getYear();
        else
            string = string + "Ending date: -";

        return string;
    }
}
