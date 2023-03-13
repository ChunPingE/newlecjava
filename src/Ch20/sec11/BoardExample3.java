package Ch20.sec11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BoardExample3 {
	// 필드
	private Scanner sc = new Scanner(System.in);
	private Connection conn;

	// 생성자
	public BoardExample3() {
		try {
			// JDBC Driver등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Connection객체 생성
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/thisisjava",
					"chun",
					"1234");
			System.out.println("연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}
	}

	public void list() {
		System.out.println();
		System.out.println("[게시물 목록]");
		System.out.println("-------------------------------------------------------");
		System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "date", "title");
		System.out.println("-------------------------------------------------------");

		try {
			String sql = "SELECT bno, bwriter, bdate, btitle FROM boards ORDER BY bno DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setBno(rs.getInt("bno"));
				board.setBtitle(rs.getString("btitle"));
				board.setBwriter(rs.getString("bwriter"));
				board.setBdate(rs.getDate("bdate"));

				System.out.printf("%-6s%-12s%-16s%-40s\n",
						board.getBno(),
						board.getBwriter(),
						board.getBdate(),
						board.getBtitle());
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			exit();
		}

		// 메인메뉴 출력
		mainMenu();
	}

	public void mainMenu() {
		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
		System.out.print("메뉴 선택: ");
		int menuNo = Integer.parseInt(sc.nextLine());
		System.out.println();

		switch (menuNo) {
		case 1:
			create();
			break;
		case 2:
			read();
			break;
		case 3:
			clear();
			break;
		case 4:
			exit();
			break;
		}
	}

	public int subMenu() {
		System.out.println("-------------------------------------------------------");
		System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
		System.out.print("메뉴 선택: ");
		int menuNo = Integer.parseInt(sc.nextLine());
		return menuNo;
	}

	public void create() {
		// 입력받기
		Board board = new Board();
		System.out.println("[새 게시물 입력]");
		System.out.print("제목: ");
		board.setBtitle(sc.nextLine());
		System.out.print("내용: ");
		board.setBcontent(sc.nextLine());
		System.out.print("작성자: ");
		board.setBwriter(sc.nextLine());
		
		//보조메뉴출력
		int menuNo = subMenu();
		
		if(menuNo == 1) {
			try {
				String sql = "INSERT INTO boards(btitle, bcontent, bwriter, bdate) "
						+ "VALUES (?,?,?,now())";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getBtitle());
				pstmt.setString(2, board.getBcontent());
				pstmt.setString(3, board.getBwriter());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				exit();
			}
		} else {
			list();
		}
	}

	public void read() {
		System.out.println("read메소드 실행");
		list();
	}

	public void clear() {
		System.out.println("clear메소드 실행");
		list();
	}

	public void exit() {
		System.out.println("프로그램 종료");
		System.exit(0);
	}

	public static void main(String[] args) {
		BoardExample3 bd = new BoardExample3();
		bd.list();
	}
}
