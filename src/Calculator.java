import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calculator extends JFrame implements ActionListener, KeyListener{

	private JPanel p_all, p_cal, p_cont; // 객체 선언
	private JButton rem, ce, c, b_space, frac, squa, root, div, seven, eight, nine, mul, four, five,
		    six, minus, one, two, three, plus, sign, zero, point, equal;// 버튼 선언
	
	private JLabel process, result; // 레이블 선언
	
	final int MAX_INPUT_LENGTH = 20; //최대한 입력 가능한 길이를 제한
	
	//각 모드별로 index를 부여
	final int INPUT_MODE = 0;
	final int RESULT_MODE = 1;
	final int ERROR_MODE = 2;
	int displayMode;
	String input;
	 
	boolean clearOnNextDigit;  //화면에 표시될 숫자를 지울지 말지 결정하는 변수
	 
	double lastNumber = 0;   //마지막에 기억될 수
	String lastOperator = "0";  // 마지막에 누른 연산자를 기억.

	
	
	
	public Calculator(String title) {
		
		// 기본 프로그램 설정(제목, 크기, 위치)
		this.setTitle(title);
		this.setSize(350, 450);
		this.setLocation(800, 280);
		
		//종료(EXIT_ON_CLOSE를 사용, 없으면 작업 프로세스에 남아서 메모리만 차지함.)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		/* 1. 배치 관리자 종류
		 * 1) FlowLayout : 왼쪽에서 오른쪽으로 배치, 오른쪽 공간 없으면 아래로 자동 배치
		 *  - 사용 예제 : FlowLatout(FlowLayout.LEFT, 10, 20); // 왼쪽 정렬, 좌우간격 10, 상하간격 20
		 * 2) BorderLayout : 상하좌우 + 중앙 5개의 영역으로 나눠 배치
		 *  - 사용 예제 : BorderLayout(30,20); //좌우간격 30, 상하간격 20
		 * 3) GridLayout : 2차원 그리드로서 n x n 으로 설정, 왼->오, 위->아래 순 배치 
		 *  - 사용 예제 : GridLayout(4,3,5,50); // 4 x 3 그리드, 좌우간격 5, 상하간격 50
		 * 4) CardLayout : 컴포넌트를 포개어 배치
		 * 5) Null : 레이아웃을 쓰지 않고 각 컴포넌트마다 수동으로 위치 설정해줄 때 사용
		 */
		
		//Panel 객체 생성
		p_all = new JPanel();
		p_cal = new JPanel();
		p_cont = new JPanel();
		
		//폰트 생성
		Font bold = new Font("맑은 고딕", Font.BOLD, 24);
		Font cal_bold = new Font("맑은 고딕", Font.BOLD, 18);
		
		//색깔 생성
		Color gray = new Color(204,204,204);
		Color white = new Color(255,255,255);
		Color light_blue = new Color(153,153,255);
		
		//p_all(전체적인 레이아웃) 설정
		this.add(p_all);
		p_all.setLayout(new FlowLayout());
	
		//p_cal 레이아웃
		p_cal.setLayout(new GridLayout(2,1));
		p_cal.setPreferredSize(new Dimension(290,80)); // 레이아웃 크기 설정
		process = new JLabel("",JLabel.RIGHT);
		result = new JLabel("0",JLabel.RIGHT);
		p_cal.add(process);
		p_cal.add(result);
		
		//p_cal 레이블 정렬
		process.setAlignmentX(RIGHT_ALIGNMENT);
		result.setAlignmentX(RIGHT_ALIGNMENT);
		
		//p_cal 폰트 적용
		result.setFont(bold);
		
		//p_cal 색상 적용
		process.setForeground(Color.gray);
		
		//p_cont 레이아웃
		p_cont.setLayout(new GridLayout(6,4));
		p_cont.setPreferredSize(new Dimension(290,300));
		
		//p_cont 1번줄
		rem = new JButton("%");
		rem.addActionListener(this); // 마우스 이벤트에 사용하기 위한 작업
		rem.addKeyListener(this); // 키보드 이벤트에 사용하기 위한 작업
		p_cont.add(rem); 
		ce = new JButton("CE");
		ce.addActionListener(this);
		ce.addKeyListener(this);
		p_cont.add(ce);
		c = new JButton("C");
		c.addActionListener(this);
		c.addKeyListener(this);
		p_cont.add(c);
		b_space = new JButton("←");
		b_space.addActionListener(this);
		b_space.addKeyListener(this);
		p_cont.add(b_space);
		
		//p_cont 2번줄
		frac = new JButton("¹/x");
		frac.addActionListener(this);
		frac.addKeyListener(this);
		p_cont.add(frac);
		squa = new JButton("x²");
		squa.addActionListener(this);
		squa.addKeyListener(this);
		p_cont.add(squa);
		root = new JButton("²√x");
		root.addActionListener(this);
		root.addKeyListener(this);
		p_cont.add(root);
		div = new JButton("÷");
		div.addActionListener(this);
		div.addKeyListener(this);
		p_cont.add(div);
		
		//p_cont 3번줄
		seven = new JButton("7");
		seven.addActionListener(this);
		seven.addKeyListener(this);
		p_cont.add(seven);
		eight = new JButton("8");
		eight.addActionListener(this);
		eight.addKeyListener(this);
		p_cont.add(eight);
		nine = new JButton("9");
		nine.addActionListener(this);
		nine.addKeyListener(this);
		p_cont.add(nine);
		mul = new JButton("×");
		mul.addActionListener(this);
		mul.addKeyListener(this);
		p_cont.add(mul);
		
		//p_cont 4번줄
		four = new JButton("4");
		four.addActionListener(this);
		four.addKeyListener(this);
		p_cont.add(four);
		five = new JButton("5");
		five.addActionListener(this);
		five.addKeyListener(this);
		p_cont.add(five);
		six = new JButton("6");
		six.addActionListener(this);
		six.addKeyListener(this);
		p_cont.add(six);
		minus = new JButton("-");
		minus.addActionListener(this);
		minus.addKeyListener(this);
		p_cont.add(minus);
		
		//p_cont 5번줄
		one = new JButton("1");
		one.addActionListener(this);
		one.addKeyListener(this);
		p_cont.add(one);
		two = new JButton("2");
		two.addActionListener(this);
		two.addKeyListener(this);
		p_cont.add(two);
		three = new JButton("3");
		three.addActionListener(this);
		three.addKeyListener(this);
		p_cont.add(three);
		plus = new JButton("+");
		plus.addActionListener(this);
		plus.addKeyListener(this);
		p_cont.add(plus);
				
		//p_cont 6번줄
		sign = new JButton("±");
		sign.addActionListener(this);
		sign.addKeyListener(this);
		p_cont.add(sign);
		zero = new JButton("0");
		zero.addActionListener(this);
		zero.addKeyListener(this);
		p_cont.add(zero);
		point = new JButton(".");
		point.addActionListener(this);
		point.addKeyListener(this);
		p_cont.add(point);
		equal = new JButton("=");
		equal.addActionListener(this);
		equal.addKeyListener(this);
		p_cont.add(equal);
		
		//p_cont 폰트 사이즈 설정
		zero.setFont(bold);
		one.setFont(bold);
		two.setFont(bold);
		three.setFont(bold);
		four.setFont(bold);
		five.setFont(bold);
		six.setFont(bold);
		seven.setFont(bold);
		eight.setFont(bold);
		nine.setFont(bold);
		rem.setFont(cal_bold);
		ce.setFont(cal_bold);
		c.setFont(cal_bold);
		b_space.setFont(cal_bold);
		frac.setFont(cal_bold);
		squa.setFont(cal_bold);
		root.setFont(cal_bold);
		div.setFont(cal_bold);
		mul.setFont(cal_bold);
		minus.setFont(cal_bold);
		plus.setFont(cal_bold);
		sign.setFont(cal_bold);
		point.setFont(cal_bold);
		equal.setFont(cal_bold);
		
		
		//p_cont 버튼 배경색 설정
		rem.setBackground(gray);
		ce.setBackground(gray);
		c.setBackground(gray);
		b_space.setBackground(gray);
		frac.setBackground(gray);
		squa.setBackground(gray);
		root.setBackground(gray);
		div.setBackground(gray);
		mul.setBackground(gray);
		minus.setBackground(gray);
		plus.setBackground(gray);
		sign.setBackground(gray);
		point.setBackground(gray);
		
		equal.setBackground(light_blue);
		
		zero.setBackground(white);
		one.setBackground(white);
		two.setBackground(white);
		three.setBackground(white);
		four.setBackground(white);
		five.setBackground(white);
		six.setBackground(white);
		seven.setBackground(white);
		eight.setBackground(white);
		nine.setBackground(white);
		
		//내부 레이아웃(계산 레이블, 키 버튼) -> 전체 레이아웃에 추가
		p_all.add(p_cal);
		p_all.add(p_cont);
		
		//창 보이기 설정(true : 보임 , false 안보임)
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// Corine의 계산기 실행
		new Calculator("계산기");
	}
	
	// 마우스 입력에 의한 동작
	public void actionPerformed(ActionEvent e) {
		double result = 0;
		
		// 숫자 1 클릭
		if(e.getSource() == one)
			addToDisplay(1);
		// 숫자 2 클릭
		else if(e.getSource() == two)
			addToDisplay(2);
		// 숫자 3 클릭
		else if(e.getSource() == three)
			addToDisplay(3);
		// 숫자 4 클릭
		else if(e.getSource() == four)
			addToDisplay(4);
		// 숫자 5 클릭
		else if(e.getSource() == five)
			addToDisplay(5);
		// 숫자 6 클릭
		else if(e.getSource() == six)
			addToDisplay(6);
		// 숫자 7 클릭
		else if(e.getSource() == seven)
			addToDisplay(7);
		// 숫자 8 클릭
		else if(e.getSource() == eight)
			addToDisplay(8);
		// 숫자 9 클릭
		else if(e.getSource() == nine)
			addToDisplay(9);
		// 숫자 0 클릭
		else if(e.getSource() == zero)
			addToDisplay(0);
		// ± 클릭
		else if(e.getSource() == sign)
			processSingChange();
		// . 클릭
		else if(e.getSource() == point)
			addPoint();
		// = 클릭
		else if(e.getSource() == equal)
			processEquals();
		// ÷ 클릭
		else if(e.getSource() == div)
			processOperator("/");
		// × 클릭
		else if(e.getSource() == mul)
			processOperator("*");
		// - 클릭
		else if(e.getSource() == minus)
			processOperator("-");
		// + 클릭
		else if(e.getSource() == plus)
			processOperator("+");
		// % 클릭
		else if(e.getSource() == squa)
			processOperator("x²");
		
		else if(e.getSource() == rem)
			processOperator("%");
		// √ 클릭
		else if(e.getSource() == root) {
			// 출력 모드 : 에러가 아닌 경우
			if (displayMode != ERROR_MODE) {
				try{	
                    // result 레이블 첫 글자가 -인 경우
					if (getDisplayString().indexOf("-") == 0)
						displayError("함수에 대한 잘못된 입력!");
					// result 레이블에서 반환한 값 제곱근하여 결과값 저장
					result = Math.sqrt(getNumberInDisplay()); 
					displayResult(result); // 제곱근한 결과값 출력
		        }
		        catch(Exception ex) // 에러 처리
		        {
		        	displayError("함수에 대한 잘못된 입력!");
		        	displayMode = ERROR_MODE; // 출력모드 : 에러 저장
		        }
			}
		// ¹/x 클릭
		} else if(e.getSource() == frac) {
			// 출력 모드 : 에러가 아닌 경우
			if (displayMode != ERROR_MODE){
				try{	
                    // result 레이블에 있는 수가 0인 경우
					if (getNumberInDisplay() == 0)
						displayError("영으로 나눌 수 없습니다.");
		  
					result = 1 / getNumberInDisplay(); // 1/x 계산후 계산 값 저장
					displayResult(result); // 1/x 계산값 출력
		        } catch(Exception ex) { // 에러 처리
		        	displayError("영으로 나눌 수 없습니다.");
		        	displayMode = ERROR_MODE; // 출력모드 : 에러 저장
		        }
			}
		// ← 클릭
		} else if(e.getSource() == b_space)
			backspace();
		// CE 클릭
		else if(e.getSource() == ce) {
			setDisplayString("0"); // result 레이블 0 초기화
			clearOnNextDigit = true; // 숫자 지우기 ON
			displayMode = INPUT_MODE; // 출력 모드 : 입력 저장
		// C 클릭
		} else if(e.getSource() == c)
			clearAll();
			
	}
	
	//키보드 입력에 대한 동작
	public void keyPressed(KeyEvent e){
		
		int keycode = e.getKeyChar();
		//System.out.println(e.getKeyCode()+ "  "+ keycode );
		switch(keycode){
			case KeyEvent.VK_0:
				addToDisplay(0);
				break;
		    case KeyEvent.VK_1:
		    	addToDisplay(1);
		    	break;
		    case KeyEvent.VK_2:
		    	addToDisplay(2);
		    	break;
		    case KeyEvent.VK_3:
		    	addToDisplay(3);
		    	break;
		    case KeyEvent.VK_4:
		    	addToDisplay(4);
		    	break;
		    case KeyEvent.VK_5:
			   addToDisplay(5);
			   break;
		    case KeyEvent.VK_6:
		    	addToDisplay(6);
		    	break;
		    case KeyEvent.VK_7:
		    	addToDisplay(7);
		    	break;
		    case KeyEvent.VK_8:
		    	addToDisplay(8);
		    	break;
		    case KeyEvent.VK_9:
		    	addToDisplay(9);
		    	break;
		    case 46: // .
		    	addPoint();    
		    	break;
		    case 10: // =
		    	processEquals();
		    	break;
		    case 47: // /
		    	processOperator("/");
		    	break;
		    case 42: // *
		    	processOperator("*");
		    	break;
		    case 43: // +
		    	processOperator("+");
		    	break;
		    case 45: // -
		    	processOperator("-");
		    	break;
		    case 8: //backspace
		    	backspace();
		    	break;
		    case 27: //ESC
		    	clearAll();
		    	break;    
		  }
		  
	}
	
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

	// C 처리
	private void clearAll() {
		  setDisplayString("0"); // result 문자열 0으로 초기화
		  lastOperator = "0"; // 마지막 연산 초기화
		  lastNumber = 0; // 마지막 숫자 0 초기화
		  displayMode = INPUT_MODE; // 출력 모드 : 입력 저장
		  clearOnNextDigit = true; // 숫자 지우기 ON
	}
	// ← 처리
	private void backspace(){
		  // 출력 모드 : 에러가 아닌 경우
		  if (displayMode != ERROR_MODE){
          	// result 문자열 마지막 글자 빼고 출력
		  	setDisplayString(getDisplayString().substring(0,getDisplayString().length() - 1)); 
		  	// result 문자열 길이가 1 미만인 경우 무조건 0 출력
		  	if (getDisplayString().length() < 1)
			   setDisplayString("0");
		  }
	}
	
	// 연산 처리(+,-,×,÷,%)
	private void processOperator(String string) {
		// 출력 모드 : 에러가 아닌 경우
		if (displayMode != ERROR_MODE){
			double numberInDisplay = getNumberInDisplay(); // result 출력된 숫자 저장
			
			// 마지막 연산자가 0이 아닌 경우
		    if (!lastOperator.equals("0")){
		    	try {
		    		 double result = processLastOperator(); // 연산 후 반환한 최종 계산값 저장 
		    		 displayResult(result); // result 레이블에 최종 계산값 출력
		    		 lastNumber = result; // 최종 계산값을 마지막 수로 저장
		    	}catch(Exception e){
		    		 e.getStackTrace(); // 예외 발생 시 처리
		    	}
		    	// 마지막 연산자가 0인 경우
		    }else{
            		// result 출력된 숫자를 마지막 기억된 수에 저장
		    		lastNumber = numberInDisplay; 
		    }
		   
		    clearOnNextDigit = true; // 숫자 지우기 ON
		    lastOperator = string; // 연산자를 마지막 연산자로 저장
		}
	}
	
	// 마지막 연산 처리(에러 발생 시 이 메소드를 사용한 메소드에서 처리)
	private double processLastOperator() throws Exception{
		double result = 0; // 최종 계산 값을 담을 변수
		double numberInDisplay = getNumberInDisplay(); // result 출력된 숫자 저장(두번째 피연산자)
		// 마지막 연산자가 ÷인 경우
		if (lastOperator.equals("/")){
			// result 출력된 숫자가 0인 경우 예외 처리
			if (numberInDisplay == 0)
				throw (new Exception());
			result = lastNumber / numberInDisplay; // 나눗셈한 결과 값 저장
		}
		// 마지막 연산자가 ×인 경우
		if (lastOperator.equals("*")){
			result = lastNumber * numberInDisplay; // 곱셈 후 몫 결과 값 저장
		}
		// 마지막 연산자가 -인 경우
		if (lastOperator.equals("-")){
			result = lastNumber - numberInDisplay; // 뺄셈한 결과 값 저장
		}
		// 마지막 연산자가 +인 경우
		if (lastOperator.equals("+")){
			result = lastNumber + numberInDisplay; // 덧셈한 결과 값 저장
		}
		// 마지막 연산자가 %인 경우
		if (lastOperator.equals("%")){
			result = lastNumber % numberInDisplay; // 곱셈 후 나머지 결과 값 저장
		}
		if (lastOperator.equals("x²")){
			result = lastNumber * lastNumber; // 제곱 후 나머지 결과 값 저장
		}
  
		return result; // 결과 값 반환
	}

	// = 처리
	private void processEquals() {
		
		double result = 0;
		// 출력 모드 : 에러가 아닌 경우
		if (displayMode != ERROR_MODE){
			try{
				result = processLastOperator(); // 연산 후 반환한 최종 계산값 저장 
				displayResult(result); // result 레이블에 최종 계산값 출력
			}catch (Exception e){
				displayError("영으로 나눌 수 없습니다."); // 에러 처리
			}
			lastOperator = "0"; // 계산 처리 후 마지막 연산자 초기화
		}
	}
	// 소수점 처리
	private void addPoint() {
		displayMode = INPUT_MODE; // 출력모드 : 입력 저장
		// 지워야 하는 숫자인 경우
		if (clearOnNextDigit)
			setDisplayString(""); // result 레이블 문자열 초기화 
		// 기존 화면에 있는 문자열 저장
		String inputString = getDisplayString();
		// 이미 점이 찍힌 경우 반복해서 찍지 않음.
		if (inputString.indexOf(".") < 0)
			setDisplayString(new String(inputString + "."));
		}
	
	// ± 처리
	private void processSingChange() {
		// 출력모드 : 입력인 경우
		if (displayMode == INPUT_MODE){
			input = getDisplayString(); // 기존 화면에 있는 문자열 저장
			// 기존 화면에 있는 문자열의 길이가 0보다 크고 0이 아닌 경우
			if (input.length() > 0 && !input.equals("0")){
				if (input.indexOf("-") == 0) // 기존 화면에 있는 문자열 첫번째 문자가 -인 경우
					setDisplayString(input.substring(1)); // 첫번째 문자 - 빼고 출력
				else // 기존 화면에 있는 문자열 첫번째 문자가 +인 경우
					setDisplayString("-" + input); // 문자열 첫번째 문자에 - 추가
			}
		// 출력모드 : 결과인 경우(계산이 끝난 상태)
		}else if (displayMode == RESULT_MODE){
			double numberInDisplay = getNumberInDisplay(); // result 출력된 숫자 저장
			// result 출력된 문자열이 0이 아닌경우
			if (numberInDisplay != 0)
				displayResult(-numberInDisplay); // 기존 값 부호 변경
		}
	}
	
	// result 부호 변경 메소드
	private void displayResult(double result) {
		setDisplayString(Double.toString(result)); // result 숫자 -> 문자열 형으로 변환하여 result 레이블 위에 출력
		lastNumber = result; // result를 마지막 숫자로 저장
		displayMode = RESULT_MODE; // 출력모드 : 결과
		clearOnNextDigit = true; // 숫자 지우기 ON
	}
	
	// 에러 출력 메소드
	private void displayError(String error) {
		setDisplayString(error); // result 에러 출력
		lastNumber = 0; // 마지막 수 0 초기화
		displayMode = ERROR_MODE; // 출력모드 : 오류
		clearOnNextDigit = true; // 숫자 지우기 ON
	}
	
	// result 레이블에 출력된 숫자 형변환하여 가져오기
	private double getNumberInDisplay() {
		String input = result.getText(); // result의 문자열 저장
		return Double.parseDouble(input); // result의 문자열을 Double형으로 형변환 후 반환
	}

	// 화면에 추가하는 메소드
	private void addToDisplay(int i) {
		// 지워야 하는 숫자인 경우
		if (clearOnNextDigit)
			// result 레이블 문자열 초기화
			setDisplayString("");
		
		// 기존 화면에 있는 문자열 저장
		String inputString = getDisplayString();
		
		// result 레이블에 있는 문자열 첫 글자가 0인 경우 0삭제
		if(inputString.indexOf("0") == 0){
			inputString = inputString.substring(1);
		}
	
        // result 레이블에 있는 문자열이 0이 아니거나 입력받은 숫자가 0보다 크고
        // result 문자열 길이가 최대길이(20) 미만인 경우
		if((!inputString.equals("0") || i>0 ) && inputString.length() < MAX_INPUT_LENGTH){
			// result 레이블 + 입력받은 숫자
			setDisplayString(inputString + i);
		}
		  
		displayMode = INPUT_MODE; // 출력모드 : 입력
		clearOnNextDigit = false; // 숫자 지우기 OFF
		
	}

	//result 레이블에 문자열 넣어주는 메소드
	private void setDisplayString(String string) {
		result.setText(string);
	}

	//result 레이블에 있는 문자열 가져오는 메소드
	private String getDisplayString() {
		return result.getText();
	}
	
	
	
	
	
}