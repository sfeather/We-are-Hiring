public class Finance extends Department {
    public Finance() {
        setDepartmentName("Finance");
    }

    @Override
    public double getTotalSalaryBudget() {
        //impozit = 10% pentru cei care au vechimea mai putin de 1 an
        //impozit = 16% pentru restul
        int i = 0;
        double salaryBudget = 0;

        for (i = 0; i < getEmployees().size(); i++)
            if (getEmployees().get(i).experienceUnderOneYear())
                salaryBudget += getEmployees().get(i).getWage() * 1.1;
            else
                salaryBudget += getEmployees().get(i).getWage() * 1.16;

        return salaryBudget;
    }

    public String toString() {
        return "Finance department";
    }
}