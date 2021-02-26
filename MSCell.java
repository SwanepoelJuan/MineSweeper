public class MSCell
{
	private boolean revealed;
	private boolean bomb;
	private int value;
	private boolean flagged; 

	public MSCell()
	{
		setRevealed(false);
		setBomb(false);
		setValue(0);
		setFlagged(false);
	}
	public MSCell(int val, boolean rev)
	{
		setRevealed(rev);
		setBomb(false);
		setValue(val);
		setFlagged(false);
	}

	//accessor and mutators
	public void setRevealed(boolean rev)
	{
		revealed = rev; 
	}
	public boolean isRevealed()
	{
		return revealed; 
	}
	public void setBomb(boolean bmb)
	{
		bomb = bmb; 
	}
	public boolean isBomb()
	{
		return bomb;  
	}
	public void setValue(int v)
	{
		value = v; 
	}	
	public int getValue()
	{
		return value; 
	}
	public void setFlagged(boolean flg)
	{
		flagged = flg;
	}
	public boolean isFlagged()
	{
		return flagged; 
	}
	public String toString()
	{
		if(isBomb() == true)
		{
			return "B"; 
		}
		else if(value==-1)
		{
			return "-1";
		}
		else if(value==0)
		{
			return " ";
		}
		else if(value==1)
		{
			return "1";
		} 
		else if(value==2)
		{
			return "2";
		}
		else if(value==3)
		{
			return "3";
		}
		else if(value==4)
		{
			return "4";
		}
		else if(value==5)
		{
			return "5";
		}
		else if(value==6)
		{
			return "6";
		}
		else if(value==7)
		{
			return "7";
		}
		else if(value==9)
		{
			return "9";
		}		
		else 
			return null;
	}
}