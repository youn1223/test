package album.model;

import javax.validation.constraints.Min; // pom.xml�� �߰��ؾ� import�� �� �� �ִ�. 
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
/*
pom.xml�� 
hibernate-validator �� �־�� 
���� import �� �� �ִ�.
*/
import org.hibernate.validator.constraints.NotEmpty;

public class AlbumBean {
	
	//bean�� ���� �̸��� DB ���̺��� Į����� ���ƾ� �Ѵ�.���� ������ ��������. 
	private int num;
	
	@NotEmpty(message="���� ����@NotEmpty") // �ƹ��͵� �Է¾��ϸ� ����, �����̽��Է��ϸ� �����Է�
	//@NotNull(message="NotNull")// �ƹ��͵� �Է¾��ص� ����, �����̽��Է��ص� �����Է�=>�׷� ���� ����üũ����?? type=text�� ������ �����°ǰ�?? 
	//@NotBlank(message="@NotBlank")// �ƹ��͵� �Է¾��ϸ� ����, �����̽��Է��ص� ����
	private String title;
	
	@Length(min=3, max=7, message="���� �̸��� �ּ� 3�ڸ� �ִ� 7�ڸ� �Դϴ�.")
	private String singer;
	
	// @NotEmpty(message="���� ����")
	@Min(value=1000, message="������ �ּ� 1000�� �̻� �̿��� �մϴ�")
	// private int price; // int�� �ϸ� ��ȿ�� �˻� �Ҽ��� ���(������) String���� �ٲپ���
	private String price;
	
	//������ int price�� @NotEmpty ���� ��������.
	//price�� String���� �� ������ @Min �ϳ��� �ּҰ�üũ�� ����üũ �����Ҽ� �ִ�.
	
	// ���� üũ �Ʒ� 3�� ���� �������� �� �Ʒ� 3���� �޼����� ��� ���´�. 
	// ���� �ڵ带 ���°� ���ڴ�.
	//@NotEmpty(message="���� ����")
	//@Length(min=4,message="������ �ּ� 1000�� �̻� �̾�� �մϴ�")
	//@Pattern(regexp="^[0-9]+$", message="price�� ���ڷ� �Է��ϼ���") 
	
	
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
