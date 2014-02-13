package com.duytsev.userlist;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Application controller
 */
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserBeanDao dao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletController() {
		super();
		dao = new UserBeanDao();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			dao.connectDB();
			if (request.getParameter("action").equalsIgnoreCase("delete")) {
				dao.deleteUser(request.getParameter("userMail"));
			} else if (request.getParameter("action").equalsIgnoreCase("add")) {
				UserBean user = new UserBean();
				user.setFirstName(request.getParameter("f_name"));
				user.setLastName(request.getParameter("l_name"));
				user.setEmail(request.getParameter("email"));
				dao.addUser(user);
			}
			request.setAttribute("userlist", dao.getAllUsers());
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
