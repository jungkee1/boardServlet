package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import vo.BoardVO;

/**
 * Servlet implementation class AnswerAction
 */
@WebServlet("/board/answer")
public class AnswerAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		//답글 달기 (파일도 있으니 multipart 쓸것 넘어 올떄부터 그럼)
		String uploadPath=req.getRealPath("/write");
		int size = 10*1024*1024;
		
		MultipartRequest multi = new MultipartRequest(req, uploadPath, size, "UTF-8", new DefaultFileRenamePolicy());
		int ref = Integer.parseInt(multi.getParameter("ref"));
		int lev = Integer.parseInt(multi.getParameter("lev"));
		int step = Integer.parseInt(multi.getParameter("step"));
		//req.getParameter("board_num"); //num 필요 없음
		String board_name = multi.getParameter("writer");
		String board_subject = multi.getParameter("title");
		String board_pass = multi.getParameter("password");
		String board_content = multi.getParameter("content");
		String board_file = multi.getFilesystemName("file");
		
		BoardVO vo = new BoardVO();
		vo.setBoard_re_ref(ref);
		vo.setBoard_re_lev(lev);
		vo.setBoard_re_step(step);
		vo.setBoard_name(board_name);
		vo.setBoard_subject(board_subject);
		vo.setBoard_pass(board_pass);
		vo.setBoard_content(board_content);
		vo.setBoard_file(board_file);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.boardAnswer(vo);
		resp.sendRedirect("list");
		
		
		
		
	}

}
