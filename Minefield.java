import java.io.File; //enable use of colors for labels
import javax.sound.sampled.AudioInputStream; //enable use of sound effect components
import javax.sound.sampled.AudioSystem; //enable use of sound effect components
import javax.sound.sampled.Clip; //enable use of sound effect components
import javax.swing.*; // to enable use of Swing components
import java.awt.GridLayout; // to enable use the gridlayout 
import java.awt.event.*; // enable use of action event for buttons
import java.awt.*; //enable use of awt 
import java.util.Random; //import random for random number
import javax.swing.border.Border;
import javax.swing.border.LineBorder; //import to enable use of a order
import java.util.Timer;
import java.util.TimerTask;
import java.lang.*;

public class Minefield extends JFrame implements MouseListener
{
	private int ROWS; //global variable for rows
	private int COLS; //global bariables for columns
	private int BOMBS; //variable for the total bombs
	private int numOfClicks =0; //counting the number of clicks for the game
	private String timeRun =""; //this is a string for the displaying counter in the top right corner 
	private int seconds = 0; // seconds in the timer
	private int minutes = 0; // minutes in the timer
	private int nonBombs = (getROWS()*getCOLS()) - getBOMBS(); //variable for total cells that isn't a bomb
	private int nonBombsCount = 0; // counter for the non bombs
	private int bombFlags, nonBombFlags =0; //counter for bombs flags
	private int labelBombs = getBOMBS(); //counter for label
	private int cellsRevealed = 0; //counter for cells revealed
	private JFrame frame; //new frame 
	private JLabel label = new JLabel(); //initialize new label 
	private JLabel lbl = new JLabel();
	private JLabel timeLabel = new JLabel();
	private JButton smileyLabel = new JButton();
	private JPanel panel = new JPanel();  //initialize new jpanel
	private JPanel minePanel = new JPanel();
	private JPanel infoPanel = new JPanel();  //initialize new jpanel
	private JButton grid [][]; //creating the grid of buttons 
	private JButton timesButtton = new JButton();
	private JComboBox dropDown = new JComboBox(); 
	private MSCell field[][]; //new object of the class MSCel
	GridBagConstraints gbc = new GridBagConstraints(); //declaring the gridbagconstraint 
	GridBagLayout layout = new GridBagLayout(); //declaring the gridbaglayout
	GridBagConstraints gbc1 = new GridBagConstraints();

	public Minefield()
	{
		super("Minesweeper"); //name of my program
		setSizeRows(10);
		setSizeCols(12);
		setBombs(15);
		createView(); //method to create my gui
		start(); //create the grid of buttons 
		initialiseField(); //methods for initialization
		plantBombs(); //method for bombs
		computeNumbers(); //method for computing the numbers in each cell
		time(0,0,0); //set the default time bakc to zero for all values in time method
	}
	//set & get method for my rows
	public void setSizeRows(int r) 
	{
		ROWS = r;
	}
	public int getROWS()
	{
		return COLS;
	}
	//set & get method for my columns
	public void setSizeCols(int c)
	{
		COLS = c;
	}
	public int getCOLS()
	{
		return ROWS;
	}
	//set & get method for my bombs
 	public void setBombs(int b)
 	{
 		BOMBS = b;
 	}
 	public int getBOMBS()
 	{
 		return BOMBS;
 	}

	public void time(int  s, int m, int d)
	{
		seconds = s; //set seconds equal to the parameter received
		minutes = m; //set mintes equal to the parameter received
		Timer timer = new Timer(); // new timer object, built in java class
		TimerTask task = new TimerTask() // new timertask, built in java class
		{
			public void run()
			{
				seconds++; //increment seconds
				if(seconds<60)
				{
					if(seconds<10 && minutes<10)
						timeLabel.setText("0"+minutes+":0"+seconds);
					else if(seconds>10 && minutes<10)
						timeLabel.setText("0"+minutes+":"+seconds);
					else if(seconds<10 && minutes>10)
						timeLabel.setText(""+minutes+":0"+seconds);
					else if(seconds>10 && minutes>10)
						timeLabel.setText(""+minutes+":"+seconds);
				}
				else if(seconds==60)
				{	
					minutes++; // increment minutes when seconds equal 60
					seconds=0; //set seconds back to 0
					timeLabel.setText("0"+minutes+":"+seconds+"0"); //change the time label at this time
				}
			}
		};
		timer.scheduleAtFixedRate(task,d,1000);
	}
	public void start()
	{
		//this method is used to ake the button grid, the reason is that we want to be able to create an entire new grid if the restart button is pressed
		grid = new JButton[getROWS()+2][getCOLS()+2];
		for(int i = 1; i<getROWS()+1; i++){
			for (int j = 1; j<(getCOLS())+1; j++){
				grid[i][j] = new JButton(); //creating a new JButton for the grid in this position
				grid[i][j].setPreferredSize(new Dimension(22,22)); //setting the size on the button on the grid in this position
				gbc.gridx = i; //grid x position 
    			gbc.gridy = j; //grid y position 
				minePanel.add(grid[i][j], gbc); //add buttin in array to panel
				grid[i][j].addMouseListener(this); //add an actionlistener to a button			
			}
		}
	}
	
