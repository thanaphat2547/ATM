import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Minesweeper implements ActionListener{
	int xZero;
	int yZero;
	int[][] solution;
	boolean[][] flagged;
	
	Random random;
	
	JFrame frame;
	JPanel textPanel;
	JPanel buttonPanel;
	JButton[][] buttons;	
	JLabel textfield;
	JButton resetButton;
	JButton flag;
	JButton flag1;
	
	@SuppressWarnings("rawtypes")
	JComboBox choice; 
	//Drop Down to choose beginner, intermediate or hard
	String[] options= {"9x9","16x16","22x22", "Modify"}; //Drop Down List options กำหนดในarray
	
	int size;
	int bombs;
	
	ImageIcon icon;
	File iconImage;
	ImageIcon iconbubu ;
	
	
	ArrayList<Integer> xPositions;
	ArrayList<Integer> yPositions;
	
	boolean flagging;
	int count=0;
	int lastXchecked;
	int lastYchecked;
	
	int position;
	int customBombs, customSize=0;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Minesweeper(int level, int mines)
	{		
		position=level;
		
		size=level;
		bombs=mines;
		
		lastXchecked=size+1;
		lastYchecked=size+1;
		
		flagged=new boolean[size][size]; //To know which places are flagged
		random=new Random(); //สุ่มตำเเหน่งของระเบิด
		xPositions=new ArrayList<Integer>(); //เก็บตำเเหน่งของระเบิดที่ทำการสุ่มไว้ใน random
		yPositions=new ArrayList<Integer>();
		
		for(int i=0;i<bombs;i++)
		{
			int randomX=random.nextInt(size);//คือการสุ่มระเบิดแนวนอน
			int randomY=random.nextInt(size);//คือการสุ่มระเบิดแนวตั้ง

			xPositions.add(randomX);
			yPositions.add(randomY);
		}
		
		 //To prevent bomb overlapping- Very IMP*
		//Otherwise sometimes one place would hold 2 bombs & give wrong count
		//ตรวจสอบตำเเหน่งที่ซ้ำ ถ้าซ้ำจะสุ่มใหม่ ตามเงื่อนไข
		for(int i=0; i<bombs; i++) //ลูปตามจำนวนของระเบิด
		{
			for(int j=i+1; j<bombs; j++)
			{
				if(xPositions.get(i)==xPositions.get(j) && yPositions.get(i)==yPositions.get(j))
				{
					xPositions.set(j, random.nextInt(size));  //สุ่มตำเเหน่งใหม่
					yPositions.set(j, random.nextInt(size));
					i=0;
					j=0;
				}
			}
		}
		

		//สร้างหน้า
		frame=new JFrame();
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		iconImage=new File("Minesweeper.png");
		if(iconImage.exists())
		{
				icon=new ImageIcon("Minesweeper.png");
				frame.setIconImage(icon.getImage());
		}
		
		textPanel=new JPanel();
		textPanel.setVisible(true);
		textPanel.setBackground(Color.BLACK);
		
		//สร้างหน้าต่าง
		buttonPanel=new JPanel();
		buttonPanel.setVisible(true);
		buttonPanel.setLayout(new GridLayout(size,size));
		
		textfield=new JLabel();
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setFont(new Font("MV Boli",Font.BOLD,20));
		textfield.setForeground(Color.BLUE);
		textfield.setText(bombs+" Bombs");		
		
		//สร้างปุ่มรีเซ็ต
		resetButton=new JButton();
		resetButton.setForeground(Color.BLUE);
		resetButton.setText("Reset");
		resetButton.setFont(new Font("MV Boli", Font.BOLD, 20));
		resetButton.setBackground(Color.WHITE);
		resetButton.setFocusable(false);
		resetButton.addActionListener(this);
		
		
		//สร้างที่เก็บรูปโดยใช้ ImgeIcon เเละ เเสดงเป็นปุ่มผ่าน JButton
		ImageIcon iconbubu = new ImageIcon("C:\\Users\\user\\Documents\\GitHub\\ATMJavaFinal\\Java1\\bu.png");
		flag = new JButton(iconbubu);
		flag.setSize(80,80);
		flag.setForeground(Color.RED);
		flag.setVisible(true);
		flag.setFont(new Font("MV Boli", Font.BOLD, 20));
		flag.setBackground(Color.WHITE);
		flag.setFocusable(false); //การกำหนดให้ปุ่ม flag ไม่สามารถรับการ focus ได้ เพื่อป้องกันไม่ให้มีการเลือกปุ่มด้วยการกดปุ่ม Tab
		flag.addActionListener(this); //เชื่อมต่อ listener กับปุ่ม flag เพื่อรอรับการคลิกปุ่มจากผู้เล่น
		
		
		buttons=new JButton[size][size];
		solution=new int[size][size]; //เก็บค่าเเสดงระเบิดที่อยู่รอบๆ
		for(int i=0; i<buttons.length; i++)
		{
			for(int j=0; j<buttons[0].length; j++)
			{
				buttons[i][j]=new JButton();
				buttons[i][j].setFocusable(false);
				buttons[i][j].setFont(new Font("MV Boli",Font.BOLD,12));
				buttons[i][j].addActionListener(this);
				buttons[i][j].setText("");
				buttonPanel.add(buttons[i][j]);
			}
		}
		
		choice=new JComboBox(options);
		choice.addActionListener(this);
		
		//เลือกขนาดของตาราง
		String selectedItem="";
		if(level==9 && bombs==10||level==16 && bombs==40||level==22 && bombs==100)
			selectedItem=String.valueOf(level+"x"+level);
		else 
			selectedItem="Modify";
		choice.setSelectedItem(selectedItem);
		choice.setFont(new Font("MV Boli",Font.BOLD,10)); 
		choice.setBackground(new Color(165, 183, 201));
		choice.setForeground(new Color(255,255,255));
		choice.setBorder(null);
		choice.setEditable(false);
		
		textPanel.add(textfield);
		frame.add(buttonPanel);
		frame.add(textPanel, BorderLayout.NORTH);

		//ปรับหน้าตางของเเต่ละขนาดที่เลือก
		if(position==9 && bombs==10)
			frame.setSize(570,570);
		else if(position==16 && bombs==40)
			frame.setSize(900,650);
		else if(position==22 && bombs==100)
			frame.setSize(1250,700);
		else 
		{
			Dimension size=Toolkit.getDefaultToolkit().getScreenSize(); //Getting the size of screen
			int height=(int) size.getHeight();
			int width=(int) size.getWidth();
			
			if(position<=9)
				frame.setSize(570,570);
			else if(position>9 && position<=16)
				frame.setSize(900,650);
			else if(position>16 && position<=22)
				frame.setSize(1250,700);
			else
			{
				frame.setSize(width, height);
				JOptionPane.showMessageDialog(null, "If You Can't See The Number, Your Screen is Small. Try a Smaller Grid", "Customize", JOptionPane.INFORMATION_MESSAGE);		
			}
		}
		
		frame.add(resetButton, BorderLayout.SOUTH);
		frame.add(flag, BorderLayout.WEST);
		frame.add(choice, BorderLayout.EAST);
		frame.revalidate();
		frame.setLocationRelativeTo(null);
		
		getSolution();
	}

	@Override
	public void actionPerformed(ActionEvent e) //รับค่า choice เลือกขนาด
	{
		if(e.getSource()==choice)
		{
			if(position==9 && bombs==10)
			{
				if(choice.getSelectedItem()=="16x16")
				{
					frame.dispose();
					new Minesweeper(16, 40);
				}
				else if(choice.getSelectedItem()=="22x22")
				{
					frame.dispose();
					new Minesweeper(22, 100);
				}
				else if(choice.getSelectedItem()=="Modify")
				{
					int success=getCustom();
					if(success==1)
					{
						frame.dispose();
						new Minesweeper(customSize, customBombs);
					}
				}
			}
			else if(position==16 && bombs==40)
			{
				if(choice.getSelectedItem()=="9x9")
				{
					frame.dispose();
					new Minesweeper(9, 10);
				}
				else if(choice.getSelectedItem()=="22x22")
				{
					frame.dispose();
					new Minesweeper(22, 100);
				}
				else if(choice.getSelectedItem()=="Modify")
				{
					int success=getCustom();
					if(success==1)
					{
						frame.dispose();
						new Minesweeper(customSize, customBombs);
					}
				}
			}
			else if(position==22 && bombs==100)
			{
				if(choice.getSelectedItem()=="9x9")
				{
					frame.dispose();
					new Minesweeper(9, 10);
				}
				else if(choice.getSelectedItem()=="16x16")
				{
					frame.dispose();
					new Minesweeper(16, 40);
				}
				else if(choice.getSelectedItem()=="Modify")
				{
					int success=getCustom();
					if(success==1)
					{
						frame.dispose();
						new Minesweeper(customSize, customBombs);
					}
				}
			}
			else 
			{
				if(choice.getSelectedItem()=="9x9")
				{
					frame.dispose();
					new Minesweeper(9, 10);
				}
				else if(choice.getSelectedItem()=="16x16")
				{
					frame.dispose();
					new Minesweeper(16, 40);
				}
				else if(choice.getSelectedItem()=="22x22")
				{
					frame.dispose();
					new Minesweeper(22, 100);
				}
			}
		}
		if(e.getSource()==flag)
		{
			if(flagging)
			{
				flag.setBackground(Color.WHITE);
				flagging=false;
			}
			else
			{
				flag.setBackground(Color.RED);
				flagging=true;
			}
		}
		if(e.getSource()==resetButton)
		{
			frame.dispose();
			new Minesweeper(size, bombs);
		}
		for(int i=0; i<buttons.length; i++)
		{
			for(int j=0; j<buttons[0].length; j++)
			{
				if(e.getSource()==buttons[i][j])
				{
					if(flagging)
					{
						if(flagged[i][j])
						{
							buttons[i][j].setText("");
							buttons[i][j].setBackground(null); //null is default bg color
							flagged[i][j]=false;
							buttons[i][j].setIcon(null);
						}
						else
						{
							if(buttons[i][j].getText().equals(""))
							{
								
								ImageIcon iconbu = new ImageIcon("C:\\Users\\user\\Documents\\GitHub\\ATMJavaFinal\\Java1\\bu.png");
								Image image = iconbu.getImage();
								Image scalImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
								ImageIcon scalIcon = new ImageIcon(scalImage);
								buttons[i][j].setBackground(Color.RED);
								buttons[i][j].setIcon(scalIcon);
								buttons[i][j].setText("");
								flagged[i][j]=true; 
								
							}
								
							
						}						
					}
					else 
					{
						if(!flagged[i][j])
						{
							buttons[i][j].setBackground(null); 
							
							check(i,j);	

						}
					}
				}				
			}
		}
	}
	
	public int getCustom()
	{
		boolean successful=false;
		
		String size= JOptionPane.showInputDialog(null,"Choose Grid Size (Eg: 8 for 8x8 Grid)","Customize", JOptionPane.PLAIN_MESSAGE);				
		try {
			customSize=Integer.parseInt(size);
			
			if(customSize<=30 && customSize>0)
				successful=true;
			else 
			{
				if(customSize>30)
					JOptionPane.showMessageDialog(null, "Grid Too Big!", "Customize", JOptionPane.WARNING_MESSAGE);
				else if(customSize<=0)
					JOptionPane.showMessageDialog(null, "Grid Can't Be Zero Or Negative!", "Customize", JOptionPane.WARNING_MESSAGE);
				successful=false;
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Input", "Customize", JOptionPane.WARNING_MESSAGE);
			successful=false;
		}
		
		
		if(successful)
		{
			String bombs=JOptionPane.showInputDialog(null,"Number Of Bombs","Customize", JOptionPane.PLAIN_MESSAGE);	
			
			try {
				customBombs=Integer.parseInt(bombs);
				
				if(customBombs<=(Math.pow(customSize, 2)) && customBombs>=0)
					successful=true;
				else 
				{
					if(customBombs>(Math.pow(customSize, 2)))
						JOptionPane.showMessageDialog(null, "More Bombs Than Spaces!", "Customize", JOptionPane.WARNING_MESSAGE);
					else if(customBombs<0)
						JOptionPane.showMessageDialog(null, "Bombs Can't Be Negative!", "Customize", JOptionPane.WARNING_MESSAGE);
					successful=false;
				}
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid Input", "Customize", JOptionPane.WARNING_MESSAGE);
				successful=false;
			}
		}
		
		if(successful)
			return 1;
		else 
			return 0;
	}

	public void getSolution()
	{
		for(int y=0; y<solution.length; y++)
		{
			for(int x=0; x<solution[0].length; x++)
			{
				boolean changed=false;
				int bombsAround=0;
				
				for(int i=0; i<xPositions.size(); i++)
				{
					if(x==xPositions.get(i)&&y==yPositions.get(i))
					{
						solution[y][x]=size+1; //Indicate mine
						changed=true;
					}
				}
				
				if(!changed)
				{
					for(int i=0; i<xPositions.size(); i++)
					{
						if(x-1==xPositions.get(i)&&y==yPositions.get(i))
							bombsAround++;
						if(x+1==xPositions.get(i)&&y==yPositions.get(i))
							bombsAround++;
						if(x==xPositions.get(i)&&y-1==yPositions.get(i))
							bombsAround++;
						if(x==xPositions.get(i)&&y+1==yPositions.get(i))
							bombsAround++;
						if(x-1==xPositions.get(i)&&y-1==yPositions.get(i))
							bombsAround++;
						if(x+1==xPositions.get(i)&&y+1==yPositions.get(i))
							bombsAround++;
						if(x+1==xPositions.get(i)&&y-1==yPositions.get(i))
							bombsAround++;
						if(x-1==xPositions.get(i)&&y+1==yPositions.get(i))
							bombsAround++;
					}
					solution[y][x]=bombsAround;
				}
			}
		}
	}
	
	public void check(int y, int x)//นี้เป็นฟังก์ชันที่ถูกเรียกเมื่อผู้เล่นคลิกที่ปุ่มบนบอร์ดเกม และมีการตรวจสอบว่าตำแหน่งนั้นมีระเบิดหรือไม่  
	{		
		boolean over=false;
		if(solution[y][x]==(size+1))//ถ้ามีระเบิด จะเรียกฟังก์ชัน gameOver() เพื่อจบเกม
		{
			gameOver(false);
			over=true;
		}
		
		if(!over)//ถ้าไม่มีระเบิด จะเรียกฟังก์ชัน getColor(y, x) เพื่อเปลี่ยนสีปุ่มและแสดงตัวเลขบนปุ่ม
		{
			getColor(y,x);
			
			if(solution[y][x]==0)//ถ้าตัวเลขบนปุ่มเป็น 0 จะเรียกฟังก์ชัน display() เพื่อแสดงปุ่มที่ติดกันที่ไม่มีระเบิดเปิดอยู่ทั้งหมด
			{
				xZero=x;
				yZero=y;
				
				count=0;
				
				display();
			}
			checkWinner();//เมื่อเปิดปุ่มแล้ว จะเรียกฟังก์ชัน checkWinner() เพื่อตรวจสอบว่าเกมจบหรือยัง
		}
	}
	
	public void gameOver(boolean won)//
	{
		if(!won)//ถ้าค่า won เป็น false จะตั้งสีของ textfield เป็นสีแดงและตั้งข้อความ "Game Over!" ใน textfield
		{
			textfield.setForeground(Color.RED);
			textfield.setText("Game Over!");
		}
		else //ถ้าค่า won เป็น true จะตั้งสีของ textfield เป็นสีเขียวและตั้งข้อความ "You Win!" ใน textfield
		{
			textfield.setForeground(Color.GREEN);
			textfield.setText("You Win!");
		}
		for(int i=0; i<buttons.length; i++)//นำเอาปุ่มทั้งหมดในตาราง buttons มาดำเนินการต่อโดยใช้ลูป for
		{
			for(int j=0; j<buttons[0].length; j++)//นำเอาปุ่มทั้งหมดในตาราง buttons มาดำเนินการต่อโดยใช้ลูป for
			{
				buttons[i][j].setBackground(null);
				buttons[i][j].setEnabled(false);//ในแต่ละปุ่มจะตั้งค่า background เป็น null และ setEnabled(false)
				for(int count=0; count<xPositions.size(); count++)
				{
					if(j==xPositions.get(count) && i==yPositions.get(count))
					{//จะตรวจสอบว่าปุ่มนี้ตรงกับตำแหน่งที่มีตัวเลขเท่ากับ ระเบิด หรือไม่  
					 //ถ้าใช่จะตั้งค่า background เป็นสีดำ และใช้ไอคอนรูปภาพของสัญลักษณ์ที่กำหนดในไฟล์ 
						ImageIcon iconpoo = new ImageIcon("C:\\Users\\user\\Documents\\GitHub\\ATMJavaFinal\\Java1\\poo.png");
								Image imagepoo = iconpoo.getImage();
								Image scalImagepoo = imagepoo.getScaledInstance(30, 30, Image.SCALE_SMOOTH);//ปรับขนาดของรูปภาพให้มีขนาด 30x30 พิกเซล
								ImageIcon scalIconpoo = new ImageIcon(scalImagepoo);
								buttons[i][j].setBackground(Color.BLACK);
								buttons[i][j].setIcon(scalIconpoo);
								buttons[i][j].setText("");//และเปลี่ยนตัวอักษรบนปุ่มเป็นช่องว่าง
								flagged[i][j]=true;//กำหนดให้ flagged[i][j] เป็น true

					
					}
				}
			}
		}
		flag.setEnabled(false);
	}
	
	public void checkWinner()//ใช้ตรวจสอบว่าผู้เล่นชนะเกมหรือยัง โดยการนับจำนวน 
	{
		int buttonsleft=0;
		
		for(int i=0; i<buttons.length; i++)
		{
			for(int j=0; j<buttons[0].length; j++)
			{
				if(buttons[i][j].getText()=="" || buttons[i][j].getText()=="1>")
					buttonsleft++;
			}
		}
		if(buttonsleft==bombs)//ถ้าจำนวนปุ่มที่เหลืออยู่เท่ากับจำนวนระเบิดทั้งหมดในเกม ก็จะเรียกใช้ฟังก์ชัน gameOver(true) เพื่อแสดงข้อความว่า "You Win!"
			gameOver(true);
	}
	public void display()
	{
		if(count<1)
		{
			if((xZero-1)>=0)
				getColor(yZero,xZero-1);
			if((xZero+1)<size)
				getColor(yZero,xZero+1);
			if((yZero-1)>=0)
				getColor(yZero-1,xZero);
			if((yZero+1)<size)
				getColor(yZero+1,xZero);
			if((yZero+1)<size && (xZero+1)<size)
				getColor(yZero+1,xZero+1);
			if((yZero-1)>=0 && (xZero-1)>=0)
				getColor(yZero-1,xZero-1);
			if((yZero+1)<size && (xZero-1)>=0)
				getColor(yZero+1,xZero-1);
			if((yZero-1)>=0 && (xZero+1)<size)
				getColor(yZero-1, xZero+1);
			
			count++;		
			display();	
		}
		else
		{
			for(int y=0; y<buttons.length; y++)
			{
				for(int x=0; x<buttons[0].length; x++)
				{
					if(buttons[y][x].getText().equals("0"))
					{
						// IMP* Explanation of how it works:
						//If a button is 0 but is empty around, means it needs to reveal everything around so display is called again
						//The if reveals everything around, the else checks which button needs to reveal everything around it if its a zero.
						//ถ้ากดเจอเลข 0 ก้จะเปิดเลขรอบๆ
						//if เปิดเผยทุกอย่างที่อยู่รอบ ๆ ส่วนอื่น ๆ จะตรวจสอบว่าปุ่มใดจำเป็นต้องเปิดเผยทุกอย่างที่อยู่รอบ ๆ หากเป็นศูนย์
						if(y-1>=0)
						{
							if(buttons[y-1][x].getText().equals("") || buttons[y-1][x].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
						if(y+1<size)
						{
							if(buttons[y+1][x].getText().equals("") || buttons[y+1][x].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
						if(x-1>=0)
						{
							if(buttons[y][x-1].getText().equals("") || buttons[y][x-1].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
						if(x+1<size)
						{
							if(buttons[y][x+1].getText().equals("") || buttons[y][x+1].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
						if(x-1>=0 && y-1>=0)
						{
							if(buttons[y-1][x-1].getText().equals("") || buttons[y-1][x-1].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
						if(x+1<size && y+1<size)
						{
							if(buttons[y+1][x+1].getText().equals("") || buttons[y+1][x+1].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
						if(x+1<size && y-1>=0)
						{
							if(buttons[y-1][x+1].getText().equals("") || buttons[y-1][x+1].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
						if(x-1>=0 && y+1<size)
						{
							if(buttons[y+1][x-1].getText().equals("") || buttons[y+1][x-1].getText().equals("1>"))
							{
								lastXchecked=x;
								lastYchecked=y;
							}
						}
					}
				}
			}
			
			//The reason why the program entered this when there was a single zero the first time was since I made lastXcheked=10, instead of size+1			
			if(lastXchecked<size+1 && lastYchecked<size+1)
			{				
				xZero=lastXchecked;
				yZero=lastYchecked;
				
				count=0;
				
				lastXchecked=size+1;
				lastYchecked=size+1;
				
				display();			
			}
		}
	}
	
	public void getColor(int y, int x) //กำหนดสีของปุ้ม
	{		
		if(solution[y][x]==0)
			buttons[y][x].setEnabled(false);
		if(solution[y][x]==1)
			buttons[y][x].setForeground(Color.BLUE);
		if(solution[y][x]==2)
			buttons[y][x].setForeground(Color.GREEN);
		if(solution[y][x]==3)
			buttons[y][x].setForeground(Color.RED);
		if(solution[y][x]==4)
			buttons[y][x].setForeground(Color.MAGENTA);
		if(solution[y][x]==5)
			buttons[y][x].setForeground(new Color(128,0,128));
		if(solution[y][x]==6)
			buttons[y][x].setForeground(Color.CYAN);
		if(solution[y][x]==7)
			buttons[y][x].setForeground(new Color(42, 13, 93));
		if(solution[y][x]==8)
			buttons[y][x].setForeground(Color.lightGray);
		
		buttons[y][x].setBackground(null);
		buttons[y][x].setText(String.valueOf(solution[y][x]));
	}
}