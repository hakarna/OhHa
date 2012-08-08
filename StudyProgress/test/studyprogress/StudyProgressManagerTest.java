/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studyprogress;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ausr
 */
public class StudyProgressManagerTest {
    StudyProgressManager manager;
    
    public StudyProgressManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        manager = new StudyProgressManager();
        
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void loadUsersAndLogIn() {
        assertNotNull(manager.logInUser("Antti"));
        
    }
    @Test
    public void loadUsersFalseLogin() {
        assertNull(manager.logInUser("Pentti"));
    }
    @Test
    public void createUserAlreadyExists() {
        assertFalse(manager.createNewUser("Erkki"));
    }
    @Test
    public void createUserAndLogin() {
        manager.createNewUser("Hessu");
        manager.logInUser("Hessu");
        
    }
        
    
}
