package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelPhoto extends JPanel
{
	private BufferedImage mypict;

	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawImage(mypict, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	public BufferedImage getMypict()
	{
		return mypict;
	}

	public void setMypict(BufferedImage mypict)
	{
		this.mypict = mypict;
	}
}
