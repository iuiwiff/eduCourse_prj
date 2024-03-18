package eduCourse_prj.admin.design;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import eduCourse_prj.VO.AdminVO;
import eduCourse_prj.VO.CrsVO;
import eduCourse_prj.admin.dao.AdminDAO;
import eduCourse_prj.admin.event.AdminAdminMgtEvent;
import eduCourse_prj.admin.event.AdminCrsEvent;

@SuppressWarnings("serial")
public class AdminCrsDesign extends JDialog {
	AdminHomeDesign awd;

	JLabel jlBack;// 배경
	private JLabel topLogin; // 우상단 로그인상태 확인창
	private JLabel adminMgt;

	private JTable jtbCrsMgt;
	private DefaultTableModel dtmAdminMgt;

	private JButton jbtnCrsReg, jbtnSlct, jbtnDel;

	private DefaultTableModel dtmCrsMgt;

	public AdminCrsDesign(AdminHomeDesign awd, String title) {
		super(awd, title, true);
		this.awd = awd;
		setLayout(null);
		setSize(1000, 650);

		String commonPath = "C:/dev/workspace/eduCourse_prj/src/eduCourse_prj/image/common/";
		String adminPath = "C:/dev/workspace/eduCourse_prj/src/eduCourse_prj/image/admin/";
		String srsPath = "C:/dev/workspace/eduCourse_prj/src/eduCourse_prj/image/crs/";

		jlBack = new JLabel(new ImageIcon(commonPath + "back.png"));
		jlBack.setBounds(0, 0, 984, 620);
		add(jlBack);

		// 관리자관리, 등록 라벨 추가
		adminMgt = new JLabel(new ImageIcon(adminPath + "adminMgt1.png"));
		adminMgt.setBounds(10, 76, 967, 44);
		add(adminMgt);

		// 우상단 로그인상태 확인창 추가
		topLogin = new JLabel(awd.getlVO().getName() + " 관리자님 로그인 중");
		Font font = new Font("나눔스퀘어라운드 ExtraBold", Font.BOLD, 15);
		topLogin.setFont(font);
		topLogin.setForeground(Color.RED);
		topLogin.setBounds(670, 30, 200, 20);
		add(topLogin);

		// 테이블 추가
		String[] tempColumn = { "학과", "과목" };
		dtmCrsMgt = new DefaultTableModel(tempColumn, 0) {
			public boolean isCellEditable(int row, int column) {
				return false; // 테이블 셀 수정 불가하도록 설정
			} // isCellEditable
		};
		jtbCrsMgt = new JTable(dtmCrsMgt);
		JScrollPane jsp = new JScrollPane(jtbCrsMgt);

		jtbCrsMgt.setRowHeight(30); // 행 높이 조절
		jsp.setBounds(10, 120, 967, 350);
		add(jsp);

		// 과목등록, 조회, 삭제 버튼 추가

		jbtnCrsReg = new JButton(new ImageIcon(srsPath + "CrsReg.png"));
		jbtnSlct = new JButton(new ImageIcon(commonPath + "Slct.png"));
		jbtnDel = new JButton(new ImageIcon(commonPath + "Del.png"));

		jbtnCrsReg.setBounds(150, 500, 168, 59);
		jbtnSlct.setBounds(400, 500, 111, 59);
		jbtnDel.setBounds(700, 500, 111, 59);

		add(jbtnCrsReg);
		add(jbtnSlct);
		add(jbtnDel);

		// 테이블에 DB 추가
		slctCrsMgt();

		// 테이블 컬럼 가운데 정렬
		setTbHorizontal();

		// 이벤트 클래스 연결

		add(jlBack);

		AdminCrsEvent ace = new AdminCrsEvent(this);
		addWindowListener(ace);
		jbtnCrsReg.addActionListener(ace);
		jbtnSlct.addActionListener(ace);
		jbtnDel.addActionListener(ace);
		setLocationRelativeTo(null);

		setVisible(true);
	}

	/**
	 * 
	 * 
	 */
	public void slctCrsMgt() {
		AdminDAO aDAO = AdminDAO.getInstance();
		try {
			List<CrsVO> listCrsVO = aDAO.slctAllCrs();

			for (CrsVO cVO : listCrsVO) {

				Object[] row = { cVO.getDeptName(), cVO.getCourName() };
				dtmCrsMgt.addRow(row);
			} // end for

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	} // slctCrsMgt

	/**
	 * 테이블의 컬럼을 가운데 정렬
	 */
	public void setTbHorizontal() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jtbCrsMgt.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		} // end for
	} // setTbHorizontal

	public AdminHomeDesign getAwd() {
		return awd;
	}

	public JLabel getJlBack() {
		return jlBack;
	}

	public JLabel getTopLogin() {
		return topLogin;
	}

	public JLabel getAdminMgt() {
		return adminMgt;
	}

	public JTable getJtbCrsMgt() {
		return jtbCrsMgt;
	}

	public DefaultTableModel getDtmAdminMgt() {
		return dtmAdminMgt;
	}

	public JButton getJbtnCrsReg() {
		return jbtnCrsReg;
	}

	public JButton getJbtnSlct() {
		return jbtnSlct;
	}

	public JButton getJbtnDel() {
		return jbtnDel;
	}

	public DefaultTableModel getDtmCrsMgt() {
		return dtmCrsMgt;
	}

}