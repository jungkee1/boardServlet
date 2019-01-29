package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.BoardDAO;
import vo.CommentVO;

/**
 * Servlet implementation class CommentListAction
 */
@WebServlet("/board/commentList")
public class CommentListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentListAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		int bNum = Integer.parseInt(req.getParameter("bNum"));
		BoardDAO dao = BoardDAO.getInstance();
		ArrayList<CommentVO> arr = dao.commentList(bNum);
		//���⼭ ���� ���� arr�� JSON ���� �������� �̷��� �ϰų� jsp�� ���� jstl ����ϸ� foreach �ص���
		JSONArray jarr = new JSONArray(); //json-simple jar lib���ְ� import ����
		//���� ���� jarr�� ������ ���� ���� �� ������ ������ �ѹ��� �����ϰ� �ؿ��� �ݺ������� �׳� �ݺ������� �ֱ⸸ �ϸ��
		for(CommentVO vo : arr) { //for-each ������ ���� ������� ���⶧���� �� �� ������ �ݺ���
			JSONObject obj = new JSONObject(); 
			obj.put("cNum", vo.getcNum());
			obj.put("bNum", vo.getbNum());
			obj.put("cName", vo.getcName());
			obj.put("cMsg", vo.getcMsg());
			obj.put("cDate", vo.getcDate()+""); //DATE Ÿ���� JSON�� �𸥴�.. String�̶� int�� �ȴ�
			jarr.add(obj); //���⼭ ��������� ����� (������ ���� 1���� �� ��ü��)
		}
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();	 //jsp ���Ͽ����� �ٷ� out ������ �� ������ java������ �̰� �ؼ� ��밡��
		out.println(jarr.toString()); //triger �ݹ����� ���ư�
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
