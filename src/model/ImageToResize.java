package model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class ImageToResize {

	private File _file;
	private Dimension _imageDimension;
	private BufferedImage _image;
	private int WIDTH_MOBILE = 700;
	private int WIDTH_DESKTOP = 1500;

	public ImageToResize(String path) {
		_file = new File(path);
		try {
			_image = ImageIO.read(_file);
			_imageDimension = new Dimension(_image.getWidth(), _image.getHeight());
		} catch (IOException e) {
			System.out.println(" Can't read image locate in " + path);
			e.printStackTrace();
		}
	}

	public Dimension calculateMobileDimension() {
		int originalWidth = (int) _imageDimension.getWidth();
		int originalHeight = (int) _imageDimension.getHeight();
		int newHeight = (originalHeight * WIDTH_MOBILE) / originalWidth;
		return new Dimension(WIDTH_MOBILE, newHeight);
	}

	public Dimension calculateDesktopDimension() {
		int originalWidth = (int) _imageDimension.getWidth();
		int originalHeight = (int) _imageDimension.getHeight();
		int newHeight = (originalHeight * WIDTH_DESKTOP) / originalWidth;
		return new Dimension(WIDTH_DESKTOP, newHeight);
	}

	@Override
	public String toString() {
		return "ImageToResize [_file=" + _file.getName() + "]";
	}

	public File getFile() {
		return _file;
	}

	public Dimension getImageDimension() {
		return _imageDimension;
	}
	
	
	

}
