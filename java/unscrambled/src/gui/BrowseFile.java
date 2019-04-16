package gui;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class BrowseFile
{
	private String mypath;

	public String getMypath()
	{
		return mypath;
	}

	public void setMypath()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Folder to scrambled image");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			this.mypath = chooser.getSelectedFile().toString();
		else
		{
			JOptionPane.showMessageDialog(null,
					"No folder selected", "ERROR NO FOLDER SELECTED",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
}