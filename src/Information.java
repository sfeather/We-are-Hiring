import java.util.*;

public class Information {
    private String last_name, first_name, email, phone_number, gender;
    private Date birthday_date;
    private ArrayList<Language> foreign_languages;

    //clasa care ma ajuta sa stochez o limba si nivelul acesteia
    static class Language {
        private String language, level;

        public void setLanguage(String language) {
            this.language = language;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLanguage() {
            return language;
        }

        public String getLevel() {
            return level;
        }

        public String toString() {
            return language + " ----- " + level;
        }
    }

    public Information() {
        foreign_languages = new ArrayList<>();
    }

    public void setBirthday_date(Date birthday_date) {
        this.birthday_date = birthday_date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setForeign_languages(ArrayList<Language> foreign_languages) {
        this.foreign_languages = foreign_languages;
    }

    public void addForeignLanguages(String language, String level) {
        Language new_language = new Language();
        new_language.language = language;
        new_language.level = level;
        foreign_languages.add(new_language);
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Date getBirthday_date() {
        return birthday_date;
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<Language> getForeignLanguages() {
        return foreign_languages;
    }

    public String toString() {
        String string = "Birthday date: " + birthday_date.getDate() +
                "." + birthday_date.getMonth() + "." + birthday_date.getYear() + "\n" +
                "Phone number: " + phone_number + "\n" + "Email: " + email + "\n" +
                "Gender: " + gender + "\n";

        String language_string = "";

        for (int i = 0; i < foreign_languages.size(); i++) {
            language_string = language_string + foreign_languages.get(i).toString() + "\n";
        }

        string = string + "Known languages and their level: " + "\n" + language_string;


        return string;
    }
}