	public void createView()
	{ 
		GridLayout infoLayout = new GridLayout(1,3,150,150); //new gridbaglayout with sizing specifications
		grid = new JButton[getROWS()+2][getCOLS()+2];
		Border border = LineBorder.createGrayLineBorder(); //creating a new border object, then specifying the color of it
		Border border2 = LineBorder.createBlackLineBorder(); //creating a new border object, then specifying the color of it
		infoPanel.setLayout(infoLayout); //setting the layout for the panel that holds our info 
		minePanel.setLayout(layout); //setting the layout for the minePinel, the panel with the grid on it
		panel.add(minePanel);

		dropDown = new JComboBox();
		dropDown.addItem("Easy");
		dropDown.addItem("Medium");
		dropDown.addItem("Hard");
		dropDown.addMouseListener(this); //add an actionlistener to a button

		lbl = new JLabel("Remaining Bombs: "+getBOMBS()); //this the label that used to count the remaining bombs when right clicked
		timeLabel = new JLabel();

		smileyLabel = new JButton(); //create a button in which I put the smiley for wins and loses
		smileyLabel.setOpaque(false); //this is visual setting for the borders, backgrounds and so on
		smileyLabel.setContentAreaFilled(false);
		smileyLabel.setBorderPainted(false);
		smileyLabel.setBorderPainted(false);
        smileyLabel.setPreferredSize(new Dimension(50,50)); //set the size of the label
        smileyLabel.setIcon(new ImageIcon("smiley.png"));
		smileyLabel.addMouseListener(this);

		infoPanel.setBorder(border2); //setting the info panels border to the settings of the 2nd border specification
        infoPanel.add(lbl);//, BorderLayout.WEST);
        infoPanel.add(dropDown, BorderLayout.SOUTH);
        infoPanel.add(smileyLabel);//, BorderLayout.SOUTH);
        infoPanel.add(timeLabel);//, BorderLayout.EAST);

		setLayout(new BorderLayout());
		add(panel,BorderLayout.CENTER);
		add(infoPanel,BorderLayout.NORTH);
	}

	public void initialiseField() 
	{
		field = new MSCell[getROWS()+2][getCOLS()+2];
		for(int i = 0; i<getROWS()+2; i++){
			for (int j = 0; j<getCOLS()+2; j++){  
				field[i][j] = new MSCell(); //create a new field for the same rows and columns of grid
				if( (i)==0 || (i)==getROWS()+1 ) //if i-1 is zero, or if i-1 is the second last row
				{
					field[i][j].setValue(-1); //set text on the button in the array	
				}
				else if((j)==0 || (j)==getCOLS()+1)
				{
					field[i][j].setValue(-1); //set text on colums for 0 	
				}
			}
		}
	}

	public int randomNum(int min, int max) //method for random number
	{
		Random randomNumbers = new Random(); //create new object of random
    	return randomNumbers.nextInt((max - min) + 1) + min; // return the number we generate 
	}

