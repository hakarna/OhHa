
package studyprogress;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
/**
 *
 * @author ausr
 */

public class MainButtonListener implements ActionListener {
    private StudyProgressManager manager;
    private Student user;

    private StudyGUI gui;
    private JList modules;
    private JList courses;
    private Container summary;
    private JFrame source;
    
    public MainButtonListener(StudyProgressManager manager, Student user, StudyGUI gui, JFrame source, JList modules, JList courses, Container summary) {
        this.manager = manager;
        this.user = user;
        this.gui = gui;
        this.modules = modules;
        this.courses = courses;
        this.summary = summary;
        this.source = source;
    }
    public void actionPerformed(ActionEvent e) {
        String buttonlabel = ((JButton)e.getSource()).getText();
        int moduleindex = modules.getSelectedIndex();
        int courseindex = courses.getSelectedIndex();
        
        if(buttonlabel.equals("Lisää valmis")) {
            gui.displayAddModelModule(modules, courses, summary);
        }
        else if(buttonlabel.equals("Lisää oma")) {
            gui.displayCreateCustomModule(modules,summary);
        }
        else if(buttonlabel.equals("Poista")) {
            if(moduleindex >= 0) {
                user.deleteModule(moduleindex);
                modules.setListData(user.modulesToStringArray());
                gui.updateSummary(summary.getComponent(0),summary.getComponent(3));
            }
        }
        else if(buttonlabel.equals("Lisää valmis kurssi") && moduleindex >= 0) {
            String modulename = user.getModuleName(moduleindex);
            int nameindex = manager.modelNameListContains(modulename);
            if(nameindex >= 0) {
                gui.displayAddModelCourse(moduleindex, modules, courses, summary);
            }
        }
        else if(buttonlabel.equals("Lisää oma kurssi") && moduleindex >= 0) {
            gui.displayCreateCustomCourse(courses, modules,summary);
        }
        
        else if(buttonlabel.equals("Poista kurssi") && moduleindex >= 0 && courseindex >= 0) {
            if(courseindex >= 0 && user.getModuleSize(moduleindex) > 0) {
                user.deleteCourseFromModule(moduleindex, courseindex);
                modules.setListData(user.modulesToStringArray());
                courses.setListData(user.moduleCoursesToStringArray(moduleindex));
                gui.updateSummary(summary.getComponent(0),summary.getComponent(3));
                modules.setSelectedIndex(moduleindex);
            }
        }
        else if(buttonlabel.equals("Lopeta")) {
            source.dispose();
            System.exit(0);
        }
        
        else {
            user.writeStudentData();
        }
    }
    
}
