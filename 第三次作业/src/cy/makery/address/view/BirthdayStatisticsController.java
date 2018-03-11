package cy.makery.address.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import cy.makery.address.model.Person;

/**
 * *生日统计视图的控制器。
 *
 * @author Marco Jakob
 */
public class BirthdayStatisticsController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    /**
     *初始化控制器类。这个方法
     *在fxml文件被加载后自动被调用*。
     */
    @FXML
    private void initialize() {
        // Get an array with the English month names.获取一个包含英文月份名称的数组。
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to a list and add it to our ObservableList of months.将其转换为列表并将其添加到我们的月的ObservableList。
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.将月份名称指定为水平轴的类别。
        xAxis.setCategories(monthNames);
    }

    /**
     * *设置人员显示统计信息。
     *
     * @param persons
     */
    public void setPersonData(List<Person> persons) {
        // Count the number of people having their birthday in a specific month.计算在特定月份中生日的人数。
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.为每个月创建一个XYChart.Data对象。将它添加到系列中。
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }
}