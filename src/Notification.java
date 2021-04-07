public class Notification {
    private String message;
    private Company company;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getMessage() {
        return message;
    }

    public Company getCompany() {
        return company;
    }
}
