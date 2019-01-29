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
		//resp.setContentType("text/html; charset=utf-8"); //jsp�� �������� ��� �Ҷ��� �ű⿡ �ֱ� ������ �̰��� �����־ �ȴ�. ���� ���⿡�� �ٷ� ����ҷ��� �̰� ������� (jsp ���� �����غ��� ���� ù��° �ٿ��� �̰� �ڵ����� ���ֱ� ����)
		BoardDAO dao = BoardDAO.getInstance();
		String pageNum = req.getParameter("pageNum")==null?"1":req.getParameter("pageNum");
		int currentPage = Integer.parseInt(pageNum);
		int pageSize = 7;
		int startRow = (currentPage-1)*pageSize+1;
		int endRow=currentPage*pageSize;
		ArrayList<BoardVO> arr = dao.boardList(startRow, endRow); //����¡ �� ��ü����
		int count = dao.boardCount(); //�� �Խñۼ�
		
		//����������
		int totPage = count/pageSize+(count%pageSize==0?0:1);
		int blockPage = 3;
		int startPage = ((currentPage-1)/blockPage)*blockPage+1;
		int endPage = startPage + blockPage-1;
		
		int number = count-(currentPage-1)*pageSize; //����¡ �� �� �Խñ� ��ȣ (����¡ �Խñ� ������ ����)						
		if(endPage > totPage) endPage = totPage;	//��� �� ��ȣ�� ������� �۹�ȣ ����
		
		req.setAttribute("number", number);
		req.setAttribute("totPage", totPage);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("blockPage", blockPage);
		
		req.setAttribute("count", count); //�� �Խù� ��
		
		req.setAttribute("list", arr); // arr���� list ������ ��Ƽ� forward ħ
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
