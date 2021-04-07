public class Management extends Department {
    public Management() {
        setDepartmentName("Management");
    }

    @Override
    public double getTotalSalaryBudget() {
        //impozit = 16%
        int i = 0;
        double salaryBudget = 0;

        for (i = 0; i < getEmployees().size(); i++)
            salaryBudget += getEmployees().get(i).getWage() * 1.16;

        return salaryBudget;
    }

    public String toString() {
        return "Management department";
    }
}
