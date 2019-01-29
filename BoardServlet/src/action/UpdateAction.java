package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class UpdateAction
 */
@WebServlet("/board/update")
public class UpdateAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(req.getParameter("num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO vo = dao.boardUpdate(num);
		req.setAttribute("vo", vo);
		resp.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = req.getRequestDispatcher("updateForm.jsp");
		rd.forward(req, resp);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String uploadPath=req.getRealPath("/write"); //�������� �Ǵ� ��� ����
		int size = 10*1024*1024;
		
		MultipartRequest multi = new MultipartRequest(req, uploadPath, size, "UTF-8", new DefaultFileRenamePolicy());
		int num = Integer.parseInt(multi.getParameter("board_num"));
		String board_name = multi.getParameter("writer");
		String board_subject = multi.getParameter("title");
		String oldPassword = multi.getParameter("oldPassword"); //���� ��й�ȣ
		String newPassword = multi.getParameter("newPassword"); 	//�Է��� ��� ����ġ�˻��
		String board_content = multi.getParameter("content");
		String board_file = multi.getFilesystemName("file");
		if(oldPassword.equals(newPassword)) { //��� ��ġ�� �����ϴ� �ܰ�
			BoardVO vo = new BoardVO();
			vo.setBoard_num(num);
			vo.setBoard_name(board_name);
			vo.setBoard_subject(board_subject);
			vo.setBoard_pass(newPassword);
			vo.setBoard_content(board_content);
			vo.setBoard_file(board_file);
			BoardDAO dao = BoardDAO.getInstance();
			dao.boardUpdate2(vo);
			resp.sendRedirect("list");
		}else { //��� Ʋ����
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('��й�ȣ�� ���� �ʽ��ϴ�.');history.go(-1);</script>");
		}
		

		
		
	}

}
