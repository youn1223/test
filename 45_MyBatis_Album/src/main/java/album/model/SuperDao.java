/*package album.model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SuperDao {
	public SqlSession sqlSession = null;
	public String namespace = null;

	private SqlSessionFactory sqlSessionFactory;

	private String mapConfigFile = null;

	public SuperDao() { }

	public SuperDao(String mapConfigFile, String namespace) {
		this.mapConfigFile =mapConfigFile;
		this.namespace =namespace;

	}
	public SqlSession SessionOpenReader(){		
		return null ;
	}
	public SqlSession SessionOpenStream(){
		InputStream inputStream=null;
		try {
			inputStream= Resources.getResourceAsStream(this.mapConfigFile);
			this.sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
			this.sqlSession = this.sqlSessionFactory.openSession(true);
			
			 getResourceAsStream() : 
				외부 리소스를 이용하여 Byte Stream(InputStream) 객체를 구해준다.
			 
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(inputStream != null){inputStream.close();}	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return this.sqlSession ;
	}

	public void SessionClose(){
		//SqlSession 객체를 닫아 주는 메소드
		if( this.sqlSession != null ){ this.sqlSession.close(); }
	}
}

*/