package cy.makery.address;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import cy.makery.address.model.PersonListWrapper;
import cy.makery.address.view.BirthdayStatisticsController;
import cy.makery.address.view.RootLayoutController;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import cy.makery.address.model.Person;
import cy.makery.address.view.PersonEditDialogController;
import cy.makery.address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.*;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        //图标
        this.primaryStage.getIcons().add(new Image("file:resource/image/图标.png"));
        initRootLayout();

        showPersonOverview();
    }
    // ...其他变量后...

    /**
     * 数据作为可观察的人员名单
     * import javax.xml.bind.JAXBContext;
     import javax.xml.bind.Marshaller;
     import javax.xml.bind.Unmarshaller;
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    /**
     * 将数据作为可观察的人员列表返回。
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    //  ...班级的其他人...
    /**
     * Initializes the root layout.
     */


    /**
     * S*在根布局中显示个人概述。
     */
    public void showPersonOverview() {
        try {
            // 载入人物概览
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // 将人物概览设置为根布局的中心。
            rootLayout.setCenter(personOverview);

            // 让控制器访问主应用程序。
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *打开一个对话框来编辑指定人员的详细信息。如果用户
     *单击确定，则更改将保存到提供的人员对象中，并
     返回true *。
     *
     * @paramperson要编辑的人物对象
     * @如果用户单击了OK，则返回true，否则返回false。
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // 加载fxml文件并为弹出对话框创建一个新阶段
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // 创建对话阶段.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //将人员设置为控制器.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // 显示对话框并等待用户关闭
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    /**返回人员文件首选项，即上次打开的文件。
     *首选项是从特定于操作系统的注册表中读取的。如果没有这样的
     *可以找到首选项，返回null。
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     *设置当前加载文件的文件路径。这条道路是坚持不懈的
     *操作系统特定的注册表。
     *
     * @param文件的文件或null删除路径
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // 更新舞台标题。
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // 更新舞台标题。
            primaryStage.setTitle("AddressApp");
        }

    }
    /**
     *从指定文件加载人员数据。当前的人员数据将会
     *被替换。
     *
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // 从文件中读取XML并取消编组。
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // 将文件路径保存到注册表中。
            setPersonFilePath(file);

        } catch (Exception e) { // 捕获任何异常

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    /**
     *将当前人员数据保存到指定的文件。
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // 包装我们的个人数据
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            //编组并将XML保存到文件中。
            m.marshal(wrapper, file);

            // 将文件路径保存到注册表中。
            setPersonFilePath(file);
        } catch (Exception e) {
            // 捕获任何异常
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }
    }
    /**
     *初始化根布局并尝试加载上次打开的
     * person文件。
     */
    public void initRootLayout() {
        try {
            // 从fxml文件加载根布局
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.显示包含根布局的场景。
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.让控制器访问主应用程序。
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.尝试加载上次打开的人员文件。
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }
    /**
     *打开一个对话框来显示生日统计信息。
     */
    public void showBirthdayStatistics() {
        try {
            // 加载fxml文件并为弹出窗口创建一个新阶段。
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 加载fxml文件并为弹出窗口创建一个新阶段。
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}