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
		//여기서 위에 들어온 arr을 JSON 으로 돌릴거임 이러헥 하거나 jsp로 가서 jstl 사용하면 foreach 해도됨
		JSONArray jarr = new JSONArray(); //json-simple jar lib에넣고 import 가능
		//위에 만든 jarr은 여러개 값을 담을 수 있으니 위에서 한번만 생성하고 밑에서 반복문에서 그냥 반복적으로 넣기만 하면됨
		for(CommentVO vo : arr) { //for-each 구문은 넣은 순서대로 빼기때문에 그 떄 유용한 반복문
			JSONObject obj = new JSONObject(); 
			obj.put("cNum", vo.getcNum());
			obj.put("bNum", vo.getbNum());
			obj.put("cName", vo.getcName());
			obj.put("cMsg", vo.getcMsg());
			obj.put("cDate", vo.getcDate()+""); //DATE 타입은 JSON은 모른다.. String이랑 int만 안다
			jarr.add(obj); //여기서 계속적으로 담는중 (위에서 생성 1번만 한 객체에)
		}
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();	 //jsp 파일에서는 바로 out 내보낼 수 있지만 java에서는 이거 해서 사용가능
		out.println(jarr.toString()); //triger 콜백으로 돌아감
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
