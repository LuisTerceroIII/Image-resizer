package view;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View {

	private JFrame _frame;
	private JLabel _title;
	private JLabel _imageFolder;
	private JProgressBar _progressBar;
	private JLabel _chooseFolder;

	/**
	 * Launch the application.
	 */
	public void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					View window = new View();
					window._frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public View() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		setFrame();
		setTitle("Resize Images");
		setFolderImage();
		setProgressBar();
		setChooseFolder();
		setStartButtom();
		setFolderSelection();
		setInfoToolTip();
	}

	private void setFrame() {
		_frame = new JFrame();
		_frame.setTitle(" Luis Espinoza");
		_frame.setBackground(Color.WHITE);
		_frame.getContentPane().setBackground(Color.WHITE);
		_frame.setBounds(100, 100, 649, 557);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.getContentPane().setLayout(null);
		_frame.setResizable(false);
	}

	private void setInfoToolTip() throws IOException {
		BufferedImage infoImage = ImageIO.read(getClass().getResource("info.png"));
		JLabel info = new JLabel(new ImageIcon(infoImage));
		info.setToolTipText("The images also will be saved in the selected source directory.");
		info.setBounds(375, 270, 47, 64);
		_frame.getContentPane().add(info);
	}

	private void setFolderSelection() {
		JButton selectFolder = new JButton("Select Folder");
		selectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fileChooser.showOpenDialog(_frame);
				if(response == JFileChooser.APPROVE_OPTION) {
					_chooseFolder.setText(fileChooser.getSelectedFile().toString());
					
				} else {
					_chooseFolder.setText("The file open operation was cancelled");
				}
			}
		});
		selectFolder.setBounds(259, 292, 117, 23);
		_frame.getContentPane().add(selectFolder);
	}

	private void setStartButtom() {
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadingThread processImages = new LoadingThread(_progressBar,_chooseFolder);
				processImages.execute();
			}
		});
		start.setBounds(273, 428, 89, 23);
		_frame.getContentPane().add(start);
	}

	private void setChooseFolder() {
		_chooseFolder = new JLabel("");
		_chooseFolder.setHorizontalAlignment(SwingConstants.CENTER);
		_chooseFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		_chooseFolder.setBounds(0, 326, 633, 27);
		_frame.getContentPane().add(_chooseFolder);
	}

	private JProgressBar setProgressBar() {
		_progressBar = new JProgressBar();
		_progressBar.setBounds(98, 358, 445, 41);
		_frame.getContentPane().add(_progressBar);
		return _progressBar;
	}

	private void setFolderImage() throws IOException {
		BufferedImage folderImage = ImageIO.read(getClass().getResource("folder.png"));
		_imageFolder = new JLabel(new ImageIcon(folderImage));
		_imageFolder.setBounds(0, 87, 623, 172);
		_frame.getContentPane().add(_imageFolder);
	}

	private void setTitle(String title) {
		_title = new JLabel("Image Resizer");
		_title.setFont(new Font("Tahoma", Font.PLAIN, 37));
		_title.setHorizontalAlignment(SwingConstants.CENTER);
		_title.setBounds(0, 27, 633, 53);
		_frame.getContentPane().add(_title);
	}
}
