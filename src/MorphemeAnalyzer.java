
//分析器组件
/*
 * 词法分析器
 * 说明：
 * 可识别出 1.关键字  2.标识符(含中文标识符)  3.常量（包括浮点小数、字符串常量及字符常量）
 *       4.运算符  5.界符
 * 能够滤过注释以及空白处。
 * 有简单的打开文件功能，也就是分析自身源文件的代码。
 */

//import java.io.*;
import java.util.*;
public class MorphemeAnalyzer
{
	int type;
	char ch;
	String line;
	String str;
	int cc=-1;
	String strbox="";
	String []keywords={
				//基本数据类型
				"int","char","byte","String","boolean","float","double","null","true","false","byte","short",
                                "long",
				//访问控制
				"public","private","protect","final","static","native","super","extends",
				//流程控制
				"if","while","for","continue","break","else","return","switch","case",
				//其他保留字
				"void","static","import","class","main","try","catch","throw","synchronized","implements",
				"new","BufferedReader","FileReader","ActionEvent","ActionListener","BorderLayout","FlowLayout",
				"JTextArea","JScrollPane","JPanel","JButton","instanceof","interface"
				};
	ArrayList<String> idenlist=new ArrayList<String>();//使用String的数组


	public boolean isKeyword(String str){
		boolean flag=false;
		for(int i=0;i<keywords.length;i++){
			if(str.equalsIgnoreCase(keywords[i]))
				flag=true;
		}
		return flag;
	}
	public boolean isOperator(char ch){
		boolean flag=false;
		if (ch=='+'||ch=='-'||ch=='*'
			||ch=='/'||ch=='='||ch=='&'
			||ch=='|'||ch=='%'||ch=='<'
			||ch=='>'||ch=='^')
		{
			flag=true;
		}
		return flag;
	}
	public boolean isBoundary(char ch){
		boolean flag=false;
		if (ch=='('||ch==')'||ch=='['||ch==']'||ch=='{'||ch=='}'
			||ch==','||ch=='.'||ch==';'||ch==':'
			||ch=='!')
		{
			flag=true;
		}
		return flag;
	}
	public void getch(){
		cc++;
		ch=str.charAt(cc);
	}
	public void getsym(){
		str.toLowerCase();
		str=str+'@';
		//空行则直接跳过
		if(str.trim().equals("@")){
			strbox+="没有可分析的字符串";
			return;
		}
		getch();
		//滤过空格、回车、TAB符
		while (ch!='@'){
			if (ch==' '||ch=='\n'||ch=='\r'||ch=='\t'){
				getch();
				continue;
			}
			line=("");
			//标识符
			if(Character.isLetter(ch) || ch=='_'){
				type=2;
				line=line+ch;
				getch();
				if (ch=='@'){
					if (idenlist.indexOf(line)==-1)
						idenlist.add(line);
					strbox+=line+" 是标识符"+" ( "+type+" , \'"+idenlist.indexOf(line)+"\' )"+'\n';
					continue;
				}
				while(Character.isLetter(ch) || Character.isDigit(ch)
								|| ch=='_' || ch=='$'){
					line=line+ch;
					getch();
				}
				//关键字
				if (isKeyword(line)){
					type=1;
					strbox+=line+" 是关键字"+" ( "+type+" , \'"+line+"\' )"+'\n';
					continue;
				}
				if (isOperator(ch)||isBoundary(ch)||ch=='@'||ch=='\n'||ch=='\t'||ch=='.'||ch==' '){
					if (idenlist.indexOf(line)==-1)
						idenlist.add(line);
					strbox+=line+" 是标识符"+" ( "+type+" , \'"+idenlist.indexOf(line)+"\' )"+'\n';
					continue;
				}
				else{
					line=line+ch;
					strbox+=line+"* 错误的字符串，退出"+'\n';
					break;
				}
			}
			//无符号小数
			else if(Character.isDigit(ch) /** || ch=='+' || ch=='-' */){
					type=3;
					line=line+ch;
					getch();
					if (ch=='@'){
						strbox+=line+" 是数字常量"+" ( "+type+" , \'"+line+"\' )"+'\n';
						continue;
					}
					while(Character.isDigit(ch)){
						line=line+ch;
						getch();
					}
					if (ch=='.'){
						line=line+ch;
						getch();
						while(Character.isDigit(ch)){
							line=line+ch;
							getch();
						}
						if(ch=='.'){
							line=line+ch;
							strbox+=line+" 错误的字符串，退出"+'\n';
							break;
						}
					}
                                        //分析浮点小数
                                        if (ch=='e'){
                                            line=line+ch;
                                            getch();
                                            if (ch=='-'||ch=='+'){
                                                line=line+ch;
                                                getch();
                                            }
                                                while(Character.isDigit(ch)){
							line=line+ch;
							getch();
						}
                                                if(ch=='.'){
							line=line+ch;
							strbox+=line+" 错误的字符串，退出"+'\n';
							break;
						}

                                        }

					if (isOperator(ch)||isBoundary(ch)||ch=='@'||ch=='\n'||ch=='\t'||ch=='.'||ch==' '){
						strbox+=line+" 是数字常量"+" ( "+type+" , \'"+line+"\' )"+'\n';
						continue;
					}
					else{
						line=line+ch;
						strbox+=line+"* 错误的字符串，退出"+'\n';
						break;
					}
				}
				//界符
				else if (isBoundary(ch))
					{
						line=line+ch;
						//小于1的无符号小数
						if (ch=='.'){
							getch();
							if (Character.isDigit(ch))
							{
								while (Character.isDigit(ch)){
									line=line+ch;
										getch();
								}
                                                                //浮点小数
                                                                if (ch=='e'){
                                                                    line=line+ch;
                                                                    getch();
                                                                    if (ch=='-'||ch=='+'){
                                                                        line=line+ch;
                                                                        getch();
                                                                    }
                                                                    while(Character.isDigit(ch)){
                                                                            line=line+ch;
                                                                            getch();
                                                                    }
                                                                    if(ch=='.'){
                                                                            line=line+ch;
                                                                            strbox+=line+" 错误的字符串，退出"+'\n';
                                                                            break;
                                                                    }
                                                                }
                                                                if (isOperator(ch)||isBoundary(ch)||ch=='@'||ch=='\n'||ch=='\t'||ch==' '){
                                                                    //小数点后面又出现小数点
                                                                    if(ch=='.'){
                                                                        line=line+ch;
									strbox+=line+"* 错误的字符串，退出"+'\n';
									break;
                                                                    }
                                                                    type=3;
                                                                    strbox+=line+" 是数字常量"+" ( "+type+" , \'"+line+"\' )"+'\n';
                                                                    continue;
								}
								else{
									line=line+ch;
									strbox+=line+"* 错误的字符串，退出"+'\n';
									break;
								}
							}
							else{
								type=5;
								strbox+=line+" 是界符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}
						}
						//其他界符的处理方法
						type=5;
						strbox+=line+" 是界符"+" ( "+type+" , \'"+line+"\' )"+'\n';
						getch();
						continue;
					}
					//运算符
					else if (isOperator(ch))
						{
							type=4;
							line=line+ch;
							if (ch=='+'){
								getch();
								if (ch=='+'||ch=='='){
									line=line+ch;
									getch();
								}
								strbox+=line+" 是运算符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}
							if (ch=='-'){
								getch();
								if (ch=='-'||ch=='='){
									line=line+ch;
									getch();
								}
								strbox+=line+" 是运算符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}
							if (ch=='='||ch=='&'||ch=='|'){
								getch();
								if (ch=='='||ch=='&'||ch=='|'){
									line=line+ch;
									getch();
								}
								strbox+=line+" 是运算符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}
							if (ch=='<'){
								getch();
								if (ch=='='||ch=='>'){
									line=line+ch;
									getch();
								}
								strbox+=line+" 是运算符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}
							if (ch=='>'){
								getch();
								if (ch=='='){
									line=line+ch;
									getch();
								}
								strbox+=line+" 是运算符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}
							if (ch=='%'||ch=='*'||ch=='^'){
								getch();
								strbox+=line+" 是运算符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}
							//跳过注释
							if (ch=='/'){
								getch();
								if (ch=='/'){
									while(ch!='\n')
										getch();
									continue;
								}
								if (ch=='*'){
									while(ch!='@'){
										getch();
										if(ch=='*'){
											getch();
											if (ch=='/')
												getch();
												break;
											}
									}
									continue;
								}
								strbox+=line+" 是运算符"+" ( "+type+" , \'"+line+"\' )"+'\n';
								continue;
							}

						}
						//字符串常量
						else if (ch=='\''||ch=='\"'){
							type=3;
							line=line+ch;
								if (ch=='\"'){
									getch();
									//分析完一行或是到终止符
									while (ch!='\"'&&ch!='\n'&&ch!='@'){
										line=line+ch;
										getch();
                                                                                //遇到字符串中的转义字符则跳过
                                                                                if (ch=='\\')
                                                                                {
                                                                                    line=line+ch;
                                                                                    getch();
                                                                                    line=line+ch;
                                                                                    getch();
                                                                                }
									}
                                                                        if (ch=='\"'){
										line=line+ch;
										getch();
										strbox+=line+" 是字符串常量"+" ( "+type+" , \'"+line+"\' )"+'\n';
										continue;
									}
									else{
										strbox+=line+" 字符串未结束，错误退出"+'\n';
										break;
									}
								}
								if (ch=='\''){
									getch();
									//分析完一行或是到终止符
									while (ch!='\''&&ch!='\n'&&ch!='@'){
										line=line+ch;
										getch();
									}
									if (ch=='\''){
										line=line+ch;
										getch();
										if(line.length()<4){
											strbox+=line+" 是字符常量"+" ( "+type+" , \'"+line+"\' )"+'\n';
											continue;
										}
										//判断转义字符
										else if(line.equals("'\\n'")||line.equals("'\\r'")||line.equals("'\\t'")){
											strbox+=line+" 是字符常量"+" ( "+type+" , \'"+line+"\' )"+'\n';
											continue;
										}
										else{
											strbox+=line+" 错误的字符，退出"+'\n';
											break;
										}
									}
									else{
										strbox+=line+" 字符未结束，错误退出"+'\n';
										break;
									}
								}
							}
							else{
								strbox+=line+"* 错误的字符串，退出"+'\n';
								break;
							}
		}
	}
/*
	public static void main(String[] args)
	{
		MorphemeAnalyzer analyzer=new MorphemeAnalyzer();
		try{
			BufferedReader keyin=new BufferedReader(
				new InputStreamReader(System.in));
			analyzer.str=keyin.readLine();
			analyzer.getsym();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
 *
 */
}