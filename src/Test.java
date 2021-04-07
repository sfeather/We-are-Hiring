import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Test {

    public void readFromJSON() {
        int i, j;

        try {
            JsonElement jsonFile = JsonParser.parseReader(new FileReader("consumers.json"));
            JsonObject objectFile = jsonFile.getAsJsonObject();

            ArrayList<Employee> employeeList = new ArrayList<Employee>();
            ArrayList<Recruiter> recruiterList = new ArrayList<Recruiter>();
            ArrayList<User> userList = new ArrayList<User>();
            ArrayList<Manager> managerList = new ArrayList<Manager>();

            Application newApp = Application.getInstance();

            Company googleComp = new Company();
            Company amazonComp = new Company();

            googleComp.setCompanyName("Google");
            amazonComp.setCompanyName("Amazon");

            Department itDepGoogle = FactoryDepartment.factory("IT");
            Department managementDepGoogle = FactoryDepartment.factory("Management");
            Department marketingDepGoogle = FactoryDepartment.factory("Marketing");
            Department financeDepGoogle = FactoryDepartment.factory("Finance");

            Department itDepAmazon = FactoryDepartment.factory("IT");
            Department managementDepAmazon = FactoryDepartment.factory("Management");
            Department marketingDepAmazon = FactoryDepartment.factory("Marketing");
            Department financeDepAmazon = FactoryDepartment.factory("Finance");

            googleComp.add(itDepGoogle);
            googleComp.add(managementDepGoogle);
            googleComp.add(marketingDepGoogle);
            googleComp.add(financeDepGoogle);

            amazonComp.add(itDepAmazon);
            amazonComp.add(managementDepAmazon);
            amazonComp.add(marketingDepAmazon);
            amazonComp.add(financeDepAmazon);

            //EXTRAGERE EMPLOYEES DIN FISIERUL JSON

            JsonArray jsonArrayOfEmployees = objectFile.get("employees").getAsJsonArray();
            for (JsonElement employeeElement : jsonArrayOfEmployees) {
                JsonObject employeeJsonObject = employeeElement.getAsJsonObject();

                Employee employee = new Employee();
                Information information = new Information();
                ArrayList<Education> educationList = new ArrayList<Education>();
                ArrayList<Experience> experienceList = new ArrayList<Experience>();

                //EXTRAGERE INFORMATII

                String name = employeeJsonObject.get("name").getAsString();
                String email = employeeJsonObject.get("email").getAsString();
                String phone = employeeJsonObject.get("phone").getAsString();
                String date_of_birth = employeeJsonObject.get("date_of_birth").getAsString();
                String genre = employeeJsonObject.get("genre").getAsString();

                String[] names = name.split("\\s+");
                String[] birth_dates = date_of_birth.split("\\.");

                information.setFirst_name(names[0]);
                information.setLast_name(names[1]);
                information.setEmail(email);
                information.setPhone_number(phone);
                if (!date_of_birth.equals("null"))
                    information.setBirthday_date(new Date(Integer.parseInt(birth_dates[2]), Integer.parseInt(birth_dates[1]),
                                            Integer.parseInt(birth_dates[0])));
                else
                    information.setBirthday_date(null);
                information.setGender(genre);


                JsonArray jsonArrayOfLanguages = employeeJsonObject.get("languages").getAsJsonArray();
                String langString = jsonArrayOfLanguages.toString();
                String[] languagesBefore = langString.split("\",\"");
                String[] languages = new String[100];

                for (i = 0; i < languagesBefore.length; i++) {
                    if (languagesBefore.length == 1)
                        languages[i] = languagesBefore[i].substring(2, languagesBefore[i].length() - 2);
                    else if (i == 0)
                        languages[i] = languagesBefore[i].substring(2);
                    else if (i == languagesBefore.length - 1)
                        languages[i] = languagesBefore[i].substring(0, languagesBefore[i].length() - 2);
                    else
                        languages[i] = languagesBefore[i];
                }

                JsonArray jsonArrayOfLevel = employeeJsonObject.get("languages_level").getAsJsonArray();
                String levelString = jsonArrayOfLevel.toString();
                String[] levelBefore = levelString.split("\",\"");
                String[] level = new String[100];

                for (i = 0; i < levelBefore.length; i++) {
                    if (levelBefore.length == 1)
                        level[i] = levelBefore[i].substring(2, levelBefore[i].length() - 2);
                    else if (i == 0)
                        level[i] = levelBefore[i].substring(2);
                    else if (i == levelBefore.length - 1)
                        level[i] = levelBefore[i].substring(0, levelBefore[i].length() - 2);
                    else
                        level[i] = levelBefore[i];
                }

                ArrayList<Information.Language> languageArrayList = new ArrayList<Information.Language>();

                for (i = 0; i < levelBefore.length; i++) {
                    Information.Language newLang = new Information.Language();

                    newLang.setLanguage(languages[i]);
                    newLang.setLevel(level[i]);

                    languageArrayList.add(newLang);
                }

                information.setForeign_languages(languageArrayList);

                Double wage = employeeJsonObject.get("salary").getAsDouble();
                employee.setWage(wage);

                //EXTRAGERE EDUCATION LIST

                JsonArray jsonArrayOfEducation = employeeJsonObject.get("education").getAsJsonArray();

                for (JsonElement educationElement : jsonArrayOfEducation) {
                    JsonObject educationJsonObject = educationElement.getAsJsonObject();

                    Education newEduc = new Education();

                    String educationLevel = educationJsonObject.get("level").getAsString();
                    String educationName = educationJsonObject.get("name").getAsString();
                    String startDateString = educationJsonObject.get("start_date").toString();
                    String endDateString = educationJsonObject.get("end_date").toString();
                    Double grade = educationJsonObject.get("grade").getAsDouble();

                    newEduc.setInstitutionName(educationName);
                    newEduc.setLevelOfEducation(educationLevel);
                    newEduc.setFinal_GPA(grade);

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newEduc.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newEduc.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newEduc.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else
                        newEduc.setEnding_date(null);

                    educationList.add(newEduc);

                }

                //EXTRAGERE EXPERIENCE LIST

                JsonArray jsonArrayOfExperience = employeeJsonObject.get("experience").getAsJsonArray();

                for (JsonElement experienceElement : jsonArrayOfExperience) {
                    JsonObject experienceJsonObject = experienceElement.getAsJsonObject();

                    Experience newExp = new Experience();

                    String companyName = experienceJsonObject.get("company").getAsString();
                    String position = experienceJsonObject.get("position").getAsString();
                    String department = experienceJsonObject.get("department").getAsString();
                    String startDateString = experienceJsonObject.get("start_date").toString();
                    String endDateString = experienceJsonObject.get("end_date").toString();

                    newExp.setCompanyName(companyName);
                    newExp.setPosition(position);
                    newExp.setDepartmentName(department);

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newExp.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newExp.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newExp.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else {
                        Job newJob = new Job();
                        newJob.setWage(wage);
                        newJob.setJobName(position);
                        newJob.setCompanyName(companyName);
                        employee.setJob(newJob);
                        newExp.setEnding_date(null);
                    }

                    experienceList.add(newExp);
                }

                Resume resume = new Resume.ResumeBuilder(information, educationList)
                        .experienceList(experienceList)
                        .build();

                employee.setFullName(name);
                employee.setResume(resume);

                employeeList.add(employee);

            }

            //EXTRAGERE RECRUITERS DIN FISIERUL JSON

            JsonArray jsonArrayOfRecruiters = objectFile.get("recruiters").getAsJsonArray();
            for (JsonElement recruiterElement : jsonArrayOfRecruiters) {
                JsonObject recruiterJsonObject = recruiterElement.getAsJsonObject();

                Recruiter recruiter = new Recruiter();
                Information information = new Information();
                ArrayList<Education> educationList = new ArrayList<Education>();
                ArrayList<Experience> experienceList = new ArrayList<Experience>();

                //EXTRAGERE INFORMATII

                String name = recruiterJsonObject.get("name").getAsString();
                String email = recruiterJsonObject.get("email").getAsString();
                String phone = recruiterJsonObject.get("phone").getAsString();
                String date_of_birth = recruiterJsonObject.get("date_of_birth").getAsString();
                String genre = recruiterJsonObject.get("genre").getAsString();

                String[] names = name.split("\\s+");
                String[] birth_dates = date_of_birth.split("\\.");

                information.setFirst_name(names[0]);
                information.setLast_name(names[1]);
                information.setEmail(email);
                information.setPhone_number(phone);
                if (!date_of_birth.equals("null"))
                    information.setBirthday_date(new Date(Integer.parseInt(birth_dates[2]), Integer.parseInt(birth_dates[1]),
                            Integer.parseInt(birth_dates[0])));
                else
                    information.setBirthday_date(null);
                information.setGender(genre);


                JsonArray jsonArrayOfLanguages = recruiterJsonObject.get("languages").getAsJsonArray();
                String langString = jsonArrayOfLanguages.toString();
                String[] languagesBefore = langString.split("\",\"");
                String[] languages = new String[100];

                for (i = 0; i < languagesBefore.length; i++) {
                    if (languagesBefore.length == 1)
                        languages[i] = languagesBefore[i].substring(2, languagesBefore[i].length() - 2);
                    else if (i == 0)
                        languages[i] = languagesBefore[i].substring(2);
                    else if (i == languagesBefore.length - 1)
                        languages[i] = languagesBefore[i].substring(0, languagesBefore[i].length() - 2);
                    else
                        languages[i] = languagesBefore[i];
                }

                JsonArray jsonArrayOfLevel = recruiterJsonObject.get("languages_level").getAsJsonArray();
                String levelString = jsonArrayOfLevel.toString();
                String[] levelBefore = levelString.split("\",\"");
                String[] level = new String[100];

                for (i = 0; i < levelBefore.length; i++) {
                    if (levelBefore.length == 1)
                        level[i] = levelBefore[i].substring(2, levelBefore[i].length() - 2);
                    else if (i == 0)
                        level[i] = levelBefore[i].substring(2);
                    else if (i == levelBefore.length - 1)
                        level[i] = levelBefore[i].substring(0, levelBefore[i].length() - 2);
                    else
                        level[i] = levelBefore[i];
                }

                ArrayList<Information.Language> languageArrayList = new ArrayList<Information.Language>();

                for (i = 0; i < levelBefore.length; i++) {
                    Information.Language newLang = new Information.Language();

                    newLang.setLanguage(languages[i]);
                    newLang.setLevel(level[i]);

                    languageArrayList.add(newLang);
                }

                information.setForeign_languages(languageArrayList);

                Double wage = recruiterJsonObject.get("salary").getAsDouble();
                recruiter.setWage(wage);

                //EXTRAGERE EDUCATION LIST

                JsonArray jsonArrayOfEducation = recruiterJsonObject.get("education").getAsJsonArray();

                for (JsonElement educationElement : jsonArrayOfEducation) {
                    JsonObject educationJsonObject = educationElement.getAsJsonObject();

                    Education newEduc = new Education();

                    String educationLevel = educationJsonObject.get("level").getAsString();
                    String educationName = educationJsonObject.get("name").getAsString();
                    String startDateString = educationJsonObject.get("start_date").toString();
                    String endDateString = educationJsonObject.get("end_date").toString();
                    Double grade = educationJsonObject.get("grade").getAsDouble();

                    newEduc.setInstitutionName(educationName);
                    newEduc.setLevelOfEducation(educationLevel);
                    newEduc.setFinal_GPA(grade);

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newEduc.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newEduc.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newEduc.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else
                        newEduc.setEnding_date(null);

                    educationList.add(newEduc);
                }

                ////EXTRAGERE EXPERIENCE LIST

                JsonArray jsonArrayOfExperience = recruiterJsonObject.get("experience").getAsJsonArray();

                for (JsonElement experienceElement : jsonArrayOfExperience) {
                    JsonObject experienceJsonObject = experienceElement.getAsJsonObject();

                    Experience newExp = new Experience();

                    String companyName = experienceJsonObject.get("company").getAsString();
                    String position = experienceJsonObject.get("position").getAsString();
                    String startDateString = experienceJsonObject.get("start_date").toString();
                    String endDateString = experienceJsonObject.get("end_date").toString();

                    newExp.setCompanyName(companyName);
                    newExp.setPosition(position);
                    newExp.setDepartmentName("IT");

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newExp.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newExp.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newExp.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else {
                        Job newJob = new Job();
                        newJob.setWage(wage);
                        newJob.setJobName(position);
                        newJob.setCompanyName(companyName);
                        recruiter.setJob(newJob);
                        newExp.setEnding_date(null);
                    }


                    experienceList.add(newExp);
                }

                Resume resume = new Resume.ResumeBuilder(information, educationList)
                        .experienceList(experienceList)
                        .build();

                recruiter.setFullName(name);
                recruiter.setResume(resume);

                recruiterList.add(recruiter);
            }

            //EXTRAGERE USERS DIN FISIERUL JSON

            JsonArray jsonArrayOfUsers = objectFile.get("users").getAsJsonArray();
            for (JsonElement userElement : jsonArrayOfUsers) {
                JsonObject userJsonObject = userElement.getAsJsonObject();

                User user = new User();
                Information information = new Information();
                ArrayList<Education> educationList = new ArrayList<Education>();
                ArrayList<Experience> experienceList = new ArrayList<Experience>();

                //EXTRAGERE INFORMATII

                String name = userJsonObject.get("name").getAsString();
                String email = userJsonObject.get("email").getAsString();
                String phone = userJsonObject.get("phone").getAsString();
                String date_of_birth = userJsonObject.get("date_of_birth").getAsString();
                String genre = userJsonObject.get("genre").getAsString();

                String[] names = name.split("\\s+");
                String[] birth_dates = date_of_birth.split("\\.");

                information.setFirst_name(names[0]);
                information.setLast_name(names[1]);
                information.setEmail(email);
                information.setPhone_number(phone);
                if (!date_of_birth.equals("null"))
                    information.setBirthday_date(new Date(Integer.parseInt(birth_dates[2]), Integer.parseInt(birth_dates[1]),
                            Integer.parseInt(birth_dates[0])));
                else
                    information.setBirthday_date(null);
                information.setGender(genre);


                JsonArray jsonArrayOfLanguages = userJsonObject.get("languages").getAsJsonArray();
                String langString = jsonArrayOfLanguages.toString();
                String[] languagesBefore = langString.split("\",\"");
                String[] languages = new String[100];

                for (i = 0; i < languagesBefore.length; i++) {
                    if (languagesBefore.length == 1)
                        languages[i] = languagesBefore[i].substring(2, languagesBefore[i].length() - 2);
                    else if (i == 0)
                        languages[i] = languagesBefore[i].substring(2);
                    else if (i == languagesBefore.length - 1)
                        languages[i] = languagesBefore[i].substring(0, languagesBefore[i].length() - 2);
                    else
                        languages[i] = languagesBefore[i];
                }

                JsonArray jsonArrayOfLevel = userJsonObject.get("languages_level").getAsJsonArray();
                String levelString = jsonArrayOfLevel.toString();
                String[] levelBefore = levelString.split("\",\"");
                String[] level = new String[100];

                for (i = 0; i < levelBefore.length; i++) {
                    if (levelBefore.length == 1)
                        level[i] = levelBefore[i].substring(2, levelBefore[i].length() - 2);
                    else if (i == 0)
                        level[i] = levelBefore[i].substring(2);
                    else if (i == levelBefore.length - 1)
                        level[i] = levelBefore[i].substring(0, levelBefore[i].length() - 2);
                    else
                        level[i] = levelBefore[i];
                }

                ArrayList<Information.Language> languageArrayList = new ArrayList<Information.Language>();

                for (i = 0; i < levelBefore.length; i++) {
                    Information.Language newLang = new Information.Language();

                    newLang.setLanguage(languages[i]);
                    newLang.setLevel(level[i]);

                    languageArrayList.add(newLang);
                }

                information.setForeign_languages(languageArrayList);

                ArrayList<String> interested_companies = new ArrayList<String>();

                JsonArray jsonArrayOfIC = userJsonObject.get("interested_companies").getAsJsonArray();
                String icString = jsonArrayOfIC.toString();
                String[] icBefore = icString.split("\",\"");

                for (i = 0; i < icBefore.length; i++) {
                    if (icBefore.length == 1)
                        interested_companies.add(icBefore[i].substring(2, icBefore[i].length() - 2));
                    else if (i == 0)
                        interested_companies.add(icBefore[i].substring(2));
                    else if (i == icBefore.length - 1)
                        interested_companies.add(icBefore[i].substring(0, icBefore[i].length() - 2));
                    else
                        interested_companies.add(icBefore[i]);
                }

                user.setWantedCompanies(interested_companies);

                //EXTRAGERE EDUCATION LIST

                JsonArray jsonArrayOfEducation = userJsonObject.get("education").getAsJsonArray();

                for (JsonElement educationElement : jsonArrayOfEducation) {
                    JsonObject educationJsonObject = educationElement.getAsJsonObject();

                    Education newEduc = new Education();

                    String educationLevel = educationJsonObject.get("level").getAsString();
                    String educationName = educationJsonObject.get("name").getAsString();
                    String startDateString = educationJsonObject.get("start_date").toString();
                    String endDateString = educationJsonObject.get("end_date").toString();
                    Double grade = educationJsonObject.get("grade").getAsDouble();

                    newEduc.setInstitutionName(educationName);
                    newEduc.setLevelOfEducation(educationLevel);
                    newEduc.setFinal_GPA(grade);

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newEduc.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newEduc.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newEduc.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else
                        newEduc.setEnding_date(null);

                    educationList.add(newEduc);
                }

                //EXTRAGERE EXPERIENCE LIST

                JsonArray jsonArrayOfExperience = userJsonObject.get("experience").getAsJsonArray();

                for (JsonElement experienceElement : jsonArrayOfExperience) {
                    JsonObject experienceJsonObject = experienceElement.getAsJsonObject();

                    Experience newExp = new Experience();

                    String companyName = experienceJsonObject.get("company").getAsString();
                    String position = experienceJsonObject.get("position").getAsString();
                    String startDateString = experienceJsonObject.get("start_date").toString();
                    String endDateString = experienceJsonObject.get("end_date").toString();

                    newExp.setCompanyName(companyName);
                    newExp.setPosition(position);
                    newExp.setDepartmentName("");

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newExp.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newExp.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newExp.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else
                        newExp.setEnding_date(null);

                    experienceList.add(newExp);
                }

                Resume resume = new Resume.ResumeBuilder(information, educationList)
                        .experienceList(experienceList)
                        .build();

                user.setFullName(name);
                user.setResume(resume);

                userList.add(user);
            }

            //EXTRAGERE MANAGERS DIN FISIERUL JSON

            JsonArray jsonArrayOfManagers = objectFile.get("managers").getAsJsonArray();
            for (JsonElement managerElement : jsonArrayOfManagers) {
                JsonObject managerJsonObject = managerElement.getAsJsonObject();

                Manager manager = new Manager();
                Information information = new Information();
                ArrayList<Education> educationList = new ArrayList<Education>();
                ArrayList<Experience> experienceList = new ArrayList<Experience>();

                //EXTRAGERE INFORMATII

                String name = managerJsonObject.get("name").getAsString();
                String email = managerJsonObject.get("email").getAsString();
                String phone = managerJsonObject.get("phone").getAsString();
                String date_of_birth = managerJsonObject.get("date_of_birth").getAsString();
                String genre = managerJsonObject.get("genre").getAsString();

                String[] names = name.split("\\s+");
                String[] birth_dates = date_of_birth.split("\\.");

                information.setFirst_name(names[0]);
                information.setLast_name(names[1]);
                information.setEmail(email);
                information.setPhone_number(phone);
                if (!date_of_birth.equals("null"))
                    information.setBirthday_date(new Date(Integer.parseInt(birth_dates[2]), Integer.parseInt(birth_dates[1]),
                            Integer.parseInt(birth_dates[0])));
                else
                    information.setBirthday_date(null);
                information.setGender(genre);


                JsonArray jsonArrayOfLanguages = managerJsonObject.get("languages").getAsJsonArray();
                String langString = jsonArrayOfLanguages.toString();
                String[] languagesBefore = langString.split("\",\"");
                String[] languages = new String[100];

                for (i = 0; i < languagesBefore.length; i++) {
                    if (languagesBefore.length == 1)
                        languages[i] = languagesBefore[i].substring(2, languagesBefore[i].length() - 2);
                    else if (i == 0)
                        languages[i] = languagesBefore[i].substring(2);
                    else if (i == languagesBefore.length - 1)
                        languages[i] = languagesBefore[i].substring(0, languagesBefore[i].length() - 2);
                    else
                        languages[i] = languagesBefore[i];
                }

                JsonArray jsonArrayOfLevel = managerJsonObject.get("languages_level").getAsJsonArray();
                String levelString = jsonArrayOfLevel.toString();
                String[] levelBefore = levelString.split("\",\"");
                String[] level = new String[100];

                for (i = 0; i < levelBefore.length; i++) {
                    if (levelBefore.length == 1)
                        level[i] = levelBefore[i].substring(2, levelBefore[i].length() - 2);
                    else if (i == 0)
                        level[i] = levelBefore[i].substring(2);
                    else if (i == levelBefore.length - 1)
                        level[i] = levelBefore[i].substring(0, levelBefore[i].length() - 2);
                    else
                        level[i] = levelBefore[i];
                }

                ArrayList<Information.Language> languageArrayList = new ArrayList<Information.Language>();

                for (i = 0; i < levelBefore.length; i++) {
                    Information.Language newLang = new Information.Language();

                    newLang.setLanguage(languages[i]);
                    newLang.setLevel(level[i]);

                    languageArrayList.add(newLang);
                }

                information.setForeign_languages(languageArrayList);

                Double wage = managerJsonObject.get("salary").getAsDouble();
                manager.setWage(wage);

                //EXTRAGERE EDUCATION LIST

                JsonArray jsonArrayOfEducation = managerJsonObject.get("education").getAsJsonArray();

                for (JsonElement educationElement : jsonArrayOfEducation) {
                    JsonObject educationJsonObject = educationElement.getAsJsonObject();

                    Education newEduc = new Education();

                    String educationLevel = educationJsonObject.get("level").getAsString();
                    String educationName = educationJsonObject.get("name").getAsString();
                    String startDateString = educationJsonObject.get("start_date").toString();
                    String endDateString = educationJsonObject.get("end_date").toString();
                    Double grade = educationJsonObject.get("grade").getAsDouble();

                    newEduc.setInstitutionName(educationName);
                    newEduc.setLevelOfEducation(educationLevel);
                    newEduc.setFinal_GPA(grade);

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newEduc.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newEduc.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newEduc.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else
                        newEduc.setEnding_date(null);

                    educationList.add(newEduc);
                }

                //EXTRAGERE EXPERIENCE LIST

                JsonArray jsonArrayOfExperience = managerJsonObject.get("experience").getAsJsonArray();

                for (JsonElement experienceElement : jsonArrayOfExperience) {
                    JsonObject experienceJsonObject = experienceElement.getAsJsonObject();

                    Experience newExp = new Experience();

                    String companyName = experienceJsonObject.get("company").getAsString();
                    String position = experienceJsonObject.get("position").getAsString();
                    String startDateString = experienceJsonObject.get("start_date").toString();
                    String endDateString = experienceJsonObject.get("end_date").toString();

                    newExp.setCompanyName(companyName);
                    newExp.setPosition(position);
                    newExp.setDepartmentName("");

                    if (!startDateString.contains("null"))
                        startDateString = startDateString.substring(1, startDateString.length() - 1);
                    if (!endDateString.contains("null"))
                        endDateString = endDateString.substring(1, endDateString.length() - 1);

                    String[] start_dates = startDateString.split("\\.");
                    String[] end_dates = endDateString.split("\\.");

                    if (!startDateString.equals("null"))
                        newExp.setStarting_date(new Date(Integer.parseInt(start_dates[2]), Integer.parseInt(start_dates[1]),
                                Integer.parseInt(start_dates[0])));
                    else
                        newExp.setStarting_date(null);

                    if (!endDateString.equals("null"))
                        newExp.setEnding_date(new Date(Integer.parseInt(end_dates[2]), Integer.parseInt(end_dates[1]),
                                Integer.parseInt(end_dates[0])));
                    else
                        newExp.setEnding_date(null);

                    experienceList.add(newExp);
                }

                Resume resume = new Resume.ResumeBuilder(information, educationList)
                        .experienceList(experienceList)
                        .build();

                manager.setFullName(name);
                manager.setResume(resume);

                managerList.add(manager);
            }

            //ADAUGARE COMPANII
            //ADAUGARE ANGAJATI IN DEPARTAMENTELE CORESPUNZATOARE SI IN COMANIILE CORESPUNZATOARE

            managerList.get(0).setCompanyName("Google");
            managerList.get(1).setCompanyName("Amazon");


            for (i = 0; i < employeeList.size(); i++) {
                switch (employeeList.get(i).getFullName()) {
                    case "Harmony Lorinda" -> {
                        employeeList.get(i).setDepartment(itDepAmazon);
                        amazonComp.getDepartments().get(0).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Amazon");
                    }
                    case "Shana Addy" -> {
                        employeeList.get(i).setDepartment(managementDepAmazon);
                        amazonComp.getDepartments().get(1).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Amazon");
                        employeeList.get(i).add(employeeList.get(9));
                        employeeList.get(i).add(recruiterList.get(2));
                    }
                    case "Leyla Stacy" -> {
                        employeeList.get(i).setDepartment(marketingDepAmazon);
                        amazonComp.getDepartments().get(2).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Amazon");
                        employeeList.get(i).add(employeeList.get(5));
                        employeeList.get(i).add(recruiterList.get(1));
                        employeeList.get(i).add(userList.get(0));
                        employeeList.get(i).add(userList.get(2));
                    }
                    case "Jarred Egbert" -> {
                        employeeList.get(i).setDepartment(financeDepAmazon);
                        amazonComp.getDepartments().get(3).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Amazon");
                    }
                    case "Bethney Kiara" -> {
                        employeeList.get(i).setDepartment(marketingDepAmazon);
                        amazonComp.getDepartments().get(2).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Amazon");
                    }
                    case "Rolland Lyla" -> {
                        employeeList.get(i).setDepartment(itDepGoogle);
                        googleComp.getDepartments().get(0).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Google");
                        employeeList.get(i).add(employeeList.get(2));
                        employeeList.get(i).add(recruiterList.get(3));
                    }
                    case "Steward Goodwin" -> {
                        employeeList.get(i).setDepartment(managementDepGoogle);
                        googleComp.getDepartments().get(1).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Google");
                        employeeList.get(i).add(userList.get(1));
                    }
                    case "Molly Rylie" -> {
                        employeeList.get(i).setDepartment(marketingDepGoogle);
                        googleComp.getDepartments().get(2).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Google");
                    }
                    case "Morley Denise" -> {
                        employeeList.get(i).setDepartment(financeDepGoogle);
                        googleComp.getDepartments().get(3).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Google");
                    }
                    case "Bryson Reenie" -> {
                        employeeList.get(i).setDepartment(marketingDepGoogle);
                        googleComp.getDepartments().get(2).add(employeeList.get(i));
                        employeeList.get(i).setCompanyName("Google");
                        employeeList.get(i).add(employeeList.get(1));
                        employeeList.get(i).add(userList.get(3));
                    }
                }
            }

            //ADAUGARE RECRUITERI IN COMPANIILE CORESPUNZATOARE (IN DEPARTAMENTUL DE IT)

            for (i = 0; i < recruiterList.size(); i++) {
                switch (recruiterList.get(i).getFullName()) {
                    case "Jonie Phillip" -> {
                        recruiterList.get(i).setDepartment(itDepGoogle);
                        googleComp.getDepartments().get(0).add(recruiterList.get(i));
                        recruiterList.get(i).setCompanyName("Google");
                        recruiterList.get(i).add(userList.get(1));
                    }
                    case "Janine Woody" -> {
                        recruiterList.get(i).setDepartment(itDepGoogle);
                        googleComp.getDepartments().get(0).add(recruiterList.get(i));
                        recruiterList.get(i).setCompanyName("Google");
                        recruiterList.get(i).add(employeeList.get(2));
                    }
                    case "Darrell Lillie" -> {
                        recruiterList.get(i).setDepartment(itDepAmazon);
                        amazonComp.getDepartments().get(0).add(recruiterList.get(i));
                        recruiterList.get(i).setCompanyName("Amazon");
                        recruiterList.get(i).add(employeeList.get(1));
                    }
                    case "Damian Bodhi" -> {
                        recruiterList.get(i).setDepartment(itDepAmazon);
                        amazonComp.getDepartments().get(0).add(recruiterList.get(i));
                        recruiterList.get(i).setCompanyName("Amazon");
                        recruiterList.get(i).add(employeeList.get(5));
                    }
                }
            }

            for (i = 0; i < userList.size(); i++) {
                switch (userList.get(i).getFullName()) {
                    case "Daniel Edmund" -> {
                        userList.get(i).add(userList.get(1));
                        userList.get(i).add(employeeList.get(2));
                    }
                    case "Julia Matvei" -> {
                        userList.get(i).add(userList.get(0));
                        userList.get(i).add(recruiterList.get(0));
                        userList.get(i).add(employeeList.get(6));
                    }
                    case "Tamara Haci" -> {
                        userList.get(i).add(userList.get(3));
                        userList.get(i).add(employeeList.get(2));
                    }
                    case "Linette Spartak" -> {
                        userList.get(i).add(userList.get(2));
                        userList.get(i).add(employeeList.get(9));
                    }
                }
            }

            googleComp.setManager(managerList.get(0));
            amazonComp.setManager(managerList.get(1));

            googleComp.add(recruiterList.get(0));
            googleComp.add(recruiterList.get(1));

            amazonComp.add(recruiterList.get(2));
            amazonComp.add(recruiterList.get(3));

            newApp.add(googleComp);
            newApp.add(amazonComp);
            newApp.setUserList(userList);

            //de creat propriul meu .json
            //Am creat json-ul jobs.json pentru a parsa din el datele joburilor!
            JsonElement jsonJobsFile = JsonParser.parseReader(new FileReader("jobs.json"));
            JsonObject jobsFile = jsonJobsFile.getAsJsonObject();

            ArrayList<Job> jobList = new ArrayList<Job>();

            //EXTRAGERE JOBS DIN FISIERUL JSON CREAT (jobs.json)

            JsonArray jsonArrayOfJobs = jobsFile.get("jobs").getAsJsonArray();
            for (JsonElement jobElement : jsonArrayOfJobs) {
                JsonObject jobJsonObject = jobElement.getAsJsonObject();

                Job job = new Job();

                String company_name = jobJsonObject.get("company_name").getAsString();
                String job_name = jobJsonObject.get("job_name").getAsString();
                int no_positions = jobJsonObject.get("no_positions").getAsInt();
                Double wage = jobJsonObject.get("salary").getAsDouble();
                String graduation_mins = jobJsonObject.get("graduation_min").toString();
                String graduation_maxs= jobJsonObject.get("graduation_max").toString();
                String experience_mins = jobJsonObject.get("experience_min").toString();
                String experience_maxs = jobJsonObject.get("experience_max").toString();
                String average_mins = jobJsonObject.get("average_min").toString();
                String average_maxs = jobJsonObject.get("average_max").toString();

                job.setCompanyName(company_name);
                job.setJobName(job_name);
                job.setNoPositions(no_positions);
                job.setWage(wage);

                if (!graduation_mins.equals("null")) {
                    if (!graduation_maxs.equals("null"))
                        job.setGraduationYear(new Constraint<Integer>(Integer.parseInt(graduation_mins),
                                        Integer.parseInt(graduation_maxs)));
                    else
                        job.setGraduationYear(new Constraint<Integer>(Integer.parseInt(graduation_mins),
                                        null));
                } else {
                    if (!graduation_maxs.equals("null"))
                        job.setGraduationYear(new Constraint<Integer>(null,
                                Integer.parseInt(graduation_maxs)));
                    else
                        job.setGraduationYear(new Constraint<Integer>(null, null));
                }

                if (!experience_mins.equals("null")) {
                    if (!experience_maxs.equals("null"))
                        job.setYearsOfExperience(new Constraint<Integer>(Integer.parseInt(experience_mins),
                                Integer.parseInt(experience_maxs)));
                    else
                        job.setYearsOfExperience(new Constraint<Integer>(Integer.parseInt(experience_mins),
                                null));
                } else {
                    if (!experience_maxs.equals("null"))
                        job.setYearsOfExperience(new Constraint<Integer>(null,
                                Integer.parseInt(experience_maxs)));
                    else
                        job.setYearsOfExperience(new Constraint<Integer>(null, null));
                }

                if (!average_mins.equals("null")) {
                    if (!average_maxs.equals("null"))
                        job.setMeanGPA(new Constraint<Double>(Double.parseDouble(average_mins),
                                Double.parseDouble(average_maxs)));
                    else
                        job.setMeanGPA(new Constraint<Double>(Double.parseDouble(average_mins),
                                null));
                } else {
                    if (!average_maxs.equals("null"))
                        job.setMeanGPA(new Constraint<Double>(null,
                                Double.parseDouble(average_maxs)));
                    else
                        job.setMeanGPA(new Constraint<Double>(null, null));
                }

                if (company_name.equals("Google"))
                    itDepGoogle.add(job);
                else
                    itDepAmazon.add(job);

                job.setValability(true);

                jobList.add(job);
            }


            for (i = 0; i < userList.size(); i++) {
                if (userList.get(i).getWantedCompanies().contains("Google"))
                    for (j = 0; j < googleComp.getJobs().size(); j++)
                        if (googleComp.getJobs().get(j).meetsRequirments(userList.get(i)))
                            googleComp.getJobs().get(j).apply(userList.get(i));

                if (userList.get(i).getWantedCompanies().contains("Amazon"))
                    for (j = 0; j < amazonComp.getJobs().size(); j++)
                        if (amazonComp.getJobs().get(j).meetsRequirments(userList.get(i)))
                            amazonComp.getJobs().get(j).apply(userList.get(i));
            }

            //TEST USERI
//            System.out.println("TEST USERI\n" + userList.toString() + "\n");

            //TEST ANGAJATI
//            System.out.println("TEST ANGAJATI\n" + employeeList.toString() + "\n");

            //TEST JOBURI
//            System.out.println("TEST JOBURI\n" + jobList.toString() + "\n");


            //TEST ANGAJARE AUTOMATA A USERILOR CARE AU APLICAT LA JOBURI

//            googleComp.getManager().process(jobList.get(0));
//            googleComp.getManager().process(jobList.get(1));
//
//            amazonComp.getManager().process(jobList.get(2));
//            amazonComp.getManager().process(jobList.get(3));

            //TEST USERI DUPA ANGAJAREA AUTOMATA

//            System.out.println("TEST USERI DUPA ANGAJAREA AUTOMATA\n" + userList.toString() + "\n");

            //TEST ANGAJATI DUPA ANGAJAREA AUTOMATA

//            System.out.println("TEST ANGAJATI GOOGLE DUPA ANGAJAREA AUTOMATA");
//            System.out.println("Departament IT:\n" + googleComp.getDepartments().get(0).getEmployees().toString());
//            System.out.println("Am testat doar departamentul IT deoarece doar acesta se schimba!\n");
//
//            System.out.println("TEST ANGAJATI AMAZON DUPA ANGAJAREA AUTOMATA\n");
//            System.out.println("Departament IT:\n" + amazonComp.getDepartments().get(0).getEmployees().toString());
//            System.out.println("Am testat doar departamentul IT deoarece doar acesta se schimba!\n");

            //EXPLICATII OUTPUT

            //in urma angajarii mai raman doar userii Daniel Edmund si Linette Spartak deoarece Daniel Edmund
            //nu a putut aplica la niciun job (din cauza restrictiilor) iar Linette Spartak a pierdut postul
            //in favoarea lui Julia Matvei (Julia Matvei are scorul mai mare)

            //TEST NOTIFICARI USERI

            //notificari o sa primeasca doar Daniel Edmund si Linette Spartak intrucat doar ei mai sunt pe
            //lista de Useri.
            //Daniel Edmund nu va primi niciun mesaj de inchidere a joburilor deoarece acesta nu a aplicat la
            //nicio companie (din cauza restrictiilor)
            //Linette Spartak a aplicat doar la Google (tot din cauza restrictiilor) astfel ca va primi 2 notificari
            //pentru joburile care s-au inchis in compania Google (Software Developer Engineer, respectiv
            //Software Developer Engineer Intern)
            //In plus, deoarece Linette Spartak a aplicat la Google pentru jobul de Software Developer Engineer Intern
            //aceasta va primi o notificare cum ca a fost respinsa de la acel job

//            for (i = 0; i < userList.size(); i++) {
//                System.out.println(userList.get(i).getFullName());
//                System.out.println(userList.get(i).getNotificationList().toString());
//            }

        } catch (FileNotFoundException | ResumeIncompleteException | InvalidDatesException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Test test = new Test();
//        test.readFromJSON();
//    }
}