	public void plantBombs()
	{
		int bombRow, bombCols; //create new variables for row and column bombs
		int i =0; 
		while(i < getBOMBS())
		{
		    bombRow = randomNum(1, (getROWS())); //generate random number for row bombs 
			bombCols = randomNum(1, (getCOLS())); //generate random number for cols bombs 
			if(field[bombRow][bombCols].isBomb() == false)
			{
				field[bombRow][bombCols].setBomb(true); //set the bomb to true n this position
				i++; 
			}
		}
		System.out.println();
	}
	public void computeNumbers()
	{	 
		int counter =0;
		for(int i = 1; i<getROWS()+1; i++){
			for (int j = 1; j<getCOLS()+1; j++){
				counter = 0; // instantiate counter
				for(int x=i-1; x<i+2; x++){
					for(int y=j-1; y<j+2; y++){
						if(field[x][y].isBomb()) // if the field[][] is a bomb, increment the counter
							counter++; //incrementation of the counter
					}
				}
				field[i][j].setValue(counter);
				// if(field[i][j].isBomb() == true) // if it is a bomb set text to B
				// {
				// 	grid[i][j].setText(field[i][j].toString()); //change text of the button
				// 	panel.repaint(); //refresh the panel after text has been changed
    //    				panel.revalidate(); 
				// }
			}			
		}
	}
	public void revealZero(int i, int j)
	{
		for (int a=-1; a<=1; a++) 
		{
			for (int b=-1; b<=1; b++)
			{
				if ((field[i+a][j+b].getValue() ==0) && (field[i+a][j+b].isRevealed() ==false) && (field[i+a][j+b].getValue()!=-1) )
				{
					field[i+a][j+b].setRevealed(true);
					revealButton(i+a,j+b); //call the reveal function to reveal the button in this position
					revealZero(i+a,j+b); // clear zeros 
				}
				else
				{
					if(field[i+a][j+b].isRevealed()==false && field[i+a][j+b].getValue()!=-1)
					{
						field[i+a][j+b].setRevealed(true);
						revealButton(i+a,j+b); //call the reveal function to reveal the button in this position
					}
				}
			}				
		}	
	}	
	public void revealButton(int row, int col)
	{		
		Color green = new Color(0,102,0);
		Color purple = new Color(128,0,128);
		if(field[row][col].getValue()!=-1)
		{
			cellsRevealed++;
			Border border = LineBorder.createGrayLineBorder();
			minePanel.remove(grid[row][col]); //remove the button in this position
			nonBombsCount++; 
					
			JLabel label = new JLabel(field[row][col].toString(), SwingConstants.CENTER); // set text and center the text
			if(field[row][col].isBomb())
			{
				ImageIcon icon = new ImageIcon("mine_red.png");
				label = new JLabel("", icon, JLabel.CENTER);
			}
			else if(field[row][col].getValue()==-1)
			{
				label.setForeground(Color.GRAY); //text color to gray
			}
			else if(field[row][col].getValue()==0)
			{
				label.setForeground(Color.GRAY); //text color to gray
			}
			else if(field[row][col].getValue()==1)
			{
				label.setForeground(Color.BLUE); //text color to blue
			} 
			else if(field[row][col].getValue()==2)
			{
				label.setForeground(green); //text color to green
			}
			else if(field[row][col].getValue()==3)
			{
				label.setForeground(Color.RED); //text color to red
			}
			else if(field[row][col].getValue()==4)
			{
				label.setForeground(purple); //text color to purple
			}
			else if(field[row][col].getValue()==5)
			{
				label.setForeground(Color.YELLOW); //text color to yellow
			}
			else if(field[row][col].getValue()==6)
			{
				label.setForeground(Color.BLACK); //text color to black
			}
			else if(field[row][col].getValue()==7)
			{
				label.setForeground(Color.BLACK); //text color to black
			}
			else if(field[row][col].getValue()==9)
			{
				label.setForeground(Color.DARK_GRAY); //text color to gray
			}
			label.setPreferredSize(new Dimension(22,22)); //set the size of the label
			label.setBorder(border); // setting the border of the jlabel
			
			gbc.gridx = row; //grid bag constraint in x plane
			gbc.gridy = col; // grid bag constraint in y plane
			
			minePanel.add(label , gbc); // add (first the object, the position on the layout)
			minePanel.repaint(); //refresh the panel  
			minePanel.revalidate();
		}
	}
	public void reset(int r, int c, int b)
	{

		for(int i = 1; i<getROWS()+1; i++){
			for (int j = 1; j<getCOLS()+1; j++){
				minePanel.remove(grid[i][j]); //remove the grid 
				minePanel.repaint(); //refresh the panel it was on
				minePanel.revalidate();		
			}
		}
		
		for(int i = 1; i<getROWS()+1; i++){
			for (int j = 1; j<getCOLS()+1; j++){
			field[i][j].setRevealed(false); //set revealed in MSCell back to false
			field[i][j].setFlagged(false); //set flagged in MSCell back to false
			field[i][j].setBomb(false); //set bombs in MSCell back to false
			field[i][j].setValue(0); //make all values 0 again (except the hidden outside border)
			grid[i][j].setIcon(new ImageIcon("")); //remove all images left on buttons
			}
		}
		label.setIcon(new ImageIcon("")); 

		panel.remove(minePanel); //remove the minepanel from the main panel
		panel.repaint(); //refresh the main panel
		panel.revalidate();
		this.remove(panel); //remove the panel from the frame
		this.revalidate();
		this.repaint();

		setSizeCols(r); // set the size to parameter received, if the levels are selected new numbers will be given to the col and rows and the grid and variables will change to that
		setSizeRows(c);
		setBombs(b);

		timeRun =""; //restting our timeRun variable
		seconds = 0; //restting the seconds we have played
		minutes = 0; //resetting the minutes we have played
		nonBombs = (getROWS()*getCOLS()) - getBOMBS(); //variable for total cells that isn't a bomb
		nonBombsCount = 0; // counter for the non bombs
		bombFlags =0;
		nonBombFlags =0; //counter for bombs flags
		labelBombs = getBOMBS(); //counter for label
		cellsRevealed = 0; //counter for cells revealed 

		panel = new JPanel();
		minePanel = new JPanel();
		grid = new JButton[getROWS()+2][getCOLS()+2];
		minePanel.setLayout(layout);
		start();
		initialiseField(); //methods for initialization
		plantBombs(); //method for bombs
		computeNumbers(); //method for computing the numbers in each cell
		panel.add(minePanel);
		add(panel,BorderLayout.CENTER);
		lbl.setText("Remaining Bombs: "+labelBombs);
	}
	public void gameIsWon()
	{
		for(int x =1; x<getROWS()+2; x++){
			for(int y=1; y<getCOLS()+2; y++){
				if(field[x][y].isBomb())
				{		
					revealButton(x,y); //calls our reveal button function when it is a bomb
				}
			}
		}
		smileyLabel.setIcon(new ImageIcon("cool.png"));
		lbl.setText("Remaining Bombs: "+0);
		playSound("winning.wav");
		JOptionPane.showMessageDialog(null, "You Have Won!");
		reset(getROWS(), getCOLS(), getBOMBS());
		smileyLabel.setIcon(new ImageIcon("smiley.png"));
	}
	public void gameIsLost()
	{	
		//save(timeRun);
		for(int x =1; x<getROWS()+2; x++){
			for(int y=1; y<getCOLS()+2; y++){
				if(field[x][y].isBomb())
				{		
					revealButton(x,y);
				}
			}
		}
		smileyLabel.setIcon(new ImageIcon("dead.png"));
		playSound("losing.wav");
		JOptionPane.showMessageDialog(null, "You Have Lost");
		reset(getROWS(), getCOLS(), getBOMBS());
		smileyLabel.setIcon(new ImageIcon("smiley.png"));
	}
	public void playSound(String soundName)
 	{
   		try 
   		{
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( )); //create  new file with file received as parameter
		    Clip clip = AudioSystem.getClip( ); //creating a new clip
		    clip.open(audioInputStream); //using the clip to open up the file 
		    clip.start(); //playing the clip 
   		}
   		catch(Exception ex)
	   {
	    	System.out.println("Error with playing sound.");
	   }
 	}
	public void mousePressed(MouseEvent me){} //override method
	public void mouseReleased(MouseEvent me){} //override method
	public void mouseEntered(MouseEvent me){} //override method
	public void mouseExited(MouseEvent me){} //override method
	
	public void mouseClicked(MouseEvent me)
	{//built in method in java (when action is performed)
		Border border = LineBorder.createGrayLineBorder(); //creating a new border object, then specifying the color of it
		if(me.getButton() == MouseEvent.BUTTON1) //get button is a built in method, mouse event button = left click
		{			
			numOfClicks++;
			if (me.getSource()==dropDown)
			{
				JComboBox cb = (JComboBox)me.getSource();
				String msg = (String)cb.getSelectedItem();
				switch(msg)
				{
					case "Easy":reset(12, 10, 15); //send wanted size and bombs as parameter to the reset method
						break;
					case "Medium": reset(25, 15, 50); //send wanted size and bombs as parameter to the reset method
						break;
					case "Hard": reset(50, 30, 120); //send wanted size and bombs as parameter to the reset method
						break;
				}
			}
			if(me.getSource()==smileyLabel)
			{
				playSound("sound2.wav"); //play the sound for click
				smileyLabel.setIcon(new ImageIcon("oops.png"));
				smileyLabel.setIcon(new ImageIcon("smiley.png"));
				reset(getROWS(), getCOLS(), getBOMBS()); //calls the rest method above

			}
			for(int x = 1; x<getROWS()+1; x++){
				for(int y = 1; y<getCOLS()+1; y++){
					if(me.getSource() == grid[x][y])
					{
						if(!field[x][y].isFlagged()) // can only be clicked on if the button isn't flagged 
						{
							if(field[x][y].isBomb()) // when clicking on a bomb
							{
								gameIsLost(); //call the game is lost function
							}
							else //when a normal click accours
							{
								playSound("sound1.wav"); // call the playSound method and send the file as parameter
								smileyLabel.setIcon(new ImageIcon("oops.png")); // change the smiley image on the button in infoPanel
								if(field[x][y].getValue()==0)
								{
									revealZero(x,y); // call the recursive function
								}
								else
								{
									revealButton(x,y);
									field[x][y].setRevealed(true); // set revealed to true
								}
								smileyLabel.setIcon(new ImageIcon("smiley.png"));
							}
							if(cellsRevealed==nonBombs || nonBombsCount==nonBombs) // this if tests to see whether all nonbomb cells are revelaed, or if all bombs have been flagged 
							{
								gameIsWon(); //when the game is won
							}
						}
					}
				}
			}
		}
		if(me.getButton() == MouseEvent.BUTTON3)
		{
			for(int x = 1; x<getROWS()+1; x++){
				for(int y=1; y<getCOLS()+1; y++){
					if(me.getSource() == grid[x][y])
					{
						if(field[x][y].isFlagged() == false) 
						{
							playSound("sound2.wav");
							if(field[x][y].isBomb() == true) //if to test if it is a bomb
							{
								bombFlags ++ ; //increment our flagged bombs counter
								labelBombs--; // decrement bombs counterr in infoPanel
								field[x][y].setFlagged(true); //set flagged to true
								grid[x][y].setIcon(new ImageIcon("flag.png")); //change text of the button
								minePanel.repaint(); //refresh the panel after text has been changed
       							minePanel.revalidate();
       							lbl.setText("Remaining Bombs: "+labelBombs);
       						}
       						else // if not a bomb
       						{
       							nonBombFlags ++;
       							labelBombs--; 
       							field[x][y].setFlagged(true); 
       							grid[x][y].setIcon(new ImageIcon("flag.png")); //change text of the button
								minePanel.repaint(); //refresh the panel after text has been changed
       							minePanel.revalidate();
       							lbl.setText("Remaining Bombs: "+labelBombs);
       						}
       					}
       					else if(field[x][y].isFlagged() == true) {
       						playSound("sound2.wav");
							if(field[x][y].isBomb() == true) //if to test if it is a bomb
							{
								bombFlags -- ; //decrement our flagged bombs counter if right clicked again
								labelBombs++;
								field[x][y].setFlagged(false); 
								grid[x][y].setIcon(new ImageIcon());//setText(field[x][y].toString()); //change text of the button
								minePanel.repaint(); //refresh the panel after text has been changed
       							minePanel.revalidate();
       							lbl.setText("Remaining Bombs: "+labelBombs);
       						}
       						else // if not a bomb
       						{
       							labelBombs++;
       							nonBombFlags --; //decrement if clicked again
       							field[x][y].setFlagged(false); 
       							grid[x][y].setIcon(new ImageIcon()); //change text of the button
								minePanel.repaint(); //refresh the panel after text has been changed
       							minePanel.revalidate();
       							lbl.setText("Remaining Bombs: "+labelBombs);
       						}
						}
      				}
      				if(bombFlags==getBOMBS())
      					gameIsWon();
				}
			}
		}
	}

	public static void main(String[] args)
	{
		new Minefield();
	}
}