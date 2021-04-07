import java.util.*;

abstract class Department {
    private ArrayList<Employee> employees;
    private ArrayList<Job> jobs;
    private String departmentName;

    public Department() {
        employees = new ArrayList<Employee>();
        jobs = new ArrayList<Job>();
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public abstract double getTotalSalaryBudget();


    public void add(Employee employee) {
        employees.add(employee);
    }

    public void remove(Employee employee) {
        employee.remove(employee);
    }

    public void add(Job job) {
        jobs.add(job);
    }


}
