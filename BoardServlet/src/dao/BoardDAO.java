package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.BoardVO;
import vo.CommentVO;

public class BoardDAO {
	private static BoardDAO instance;
	public static BoardDAO getInstance() {
		if(instance==null) {
			instance = new BoardDAO();
		}
		return instance;
	}
	
	private Connection getCoonnection() throws Exception{
		Context initCtx = new InitialContext();
		Context envCtx = (Context)initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/board");
		return ds.getConnection();
	}
	
	// 부모글 쓰기
	public void boardWrite(BoardVO vo) {
		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		
				
		try {
			con = getCoonnection();
			/*ps = con.prepareStatement("select max(board_num) from board"); //부모글 ref
			rs = ps.executeQuery(); //실행 시킨 다음에
			if(rs.next()) {
				ref = rs.getInt(1)+1; //실행 시킨 결과값 rs.getInt(1) 에  1추가하면 ref(부모글의 새 글번호와 동일)
			}*/
 			String sql = "insert into board values(board_seq.nextval,?,?,?,?,?,board_seq.nextval,0,0,0,sysdate)";
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getBoard_name());
			ps.setString(2, vo.getBoard_pass());
			ps.setString(3, vo.getBoard_subject());
			ps.setString(4, vo.getBoard_content());
			ps.setString(5, vo.getBoard_file());
		
			ps.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeOn(con, ps, st, rs);
		}
	}
	
	//답글쓰기 자식글
	public void boardAnswer(BoardVO vo) {
		Connection con = null;
		PreparedStatement ps = null;
		int ref = vo.getBoard_re_ref();
		int lev = vo.getBoard_re_lev();
		int step = vo.getBoard_re_step();
		String sql ="";
		try {
			con = getCoonnection();
			//if(ref!=0) 여기에 조문을 써서 부모글 자식글 구분해서 진행 할 수 있지만 나는 애초에 둘을 서블릿에서 나눠서 오기떄문에 여기선 그냥 바로 자식글 진행 
			sql = "update board set re_step = re_step+1 where re_ref=? and re_step > ?";
			ps = con.prepareStatement(sql); //게시판 내 모든 (기존)글의 re_step을 올림. 새글이 달리면서 전체 글 보기 order by 를 위해
			ps.setInt(1, ref);
			ps.setInt(2, step);
			ps.executeQuery(); //여기까진 기존의 글들 re_step값 올리고
			
			step = step+1; //여긴 지금 들어갈 답변글은 부모글의 값을 따라오니 +1 시킨다 ref는 +없이 그냥 부모글 따름
			lev = lev+1;
			
			sql = "insert into board values(board_seq.nextval,?,?,?,?,?,?,?,?,0,sysdate)";
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getBoard_name());
			ps.setString(2, vo.getBoard_pass());
			ps.setString(3, vo.getBoard_subject());
			ps.setString(4, vo.getBoard_content());
			ps.setString(5, vo.getBoard_file());
			ps.setInt(6, ref);
			ps.setInt(7, lev);
			ps.setInt(8, step);
			ps.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	//전체보기
	public ArrayList<BoardVO> boardList(int startRow, int endRow){
		ArrayList<BoardVO> arr = new ArrayList<BoardVO>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			//전체보기
			con = getCoonnection();
			sql = "select * from(select rownum rn, aa.* from "
			 		+ "(select * from board order by re_ref desc, re_step asc)aa)"
			 		+ "where rn>=? and rn<=?";
			ps = con.prepareCall(sql);
			ps.setInt(1, startRow);
			ps.setInt(2, endRow);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setBoard_num(rs.getInt("board_num"));
				vo.setBoard_name(rs.getString("board_name"));
				vo.setBoard_pass(rs.getString("board_pass"));
				vo.setBoard_subject(rs.getString("board_subject"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setBoard_file(rs.getString("board_file"));
				vo.setBoard_re_ref(rs.getInt("re_ref"));
				vo.setBoard_re_lev(rs.getInt("re_lev"));
				vo.setBoard_re_step(rs.getInt("re_step"));
				vo.setBoard_readcount(rs.getInt("board_readcount"));
				vo.setBoard_date(rs.getDate("board_date"));
				arr.add(vo);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeOn(con, null, st, rs);
		}
		return arr;
	}
	
	//전체 게시글 수 보기
	public int boardCount() {
		int count = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getCoonnection();
			String sql = "select count(*) from board";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	//상세보기
	public BoardVO boardView(int num) {
		BoardVO vo = new BoardVO();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getCoonnection();
			String sql = "select * from board where board_num =" + num;
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				vo.setBoard_num(rs.getInt("board_num"));
				vo.setBoard_name(rs.getString("board_name"));
				vo.setBoard_pass(rs.getString("board_pass"));
				vo.setBoard_subject(rs.getString("board_subject"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setBoard_file(rs.getString("board_file"));
				vo.setBoard_re_ref(rs.getInt("re_ref"));
				vo.setBoard_re_lev(rs.getInt("re_lev"));
				vo.setBoard_re_step(rs.getInt("re_step"));
				vo.setBoard_readcount(rs.getInt("board_readcount"));
				vo.setBoard_date(rs.getDate("board_date"));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeOn(con, null, st, rs);
		}
		
		return vo;
	}
	
	//수정하기1 (게시글 전체 정보 다 불러다 bean에 담음) //여기는 일단 글 불러오기용 밑에 2에서 진짜 수정함
	public BoardVO boardUpdate(int num) {
		BoardVO vo = new BoardVO();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getCoonnection();
			String sql = "select * from board where board_num= " +num;
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				vo.setBoard_num(rs.getInt("board_num"));
				vo.setBoard_name(rs.getString("board_name"));
				vo.setBoard_pass(rs.getString("board_pass"));
				vo.setBoard_subject(rs.getString("board_subject"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setBoard_file(rs.getString("board_file"));
				vo.setBoard_re_ref(rs.getInt("re_ref"));
				vo.setBoard_re_lev(rs.getInt("re_lev"));
				vo.setBoard_re_step(rs.getInt("re_step"));
				vo.setBoard_readcount(rs.getInt("board_readcount"));
				vo.setBoard_date(rs.getDate("board_date"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeOn(con, null, st, rs);
		}
		
		return vo;
	}
	
	//수정하기2 진짜수정
	public void boardUpdate2(BoardVO vo) {
		Connection con  =null;
		PreparedStatement ps = null;
		try {
			con = getCoonnection();
			String sql = "update board set board_pass=?, board_subject=?, board_content=?, board_file=?, board_date=SYSDATE where board_num = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getBoard_pass());
			ps.setString(2, vo.getBoard_subject());
			ps.setString(3, vo.getBoard_content());
			ps.setString(4, vo.getBoard_file());
			ps.setInt(5, vo.getBoard_num());
			ps.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeOn(con, ps, null, null);
		}
		
		
		
		
	}
	
	//삭제하기
	public int boardDelete(int num, String password) {
		int check = 0 ;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = getCoonnection();
			sql = "select * from board where board_pass = '" +password + "' and board_num = " +num;
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				sql = "delete from board where board_num = " +num;
				st = con.createStatement();
				st.executeQuery(sql);
				check = 1; //삭제 완료
			}else {
				check = -1; //비밀번호 틀림
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return check;
	}
	
	//댓글달기
	public void commentInsert(int bNum, String msg, String cName) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getCoonnection();
			String sql = "insert into comment_board values(comment_board_seq.nextval,?,?,?,sysdate)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, bNum);
			ps.setString(2, cName);
			ps.setString(3, msg);
			ps.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//댓글 전체보기
	public ArrayList<CommentVO> commentList(int bNum){
		ArrayList<CommentVO> arr = new ArrayList<CommentVO>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getCoonnection();
			String sql = "select * from comment_board where bnum= " +bNum + " order by cnum desc";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				CommentVO vo = new CommentVO();
				vo.setcNum(rs.getInt("cnum"));
				vo.setbNum(rs.getInt("bnum"));
				vo.setcName(rs.getString("cname"));
				vo.setcMsg(rs.getString("cmsg"));
				vo.setcDate(rs.getDate("cdate"));
				arr.add(vo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	
	public void closeOn(Connection con, PreparedStatement ps, Statement st, ResultSet rs) {
		
			try {
				if(con!=null)con.close();
				if(ps!=null)ps.close();
				if(st!=null)st.close();
				if(rs!=null)rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
