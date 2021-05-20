package com.stage.run.files;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public class UploadFile {
	public static final String allowedExtensions[] = {"jpg","jpeg","gif","png","bmp","tiff","doc","pdf","ppt","zip","rar","jar","xls","pptx","docx","xlsx", "txt"};
	public static final String allowedImgExtensions[] = {"jpg","jpeg","gif","png","bmp","tiff"};
	public static final String uploadDir = "C:\\Users\\LAGRIDA\\eclipse-workspace\\PFAProject\\src\\main\\resources\\static\\files";
	public static final String webUploadDir = "files";
	public UploadFile(){
		
	}
	public void uploadTheFile(MultipartFile fileData, String newFileName, String extension) {
			String fileName = fileData.getOriginalFilename();
				if(fileName != null && fileName.length() > 0) {
					try {
						newFileName = uploadDir + "\\" + newFileName + "." + extension;
						File serverFile = new File(newFileName);
		               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		               stream.write(fileData.getBytes());
		               stream.close();
		            } catch (Exception e) {
		            }
		         }
	}
	public static String generateRandomText() {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	 
	    return generatedString;
	}
	public String getExtension(String fileName) {
		int indexOf = fileName.lastIndexOf(".");
		if(indexOf == -1){
			return "";
		}
		String extension = fileName.substring(indexOf+1);
		return extension;
	}
	public String getOriginalName(String fileName) {
		int indexOf = fileName.lastIndexOf(".");
		if(indexOf == -1){
			return fileName;
		}
		return fileName.substring(0, indexOf);
	}
	public boolean checkExtension(String myExtension) {
		for(String extension : allowedExtensions) {
			if(myExtension.toLowerCase().equals(extension)) {
				return true;
			}
		}
		return false;
	}
}
