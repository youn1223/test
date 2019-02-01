package album.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import utility.Paging;

@Component("myAlbumDao")
public class AlbumDao {

	// 주의 : 부모 생성자 호출시 첫번째 매개 변수인 맵 설정 파일의 경로를 명시할 때
	// 앞에 슬래시 붙이지 말고 패키지 경로 쭉~~~ 파일 이름 명시하도록 한다.
	private final String namespace = "album.AlbumBean";

	// namespace는 매퍼파일에 들어있는 namespace이다.

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	// root-context.xml에서 만든 SqlSessionTemplate객체가 
	// 윗줄에서 @Autowired된다.
	/*
	SqlSessionTemplate은 마이바티스 스프링 연동모듈의 핵심이다. 
	SqlSessionTemplate은 SqlSession을 구현하고 코드에서 SqlSession를 대체하는 역할을 한다.
	console에서 실행할 때에는 SqlSession 객체를 이용했었다.
	SqlSessionTemplate 은 SqlSession을 구현하고 있다.
	SqlSessionTemplate 은 여러개의 인젝트 되어진 맵퍼 클래스에 의해 사용될때 쓰레드 안전하다.
	동일 어플리케이션에서 이 두가지 클래스(DefaultSqlSession 과 SqlSessionTemplate)를 혼용하는것은 데이터 무결성 문제를 야기할수 있다.
	*/
	
	public AlbumDao(){
		System.out.println("AlbumDao()");
	}
/*
	public AlbumBean select_one(Integer prodid) {
		AlbumBean bean = null;
		bean = sqlSessionTemplate.selectOne(namespace + ".GetMemberByName",
				prodid);
		return bean;
	}
*/
	public int GetTotalCount(Map<String, String> map) {
		// TODO Auto-generated method stub
		int cnt = -1;
		cnt = sqlSessionTemplate.selectOne(namespace+".GetTotalCount",map);
		System.out.println("cnt:"+cnt); // 레코드가 없으면 0나옴, null이 아님
		return cnt;
	}
	
	//Model이 넘어온다면 위 메서드대신 아래 메서드도 된다.
	/*
	public int GetTotalCount(Model map) {
		// TODO Auto-generated method stub
		int cnt = -1;
		cnt = sqlSessionTemplate.selectOne(namespace+".GetTotalCount",map);
		System.out.println("cnt:"+cnt); // 레코드가 없으면 0나옴, null이 아님
		return cnt;
	}
	*/

	public List<AlbumBean> GetAlbumList(Paging pageInfo, Map<String, String> map) {
		// TODO Auto-generated method stub
		List<AlbumBean> lists = new ArrayList<AlbumBean>();
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit() );
		//offset : 0, limit : 2
		// 아래 GetAlbumList 에서 설정한 대로 num을 기준으로 내림차순한 상태에서 2개를 의미하는 듯함(맞는듯함)
		lists = sqlSessionTemplate.selectList(namespace + ".GetAlbumList", map, rowBounds);
		System.out.println("lists.size() : " +lists.size()); // 10개(limit로 설정된값-전체 갯수가 나오는것이 아님)
		// lists.size()는 현재 페이지에 나타날 레코드 갯수(2개 일수도 있고, 맨 끝 페이지면 1개 일수도 있고..)
		return lists; //AlbumListController.java로 리턴된다. 
		/*RowBounds 파라미터는
		MyBatis로 하여금 특정 개수 만큼의 레코드를 건너 띄게 한다. */

	}

	public int InsertAlbum(AlbumBean album) {
		int cnt=-1;
		cnt = sqlSessionTemplate.insert(namespace+".InsertAlbum",album);
		return cnt;
	}

	public AlbumBean GetAlbum(int num) {
		AlbumBean album = null;
		album = sqlSessionTemplate.selectOne(namespace+".GetAlbum",num);
		return album;
	}

	public int DeleteAlbum(int num) {
		int cnt = -1;
		cnt = sqlSessionTemplate.delete(namespace+".DeleteAlbum",num);
		System.out.println("삭제된 갯수 cnt : "+cnt);
		return cnt;
	}
   
	public int updateAlbum(AlbumBean album) {
		Integer cnt = -1;
		cnt = sqlSessionTemplate.insert(namespace + ".UpdateAlbum", album);
		return cnt;
	}

}


