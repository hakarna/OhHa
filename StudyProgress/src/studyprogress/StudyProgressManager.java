
package studyprogress;

/**
 *A class containing the basic operations of the
 * StudyProgress-application, such as logging in and creating a user.
 * 
 * @author Antti Pekkarinen
 */
import java.util.*;
import java.io.*;

public class StudyProgressManager {
    
    private ArrayList<String> usernamelist;
    private ArrayList<Module> modelmodules;
    private ArrayList<String> modelmodulenames;
    private String userfilepath;
    private String userdirectorypath;
    private File userfile;
    private File userdirectory;
    private File modelsfile;


 
    public StudyProgressManager() {
        userdirectorypath = "data";
        userfilepath = "data/users.txt";
        userfile = new File(userfilepath);
        userdirectory = new File(userdirectorypath);
        modelsfile = new File("data/models/models.txt");
        modelmodulenames = new ArrayList<String>();
        
        if(!userdirectory.exists()) {
            userdirectory.mkdir();
        }
        
        
        usernamelist = loadUserListFromFile();
        modelmodules = new ArrayList<Module>();
        loadModelModules();
        
    }
    

    
    public Student logInUser(String username) {
        if(usernamelist.contains(username)) {
            return new Student(username);
            
        }
        else {
            return null;
        }
    }
    public boolean createNewUser(String username) {
        File newuserdatadirectory = new File("data/"+username);
        File modulesfile = new File("data/"+username+"/modules.txt");
        if(usernamelist.contains(username)) {
            return false;
        }
        else {
            usernamelist.add(username);
            writeUserNameList();
            newuserdatadirectory.mkdir();
            try {
                modulesfile.createNewFile();
            } catch (Exception e) {
                System.out.println("Käyttäjätietojen luominen ei onnistunut!");
            }
            
            return true;
        }
    }
    

    private ArrayList<String> loadUserListFromFile() {
        
        String username;
        ArrayList<String> usernames = new ArrayList<String>();
        
        if(userfile.exists()) {
            try {
                Scanner filescanner = new Scanner(userfile, "UTF-8");
                
                while(filescanner.hasNextLine()) {
                    username = filescanner.nextLine();
                    usernames.add(username);
                }
                
            } catch (Exception e) {
                System.out.println("Virhe ladattaessa käyttäjälistaa!");
            }
        }
        return usernames;
    }
    
    private void writeUserNameList() {
        String username;
        int userlistsize = usernamelist.size();
        
        try {
            PrintWriter writer = new PrintWriter(userfile, "UTF-8");
            for (int i = 0; i < userlistsize; i++) {
                username = usernamelist.get(i);
                writer.println(username);             
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Virhe kirjoitettaessa käyttäjänimitiedostoa!");
        }
    }
    public String[] modelModulesToStringArray() {
    String[] empty = {""};
        try {
            int size = modelmodules.size();
            Module module;
            if(size > 0) {
                String[] modules = new String[size];
                for (int i = 0; i < size; i++) {
                    module = modelmodules.get(i);
                    modules[i] = module.getName() + ", laajuus "+module.getTotalCredits()+" op.";
                }
                return modules;
            }
            else {
                System.out.println("Model array empty!");
                return empty;
            }
        } catch (Exception e) {
            System.out.println("Model array empty!");
            e.printStackTrace();
            return empty;
        }
    }

    public String[] moduleCoursesToStringArray(int moduleindex) {
        String[] empty = {""};
        try {
        Module module = modelmodules.get(moduleindex);          

            int size = module.getNumberOfCourses();
            if(size >0) {
               String[] returnarray = new String[size];
                for (int i = 0; i < size; i++) {
                   returnarray[i] = module.getCourse(i).toString();
                }
                return returnarray; 
            }
            else {
                return empty;

            }
        } catch(Exception e) {
            return empty;
        }
    
    }
    public int modelNameListContains(String name) {
        return modelmodulenames.indexOf(name);
        
    }
    public Module getModelModule(int moduleindex) {
        return modelmodules.get(moduleindex);
    }
    public int getNumberOfModelModules() {
        return modelmodules.size();
    }
    public static void deleteFile(String filepath) {
        File file = new File(filepath);
        if(file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                System.out.println("Tiedoston poistaminen epäonnistui!");
            }
        }        
    }
    private void loadModelModules() {
        modelmodules.clear();
        modelmodulenames.clear();
        String modelmodulename;
        try {
            Scanner modulescanner = new Scanner(modelsfile, "UTF-8");
            while(modulescanner.hasNextLine()) {
                modelmodulename = modulescanner.nextLine();
                modelmodulenames.add(modelmodulename);
                Module model = Student.loadModule(modelmodulename, "data/models/"+modelmodulename+".txt");
                modelmodules.add(model);
            }
            
        } catch (Exception e) {
            System.out.println("Virhe luettaessa mallikokonaisuuksia!");
        }
    }

}
