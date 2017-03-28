/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.lernstickwelcome.fxmlcontroller;

import ch.fhnw.lernstickwelcome.controller.ValidationException;
import ch.fhnw.lernstickwelcome.model.firewall.IpFilter;
import ch.fhnw.lernstickwelcome.model.firewall.WebsiteFilter;
import ch.fhnw.lernstickwelcome.util.WelcomeUtil;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author user
 */
public class WelcomeApplicationFirewallController implements Initializable {

    @FXML
    private Button btn_fw_help;
    @FXML
    private CheckBox cb_fw_allow_monitoring;
    @FXML
    private TableView<WebsiteFilter> tv_fw_allowed_sites;
    @FXML
    private TableColumn<WebsiteFilter, WebsiteFilter.SearchPattern> tab_fw_allowed_sites;
    @FXML
    private TableColumn<WebsiteFilter, String> tab_fw_allowed_sites_firewall;
    @FXML
    private TextField txt_fw_matching_string;
    @FXML
    private ComboBox<WebsiteFilter.SearchPattern> choice_fw_matchtype;
    @FXML
    private Button btn_fw_new_rule;
    @FXML
    private TableView<IpFilter> tv_fw_allowed_servers;
    @FXML
    private TableColumn<IpFilter, IpFilter.Protocol> tab_fw_server_protocol;
    @FXML
    private TableColumn<IpFilter, String> tab_fw_server_ip;
    @FXML
    private TableColumn<IpFilter, String> tab_fw_server_port;
    @FXML
    private TableColumn<IpFilter, String> tab_fw_server_desc;
    @FXML
    private Button btn_fw_add_new_server;
    @FXML
    private TextField txt_fw_new_desc;
    @FXML
    private TextField txt_fw_new_port;
    @FXML
    private TextField txt_fw_new_ip;
    @FXML
    private ComboBox<IpFilter.Protocol> choice_fw_protocol;
    
    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        
        // Set TableView WebsiteFilter cell properties and implement edit functionality
        tab_fw_allowed_sites.setCellValueFactory(p -> p.getValue().searchPatternProperty());
        tab_fw_allowed_sites.setCellFactory(ComboBoxTableCell.forTableColumn(WebsiteFilter.SearchPattern.values()));
        tab_fw_allowed_sites.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
                        .searchPatternProperty().setValue(e.getNewValue()));
        tab_fw_allowed_sites_firewall.setCellValueFactory(p -> p.getValue().searchCriteriaProperty());
        tab_fw_allowed_sites_firewall.setCellFactory(TextFieldTableCell.forTableColumn());
        tab_fw_allowed_sites_firewall.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
                        .searchCriteriaProperty().setValue(e.getNewValue()));
        
        // Set TableView IpFilter cell properties and implement edit functionality
        tab_fw_server_protocol.setCellValueFactory(p -> p.getValue().protocolProperty());
        tab_fw_server_protocol.setCellFactory(ComboBoxTableCell.forTableColumn(IpFilter.Protocol.values()));
        tab_fw_server_protocol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
                        .protocolProperty().setValue(e.getNewValue()));
        tab_fw_server_ip.setCellFactory(TextFieldTableCell.forTableColumn());
        tab_fw_server_ip.setCellValueFactory(p -> p.getValue().ipAddressProperty());
        tab_fw_server_ip.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
                        .ipAddressProperty().setValue(e.getNewValue()));
        tab_fw_server_port.setCellValueFactory(p -> p.getValue().portProperty());
        tab_fw_server_port.setCellFactory(TextFieldTableCell.forTableColumn());
        tab_fw_server_port.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
                        .portProperty().setValue(e.getNewValue()));
        tab_fw_server_desc.setCellValueFactory(p -> p.getValue().descriptionProperty());
        tab_fw_server_desc.setCellFactory(TextFieldTableCell.forTableColumn());
        tab_fw_server_desc.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
                        .descriptionProperty().setValue(e.getNewValue()));
        
        // Load ComboBox data
        choice_fw_matchtype.getItems().addAll(WebsiteFilter.SearchPattern.values());
        choice_fw_protocol.getItems().addAll(IpFilter.Protocol.values());
    }

    @FXML
    private void onClickShowHelp(MouseEvent event) {
    }

    @FXML
    private void onClickNewWebsiteRule(MouseEvent event) {
        if (validateSitesFields())
        tv_fw_allowed_sites.getItems().add(new WebsiteFilter(
                choice_fw_matchtype.getValue(),
                txt_fw_matching_string.getText()));
    }

    @FXML
    private void onClickNewServerRule(MouseEvent event) {
        if(validateServerFields()) {
            tv_fw_allowed_servers.getItems().add(new IpFilter(
                    choice_fw_protocol.getValue(), 
                    txt_fw_new_ip.getText(), 
                    txt_fw_new_port.getText(), 
                    txt_fw_new_desc.getText()));
        }
    }

    public TableView getTv_fw_allowed_sites() {
        return tv_fw_allowed_sites;
    }

    public TableView getTv_fw_allowed_servers() {
        return tv_fw_allowed_servers;
    }

    public CheckBox getCb_fw_allow_monitoring() {
        return cb_fw_allow_monitoring;
    }

    public TextField getTxt_fw_matching_string() {
        return txt_fw_matching_string;
    }

    public ComboBox<WebsiteFilter.SearchPattern> getChoice_fw_matchtype() {
        return choice_fw_matchtype;
    }

    public Button getBtn_fw_new_rule() {
        return btn_fw_new_rule;
    }

    public Button getBtn_fw_add_new_server() {
        return btn_fw_add_new_server;
    }

    public TextField getTxt_fw_new_desc() {
        return txt_fw_new_desc;
    }

    public TextField getTxt_fw_new_port() {
        return txt_fw_new_port;
    }

    public TextField getTxt_fw_new_ip() {
        return txt_fw_new_ip;
    }

    public ComboBox<IpFilter.Protocol> getChoice_fw_protocol() {
        return choice_fw_protocol;
    }
    
    private boolean validateSitesFields() {
        if (choice_fw_matchtype.getSelectionModel().isEmpty()) return false;
        if (txt_fw_matching_string.getText().length() <= 0) return false;
        return true;
    }

    private boolean validateServerFields() {
        if (choice_fw_protocol.getSelectionModel().isEmpty()) return false;
        try {
            WelcomeUtil.checkTarget(txt_fw_new_ip.getText(), -1);
            txt_fw_new_ip.setStyle("");
        } catch(ValidationException ex) {
            txt_fw_new_ip.setPromptText(MessageFormat.format(rb.getString(ex.getMessage()), ex.getMessageDetails()));
            txt_fw_new_ip.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return false;
        }
        try {
            WelcomeUtil.checkPortRange(txt_fw_new_port.getText(), -1);
            txt_fw_new_ip.setStyle("");
        } catch(ValidationException ex) {
            txt_fw_new_port.setPromptText(MessageFormat.format(rb.getString(ex.getMessage()), ex.getMessageDetails()));
            txt_fw_new_port.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return false;
        }
        return true;
    }
    
}
