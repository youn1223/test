package album.model;

import javax.validation.constraints.Min; // pom.xml에 추가해야 import도 할 수 있다. 
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
/*
pom.xml에 
hibernate-validator 가 있어야 
위의 import 할 수 있다.
*/
import org.hibernate.validator.constraints.NotEmpty;

public class AlbumBean {
	
	//bean의 변수 이름은 DB 테이블의 칼럼명과 같아야 한다.같지 않으면 에러난다. 
	private int num;
	
	@NotEmpty(message="제목 누락@NotEmpty") // 아무것도 입력안하면 에러, 스페이스입력하면 정상입력
	//@NotNull(message="NotNull")// 아무것도 입력안해도 정상, 스페이스입력해도 정상입력=>그럼 언제 에러체크하지?? type=text일 때에는 못쓰는건가?? 
	//@NotBlank(message="@NotBlank")// 아무것도 입력안하면 에러, 스페이스입력해도 에러
	private String title;
	
	@Length(min=3, max=7, message="가수 이름은 최소 3자리 최대 7자리 입니다.")
	private String singer;
	
	// @NotEmpty(message="가격 누락")
	@Min(value=1000, message="가격은 최소 1000원 이상 이여야 합니다")
	// private int price; // int로 하면 유효성 검사 할수가 없어서(에러남) String으로 바꾸었음
	private String price;
	
	//위에서 int price는 @NotEmpty 쓰면 에러난다.
	//price를 String으로 해 놓으면 @Min 하나로 최소값체크와 누락체크 같이할수 있다.
	
	// 가격 체크 아래 3줄 쓰면 누락됐을 때 아래 3가지 메세지가 모두 나온다. 
	// 위의 코드를 쓰는게 낫겠다.
	//@NotEmpty(message="가격 누락")
	//@Length(min=4,message="가격은 최소 1000원 이상 이어야 합니다")
	//@Pattern(regexp="^[0-9]+$", message="price는 숫자로 입력하세요") 
	
	
	private String day;
	
	
	public AlbumBean() {
		super();
	}
	
	public AlbumBean(int num, String title, String singer, String price, String day) {
		super();
		this.num = num;
		this.title = title;
		this.singer = singer;
		this.price = price;
		this.day = day;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	@Override
	public String toString() {
		return "AlbumBean [num=" + num + ", title=" + title + ", singer="
				+ singer + ", price=" + price + ", day=" + day + "]";
	}
	
	
}
