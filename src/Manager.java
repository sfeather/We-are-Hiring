import java.util.*;

public class Manager extends Employee {
    private Vector<Request<Job, Consumer>> jobRequests;

    public Manager() {
        jobRequests = new Vector<Request<Job, Consumer>>();
    }

    public void setJobRequests(Vector<Request<Job, Consumer>> jobRequests) {
        this.jobRequests = jobRequests;
    }


    public Vector<Request<Job, Consumer>> getJobRequests() {
        return jobRequests;
    }

    public void addJobRequest(Request<Job, Consumer> request) {
        jobRequests.add(request);
    }

    public void process(Job job) {
        int i;
        Request<Job, Consumer> newRequest;
        Vector<Request<Job, Consumer>> matchedRequests = new Vector<Request<Job, Consumer>>();
        Application newApp = Application.getInstance();
        User newUser;

        for (i = 0; i < jobRequests.size(); i++) {
            newRequest = jobRequests.get(i);
            //daca este vorba despre acelasi job atunci
            if (newRequest.getKey() == job) {
                matchedRequests.add(newRequest);
                jobRequests.remove(newRequest);
            }
        }

        matchedRequests.sort(new Comparator<Request<Job, Consumer>>() {
            @Override
            public int compare(Request<Job, Consumer> o1, Request<Job, Consumer> o2) {
                return -o1.getScore().compareTo(o2.getScore());
            }
        });

        for (i = 0; i < job.getNoPositions(); i++) {
            //angajam primii noPositions angajati
            newUser = (User) matchedRequests.get(i).getValue1();

            if (Application.getInstance().getUserList().contains(newUser)) {
                if(newApp.getUserList().remove(newUser)) {
                    newApp.getUserList().remove(newUser);
                    hire(job, newUser);
                }
            }
        }

        //Notificam userii care nu au primit jobul

        Company company = newApp.getCompany(getCompanyName());
        Notification newNot = new Notification();
        newNot.setCompany(company);
        newNot.setMessage("Unfortunately you got rejected from the job: " + job.getJobName()
                    + " inside the company: " + getCompanyName() + ".");


        for (i = 0; i < jobRequests.size(); i++)
            if (jobRequests.get(i).getKey().getJobName().equals(job.getJobName()))
                ((User) jobRequests.get(i).getValue1()).update(newNot);

        if (job.getValability())
            job.setValability(false);
    }


    //angajeaza un User in companie
    public void hire(Job job, User user) {
        Application newApp = Application.getInstance();
        int i;

        Employee newEmployee = user.convert(job.getWage(), job.getCompanyName(), job);
        job.getDepartment().add(newEmployee);
        newEmployee.setDepartment(job.getDepartment());
        newEmployee.setJob(job);

        //il stergem din colectia de observatori ai companiilor
        for (i = 0; i < newApp.getCompanyList().size(); i++)
            newApp.getCompanyList().get(i).removeObserver(user);
    }
}
