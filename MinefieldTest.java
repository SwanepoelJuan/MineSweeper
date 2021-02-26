import java.awt.event.*; //enable use of awt events
import javax.swing.*; //enable use of swing components
import java.awt.*; //enable use of of awt 
public class MinefieldTest
{
	public static void main(String[] args)
	{
		Minefield frame = new Minefield(); //create new object of the minefield
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close when exiting program
		frame.setSize(1150, 780); //sixe of the frame
		frame.setLocationRelativeTo(null); //center my program
		frame.getContentPane(); 
		frame.setVisible(true); //set visibility of the frame
	}
}