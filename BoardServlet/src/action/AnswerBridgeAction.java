package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MiddleBridgeAction
 */
@WebServlet("/board/answerBridge")
public class AnswerBridgeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerBridgeAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//���� ��ũ��Ʈ���� ���� �ʰ� �ϴ� ���2 ����� ������ ���� ������ �������� �Ұ����� ���� ���� ��������
		// ������ int ������ �޾ƾ��� ������ ������ ó���� �������� String���� �� ó���Ͽ���
		req.setCharacterEncoding("utf-8");
		String num = req.getParameter("num");
		String ref = req.getParameter("ref");
		String lev = req.getParameter("lev");
		String step = req.getParameter("step");
		
		req.setAttribute("board_num", num);
		req.setAttribute("board_re_ref", ref);
		req.setAttribute("board_re_lev", lev);
		req.setAttribute("board_re_step", step);
		RequestDispatcher rd = req.getRequestDispatcher("boardAnswer.jsp");
		rd.forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
