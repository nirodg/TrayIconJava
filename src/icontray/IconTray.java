
package icontray;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * TrayIcon
 * @version 0.1
 * @author Dorin Gheorghe Brage
 */
public class IconTray {
    
    private static final String APP_NAME = "TrayIcon";
    private static final String APP_GIT = "https://github.com/nirodg";
    
    public static void main(String[] args) {
        
        hideFromDock(true);
        showTray();
        
    }
    
    /**
     * Show the TrayIcon
     */
    public static void showTray(){
        
        try {
            if(!SystemTray.isSupported()){
                System.err.println("System tray not suported...");
                return;
            }
            
            final String imagePath = System.getProperty("user.dir") + "/Resources/logo.png"; 
            final PopupMenu menu = new PopupMenu();
            final SystemTray tray = SystemTray.getSystemTray();
            final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(imagePath), "Tray demo", menu);
            
            
            // About
            MenuItem aboutItem = new MenuItem("About");
            aboutItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    AboutDialog aboutDialog = new AboutDialog(null, true);
                    aboutDialog.set(APP_NAME, new ImageIcon(imagePath) );
                    aboutDialog.show();
                }
            });

            // Exit
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(1);
                }
            });      
            
            // Go to GitHub
            MenuItem gitHubItem = new MenuItem("Go to GitHub");
            gitHubItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Desktop d = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                    if(d != null){
                        try {
                            d.browse(new URL(APP_GIT).toURI());
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(IconTray.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(IconTray.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(IconTray.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });         
            
            // Adding items to the menu
            menu.add(aboutItem);
            menu.add(gitHubItem);
            menu.add(exitItem);
            
            
            trayIcon.setImageAutoSize(true);
            
            tray.add(trayIcon);
            
        } catch (AWTException ex) {
            Logger.getLogger(IconTray.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    /**
     * Hide the icon on the Dock
     * @param hide 
     */
    public static void hideFromDock(boolean hide){
        if(hide){
            System.setProperty("apple.awt.UIElement", "true");            
        }else{
            System.setProperty("apple.awt.UIElement", "false");
        }
    }
}
