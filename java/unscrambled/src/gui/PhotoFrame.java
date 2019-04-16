/*
 * CREATED BY BORROUGAGNOU : 2019-04-14
 * 
 * used multiple ressource for convert my python algorythm to java:
 * - https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html#getSubimage(int,%20int,%20int,%20int)
 * - https://stackoverflow.com/questions/2386064/how-do-i-crop-an-image-in-java
 * - https://docs.oracle.com/javase/tutorial/2d/images/drawonimage.html
 * - https://stackoverflow.com/questions/10055005/how-to-draw-an-image-over-another-image#10055306
 * - http://www-igm.univ-mlv.fr/~forax/ens/ig/cours/pdf/UI-14-Images.pdf
 * - http://dspace.univ-tlemcen.dz/bitstream/112/5813/1/Developpement-dune-application-de-traitement-dimages.pdf
 * - https://www.dyclassroom.com/image-processing-project/introduction
 * - https://stackoverflow.com/questions/36462710/bufferedimage-extract-subimage-with-same-data
 * - https://www.gladir.com/CODER/JAVA/classbufferedimage.htm
 * - https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html
 * - IRC
 * - Discord
 * 
 * used this video for create the interface:
 * - https://www.youtube.com/watch?v=yOhThz6EolM
 */

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PhotoFrame extends JFrame implements ActionListener
{
	private JLabel jlabelphoto = new JLabel("Photo");
	private JComboBox<String> jcomboboxphoto;
	private JButton jbouttonTraiter = new JButton("Traiter");
	private PanelPhoto panelPhoto = new PanelPhoto();
	private BrowseFile mypath = new BrowseFile();
	
	public PhotoFrame()
	{
		mypath.setMypath();
		File f				= new File(mypath.getMypath());
		String[] imageList	= f.list();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		try
		{
			jcomboboxphoto		 = new JComboBox<String>(imageList);
			String nomPhoto		 = (String) jcomboboxphoto.getSelectedItem();
			BufferedImage mypict = ImageIO.read(new File(mypath.getMypath()+"/"+nomPhoto));
			panelPhoto.setMypict(mypict);

			JPanel jPanelN		 = new JPanel();
			jPanelN.setLayout(new FlowLayout());
			jPanelN.add(jlabelphoto);
			jPanelN.add(jcomboboxphoto);
			jPanelN.add(jbouttonTraiter);
			this.add(jPanelN, BorderLayout.NORTH);
			this.add(panelPhoto, BorderLayout.CENTER);
			this.setBounds(10, 10, 600, 849);
			this.setVisible(true);

			jcomboboxphoto.addActionListener(this);
			jbouttonTraiter.addActionListener(this);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
					"ERROR: "+ e.getMessage(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,
					"ERROR: "+ e.getMessage(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// take the name into the combobox
		String nomPhoto = (String) jcomboboxphoto.getSelectedItem();

		if (e.getSource() == jbouttonTraiter)
		{
			// THIS PART IS A UNSCRAMBLED ALGORYTHM

			BufferedImage myImg = panelPhoto.getMypict();
			int maxH			= myImg.getHeight();
			int maxW			= myImg.getWidth();
			int x				= 0;
			int y				= 0;
			BufferedImage reslt = new BufferedImage(maxW, maxH, BufferedImage.TYPE_INT_RGB);

			// can write into BufferedImage result
			Graphics2D g2d = reslt.createGraphics();

			// ### TIME 1 ###
			// this 2 BufferedImage are for "copyData"
			BufferedImage row1 = new BufferedImage(myImg.getColorModel(), myImg.getRaster().createCompatibleWritableRaster(maxW, 100), myImg.isAlphaPremultiplied(), null);
			BufferedImage row2 = new BufferedImage(myImg.getColorModel(), myImg.getRaster().createCompatibleWritableRaster(maxW, 100), myImg.isAlphaPremultiplied(), null);
			while (y < maxH - 200)
			{
				// pict a square of original image
				BufferedImage subimg1 = myImg.getSubimage(0, y, maxW, 100);
				// paste into "row1"
				subimg1.copyData(row1.getRaster());
	
				BufferedImage subimg2 = myImg.getSubimage(0, y + 100, maxW, 100);
				subimg2.copyData(row2.getRaster());

				// switch time ! add row2 at first and row1 at the second 
				g2d.drawImage(row2, 0, y      , null);
				g2d.drawImage(row1, 0, y + 100, null);
				y += 200;
			}

			// the end of the scrambled image is different, so:
			if (y > maxH - 200)
			{
				BufferedImage row = new BufferedImage(myImg.getColorModel(), myImg.getRaster().createCompatibleWritableRaster(maxW, maxH - y), myImg.isAlphaPremultiplied(), null);
				BufferedImage subimg = myImg.getSubimage(0, y, maxW, maxH - y);
				subimg.copyData(row.getRaster());
				g2d.drawImage(row, 0, y, null);
			}

			// ### TIME 2 ###
			BufferedImage col1 = new BufferedImage(reslt.getColorModel(), reslt.getRaster().createCompatibleWritableRaster(100, maxH), reslt.isAlphaPremultiplied(), null);
			BufferedImage col2 = new BufferedImage(reslt.getColorModel(), reslt.getRaster().createCompatibleWritableRaster(100, maxH), reslt.isAlphaPremultiplied(), null);
			while (x < maxW - 200)
			{
				BufferedImage subimg1 = reslt.getSubimage(x      , 0, 100, maxH);
				subimg1.copyData(col1.getRaster());
	
				BufferedImage subimg2 = reslt.getSubimage(x + 100, 0, 100, maxH);
				subimg2.copyData(col2.getRaster());

				g2d.drawImage(col2, x      , 0, null);
				g2d.drawImage(col1, x + 100, 0, null);
				x +=200;
			}

			// show the result into the graphic box
			panelPhoto.setMypict(reslt);
			panelPhoto.repaint();
			
			// save into file
			try
			{
				File fs = null;
				fs = new File(mypath.getMypath()+"/"+nomPhoto+"_OK.jpg");
				ImageIO.write(reslt, "jpg", fs);
			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(null,
						"Action error: "+ e1.getMessage(), "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (e.getSource() == jcomboboxphoto)
		{
			this.setBounds(10, 10, 600, 849);
			try
			{
				BufferedImage mypict = ImageIO.read(new File(mypath.getMypath()+"/"+nomPhoto));
				panelPhoto.setMypict(mypict);
				panelPhoto.repaint();
			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(null,
						"Action error: "+ e1.getMessage(), "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new PhotoFrame();
	}
}
