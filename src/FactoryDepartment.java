public class FactoryDepartment {
    public static Department factory(String departmentName) {
        if (departmentName.equals("IT"))
            return new IT();
        if (departmentName.equals("Management"))
            return new Management();
        if (departmentName.equals("Marketing"))
            return new Marketing();
        if (departmentName.equals("Finance"))
            return new Finance();
        return null;
    }
}
