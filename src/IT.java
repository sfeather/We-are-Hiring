public class IT extends Department {
    public IT() {
        setDepartmentName("IT");
    }

    @Override
    public double getTotalSalaryBudget() {
        //taxes = 0;
        int i = 0;
        double salaryBudget = 0;

        for (i = 0; i < getEmployees().size(); i++)
            salaryBudget += getEmployees().get(i).getWage();

        return salaryBudget;
    }

    public String toString() {
        return "IT department";
    }
}
