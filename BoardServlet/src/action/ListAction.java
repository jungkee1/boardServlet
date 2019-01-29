package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.BoardVO;

/**
 * Servlet implementation class ListAction
 */
@WebServlet("/board/list")
public class ListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		//resp.setContentType("text/html; charset=utf-8"); //jsp로 내보내서 출력 할때는 거기에 있기 때문에 이것을 안해주어도 된다. 만약 여기에서 바로 출력할려면 이걸 해줘야함 (jsp 파일 생성해보면 제일 첫번째 줄에서 이걸 자동으로 해주기 때문)
		BoardDAO dao = BoardDAO.getInstance();
		String pageNum = req.getParameter("pageNum")==null?"1":req.getParameter("pageNum");
		int currentPage = Integer.parseInt(pageNum);
		int pageSize = 7;
		int startRow = (currentPage-1)*pageSize+1;
		int endRow=currentPage*pageSize;
		ArrayList<BoardVO> arr = dao.boardList(startRow, endRow); //페이징 및 전체보기
		int count = dao.boardCount(); //총 게시글수
		
		//총페이지수
		int totPage = count/pageSize+(count%pageSize==0?0:1);
		int blockPage = 3;
		int startPage = ((currentPage-1)/blockPage)*blockPage+1;
		int endPage = startPage + blockPage-1;
		
		int number = count-(currentPage-1)*pageSize; //페이징 한 후 게시글 번호 (페이징 게시글 갯수와 관련)						
		if(endPage > totPage) endPage = totPage;	//디비 글 번호와 상관없이 글번호 붙음
		
		req.setAttribute("number", number);
		req.setAttribute("totPage", totPage);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("blockPage", blockPage);
		
		req.setAttribute("count", count); //총 게시물 수
		
		req.setAttribute("list", arr); // arr값을 list 변수에 담아서 forward 침
		RequestDispatcher rd = req.getRequestDispatcher("boardList.jsp");
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
