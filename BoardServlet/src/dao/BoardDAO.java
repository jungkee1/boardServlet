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
	
	// �θ�� ����
	public void boardWrite(BoardVO vo) {
		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		
				
		try {
			con = getCoonnection();
			/*ps = con.prepareStatement("select max(board_num) from board"); //�θ�� ref
			rs = ps.executeQuery(); //���� ��Ų ������
			if(rs.next()) {
				ref = rs.getInt(1)+1; //���� ��Ų ����� rs.getInt(1) ��  1�߰��ϸ� ref(�θ���� �� �۹�ȣ�� ����)
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
	
	//��۾��� �ڽı�
	public void boardAnswer(BoardVO vo) {
		Connection con = null;
		PreparedStatement ps = null;
		int ref = vo.getBoard_re_ref();
		int lev = vo.getBoard_re_lev();
		int step = vo.getBoard_re_step();
		String sql ="";
		try {
			con = getCoonnection();
			//if(ref!=0) ���⿡ ������ �Ἥ �θ�� �ڽı� �����ؼ� ���� �� �� ������ ���� ���ʿ� ���� �������� ������ ���⋚���� ���⼱ �׳� �ٷ� �ڽı� ���� 
			sql = "update board set re_step = re_step+1 where re_ref=? and re_step > ?";
			ps = con.prepareStatement(sql); //�Խ��� �� ��� (����)���� re_step�� �ø�. ������ �޸��鼭 ��ü �� ���� order by �� ����
			ps.setInt(1, ref);
			ps.setInt(2, step);
			ps.executeQuery(); //������� ������ �۵� re_step�� �ø���
			
			step = step+1; //���� ���� �� �亯���� �θ���� ���� ������� +1 ��Ų�� ref�� +���� �׳� �θ�� ����
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
	
	//��ü����
	public ArrayList<BoardVO> boardList(int startRow, int endRow){
		ArrayList<BoardVO> arr = new ArrayList<BoardVO>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			//��ü����
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
	
	//��ü �Խñ� �� ����
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
	
	//�󼼺���
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
	
	//�����ϱ�1 (�Խñ� ��ü ���� �� �ҷ��� bean�� ����) //����� �ϴ� �� �ҷ������ �ؿ� 2���� ��¥ ������
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
	
	//�����ϱ�2 ��¥����
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
	
	//�����ϱ�
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
				check = 1; //���� �Ϸ�
			}else {
				check = -1; //��й�ȣ Ʋ��
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return check;
	}
	
	//��۴ޱ�
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
	
	//��� ��ü����
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
