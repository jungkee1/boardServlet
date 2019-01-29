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
		//여긴 스크립트릿을 쓰지 않고 하는 모듈2 설계시 착오로 인한 실패한 개발자의 불가피한 서블릿 생성 구간으로
		// 원래는 int 형으로 받아야할 값들을 어차피 처리용 페이지라 String으로 다 처리하였음
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
