package myCalculator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 一个计算器，与Windows附件自带计算器的标准版功能、界面相仿。 
 * 1111
 */
public class Calculator extends JFrame implements ActionListener {
	/** 计算器上的键的显示名字。。 */
	private final String[] KEYS = { "7", "8", "9", "/", "sqrt","x^2", "4", "5", "6",
			"*", "%", "Abs","1", "2", "3", "-", "1/x","Int", "0", "+/-", ".", "+", "=" ,
			"x^3","Sin","Cos","tan","ln","lg",""};
	/** 计算器上的功能键的显示名字 */
	private final String[] COMMAND = { "退位", "清除", "归零" };
	/** 计算器上键的按钮 */
	private JButton keys[] = new JButton[KEYS.length];
	/** 计算器上的功能键的按钮 */
	private JButton commands[] = new JButton[COMMAND.length];
	/** 计算结果文本框 */
	public JTextField resultText = new JTextField("0");

	// 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
	public boolean firstDigit = true;
	// 计算的中间结果。
	public double resultNum = 0.0;
	// 当前运算的运算符
	public String operator = "=";
	// 操作是否合法
	public boolean operateValidFlag = true;
	private double prenumber = 0.0; 
	/**
	 * 构造函数
	 */
	public Calculator() {
		super();
		// 初始化计算器
		init();
		// 设置计算器的背景颜色
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("科学计算器");
		// 在屏幕(500, 300)坐标处显示计算器
		this.setLocation(500, 300);
		// 使计算器中各组件大小合适
		this.pack();
	}

	/**
	 * 初始化计算器
	 */
	private void init() {
		// 文本框中的内容采用右对齐方式
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		// 不允许修改结果文本框
		resultText.setEditable(false);
		// 设置文本框背景颜色为白色
		resultText.setBackground(Color.WHITE);
		// 初始化计算器上键的按钮，将键放在一个画板内
		JPanel calckeysPanel = new JPanel();
		// 用网格布局器，5行，5列的网格，网格之间的水平方向间隔为4个象素，垂直方向间隔为4个象素
		calckeysPanel.setLayout(new GridLayout(5, 6, 4, 4));
		for (int i = 0; i < KEYS.length; i++) {
			keys[i] = new JButton(KEYS[i]);
			calckeysPanel.add(keys[i]);
			keys[i].setForeground(Color.blue);
		}
		
		// 运算符键用红色标示，其他键用蓝色表示
		keys[3].setForeground(Color.red);
		keys[4].setForeground(Color.red);
		keys[5].setForeground(Color.red);
		keys[9].setForeground(Color.red);
		keys[10].setForeground(Color.red);
		keys[11].setForeground(Color.red);
		keys[15].setForeground(Color.red);
		keys[16].setForeground(Color.red);
		keys[17].setForeground(Color.red);
		keys[19].setForeground(Color.red);
		keys[20].setForeground(Color.red);
		keys[21].setForeground(Color.red);
		keys[22].setForeground(Color.red);
		keys[23].setForeground(Color.red);
		keys[24].setForeground(Color.red);
		keys[25].setForeground(Color.red);
		keys[26].setForeground(Color.red);
		keys[27].setForeground(Color.red);
		keys[28].setForeground(Color.red);
		keys[29].setForeground(Color.red);

		// 初始化功能键，都用红色标示
		JPanel commandsPanel = new JPanel();
		// 用网格布局器，1行，3列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
		commandsPanel.setLayout(new GridLayout(1,3, 4, 4));
		for (int i = 0; i < COMMAND.length; i++) {
			commands[i] = new JButton(COMMAND[i]);
			commandsPanel.add(commands[i]);
			commands[i].setForeground(Color.red);
		}

		// 下面进行计算器的整体布局，将calckeys和command画板放在计算器的中部，
		// 将文本框放在北部

		// 新建一个大的画板，将上面建立的command和calckeys画板放在该画板内
		JPanel panel1 = new JPanel();
		// 画板采用边界布局管理器，画板里组件之间的水平和垂直方向上间隔都为4象素
		panel1.setLayout(new BorderLayout(4, 4));
		panel1.add("North", commandsPanel);
		panel1.add("Center", calckeysPanel);

		// 建立一个画板放文本框
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add("Center", resultText);

		// 整体布局
		getContentPane().setLayout(new BorderLayout(3, 5));
		getContentPane().add("North", top);
		getContentPane().add("Center", panel1);
		
		
		// 为各按钮添加事件侦听器
		// 都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener
		for (int i = 0; i < KEYS.length; i++) {
			keys[i].addActionListener(this);
		}
		for (int i = 0; i < COMMAND.length; i++) {
			commands[i].addActionListener(this);
		}
	}

