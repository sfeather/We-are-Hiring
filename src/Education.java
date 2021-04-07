import java.util.*;

public class Education implements Comparable<Education> {
    private Date starting_date, ending_date;
    private String institutionName, levelOfEducation;
    private Double final_GPA;

    public Education() {
        starting_date = null;
        ending_date = null;
    }

    //folosim criteriile de comparatie ca in enunt
    @Override
    public int compareTo(Education educ) {
        if (educ.ending_date == null || ending_date == null) {
            if (educ.starting_date.after(starting_date))
                return -1;
            else if (educ.starting_date.before(starting_date))
                return 1;
            else
                return 0;
        }

        if (educ.ending_date.after(ending_date))
            return 1;
        else if (educ.ending_date.before(ending_date))
            return -1;
        else
            return -Double.compare(final_GPA, educ.final_GPA);
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

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public void setLevelOfEducation(String levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public void setFinal_GPA(Double final_GPA) {
        this.final_GPA = final_GPA;
    }

    public Date getStarting_date() {
        return starting_date;
    }

    public Date getEnding_date() {
        return ending_date;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getLevelOfEducation() {
        return levelOfEducation;
    }

    public Double getFinal_GPA() {
        return final_GPA;
    }

    public String toString() {
        String string = "Level of education: " + levelOfEducation + "\n" +
                "Institution name: " + institutionName + "\n" +
                "Starting date: " + starting_date.getDate() +
                "." + starting_date.getMonth() + "." + starting_date.getYear() + "\n";

        if (ending_date != null)
            string = string + "Ending date: " + ending_date.getDate() + "." + ending_date.getMonth() +
                "." + ending_date.getYear() + "\n" + "Final GPA: " + final_GPA;
        else
            string = string + "Ending date: -";

        return string;
    }
}
