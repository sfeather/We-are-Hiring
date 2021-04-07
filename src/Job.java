import java.util.*;

public class Job {
    private String jobName, companyName;
    private boolean valability;
    private Constraint<Integer> graduationYear, yearsOfExperience;
    private Constraint<Double> meanGPA;
    private Vector<User> candidates;
    private int noPositions;
    private Double wage;

    public Job() {
        candidates = new Vector<User>();
        jobName = "";
        companyName = "";
        valability = false;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setValability(boolean valability) {
        //daca jobul este valabil/s-a inchis atunci dau o notificare tuturor observerilor
        //din company
        Application newApp = Application.getInstance();
        Company company = newApp.getCompany(companyName);

        if (valability)
            company.notifyAllObservers("You can now apply to the following job: " + jobName + ".");
        else
            company.notifyAllObservers("Unfortunately, the job: " + jobName + " is now closed.");

        this.valability = valability;
    }

    public void setCandidates(Vector<User> candidates) {
        this.candidates = candidates;
    }

    public void setGraduationYear(Constraint<Integer> graduationYear) {
        this.graduationYear = graduationYear;
    }

    public void setYearsOfExperience(Constraint<Integer> yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setMeanGPA(Constraint<Double> meanGPA) {
        this.meanGPA = meanGPA;
    }

    public void setNoPositions(int noPositions) {
        this.noPositions = noPositions;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public String getJobName() {
        return jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Constraint<Integer> getGraduationYear() {
        return graduationYear;
    }

    public Constraint<Integer> getYearsOfExperience() {
        return yearsOfExperience;
    }

    public Constraint<Double> getMeanGPA() {
        return meanGPA;
    }

    public Vector<User> getCandidates() {
        return candidates;
    }

    public int getNoPositions() {
        return noPositions;
    }

    public Double getWage() {
        return wage;
    }

    public boolean getValability() {
        return valability;
    }

    public Department getDepartment() {
        Application newApp = Application.getInstance();
        Company company = newApp.getCompany(getCompanyName());
        int i;

        for (i = 0; i < company.getDepartments().size(); i++) {
            if (company.getDepartments().get(i).getJobs().contains(this))
                return company.getDepartments().get(i);
        }

        return null;
    }

    public void apply(User user) {
        //se va selecta un Recruiter din lista de recruiter din compania mama
        //si se va apela metoda "evaluate" pentru user-ul dat ca parametru

        Application newApp = Application.getInstance();

        Company company = newApp.getCompany(companyName);
        Recruiter recruiter = company.getRecruiter(user);

        //adaug user-ul in lista de observatori ai companiei
        if (!company.getObserverList().contains(user))
            company.addObserver(user);


        if (valability)
            recruiter.evaluate(this, user);
    }

    public boolean meetsRequirments(User user) {
        int years_of_experience = user.getYearsOfExperience();
        int graduation_year;
        Double mean_GPA = user.meanGPA();

        if (user.getGraduationYear() == null)
            graduation_year = -1;
        else
            graduation_year = user.getGraduationYear();

        if (graduation_year == -1 && (graduationYear.getSuperiorLimit() != null || graduationYear.getInferiorLimit() != null))
            return false;

        if (yearsOfExperience.getInferiorLimit() != null && yearsOfExperience.getSuperiorLimit() != null) {
            if (years_of_experience < yearsOfExperience.getInferiorLimit() ||
                    years_of_experience > yearsOfExperience.getSuperiorLimit())
                return false;
        } else if (yearsOfExperience.getInferiorLimit() == null && yearsOfExperience.getSuperiorLimit() != null) {
            if (years_of_experience > yearsOfExperience.getSuperiorLimit())
                return false;
        } else if (yearsOfExperience.getInferiorLimit() != null && yearsOfExperience.getSuperiorLimit() == null) {
            if (years_of_experience < yearsOfExperience.getInferiorLimit())
                return false;
        }

        if (graduationYear.getInferiorLimit() != null && graduationYear.getSuperiorLimit() != null) {
            if (graduation_year < graduationYear.getInferiorLimit() ||
                    graduation_year > graduationYear.getSuperiorLimit())
                return false;
        } else if (graduationYear.getInferiorLimit() == null && graduationYear.getSuperiorLimit() != null) {
            if (graduation_year > graduationYear.getSuperiorLimit())
                return false;
        } else if (graduationYear.getInferiorLimit() != null && graduationYear.getSuperiorLimit() == null) {
            if (graduation_year < graduationYear.getInferiorLimit())
                return false;
        }

        if (meanGPA.getInferiorLimit() != null && meanGPA.getSuperiorLimit() != null) {
            if (mean_GPA < meanGPA.getInferiorLimit() ||
                    mean_GPA > meanGPA.getSuperiorLimit())
                return false;
        } else if (meanGPA.getInferiorLimit() == null && meanGPA.getSuperiorLimit() != null) {
            if (mean_GPA > meanGPA.getSuperiorLimit())
                return false;
        } else if (meanGPA.getInferiorLimit() != null && meanGPA.getSuperiorLimit() == null) {
            if (mean_GPA < meanGPA.getInferiorLimit())
                return false;
        }


        return true;
    }

    public String showJobDetails() {
        String string = "";

        string = string + jobName + " --- ";
        if (valability)
            string = string + "JOB IS OPEN";
        else
            string = string + "JOB IS CLOSED";

        return string;
    }

    public String toString() {
        return jobName + "\n";
    }
}
