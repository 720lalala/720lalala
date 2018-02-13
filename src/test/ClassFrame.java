package test;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import  java.io.*;
public class ClassFrame extends JFrame {// 写一个类继承自JFrame 窗体
	
	// 定义组件
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField  allInfo;
    private JComboBox<String> name,car,where;
    private static String con;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClassFrame frame = new ClassFrame();// 创建一个窗口实例
                    frame.setVisible(true);// 让该窗口实例可见
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 窗口属性的设置,内部组件的初始化
     */
    public ClassFrame() {
        setTitle("主谓宾");//标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭是退出JVM
        setSize(450, 339);// 设置窗体大小
        setLocationRelativeTo(null);// 窗体居中
        contentPane = new JPanel();// 内容面板
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));// 设置布局
        setContentPane(contentPane);
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 10));//5行1列的表格布局
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panel, BorderLayout.CENTER);//给panel添加边框
        JPanel panel_1 = new JPanel();      
        panel.add(panel_1);
        JLabel label = new JLabel("姓名");
        label.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panel_1.add(label);
        name = new JComboBox<String>();
        name.setModel(new DefaultComboBoxModel<String>(new String[] { " ","刘一" ,"陈二","张三","李四", "王五", "赵六", "孙七","周八","吴九","郑十", "老王" }));
        name.setPreferredSize(new Dimension(100,25));
        panel_1.add(name);
        JPanel panel2 = new JPanel();
        panel.add(panel2);        
        JLabel label2 = new JLabel("车辆");
        label2.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panel2.add(label2);
        car = new JComboBox<String>();
        car.setModel(new DefaultComboBoxModel<String>(new String[] { " ","劳斯莱斯" ,"宾利","迈巴赫","法拉利", "兰博基尼", "迈凯轮", "阿斯顿马丁","布加迪","帕加尼","奔驰" }));
        car.setPreferredSize(new Dimension(100,25));
        panel2.add(car); 
        JPanel panel3 = new JPanel();
        panel.add(panel3);        
        JLabel label3 = new JLabel("地点");
        label3.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panel3.add(label3);
        where = new JComboBox<String>();
        where.setModel(new DefaultComboBoxModel<String>(new String[] { " ","广东" ,"广西","吉林","北京", "湖南", "湖北", "四川","西藏"}));
        where.setPreferredSize(new Dimension(100,25));
        panel3.add(where);
        JPanel panel_5 = new JPanel();
        panel.add(panel_5);
        JButton jbOk = new JButton("确定");
        panel_5.add(jbOk);
        JButton jbRest = new JButton("重填");
        panel_5.add(jbRest);
        JPanel panelSouth = new JPanel();
        contentPane.add(panelSouth, BorderLayout.SOUTH);
        JLabel labe = new JLabel("开车信息");
        labe.setHorizontalAlignment(SwingConstants.LEFT);
        panelSouth.add(labe);
        allInfo = new JTextField();
        allInfo.setColumns(30);
        panelSouth.add(allInfo);
        JPanel panelend = new JPanel();
        panel.add(panelend);
        JButton baocun = new JButton("保存");
        panel_5.add(baocun);
        JPanel panelNorth = new JPanel();
        contentPane.add(panelNorth, BorderLayout.NORTH);
        JLabel labelTitle = new JLabel("车辆出发登记");
        labelTitle.setForeground(Color.DARK_GRAY);
        labelTitle.setFont(new Font("宋体", Font.BOLD, 20));
        panelNorth.add(labelTitle);
 
        //给确定按钮添加事件处理代码
        jbOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder info = new StringBuilder();
                if(name.getSelectedIndex()==0||car.getSelectedIndex()==0||where.getSelectedIndex()==0)
                info.append("请保证信息正确！");
                else {
                String name1=name.getSelectedItem().toString();
                String car1=car.getSelectedItem().toString();
                String where1=where.getSelectedItem().toString();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
                // new Date()为获取当前系统时间
                info.append(df.format(new Date())+"时      "+name1+"开"+car1+"去"+where1);
              }
                allInfo.setText(info.toString());//把学生信息和选课信息放到文本框
                con=info.toString();
            }
        });
        //给重填按钮 设置事件处理代码
        jbRest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	name.setSelectedIndex(0);
            	car.setSelectedIndex(0);
            	where.setSelectedIndex(0);
               
            }
        });
        baocun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String aa="请保证信息正确！";
            	String bb=allInfo.getText();
                if(name.getSelectedIndex()!=0&&car.getSelectedIndex()!=0&&where.getSelectedIndex()!=0&&bb.compareTo(aa)!=0&&bb.length()>3 )
                	{
                		writeToFile1();
                		allInfo.setText("信息保存成功！");
                	}
                
            }
        });
    }
    
	public static void writeToFile1(){
	     
	    try {
	        String content =con;
	        File file = new File("c:/720.txt");
	        if(file.exists()){
	            FileWriter fw = new FileWriter(file,true);
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(content+"\r\n");
	            bw.close(); fw.close();
	        }
	        
	    } 
	    
	    catch (Exception e) {
	        // TODO: handle exception
	    
	    }
	}
}