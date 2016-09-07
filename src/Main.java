
import dao.*;
import gui.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mowca765
 */
public class Main {

    private static DaoInterface daoInterface = new ProductDBM();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        // create the frame instance
        MainMenu menu = new MainMenu(daoInterface);

        // centre the frame on the screen
        menu.setLocationRelativeTo(null);

        // show the frame
        menu.setVisible(true);
    }
    
}
