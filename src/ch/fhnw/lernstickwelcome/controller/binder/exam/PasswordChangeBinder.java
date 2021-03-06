/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.lernstickwelcome.controller.binder.exam;

import ch.fhnw.lernstickwelcome.controller.WelcomeController;
import ch.fhnw.lernstickwelcome.controller.exception.ProcessingException;
import ch.fhnw.lernstickwelcome.fxmlcontroller.ErrorController;
import ch.fhnw.lernstickwelcome.fxmlcontroller.exam.PasswordChangeController;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Binder class to init binings between view components and backend (model) properties
 * 
 * @author sschw
 */
public class PasswordChangeBinder {

    private final PasswordChangeController password;
    private final WelcomeController controller;
    
    /**
     * Constructor of ExamInformationBinder class
     * 
     * @param controller is needed to provide access to the backend properties
     * @param password   FXML controller which prviedes the view properties
     */
    public PasswordChangeBinder(WelcomeController controller, PasswordChangeController password) {
        this.password = password;
        this.controller = controller;
    }
    
    /**
     * Method to initialize the handlers for this class.
     *
     * @param errorStage the dialog that should be shown on error.
     * @param error the controller which the error message can be provided.
     */
    public void initHandlers(Stage errorStage, ErrorController error) {
        password.getBtOk().setOnAction(evt -> {
            controller.getSysconf().passwordProperty().setValue(password.getTxtPassword().getText());
            controller.getSysconf().passwordRepeatProperty().setValue(password.getTxtPasswordRepeat().getText());
            try {
                controller.getSysconf().changePassword();
                controller.getProperties().newTask().run();
                ((Stage)((Node) evt.getSource()).getScene().getWindow()).close();
            } catch(ProcessingException ex) {
                error.initErrorMessage(ex);
                errorStage.showAndWait();
            }
        });
        // If user clicks on ignore remove the already tried passwords.
        password.getBtCancel().setOnAction(evt -> 
        {
            controller.getSysconf().passwordProperty().setValue(null);
            controller.getSysconf().passwordRepeatProperty().setValue(null);
            ((Stage)((Node) evt.getSource()).getScene().getWindow()).close();
        });
    }
}
