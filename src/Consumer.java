import java.util.*;

abstract class Consumer {
    private String fullName;
    private Resume resume;
    private ArrayList<Consumer> socialNetwork;

    public Consumer() {
        fullName = "";
        resume = new Resume();
        socialNetwork = new ArrayList<Consumer>();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public void setSocialNetwork(ArrayList<Consumer> socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getFullName() {
        return fullName;
    }

    public Resume getResume() {
        return resume;
    }

    public ArrayList<Consumer> getSocialNetwork() {
        return socialNetwork;
    }

    public void add(Education education) {
        resume.getEducationList().add(education);
    }

    public void add(Experience experience) {
        resume.getExperienceList().add(experience);
    }

    public void add(Consumer consumer) {
        socialNetwork.add(consumer);
    }

    public int getDegreeInFriendship(Consumer consumer) {
        //determinarea gradului de prietenie cu un alt utilizator (se realizeaza o parcurgere
        //in latime in reteaua sociala a utilizatorului)
        int i;
        HashMap<Consumer, Integer> distance = new HashMap<Consumer, Integer>();
        LinkedList<Consumer> queue = new LinkedList<Consumer>();
        ArrayList<Consumer> visitedConsumers = new ArrayList<Consumer>();
        Consumer newConsumer;

        queue.add(this);
        visitedConsumers.add(this);
        distance.put(this, 0);


        //facem un BFS in reteaua sociala "socialNetwork" a utilizatorului
        //am luat un hashmap "distance" care asociaza unui consumer o distanta (nr intreg)
        //fata de utilizator
        while(queue.size() != 0) {
            newConsumer = queue.poll();

            for (i = 0; i < newConsumer.getSocialNetwork().size(); i++) {
                if (!visitedConsumers.contains(newConsumer.getSocialNetwork().get(i))) {
                    distance.put(newConsumer.getSocialNetwork().get(i), distance.get(newConsumer) + 1);

                    if (newConsumer.getSocialNetwork().get(i).equals(consumer))
                        return distance.get(consumer);

                    visitedConsumers.add(newConsumer.getSocialNetwork().get(i));
                    queue.add(newConsumer.getSocialNetwork().get(i));
                }
            }
        }

        return 0;
    }


    public void remove(Consumer consumer) {
        socialNetwork.remove(consumer);
    }

    public Integer getGraduationYear() {
        //determina anul absolvirii
        //din education iau ending date-ul de la "college"
        int i;

        for (i = 0; i < resume.getEducationList().size(); i++)
            if (resume.getEducationList().get(i).getLevelOfEducation().equals("college")) {
                Date graduationDate = resume.getEducationList().get(i).getEnding_date();

                if (graduationDate == null)
                    return null;
                else
                    return graduationDate.getYear();
            }

        return null;
    }

    public Double meanGPA() {
        //determina media studiilor absolvite
        int i, count = 0;
        Double GPAsum = 0.0;

        if (resume.getEducationList().size() == 0)
            return 0.0;

        for (i = 0; i < resume.getEducationList().size(); i++) {
            GPAsum += resume.getEducationList().get(i).getFinal_GPA();
            count++;
        }

        return GPAsum / count;
    }

    public String toString() {
        return fullName + "\n";
    }
}
