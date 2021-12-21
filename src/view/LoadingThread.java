package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import model.ResizeImages;

public class LoadingThread extends SwingWorker<Boolean, Integer> {
	
	private JProgressBar _progBar;
	private JLabel _chooseFolder;
	private JLabel _imageCheck;
	private JButton _start;
	private JButton _selectFolder;
	
	public LoadingThread(JProgressBar progBar , JLabel chooseFolder,  JLabel imageCheck, JButton start,JButton selectFolder) {
		_progBar = progBar;
		_chooseFolder = chooseFolder;
		_imageCheck = imageCheck;
		_start = start;
		_selectFolder = selectFolder;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		_imageCheck.setIcon(null);
		_start.setEnabled(false);
		_progBar.setIndeterminate(true);	
		_selectFolder.setEnabled(false);
		ResizeImages images = new ResizeImages(_chooseFolder.getText());
		images.resizeImages();
		return true;
	}

	@Override
	public void done() {
		_progBar.setIndeterminate(false);
		_progBar.setValue(100);
		_start.setEnabled(true);
		_selectFolder.setEnabled(true);
		try {
			BufferedImage completedImg = ImageIO.read(getClass().getResource("check.png"));
			_imageCheck.setIcon(new ImageIcon(completedImg));
			_imageCheck.setBounds(553, 344, 37, 64);
		} catch (IOException e) {
			System.out.println("Fail check icon");
			e.printStackTrace();
		}
	
	}
	
	

}
