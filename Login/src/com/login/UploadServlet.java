package com.login;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.login.data.UsersDB;
import com.login.repo.Repo;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@SuppressWarnings("rawtypes")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Repo repo;
	private UsersDB db;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        this.repo = Repo.getRepo();
        this.db = UsersDB.getDb();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=null;
				
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				 if (!item.isFormField()) {
					 String fileName = this.repo.seveAFile(item);
					 request.setAttribute("uploadStatus", fileName + " loaded!");
				} else{
					if(item.getFieldName().equals("username"))
							username=item.getString();
				}
			}
		} catch (Exception e) {
			request.setAttribute("uploadStatus", "No file received!");
		}
		
		request.setAttribute("repoFiles", repo.getFileList());
		request.setAttribute("current_user", db.getAnUser(username));
		request.getRequestDispatcher("/profile.jsp").forward(request, response);
	}
	
}
