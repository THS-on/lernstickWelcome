/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.lernstickwelcome.controller.binder;

import ch.fhnw.lernstickwelcome.controller.WelcomeController;
import ch.fhnw.lernstickwelcome.model.WelcomeConstants;
import ch.fhnw.lernstickwelcome.model.application.ApplicationGroupTask;
import ch.fhnw.lernstickwelcome.model.application.ApplicationTask;
import ch.fhnw.lernstickwelcome.util.WelcomeUtil;
import ch.fhnw.lernstickwelcome.view.impl.ApplicationGroupView;
import ch.fhnw.lernstickwelcome.view.impl.ApplicationView;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * Binder class to init bindings between view components and backend (model) properties
 * 
 * @author sschw
 */
public class ApplicationBinder {
    private final static Logger LOGGER = Logger.getLogger(ApplicationBinder.class.getName());
    private final VBox applicationContainer;
    private final WelcomeController controller;
    private final Button helpBtn;
    
    /**
     * Constructor of ApplicationBinder class
     * 
     * @param controller is needed to provide access to the backend properties
     * @param applicationContainer the gui container for the applications
     */
    public ApplicationBinder(WelcomeController controller, VBox applicationContainer, Button helpBtn) {
        this.controller = controller;
        this.applicationContainer = applicationContainer;
        this.helpBtn = helpBtn;
    }
    
    /**
     * Loads an application group into the applicationContainer.
     * @param appGroup the application group that should be loaded into the container
     * @param binder the binder for the help to configure the help dialog.
     * @param help the help dialog that should be shown.
     */
    public void addApplicationGroup(ApplicationGroupTask appGroup, HelpBinder binder, Stage help) {
        ResourceBundle rb = controller.getBundle();
        ApplicationGroupView groupView = new ApplicationGroupView();
        groupView.setTitle(rb.getString(appGroup.getTitle()));
        addApplicationList(groupView.getAppContainer(), appGroup.getApps(), binder, help);
        applicationContainer.getChildren().add(groupView);
    }
    
    /**
     * Loads applications of an application group into the applicationContainer.
     * @param appGroup the application group that should be loaded into the container
     * @param binder the binder for the help to configure the help dialog.
     * @param help the help dialog that should be shown.
     */
    public void addApplications(ApplicationGroupTask appGroup, HelpBinder binder, Stage help) {
        addApplicationList(applicationContainer, appGroup.getApps(), binder, help);
    }
    
    private void addApplicationList(VBox container, List<ApplicationTask> applications, HelpBinder binder, Stage help) {
        ResourceBundle rb = controller.getBundle();
        boolean even = false;
        for(ApplicationTask app : applications) {
            ApplicationView appView = new ApplicationView(rb);
            try {
                appView.setTitle(rb.getString(app.getName()));
            } catch(Exception ex) {
                appView.setTitle(app.getName());
            }
            if(app.getDescription() != null && !app.getDescription().isEmpty()) {
                try {
                    appView.setDescription(rb.getString(app.getDescription()));
                } catch(Exception ex) {
                    LOGGER.log(Level.WARNING, "Description has key but key couldnt be load from bundle for app {0}", app.getName());
                }
            }
            
            if(app.getHelpPath() != null && !app.getHelpPath().isEmpty()) {
                String helpPath;
                try {
                    helpPath = rb.getString(app.getHelpPath());
                } catch(Exception ex) {
                    helpPath = app.getHelpPath();
                    LOGGER.log(Level.WARNING, "Help Path couldn't be found in bundle for app {0}", app.getName());
                    LOGGER.log(Level.WARNING, "The help path itself is taken for validation. "
                            + "(Language support isn't guaranteed)");
                }
                if(helpPath.startsWith("HelpChapter:")) {
                    String helpChapter = helpPath.substring(12);
                    appView.setHelpAction(evt -> {
                        binder.setHelpEntryByChapter(helpChapter);
                        help.show();
                    });
                } else {
                    String helpUrl = helpPath;
                    appView.setHelpAction(evt -> WelcomeUtil.openLinkInBrowser(helpUrl));
                }
            }
            if(app.getIcon() != null && !app.getIcon().isEmpty()) {
                String path = WelcomeConstants.ICON_APPLICATION_FILE_PATH + "/" + app.getIcon() + ".png";
                File f = new File(path);
                if(f.exists()) {
                    appView.setIcon(new Image(f.toURI().toString()));
                }
            }
            appView.disableProperty().bind(app.installedProperty());
            appView.installingProperty().bindBidirectional(app.installingProperty());
            appView.setPrefWidth(container.getWidth());
            if(even)
                appView.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00000011"), CornerRadii.EMPTY, Insets.EMPTY)));
            even = !even;
            container.getChildren().add(appView);
        }
    }

    /**
     * Open other view by clicking on help button
     *
     * @param chapter the help chapter for this application view
     * @param helpStage additional window showing help
     * @param help links to online user guide
     */
    public void initHelp(String chapter, Stage helpStage, HelpBinder help) {
        helpBtn.setOnAction(evt -> {
            help.setHelpEntryByChapter(chapter);
            helpStage.show();
        });
    }
}
