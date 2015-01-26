
//词法分析器的面板

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class MainFram
{
        private JTextArea inputText=new JTextArea(23,32);
        private JTextArea outputText=new JTextArea(23,22);
        private JScrollPane inputArea= new JScrollPane(inputText);
        private JScrollPane outputArea= new JScrollPane(outputText);

        private JPanel buttonPanel=new JPanel();
        private JPanel textPanel=new JPanel();
        private JPanel Panel1=new JPanel();

        private JButton button1=new JButton("词法分析");
        private JButton button2=new JButton("打开文件");
	private JButton button3=new JButton("清空内容");

        private JLabel label2=new JLabel("0706230243 杨志毅");

        //面板初始化
	public MainFram(){

        inputText.setTabSize(5);
        inputText.setText("String a = \"ABC\" ;      //添加注释"+'\n'+
                "double b = 1.3e3 + 2e-3 ;");
	outputText.setEditable(false);

	buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,50,15));
	buttonPanel.add(button1);
	buttonPanel.add(button2);
	buttonPanel.add(button3);

	textPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
	textPanel.add(inputArea);
	textPanel.add(outputArea);

	JFrame frame=new JFrame("词法分析器");
	frame.setSize(700,500);
	frame.setLocation(200,200);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	frame.add(buttonPanel,BorderLayout.NORTH);
	frame.add(textPanel,BorderLayout.CENTER);
        frame.add(label2,BorderLayout.SOUTH);
	//分析按钮按下
	button1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			//创建词法分析对象
                        MorphemeAnalyzer analyzer=new MorphemeAnalyzer();
                        analyzer.str=inputText.getText();
                        analyzer.getsym();
                        outputText.setText(analyzer.strbox);
		}
	});
	//打开文件按钮按下
	button2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
                        inputText.setText(null);
			//读取文件
			try{
				String str;
				BufferedReader reader=new BufferedReader(
                                    new FileReader("D:/My Documents/NetBeansProjects/词法分析器/src/MainFram.java"));
				//逐行读取文件放入输入文本框
				str=reader.readLine();
				while(str!=null){
					inputText.append(str+'\n');
					str=reader.readLine();
				}
			}catch(IOException ioexp){
			}catch(Exception exp){
			}
                        inputText.setCaretPosition(0);
		}
	});
	//清空按钮按下
	button3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			inputText.setText(null);
			outputText.setText(null);
		}
	});
        }
	public static void main(String[] args)
	{
            /*
            try{
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            }
                catch (Exception e)
            {}
             * 
             */
		MainFram fram=new MainFram();
	}
}