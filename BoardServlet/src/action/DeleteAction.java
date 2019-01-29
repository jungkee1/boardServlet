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
		
		//지우기 직전 비밀번호 화면으로 넘어 갈때(번호를 가지고 간다) 여기 1번 밑에post 2번
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//실제로 삭제 눌렀을때 비밀번호 유효 여부 검사후 삭제 시킨다음에 전체보기로 감
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		String password = request.getParameter("password");
		BoardDAO dao = BoardDAO.getInstance();
		int check = dao.boardDelete(num, password);
		if(check == 1) { //삭제 완료
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('삭제 되었습니다.');</script>");
			//response.sendRedirect("list"); 여기서 이렇게하면 위의 script가 실행이 안되고 바로 이동하니깐
			out.println("<script>location.href = 'list'; </script>");//script써서 보내 줘야함
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('비밀번호를 확인해 주세요');history.go(-1);</script>");
		}
		
	}

}
