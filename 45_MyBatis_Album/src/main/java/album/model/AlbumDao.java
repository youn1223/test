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

	// ���� : �θ� ������ ȣ��� ù��° �Ű� ������ �� ���� ������ ��θ� ����� ��
	// �տ� ������ ������ ���� ��Ű�� ��� ��~~~ ���� �̸� ����ϵ��� �Ѵ�.
	private final String namespace = "album.AlbumBean";

	// namespace�� �������Ͽ� ����ִ� namespace�̴�.

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	// root-context.xml���� ���� SqlSessionTemplate��ü�� 
	// ���ٿ��� @Autowired�ȴ�.
	/*
	SqlSessionTemplate�� ���̹�Ƽ�� ������ ��������� �ٽ��̴�. 
	SqlSessionTemplate�� SqlSession�� �����ϰ� �ڵ忡�� SqlSession�� ��ü�ϴ� ������ �Ѵ�.
	console���� ������ ������ SqlSession ��ü�� �̿��߾���.
	SqlSessionTemplate �� SqlSession�� �����ϰ� �ִ�.
	SqlSessionTemplate �� �������� ����Ʈ �Ǿ��� ���� Ŭ������ ���� ���ɶ� ������ �����ϴ�.
	���� ���ø����̼ǿ��� �� �ΰ��� Ŭ����(DefaultSqlSession �� SqlSessionTemplate)�� ȥ���ϴ°��� ������ ���Ἲ ������ �߱��Ҽ� �ִ�.
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
		System.out.println("cnt:"+cnt); // ���ڵ尡 ������ 0����, null�� �ƴ�
		return cnt;
	}
	
	//Model�� �Ѿ�´ٸ� �� �޼����� �Ʒ� �޼��嵵 �ȴ�.
	/*
	public int GetTotalCount(Model map) {
		// TODO Auto-generated method stub
		int cnt = -1;
		cnt = sqlSessionTemplate.selectOne(namespace+".GetTotalCount",map);
		System.out.println("cnt:"+cnt); // ���ڵ尡 ������ 0����, null�� �ƴ�
		return cnt;
	}
	*/

	public List<AlbumBean> GetAlbumList(Paging pageInfo, Map<String, String> map) {
		// TODO Auto-generated method stub
		List<AlbumBean> lists = new ArrayList<AlbumBean>();
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit() );
		//offset : 0, limit : 2
		// �Ʒ� GetAlbumList ���� ������ ��� num�� �������� ���������� ���¿��� 2���� �ǹ��ϴ� ����(�´µ���)
		lists = sqlSessionTemplate.selectList(namespace + ".GetAlbumList", map, rowBounds);
		System.out.println("lists.size() : " +lists.size()); // 10��(limit�� �����Ȱ�-��ü ������ �����°��� �ƴ�)
		// lists.size()�� ���� �������� ��Ÿ�� ���ڵ� ����(2�� �ϼ��� �ְ�, �� �� �������� 1�� �ϼ��� �ְ�..)
		return lists; //AlbumListController.java�� ���ϵȴ�. 
		/*RowBounds �Ķ���ʹ�
		MyBatis�� �Ͽ��� Ư�� ���� ��ŭ�� ���ڵ带 �ǳ� ��� �Ѵ�. */

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
		System.out.println("������ ���� cnt : "+cnt);
		return cnt;
	}
   
	public int updateAlbum(AlbumBean album) {
		Integer cnt = -1;
		cnt = sqlSessionTemplate.insert(namespace + ".UpdateAlbum", album);
		return cnt;
	}

}


