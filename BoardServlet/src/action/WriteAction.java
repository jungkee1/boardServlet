package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.ServletContext;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import vo.BoardVO;

/**
 * Servlet implementation class WriteAction
 */
@WebServlet("/board/write.vo")
public class WriteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteAction() {
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
		//System.out.println(req.getContextPath());
		//ServletContext context = req.getServletContext();
		//String uploadPath = context.getRealPath("/write");
		String uploadPath=req.getRealPath("/write");  //�������� �Ǵ� ��� ����
		System.out.println(uploadPath);
		int size = 10*1024*1024;
		
		MultipartRequest multi = new MultipartRequest(req, uploadPath, size, "UTF-8", new DefaultFileRenamePolicy());
		//���⿡�� req�� �� �����Ƿ� req.getParameter���� multi.getParameter�� �ٷ� �ϸ� ���ϱ��� �����ϴϱ� ��!
		String board_name = multi.getParameter("writer");
		String board_pass = multi.getParameter("password");
		String board_subject = multi.getParameter("title");
		String board_content = multi.getParameter("content");
		String board_file = multi.getFilesystemName("file");
		
		BoardVO vo = new BoardVO();
		vo.setBoard_name(board_name);
		vo.setBoard_pass(board_pass);
		vo.setBoard_content(board_content);
		vo.setBoard_subject(board_subject);
		vo.setBoard_file(board_file);
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		BoardDAO dao = BoardDAO.getInstance();
		dao.boardWrite(vo);
		resp.sendRedirect("list");// list�� ����(ListAction.java) ��ü���� 
		
	}

}
