import java.util.*;

public class Company implements Subject {
    private String companyName;
    private Manager manager;
    private ArrayList<Department> departments;
    private ArrayList<Recruiter> recruiters;
    private ArrayList<User> observerList;

    public Company() {
        manager = new Manager();
        departments = new ArrayList<Department>();
        recruiters = new ArrayList<Recruiter>();
        observerList = new ArrayList<User>();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setDepartments(ArrayList<Department> departaments) {
        this.departments = departaments;
    }

    public void setRecruiters(ArrayList<Recruiter> recruiters) {
        this.recruiters = recruiters;
    }

    public void setObserverList(ArrayList<User> observerList) {
        this.observerList = observerList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Manager getManager() {
        return manager;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public ArrayList<Recruiter> getRecruiters() {
        return recruiters;
    }

    public ArrayList<User> getObserverList() {
        return observerList;
    }

    public void add(Department department) {
        departments.add(department);
    }

    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Department department) {
        department.add(employee);
    }

    public void remove(Employee employee) {
        int i;

        for (i = 0; i < departments.size(); i++)
            departments.get(i).getEmployees().remove(employee);
    }

    public void remove(Department department) {
        department.getEmployees().clear();
        departments.remove(department);
    }

    public void remove(Recruiter recruiter) {
        recruiters.remove(recruiter);
    }

    public void move(Department source, Department destination) {
        int i;

        for (i = 0; i < destination.getEmployees().size(); i++)
            source.add(destination.getEmployees().get(i));

        for (i = 0; i < destination.getJobs().size(); i++)
            source.add(destination.getJobs().get(i));

        destination.getJobs().clear();
        destination.getEmployees().clear();
        departments.remove(destination);
    }

    public void move(Employee employee, Department newDepartment) {
        int i;

        for (i = 0; i < departments.size(); i++)
            departments.get(i).getEmployees().remove(employee);

        newDepartment.getEmployees().add(employee);
    }

    public boolean contains(Department department) {
        return departments.contains(department);
    }

    public boolean contains(Employee employee) {
        int i;

        for (i = 0; i < departments.size(); i++)
            if (departments.get(i).getEmployees().contains(employee))
                return true;

        return false;
    }

    public boolean contains(Recruiter recruiter) {
        return recruiters.contains(recruiter);
    }

    //aleg recruiter ul potrivit pentru utilizatorul dat ca parametru astfel incat se respecta
    //criteriile din enunt
    public Recruiter getRecruiter(User user) {
        Recruiter newRecruiter = new Recruiter();
        int i, max = 0;
        ArrayList<Integer> distance = new ArrayList<Integer>();
        ArrayList<Recruiter> possibleRecruiters = new ArrayList<Recruiter>();

        //calculez distanta maxima a fiecarui recruiter fata de user
        for (i = 0; i < recruiters.size(); i++) {
            distance.add(i, user.getDegreeInFriendship(recruiters.get(i)));

            if (distance.get(i) >= max)
                max = distance.get(i);
        }

        //aleg recruiterii care sunt cei mai indepartati de user (cu gradul de prietenie maxim)
        for (i = 0; i < recruiters.size(); i++)
            if (distance.get(i) == max)
                possibleRecruiters.add(recruiters.get(i));


        //ii sortez pe acestia dupa rating (descrescator)
        possibleRecruiters.sort(new Comparator<Recruiter>() {
            @Override
            public int compare(Recruiter o1, Recruiter o2) {
                return o2.getRating().compareTo(o1.getRating());
            }
        });

        //aleg recruiter ul cu rating cel mai mare
        return possibleRecruiters.get(0);
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> newJobArray = new ArrayList<Job>();
        int i, j;

        for (i = 0; i < departments.size(); i++) {
            for (j = 0; j < departments.get(i).getJobs().size(); j++)
                if (departments.get(i).getJobs().get(j).getValability())
                    newJobArray.add(departments.get(i).getJobs().get(j));
        }

        return newJobArray;
    }

    @Override
    public void addObserver(User user) {
        observerList.add(user);
    }

    @Override
    public void removeObserver(User user) {
        observerList.remove(user);
    }

    //trimite notificari catre toti userii din lista de abonati ai companiei
    @Override
    public void notifyAllObservers(String message) {
        Notification newNot = new Notification();
        int i;

        newNot.setCompany(this);
        newNot.setMessage(message);

        for (i = 0; i < observerList.size(); i++)
            observerList.get(i).update(newNot);
    }

    public String showCompanyInfo() {
        String string = "Company: " + companyName + "\n\n" + "Departments: IT, Management, Marketing and Finance." + "\n\n";
        int i;

        string = string + "Employees of the IT department:" + "\n";
        for (i = 0; i < getDepartments().get(0).getEmployees().size(); i++)
            string = string + getDepartments().get(0).getEmployees().get(i).getFullName() + "\n";

        string = string + "\n" + "Total wage budget of the IT department: " + getDepartments().get(0).getTotalSalaryBudget() + "\n";

        string = string + "\n" + "Jobs in the IT department:" + "\n";
        for (i = 0; i < getDepartments().get(0).getJobs().size(); i++)
            string = string + getDepartments().get(0).getJobs().get(i).toString() + "\n";

        string = string + "\n" + "Employees of the Management department:" + "\n";
        for (i = 0; i < getDepartments().get(1).getEmployees().size(); i++)
            string = string + getDepartments().get(1).getEmployees().get(i).getFullName() + "\n";

        string = string + "\n" + "Total wage budget of the Management department: " + getDepartments().get(1).getTotalSalaryBudget() + "\n";

        string = string + "\n" + "Employees of the Marketing department:" + "\n";
        for (i = 0; i < getDepartments().get(2).getEmployees().size(); i++)
            string = string + getDepartments().get(2).getEmployees().get(i).getFullName() + "\n";

        string = string + "\n" + "Total wage budget of the Marketing department: " + getDepartments().get(2).getTotalSalaryBudget() + "\n";

        string = string + "\n" + "Employees of the Finance department:" + "\n";
        for (i = 0; i < getDepartments().get(3).getEmployees().size(); i++)
            string = string + getDepartments().get(3).getEmployees().get(i).getFullName() + "\n";

        string = string + "\n" + "Total wage budget of the Finance department: " + getDepartments().get(3).getTotalSalaryBudget();


        return string;
    }

    public String toString() {
        return companyName;
    }
}
