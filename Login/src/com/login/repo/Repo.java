package com.login.repo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileItem;


public class Repo {
	private File rootFolder;
	
	private final String REPO_FOLDER = System.getenv().get("TOMCAT_HOME") + "/webapps/Login/Repo";
	
	private static Repo mySelf;
	
	private Repo(){
		this.rootFolder = new File(this.REPO_FOLDER);
		if(!this.rootFolder.exists())
			this.rootFolder.mkdir();
		
	}
	
	public File[] getFileList(){
		return this.rootFolder.listFiles();
	
	}
	
	public void saveFile(byte[] dataBytes, String filename) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(this.REPO_FOLDER + "/" +filename);
		fileOut.write(dataBytes);
		fileOut.flush();
		fileOut.close();
		
	}
	
	public String seveAFile(FileItem item) throws Exception{
		String fileName = item.getName();
		File uploadedFile = new File(this.REPO_FOLDER+"/"+fileName);
		item.write(uploadedFile);
		
		return fileName;
	}
	
	public static Repo getRepo(){
		if(Repo.mySelf==null)
			Repo.mySelf = new Repo();
		
		return Repo.mySelf;
	}
	
	
}
