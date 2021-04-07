public class Request<K, V> {
    //key = job ul (cred ca poate fi si String)
    //value1 = utilizatorul care trebuie sa fie angajat pentru acel job
    //value2 = recruiter-ul care s-a ocupat de evaluarea utilizatorului
    //score = scorul atribuit de recruiter
    private K key;
    private V value1, value2;
    private Double score;

    public Request(K key, V value1, V value2, Double score) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.score = score;
    }

    public K getKey() {
        return key;
    }

    public V getValue1() {
        return value1;
    }

    public V getValue2() {
        return value2;
    }

    public Double getScore() {
        return score;
    }

    public String toString() {
        return "Job: " + key + " --- Applicant: " + value1 + " --- Recruiter: " + value2 +
                " --- Score: " + score;
    }
}
