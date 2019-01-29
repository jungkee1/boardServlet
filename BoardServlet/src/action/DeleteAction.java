package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;

/**
 * Servlet implementation class DeleteAction
 */
@WebServlet("/board/delete")
public class DeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		//resp.setContentType("text/html; charset=utf-8");
		int num = Integer.parseInt(req.getParameter("num"));
		req.setAttribute("num", num);
		RequestDispatcher rd = req.getRequestDispatcher("deleteForm.jsp");
		rd.forward(req, resp);
		
		//����� ���� ��й�ȣ ȭ������ �Ѿ� ����(��ȣ�� ������ ����) ���� 1�� �ؿ�post 2��
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//������ ���� �������� ��й�ȣ ��ȿ ���� �˻��� ���� ��Ų������ ��ü����� ��
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		String password = request.getParameter("password");
		BoardDAO dao = BoardDAO.getInstance();
		int check = dao.boardDelete(num, password);
		if(check == 1) { //���� �Ϸ�
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('���� �Ǿ����ϴ�.');</script>");
			//response.sendRedirect("list"); ���⼭ �̷����ϸ� ���� script�� ������ �ȵǰ� �ٷ� �̵��ϴϱ�
			out.println("<script>location.href = 'list'; </script>");//script�Ἥ ���� �����
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('��й�ȣ�� Ȯ���� �ּ���');history.go(-1);</script>");
		}
		
	}

}
