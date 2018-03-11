package cy.makery.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import org.controlsfx.dialog.Dialogs;

import cy.makery.address.MainApp;

/**
 *用于根布局的控制器。根布局提供了基本的
 *应用程序布局，其中包含一个菜单栏和
 可放置其他JavaFX *元素的空间。
 *
 * @author Marco Jakob
 */
public class RootLayoutController {

    //引用主应用程序
    private MainApp mainApp;

    /**
            *被主应用程序调用以将参考返回给自己。
            *
            * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     *创建一个空的地址簿。
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * 打开一个FileChooser让用户选择要加载的地址簿。
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // 设置扩展过滤器
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // 显示保存文件对话框
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     *将文件保存到当前打开的人员文件中。如果没有
     *打开文件，则显示“另存为”对话框。
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     *打开一个FileChooser让用户选择要保存的文件。
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        //设置扩展过滤器
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //显示保存文件对话框
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // 确保它具有正确的扩展名
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }
    /**
     * 打开生日统计。
     */
    @FXML
    private void handleShowBirthdayStatistics() {
        mainApp.showBirthdayStatistics();
    }
    /**
     *打开一个关于对话框
     */
    @FXML
    private void handleAbout() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");
        alert.showAndWait();
    }

    /**
     *关闭应用程序
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}