	/**
	 * 处理事件
	 */
	public void actionPerformed(ActionEvent e) {
		// 获取事件源的标签
		String label = e.getActionCommand();
		if (label.equals(COMMAND[0])) {
			// 用户按了"退位"键
			handleBackspace();
		} else if (label.equals(COMMAND[1])) {
			// 用户按了"清除"键
			resultText.setText("0");
		} else if (label.equals(COMMAND[2])) {
			// 用户按了"归零"键
			handleC();
		} else if ("0123456789.".indexOf(label) >= 0) {
			// 用户按了数字键或者小数点键
			handleNumber(label);
		} else {
			// 用户按了运算符键
			handleOperator(label);
		}
	}

	/**
	 * 处理退位键被按下的事件
	 */
	public void handleBackspace() {
		String text = resultText.getText();
		int i = text.length();
		if (i > 0) {
			// 退格，将文本最后一个字符去掉
			text = text.substring(0, i - 1);
			if (text.length() == 0) {
				// 如果文本没有了内容，则初始化计算器的各种值
				resultText.setText("0");
				firstDigit = true;
				operator = "=";
			} else {
				// 显示新的文本
				resultText.setText(text);
			}
		}
	}

	/**
	 * 处理数字键被按下的事件
	 * 
	 * @param key
	 */
	public void handleNumber(String key) {
		if (firstDigit) {
			// 输入的第一个数字
			resultText.setText(key);
		} else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
			// 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
			resultText.setText(resultText.getText() + ".");
		} else if (!key.equals(".")) {
			// 如果输入的不是小数点，则将数字附在结果文本框的后面
			resultText.setText(resultText.getText() + key);
		}
		// 以后输入的肯定不是第一个数字了
		firstDigit = false;
	}

	/**
	 * 处理归零键被按下的事件
	 */
	public void handleC() {
		// 初始化计算器的各种值
		resultText.setText("0");
		firstDigit = true;
		operator = "=";
	}

	/**
	 * 处理运算符键被按下的事件
	 * 
	 * @param key
	 */
	public void handleOperator(String key) {
		// 运算符等于用户按的按钮
		if (operator.equals("/")) {
			// 除法运算
			// 如果当前结果文本框中的值等于0
			if (getNumberFromText() == 0.0) {
				// 操作不合法
				operateValidFlag = false;
				resultText.setText("除数不能为零");
			} else {
				resultNum /= getNumberFromText();
			}
		} else if (operator.equals("1/x")) {
			// 倒数运算
			if (resultNum == 0.0) {
				// 操作不合法
				operateValidFlag = false;
				resultText.setText("零没有倒数");
			} else {
				resultNum = 1 / resultNum;
			}
		} else if (operator.equals("+")) {
			// 加法运算
			resultNum += getNumberFromText();
		} else if (operator.equals("-")) {
			// 减法运算
			resultNum -= getNumberFromText();
		} else if (operator.equals("*")) {
			// 乘法运算
			resultNum *= getNumberFromText();
		} else if (operator.equals("sqrt")) {
			// 平方根运算
			resultNum = Math.sqrt(resultNum);
		} else if (operator.equals("%")) {
			// 取余运算
			resultNum %= getNumberFromText();
		} else if (operator.equals("+/-")) {
			// 正数负数运算
			resultNum = resultNum * (-1);
		} else if (operator.equals("=")) {
			// 赋值运算
			resultNum = getNumberFromText();
		}else if (operator.equals("x^2")) {
			// 平方运算
			resultNum = Math.pow(resultNum,2 );
		}
		else if (operator.equals("x^3")) {
			// 立方运算
			resultNum = Math.pow(resultNum,3 );
		}
		else if (operator.equals("Abs")) {
			// 绝对值运算
			resultNum = Math.abs(resultNum);
		}
		else if (operator.equals("Int")) {
			// 取整运算
			resultNum = Math.floor(resultNum );
		}else if (operator.equals("Sin")) {
			// 正弦函数运算
			resultNum = Math.sin(resultNum );
		}else if (operator.equals("Cos")) {
			// 余弦函数运算
			resultNum = Math.cos(resultNum );
		}else if (operator.equals("tan")) {
			// 正弦函数运算
			resultNum = Math.tan(resultNum );
		}else if (operator.equals("ln")) {
			// 以e为底的对数运算
			resultNum = Math.log(resultNum );
		}else if (operator.equals("lg")) {
			// 以10为底的对数运算
			resultNum = Math.log10(resultNum );
		}
		if (operateValidFlag) {
			// 双精度浮点数的运算
			long t1;
			double t2;
			t1 = (long) resultNum;
			t2 = resultNum - t1;
			if (t2 == 0) {
				resultText.setText(String.valueOf(t1));
			} else {
				resultText.setText(String.valueOf(resultNum));
			}
		}
		operator = key;
		firstDigit = true;
		operateValidFlag = true;
	
	}

	/**
	 * 从结果文本框中获取数字
	 * 
	 * @return
	 */
	public double getNumberFromText() {
		double result = 0;
		try {
			result = Double.valueOf(resultText.getText()).doubleValue();
		} catch (NumberFormatException e) {
		}
		return result;
	}

	public static void main(String args[]) {
		Calculator calculator1 = new Calculator();
		calculator1.setVisible(true);
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}