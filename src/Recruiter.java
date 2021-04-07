public class Recruiter extends Employee {
    //orice recruiter este angajat in departamentul IT

    private Double rating;

    public Recruiter() {
        rating = 5.0;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }


    //trimite un request catre managerul companiei si returneaza scorul obtinut de user in
    //urma evaluarii
    public int evaluate(Job job, User user) {
        //rating_recruiter * scor_user
        //return getRating() * user.getTotalScore();
        rating += 0.1;
        Double eval_score = getRating() * user.getTotalScore();


        Request<Job, Consumer> newRequest = new Request<Job, Consumer>(job, user, this,
                eval_score);

        Application newApp = Application.getInstance();
        Company company = newApp.getCompany(getCompanyName());


        company.getManager().addJobRequest(newRequest);

        return eval_score.intValue();
    }
}
