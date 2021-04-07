public class Marketing extends Department {
    public Marketing() {
        setDepartmentName("Marketing");
    }

    @Override
    public double getTotalSalaryBudget() {
        //impozit = 10% pentru cei care au salariul mai mare de 5000 de lei
        //impozit = 0% pentru cei care au salariu mai mic de 3000 de lei
        //impozit = 16% pentru ceilalti
        int i = 0;
        double salaryBudget = 0;

        for (i = 0; i < getEmployees().size(); i++)
            if (getEmployees().get(i).getWage() > 5000) {
                salaryBudget += getEmployees().get(i).getWage() * 1.1;
            } else if (getEmployees().get(i).getWage() < 3000) {
                salaryBudget += getEmployees().get(i).getWage();
            } else {
                salaryBudget += getEmployees().get(i).getWage() * 1.16;
            }

        return salaryBudget;
    }

    public String toString() {
        return "Marketing department";
    }
}
