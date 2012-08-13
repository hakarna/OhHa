
package studyprogress;

/**
 *
 * @author Antti Pekkarinen
 */
import java.util.*;

public class StudyCLI {
    private StudyProgressManager manager;
    private Student user;
    private Scanner userinput;
    
    public StudyCLI(StudyProgressManager manager) {
        this.manager = manager;
        this.user = null;
        this.userinput = new Scanner(System.in, "UTF-8");
    }
    
    public void showLogIn() {
        int helpindex = 0;
        String selection;
        while(user == null) {
            System.out.println("Tervetuloa opintojen seurantajärjestelmään!");
            System.out.println("Kirjaudu sisään/luo uusi käyttäjä/näytä ohje/poistu (käyttäjänimi/h/q)");
            selection = userinput.nextLine();
            
            if(selection.equals("h")) {
                showHelp(helpindex);
            }
            else if (selection.equals("q")) {
                break;
            }
            else {
                user = manager.logInUser(selection);
                if(user == null) {
                    promptForNewUserCreation(selection);
                }
                else {
                   showMainMenu(); 
                }
            }
        }
    }
    
    private void showMainMenu() {
        String mainselection;
        boolean firstdisplay = true;
        System.out.println("Tervetuloa, "+user.getName()+"!");
        showSummary();
        while(true) {
            userinput = new Scanner(System.in, "UTF-8");
            firstdisplay = false;
            System.out.println("Päävalikko/Valitse toiminto:");
            System.out.println("Poistu ohjelmasta/q | Tarkastele opintokokonaisuuksia/o | Näytä yhteenveto/y");
            System.out.println("Lisää opintokokonaisuus/l | Muokkaa opintokokonaisuuksia/m | Käynnistä graafinen käyttöliittymä/g");
 
            mainselection = userinput.nextLine();
            if(mainselection.equals("q")) {
                user.writeStudentData();
                break;
            }
            else {
                mainMenuAction(mainselection.toLowerCase());
            }
        }
        
    }
    private void mainMenuAction(String selection) {
        
        if(selection.equals("o")) {
            showViewModules();
        }
        else if(selection.equals("y")) {
            
        }
        else if(selection.equals("l")) {
            showAddModule();
            
        }
        else if(selection.equals("m")) {
            showEditModule();
        }
        else if(selection.equals("g")) {
            
        }
        else {
            System.out.println("Tuntematon komento '" +selection+"'");
        }
        
    }
    private void showViewModules() {
        System.out.println(user);
    }
    private void showSummary() {
        System.out.println("Sinulla on yhteensä "+user.getNumberOfModules()+ " opintokokonaisuutta.");
        System.out.println("Sinulla on yhteensä "+user.getTotalNumberOfCourses() +" kurssia." ); 
    }
    private void showAddModule() {
        while(true) {
            String name;
            float credits;
            System.out.println("Uusi opintokokonaisuus: Anna kokonaisuuden nimi tai q poistuaksesi päävalikkoon");
            name = userinput.nextLine();
            if(name.equals("q")) {
                break;
            }
            System.out.println("Anna kokonaisuuden laajuus opintopisteinä");
            if(userinput.hasNextFloat()) {
                credits = Float.parseFloat(userinput.nextLine());
                user.addModule(name, credits);
                System.out.println("Lisätty kokonaisuus " +name +" ("+credits+"Op)");
            }
            else {
                System.out.println("Virheellinen laajuus, kokonaisuutta ei lisätty.");
            }
        }
    }
    private void showEditModule() {
        int moduleselection;
        String actionselection;
        while (true) {
            System.out.println(user);
            System.out.println("Valitse muokattavan opintokokonaisuuden numero: (0-"+user.getNumberOfModules()+") tai q poistuaksesi");
            if(userinput.hasNextInt()) {
                moduleselection = Integer.parseInt(userinput.nextLine());
            }
            else {
                break;
            }
            System.out.println("Haluatko poistaa opintokokonaisuuden (po), lisätä kurssin(l) vai poistaa kurssin(pk)");
            actionselection = userinput.nextLine();
            if(actionselection.equals("po")) {
                user.deleteModule(moduleselection);
            }
            else if(actionselection.equals("l")) {
                showAddCourse(moduleselection);
            }
            else if(actionselection.equals("pk")) {
                showDeleteCourse(moduleselection);
            }
            else {
                System.out.println("Tuntematon valinta "+actionselection);
            }
        }
    }
    private void showDeleteCourse(int moduleindex) {
        int selection;
        System.out.println(user.moduleToString(moduleindex));
        System.out.println("Minkä kurssin haluat poistaa? (numero 0-"+user.getModuleSize(moduleindex) +")");
        if(userinput.hasNextInt()) {
            selection = Integer.parseInt(userinput.nextLine());
            user.deleteCourseFromModule(moduleindex, selection);
            System.out.println("Poistettiin kurssi numero "+selection+".");
        }
        else {
            System.out.println("Virheellinen valinta, kurssia ei poistettu!");
        }
        
    }
    private void showAddCourse(int moduleindex) {
        String name = "";
        float credits = -1.0f;
        String semester = "";
        int year = -1;
        int grade = 0;
        System.out.println("Anna kurssin nimi:");
        name = userinput.nextLine();
        System.out.println("Anna kurssin opintopistelaajuus:");
        if(userinput.hasNextFloat()) {
            credits = Float.parseFloat(userinput.nextLine());
        }
        System.out.println("Anna kurssin lukukausi (syksy/kevät):");
        semester = userinput.nextLine();
        System.out.println("Anna kurssin lukuvuosi:");
        if(userinput.hasNextInt()) {
            year = Integer.parseInt(userinput.nextLine());
        }
        System.out.println("Anna kurssin arvosana, (0-5, tyhjä rivi = 0):");
        if(userinput.hasNextInt()) {
            grade = Integer.parseInt(userinput.nextLine());
        }
        
        if(!name.equals("") && !semester.equals("") && credits > 0.0f && year > 0 && grade >= 0) {
            user.addCourseToModule(moduleindex, new Course (name, credits, semester, year, grade));
        }
        else {
            System.out.println("Virheellisiä arvoja kurssin tiedoissa, luonti epäonnistui!");
        }
    }
    
    private void promptForNewUserCreation(String username) {
        String selection;
        System.out.println("Käyttäjää nimeltä "+username+" ei löydy, haluatko luoda uuden käyttäjän tällä nimellä? (k/e)");
        selection = userinput.nextLine();
        
        if(selection.equals("k")) {
            System.out.println("Luodaan uusi käyttäjä '"+username+"'.");
            manager.createNewUser(username);
            System.out.println("Käyttäjä "+username+" luotu.");
        }
        else if (selection.equals("e")) {
            System.out.println("Valitsit 'e', Käyttäjää ei luotu.");
        }
        else {
            System.out.println("Tuntematon valinta, käyttäjää ei luotu.");
        }
        
    }
    private void showHelp(int index) {
        
    }
    
   
    
    
    
}
