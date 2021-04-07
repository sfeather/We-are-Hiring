import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class WeAreHiringApp extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JPanel adminPanel = new JPanel();
    private JPanel managerPanel = new JPanel();
    private JPanel profilePanel = new JPanel();
    private JPanel notificationPanel = new JPanel();

    private JList<User> userList = new JList<User>();
    private JList<Company> companyList = new JList<Company>();
    private JList<Employee> employeeList = new JList<Employee>();
    private JList<Department> departmentList = new JList<Department>();
    private JList<Manager> managerList = new JList<Manager>();
    private JList<Request<Job, Consumer>> requestList = new JList<Request<Job, Consumer>>();

    private JTextArea userText = new JTextArea();
    private JTextArea employeeText = new JTextArea();
    private JTextArea informationText = new JTextArea();
    private JTextArea educationText = new JTextArea();
    private JTextArea experienceText = new JTextArea();
    private JTextArea notificationText = new JTextArea();

    private JTextField totalWageText = new JTextField("");
    private JTextField userSearchText = new JTextField("");
    private JTextField enterNameText = new JTextField("Write the USER's/EMPLOYEE's full name and press ENTER");
    private JTextField enterNameTextNotif = new JTextField("Write the USER's full name and press ENTER");
    private JTextField userSearchTextNotif = new JTextField("");

    private JButton depWageButton = new JButton("Calculate wage budget for the selected department");
    private JButton acceptRequest = new JButton("Accept job request (Hire)");
    private JButton declineRequest = new JButton("Decline job request (Reject)");

    JScrollPane userScrollPane = new JScrollPane(userList);
    JScrollPane companyScrollPane = new JScrollPane(companyList);
    JScrollPane userTextScrollPane = new JScrollPane(userText);
    JScrollPane departmentsScrollPane = new JScrollPane(departmentList);
    JScrollPane employeeScrollPane = new JScrollPane(employeeList);
    JScrollPane employeeTextScrollPane = new JScrollPane(employeeText);
    JScrollPane managerScrollPane = new JScrollPane(managerList);
    JScrollPane requestScrollPane = new JScrollPane(requestList);
    JScrollPane informationScrollPane = new JScrollPane(informationText);
    JScrollPane educationScrollPane = new JScrollPane(educationText);
    JScrollPane experienceScrollPane = new JScrollPane(experienceText);
    JScrollPane notificationScrollPane = new JScrollPane(notificationText);

    public WeAreHiringApp() throws IOException {
        setTitle("We Are Hiring!");
        setPreferredSize(new Dimension(800, 800));

        BufferedImage framePicture = ImageIO.read(new File("we_are_hiring.png"));

        setIconImage(framePicture);

        int i;

        Test test = new Test();
        test.readFromJSON();
        Application newApp = Application.getInstance();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        adminPanel.setLayout(new GridLayout(5, 5));

        depWageButton.setEnabled(false);
        depWageButton.setBackground(Color.GREEN);
        depWageButton.setOpaque(true);

        DefaultListModel<User> dlmUser = new DefaultListModel<User>();
        DefaultListModel<Company> dlmCompany = new DefaultListModel<Company>();
        DefaultListModel<Manager> dlmManager = new DefaultListModel<Manager>();

        for (i = 0; i < newApp.getUserList().size(); i++)
            dlmUser.addElement(newApp.getUserList().get(i));

        for (i = 0; i < newApp.getCompanyList().size(); i++) {
            dlmCompany.addElement(newApp.getCompanyList().get(i));
            dlmManager.addElement(newApp.getCompanyList().get(i).getManager());
        }


        userList.setModel(dlmUser);
        companyList.setModel(dlmCompany);
        managerList.setModel(dlmManager);


        userTextScrollPane.setVisible(false);
        departmentsScrollPane.setVisible(false);
        employeeScrollPane.setVisible(false);
        employeeTextScrollPane.setVisible(false);

        userScrollPane.setPreferredSize(new Dimension(350, 50));
        companyScrollPane.setPreferredSize(new Dimension(350, 50));
        userTextScrollPane.setPreferredSize(new Dimension(350, 50));
        departmentsScrollPane.setPreferredSize(new Dimension(350, 50));
        employeeScrollPane.setPreferredSize(new Dimension(350, 50));
        employeeTextScrollPane.setPreferredSize(new Dimension(350, 50));
        managerScrollPane.setPreferredSize(new Dimension(350, 50));
        requestScrollPane.setPreferredSize(new Dimension(350, 50));

        userText.setEditable(false);
        employeeText.setEditable(false);
        totalWageText.setEditable(false);


        userText.setFont(userText.getFont().deriveFont(13F));
        employeeText.setFont(employeeText.getFont().deriveFont(13F));
        informationText.setFont(informationText.getFont().deriveFont(Font.BOLD, 12F));
        educationText.setFont(educationText.getFont().deriveFont(Font.BOLD, 12F));
        experienceText.setFont(experienceText.getFont().deriveFont(Font.BOLD, 12F));
        notificationText.setFont(notificationText.getFont().deriveFont(Font.BOLD, 13F));

        totalWageText.setFont(totalWageText.getFont().deriveFont(Font.BOLD, 13F));


        userList.setFont(userList.getFont().deriveFont(14F));
        companyList.setFont(companyList.getFont().deriveFont(14F));
        employeeList.setFont(employeeList.getFont().deriveFont(14F));
        departmentList.setFont(departmentList.getFont().deriveFont(14F));
        managerList.setFont(managerList.getFont().deriveFont(14F));
        requestList.setFont(requestList.getFont().deriveFont(14F));


        //ADMIN PANEL

        ListSelectionListener userLSL = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (userList.isSelectionEmpty()) {
                    userTextScrollPane.setVisible(false);
                    return;
                }

                userTextScrollPane.setVisible(true);

                User newUser = userList.getSelectedValue();
                userText.setText(newUser.showUserDetails());
            }
        };
        userList.addListSelectionListener(userLSL);

        ListSelectionListener companyLSL = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (companyList.isSelectionEmpty()) {
                    departmentsScrollPane.setVisible(false);
                    employeeScrollPane.setVisible(false);
                    employeeTextScrollPane.setVisible(false);
                    return;
                }

                departmentsScrollPane.setVisible(true);

                Company newCompany = companyList.getSelectedValue();
                DefaultListModel<Department> dlmDepartment = new DefaultListModel<Department>();
                int i;

                for (i = 0; i < newCompany.getDepartments().size(); i++)
                    dlmDepartment.addElement(newCompany.getDepartments().get(i));

                departmentList.setModel(dlmDepartment);
            }
        };
        companyList.addListSelectionListener(companyLSL);

        ListSelectionListener departmentLSL = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (departmentList.isSelectionEmpty()) {
                    depWageButton.setEnabled(false);
                    employeeScrollPane.setVisible(false);
                    employeeTextScrollPane.setVisible(false);
                    return;
                }

                depWageButton.setEnabled(true);
                employeeScrollPane.setVisible(true);

                Department newDepartment = departmentList.getSelectedValue();
                DefaultListModel<Employee> dlmEmployee = new DefaultListModel<Employee>();
                int i;

                for (i = 0; i < newDepartment.getEmployees().size(); i++)
                    dlmEmployee.addElement(newDepartment.getEmployees().get(i));

                employeeList.setModel(dlmEmployee);
            }
        };
        departmentList.addListSelectionListener(departmentLSL);

        ListSelectionListener employeeLSL = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (employeeList.isSelectionEmpty()) {
                    employeeTextScrollPane.setVisible(false);
                    return;
                }

                employeeTextScrollPane.setVisible(true);

                Employee newEmployee = employeeList.getSelectedValue();

                employeeText.setText(newEmployee.showEmployeeDetails());
            }
        };
        employeeList.addListSelectionListener(employeeLSL);


        depWageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department department = departmentList.getSelectedValue();

                totalWageText.setText("The wage budget for the " + department.getDepartmentName() + " department is: " +
                        department.getTotalSalaryBudget());
                totalWageText.setHorizontalAlignment(JTextField.CENTER);
            }
        });

        JTextField empty1 = new JTextField("");
        JTextField empty2 = new JTextField("");
        JTextField empty3 = new JTextField("");
        JTextField empty4 = new JTextField("");
        JTextField empty5 = new JTextField("");
        JTextField empty6 = new JTextField("");

        empty1.setEditable(false);
        empty2.setEditable(false);
        empty3.setEditable(false);
        empty4.setEditable(false);
        empty5.setEditable(false);
        empty6.setEditable(false);

        empty1.setVisible(false);
        empty2.setVisible(false);
        empty3.setVisible(false);
        empty4.setVisible(false);
        empty5.setVisible(false);
        empty6.setVisible(false);

        empty4.setHorizontalAlignment(JTextField.CENTER);
        empty5.setHorizontalAlignment(JTextField.CENTER);
        empty6.setHorizontalAlignment(JTextField.CENTER);

        empty4.setFont(empty4.getFont().deriveFont(Font.BOLD, 12F));
        empty5.setFont(empty4.getFont().deriveFont(Font.BOLD, 12F));
        empty6.setFont(empty4.getFont().deriveFont(Font.BOLD, 12F));


        adminPanel.add(userScrollPane);
        adminPanel.add(companyScrollPane);
        adminPanel.add(userTextScrollPane);
        adminPanel.add(departmentsScrollPane);
        adminPanel.add(empty1);
        adminPanel.add(employeeScrollPane);
        adminPanel.add(empty2);
        adminPanel.add(employeeTextScrollPane);
        adminPanel.add(depWageButton);
        adminPanel.add(totalWageText);


        //MANAGER PANEL

        managerPanel.setLayout(new GridLayout(4, 4));

        acceptRequest.setEnabled(false);
        declineRequest.setEnabled(false);

        acceptRequest.setBackground(Color.GREEN);
        acceptRequest.setOpaque(true);

        declineRequest.setBackground(Color.RED);
        declineRequest.setOpaque(true);


        ListSelectionListener managerLSL = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (managerList.isSelectionEmpty()) {
                    return;
                }

                Manager newManager = managerList.getSelectedValue();
                DefaultListModel<Request<Job, Consumer>> dlmRequest = new DefaultListModel<Request<Job, Consumer>>();
                int i;

                for (i = 0; i < newManager.getJobRequests().size(); i++)
                    dlmRequest.addElement(newManager.getJobRequests().get(i));

                requestList.setModel(dlmRequest);
            }
        };
        managerList.addListSelectionListener(managerLSL);

        ListSelectionListener requestLSL = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (requestList.isSelectionEmpty()) {
                    acceptRequest.setEnabled(false);
                    declineRequest.setEnabled(false);
                    return;
                }

                acceptRequest.setEnabled(true);
                declineRequest.setEnabled(true);
            }
        };
        requestList.addListSelectionListener(requestLSL);

        acceptRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i, j;
                Request<Job, Consumer> newRequest = requestList.getSelectedValue();
                Manager newManager = managerList.getSelectedValue();

                newManager.hire(newRequest.getKey(), (User) newRequest.getValue1());

                DefaultListModel<Request<Job, Consumer>> dlmRequest =
                        (DefaultListModel<Request<Job, Consumer>>) requestList.getModel();
                dlmRequest.removeElement(newRequest);
                newManager.getJobRequests().remove(newRequest);

                //elimin toate requesturile facute de persoana (nu poate avea mai mult
                // de 1 job in aceeasi companie)
                for (i = 0; i < dlmRequest.size(); i++) {
                    if (dlmRequest.get(i).getValue1().equals(newRequest.getValue1())) {
                        newManager.getJobRequests().remove(dlmRequest.get(i));
                        dlmRequest.removeElementAt(i);
                    }
                }


                //sterg requesturile facute de persoana in cauza si de la celelalte companii
                //(nu poate avea mai mult de 1 job in acelasi timp)
                DefaultListModel<Manager> dlmManager =
                        (DefaultListModel<Manager>) managerList.getModel();

                for (i = 0; i < dlmManager.size(); i++) {
                    if (!dlmManager.get(i).equals(newManager)) {
                        Vector<Integer> intVector = new Vector<Integer>();
                        int k = 0;

                        for (j = 0; j < dlmManager.get(i).getJobRequests().size(); j++)
                            if (dlmManager.get(i).getJobRequests().get(j).getValue1().equals(newRequest.getValue1()))
                                intVector.add(j);

                        for (j = 0; j < intVector.size(); j++) {
                            dlmManager.get(i).getJobRequests().removeElementAt(intVector.get(j) - k);
                            k++;
                        }
                    }
                }


                DefaultListModel<User> dlmUser = (DefaultListModel<User>) userList.getModel();
                dlmUser.removeElement((User) newRequest.getValue1());

                DefaultListModel<Employee> dlmEmployee = new DefaultListModel<Employee>();
                Department newDepartment = newApp.getCompany(newManager.getCompanyName()).getDepartments().get(0);



                for (i = 0; i < newDepartment.getEmployees().size(); i++)
                    dlmEmployee.addElement(newDepartment.getEmployees().get(i));

                if (newDepartment.equals(departmentList.getSelectedValue()))
                    employeeList.setModel(dlmEmployee);

                if (newRequest.getValue1().getFullName().equals(userSearchText.getText())) {
                    informationScrollPane.setVisible(false);
                    educationScrollPane.setVisible(false);
                    experienceScrollPane.setVisible(false);

                    userSearchText.setText("");
                }

                userSearchTextNotif.setText("");
                userSearchTextNotif.setBackground(Color.WHITE);
                notificationScrollPane.setVisible(false);

            }
        });

        declineRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request<Job, Consumer> newRequest = requestList.getSelectedValue();
                Manager newManager = managerList.getSelectedValue();
                User user = (User) newRequest.getValue1();
                Job job = newRequest.getKey();

                Company company = newApp.getCompany(newManager.getCompanyName());
                Notification newNot = new Notification();
                newNot.setCompany(company);
                newNot.setMessage("Unfortunately you got rejected from the job: " + job.getJobName()
                        + " inside the company: " + company.getCompanyName() + ".");

                user.update(newNot);

                DefaultListModel<Request<Job, Consumer>> dlmRequest =
                        (DefaultListModel<Request<Job, Consumer>>) requestList.getModel();
                dlmRequest.removeElement(newRequest);
                newManager.getJobRequests().remove(newRequest);

                userSearchTextNotif.setText("");
                userSearchTextNotif.setBackground(Color.WHITE);
                notificationScrollPane.setVisible(false);
            }
        });


        managerPanel.add(managerScrollPane);
        managerPanel.add(requestScrollPane);
        managerPanel.add(acceptRequest);
        managerPanel.add(declineRequest);


        //PROFILE PANEL

        profilePanel.setLayout(new GridLayout(4, 4));

        informationScrollPane.setVisible(false);
        educationScrollPane.setVisible(false);
        experienceScrollPane.setVisible(false);

        informationText.setEditable(false);
        educationText.setEditable(false);
        experienceText.setEditable(false);

        enterNameText.setEditable(false);
        enterNameText.setHorizontalAlignment(JTextField.CENTER);
        enterNameText.setFont(enterNameText.getFont().deriveFont(Font.BOLD, 13F));

        enterNameTextNotif.setEditable(false);
        enterNameTextNotif.setHorizontalAlignment(JTextField.CENTER);
        enterNameTextNotif.setFont(enterNameTextNotif.getFont().deriveFont(Font.BOLD, 13F));

        userSearchText.setHorizontalAlignment(JTextField.CENTER);
        userSearchText.setFont(userSearchText.getFont().deriveFont(Font.BOLD, 13F));

        userSearchTextNotif.setHorizontalAlignment(JTextField.CENTER);
        userSearchTextNotif.setFont(userSearchTextNotif.getFont().deriveFont(Font.BOLD, 13F));


        userSearchText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i, j, k;
                String name = userSearchText.getText();
                DefaultListModel<User> dlmUser = (DefaultListModel<User>) userList.getModel();
                DefaultListModel<Employee> dlmEmployee = new DefaultListModel<Employee>();
                User user = new User();
                Employee employee = new Employee();

                for (i = 0; i < dlmCompany.size(); i++) {
                    Company newComp = dlmCompany.get(i);

                    for (j = 0; j < newComp.getDepartments().size(); j++) {
                        ArrayList<Employee> employeeArrayList = newComp.getDepartments().get(j).getEmployees();

                        for (k = 0; k < employeeArrayList.size(); k++)
                            dlmEmployee.addElement(employeeArrayList.get(k));
                    }
                }

                for (i = 0; i < dlmUser.size(); i++)
                    if (dlmUser.get(i).getFullName().equals(name))
                        user = dlmUser.get(i);

                for (i = 0; i < dlmEmployee.size(); i++)
                    if (dlmEmployee.get(i).getFullName().equals(name))
                        employee = dlmEmployee.get(i);

                if (user.getFullName().equals("") && employee.getFullName().equals("")) {
                    //utilizatorul nu a fost gasit!

                    informationScrollPane.setVisible(false);
                    educationScrollPane.setVisible(false);
                    experienceScrollPane.setVisible(false);

                    empty4.setVisible(true);
                    empty4.setText("The user \"" + name + "\" could not be found. Try again!");
                    userSearchText.setText("");

                    empty5.setVisible(false);
                    empty5.setText("");

                    empty6.setVisible(false);
                    empty6.setText("");

                    return;
                }

                userSearchText.setText("");

                empty4.setVisible(true);
                empty4.setText("Information about " + name);

                empty5.setVisible(true);
                empty5.setText("Education list of " + name);

                empty6.setVisible(true);
                empty6.setText("Experience list of " + name);

                informationScrollPane.setVisible(true);
                educationScrollPane.setVisible(true);
                experienceScrollPane.setVisible(true);

                String educationString = "", experienceString = "";

                if (!user.getFullName().equals("")) {
                    Collections.sort(user.getResume().getEducationList());
                    Collections.sort(user.getResume().getExperienceList());


                    for (i = 0; i < user.getResume().getEducationList().size(); i++)
                        educationString = educationString +
                                user.getResume().getEducationList().get(i).toString() + "\n\n";

                    for (i = 0; i < user.getResume().getExperienceList().size(); i++)
                        experienceString = experienceString +
                                user.getResume().getExperienceList().get(i).toString() + "\n\n";

                    informationText.setText(user.getResume().getInformation().toString());
                } else if (!employee.getFullName().equals("")) {
                    Collections.sort(employee.getResume().getEducationList());
                    Collections.sort(employee.getResume().getExperienceList());


                    for (i = 0; i < employee.getResume().getEducationList().size(); i++)
                        educationString = educationString +
                                employee.getResume().getEducationList().get(i).toString() + "\n\n";

                    for (i = 0; i < employee.getResume().getExperienceList().size(); i++)
                        experienceString = experienceString +
                                employee.getResume().getExperienceList().get(i).toString() + "\n\n";

                    informationText.setText(employee.getResume().getInformation().toString() + "\nCompany name: " +
                            employee.getCompanyName() + "\nDepartment: " + employee.getDepartment().getDepartmentName() +
                            "\nJob: " + employee.getJob().getJobName() + "\nWage: " + employee.getWage());
                }

                educationText.setText(educationString);
                experienceText.setText(experienceString);
            }
        });


        profilePanel.add(enterNameText);
        profilePanel.add(userSearchText);
        profilePanel.add(empty4);
        profilePanel.add(informationScrollPane);
        profilePanel.add(empty5);
        profilePanel.add(educationScrollPane);
        profilePanel.add(empty6);
        profilePanel.add(experienceScrollPane);

        //NOTIFICATION PANEL

        notificationPanel.setLayout(new GridLayout(3, 3));
        notificationScrollPane.setVisible(false);
        notificationText.setEditable(false);

        userSearchTextNotif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i;
                String name = userSearchTextNotif.getText();
                DefaultListModel<User> dlmUser = (DefaultListModel<User>) userList.getModel();
                User user = new User();

                for (i = 0; i < dlmUser.size(); i++)
                    if (dlmUser.get(i).getFullName().equals(name)) {
                        user = dlmUser.get(i);
                    }


                if (user.getFullName().equals("")) {
                    //utilizatorul nu a fost gasit!

                    userSearchTextNotif.setText("");
                    notificationScrollPane.setVisible(false);

                    userSearchTextNotif.setBackground(Color.RED);

                    return;
                }

                String string = "";

                if (user.getNotificationList().size() == 0)
                    string = "No notifications yet.";
                else
                    for (i = 0; i < user.getNotificationList().size(); i++)
                        string = string + user.getNotificationList().get(i) + "\n";


                userSearchTextNotif.setBackground(Color.GREEN);
                notificationScrollPane.setVisible(true);
                notificationText.setText(string);
            }
        });

        notificationPanel.add(enterNameTextNotif);
        notificationPanel.add(userSearchTextNotif);
        notificationPanel.add(notificationScrollPane);

        tabbedPane.addTab("Administrator page", adminPanel);
        tabbedPane.addTab("Manager page", managerPanel);
        tabbedPane.addTab("Profile page", profilePanel);
        tabbedPane.addTab("Notifications page", notificationPanel);



        add(tabbedPane);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new WeAreHiringApp();
    }

}
