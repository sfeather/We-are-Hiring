import java.util.ArrayList;

public class Resume {
    private Information information;
    private ArrayList<Education> educationList;
    private ArrayList<Experience> experienceList;

    public Resume() {
        information = new Information();
        educationList = new ArrayList<Education>();
        experienceList = new ArrayList<Experience>();
    }

    private Resume(ResumeBuilder builder) throws ResumeIncompleteException {
        this.information = builder.information;
        this.educationList = builder.educationList;
        this.experienceList = builder.experienceList;

        //daca la initializare obiectul de tip Resume nu contine campul de informatii si cel putin un camp de
        //education, se arunca exceptia ResumeIncompleteException
        if (this.information == null || this.educationList.size() == 0)
            throw new ResumeIncompleteException("The resume must have the following: information and at least one education");
    }

    //aici nu mai avem nevoie de set-ere datorita sablonului Builder

    public Information getInformation() {
        return information;
    }

    public ArrayList<Education> getEducationList() {
        return educationList;
    }

    public ArrayList<Experience> getExperienceList() {
        return experienceList;
    }

    public static class ResumeBuilder {
        private Information information;
        private ArrayList<Education> educationList;
        private ArrayList<Experience> experienceList;

        public ResumeBuilder(Information information, ArrayList<Education> educationList) {
            this.information = information;
            this.educationList = educationList;
        }

        public ResumeBuilder experienceList(ArrayList<Experience> experienceList) {
            this.experienceList = experienceList;
            return this;
        }

        public Resume build() throws ResumeIncompleteException {
            return new Resume(this);
        }
    }
}
