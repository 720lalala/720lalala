package cy.makery.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 助手功能处理日期。
 *
 * @author Marco Jakob
 */
public class DateUtil {

    /** 用于转换的日期模式。随你所愿改变 */
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    /** 日期格式化程序 */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * 返回给定的日期格式良好的字符串。以上定义
     *使用{@link DateUtil＃DATE_PATTERN}。
     *日期作为字符串返回的日期
     * @param
     * @返回 格式化的字符串
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * 以定义的{@link DateUtil＃DATE_PATTERN}格式转换字符串
     *到{@link LocalDate}对象。
     *
     *如果字符串无法转换，则返回null。
     *
     * @参数 dateString日期字符串
     * @返回 日期对象，如果无法转换，则返回null
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * 检查字符串是否为有效日期。
     *
     * @参数dateString
     * @return true 如果字符串是有效的日期，则
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
}