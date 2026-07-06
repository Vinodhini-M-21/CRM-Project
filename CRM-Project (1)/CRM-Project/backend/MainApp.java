
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        LeadDAO leadDAO = new LeadDAO();
        FollowUpDAO followUpDAO = new FollowUpDAO();

        int choice;
        do {
            System.out.println("\n===== CRM BACKEND TEST MENU =====");
            System.out.println("1. Show all customers");
            System.out.println("2. Add a customer");
            System.out.println("3. Delete a customer");
            System.out.println("4. Show all leads");
            System.out.println("5. Add a lead");
            System.out.println("6. Show all follow-ups");
            System.out.println("7. Show dashboard counts");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    List<Customer> customers = customerDAO.getAllCustomers();
                    for (Customer c : customers) System.out.println(c);
                    break;

                case 2:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Company: ");
                    String company = sc.nextLine();
                    customerDAO.addCustomer(new Customer(0, name, email, phone, company));
                    break;

                case 3:
                    System.out.print("Enter Customer ID to delete: ");
                    int delId = sc.nextInt();
                    customerDAO.deleteCustomer(delId);
                    break;

                case 4:
                    List<Lead> leads = leadDAO.getAllLeads();
                    for (Lead l : leads) System.out.println(l);
                    break;

                case 5:
                    System.out.print("Name: ");
                    String lname = sc.nextLine();
                    System.out.print("Email: ");
                    String lemail = sc.nextLine();
                    System.out.print("Phone: ");
                    String lphone = sc.nextLine();
                    System.out.print("Status: ");
                    String lstatus = sc.nextLine();
                    System.out.print("Source: ");
                    String lsource = sc.nextLine();
                    leadDAO.addLead(new Lead(0, lname, lemail, lphone, lstatus, lsource));
                    break;

                case 6:
                    List<FollowUp> followUps = followUpDAO.getAllFollowUps();
                    for (FollowUp f : followUps) System.out.println(f);
                    break;

                case 7:
                    System.out.println("Total Customers: " + customerDAO.getCustomerCount());
                    System.out.println("Total Leads: " + leadDAO.getLeadCount());
                    System.out.println("Total Follow-ups: " + followUpDAO.getFollowUpCount());
                    break;

                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
