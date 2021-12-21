package model;

import org.marvinproject.image.transform.scale.Scale;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.awt.Dimension;

import javax.imageio.ImageIO;

public class ResizeImages {

	private Path _imageDir;
	private HashSet<ImageToResize> _images;
	private Path _desktopDir;
	private Path _mobileDir;
	private String[] _validFormat;

	public ResizeImages(String imageDir) {
		super();
		this._imageDir = Paths.get(imageDir);
		_images = new HashSet<>();
		_validFormat = new String[] { "jpg", "jpeg", "png", "gif" };
		createFolders();
		loadImages();
	}

	public void resizeImages() {
		_images.stream().forEach(image -> {
			try {
				BufferedImage buffImg = ImageIO.read(image.getFile());
				Dimension mobile = image.calculateMobileDimension();
				Dimension desktop = image.calculateDesktopDimension();
				System.out.println();
				resizeImage(buffImg, (int) mobile.getWidth(), (int) mobile.getHeight(), _mobileDir.toString()+"\\"+image.getFile().getName());
				resizeImage(buffImg, (int) desktop.getWidth(), (int) desktop.getHeight(), _desktopDir.toString()+"\\"+image.getFile().getName());
			} catch (IOException e) {
				System.out.println("Fail resize Images " + e);
				e.printStackTrace();
			}
		});
	}

	private void createFolders() {
		
		_mobileDir = Paths.get(_imageDir.toString() + "\\Mobile");
		_desktopDir = Paths.get(_imageDir.toString() + "\\Desktop");
		
		var i = 1;
		while(Files.exists(_mobileDir) || Files.exists(_desktopDir)) {
			_mobileDir = Paths.get(_imageDir.toString() + "\\Mobile"+i);
			_desktopDir = Paths.get(_imageDir.toString() + "\\Desktop"+i);
			i++;
		}
			
		try {
			Files.createDirectory(_desktopDir);
			Files.createDirectory(_mobileDir);

		} catch (IOException e) {
			System.out.println("Fail create folders " + e);
			e.printStackTrace();
		}
	}

	private void loadImages() {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(_imageDir)) {
			stream.forEach(file -> {
				for (String format : _validFormat) {
					if (file.getFileName().toString().toLowerCase().endsWith(format)) {
						_images.add(new ImageToResize(_imageDir.toString() + "\\" + file.getFileName().toString()));
					}
				}
			});

		} catch (IOException e) {
			System.out.println("Fail load images " + e);
			System.err.println(e);
		}
	}

	private void resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight, String savePath) {
		MarvinImage image = new MarvinImage(originalImage);
		Scale scale = new Scale();
		scale.load();
		scale.setAttribute("newWidth", targetWidth);
		scale.setAttribute("newHeight", targetHeight);
		scale.process(image.clone(), image, null, null, false);
		System.out.println(savePath);
		MarvinImageIO.saveImage(image, savePath);
	}
}
