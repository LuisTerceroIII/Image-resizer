package view;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import model.ResizeImages;

public class LoadingThread extends SwingWorker<Boolean, Integer> {
	
	private JProgressBar _progBar;
	private JLabel _chooseFolder;
	
	public LoadingThread(JProgressBar progBar , JLabel chooseFolder) {
		_progBar = progBar;
		_chooseFolder = chooseFolder;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		_progBar.setIndeterminate(true);
		ResizeImages images = new ResizeImages(_chooseFolder.getText());
		images.resizeImages();
		return true;
	}
	
	@Override
	public void done() {
			_progBar.setIndeterminate(false);
	}
	
	

}
