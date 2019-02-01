package album.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;
import utility.Paging;

@Controller
public class AlbumListController {
	private static final String getPage = "AlbumList";
	private static final String command = "/list.ab"; 
	// /ex/list.ab 이렇게 쓰면 에러난다. 
	// 결과 화면의 url은 똑같이 나오는데 /ex/list.ab 이렇게 하면 화면이 안뜬다. 

	@Autowired
	@Qualifier("myAlbumDao") // 생략가능-AlbumDao를 상속받은 클래스가 여러개 있는것은 아니니까..
	private AlbumDao albumDao;

	@RequestMapping(value=command)
	public ModelAndView doActionPost(
			@RequestParam(value="whatColumn",required=false) String whatColumn,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value = "pageNumber", required = false ) String pageNumber,
			@RequestParam(value = "pageSize", required = false ) String pageSize,
			HttpServletRequest request, Model model) {
		/*
		@RequestParam : 
			required=false로 하면 파라미터가 필수 값이 아니다 라는 뜻이고,
			defaultValue로는 파라미터가 넘어오지 않더라도 기본 값을 줄 수 있다(defaultValue="kr" 이런식으로)
			기본값은 true 이다.
		*/		
		
		System.out.println("method:"+request.getMethod());
		System.out.println("검색할 컬럼(whatColumn) : " + whatColumn + ", ");
		System.out.println("검색할 단어(keyword) : " + keyword + ", ");
		System.out.println("pageNumber : " + pageNumber + ", ");
		System.out.println("pageSize : " + pageSize + ", "); //한 페이지에 보여줄 건수(레코드갯수)

		/*
		맨처음에 list.ab가 요청되면
		검색할 컬럼(whatColumn) : null, 
		검색할 단어(keyword) : null, 
		pageNumber : null, 
		pageSize : null, 
		
		2페이지 클릭하면
		검색할 컬럼(whatColumn) : null, 
		검색할 단어(keyword) : null, 
		pageNumber : 2, 
		pageSize : 2, 

		제목에 a넣고 2페이지 클릭하면
		검색할 컬럼(whatColumn) : title, 
		검색할 단어(keyword) : a, 
		pageNumber : 2, 
		pageSize : 2, 
		*/
		
		Map<String, String> map = new HashMap<String, String>() ;	

		// 아래 생략 가능
		/*if( whatColumn != null && whatColumn.equals("all")){
			whatColumn = null ; 
		}*/
		
		map.put("whatColumn", whatColumn ) ;
		map.put("keyword", "%" + keyword + "%" ) ;
		//map.put("keyword", "%하하%" ) ;
		
		//윗줄 대신 아랫줄도 된다.
		//model.addAttribute("whatColumn", whatColumn ) ;
		//model.addAttribute("keyword", "%" + keyword + "%" ) ;
		
		int totalCount = albumDao.GetTotalCount( map );
		//윗줄 대신 아랫줄도 된다.
		//int totalCount = albumDao.GetTotalCount( model ); 

		System.out.println("전체 행수(totalCount) : " + totalCount + ", ");
		String url = request.getContextPath() +  this.command ;
		// request.getContextPath() + 꼭 써야한다.(안쓰면 에러남)
		//String url = "/" +  this.command ;// 윗줄 대신 이줄로 하면 페이지 클릭했을 때 안넘어간다. 
											// 다른작업한후에 목록보기로 잘 넘어오는데 페이지 클릭했을 때만 안넘어오는 이유가 뭘까??
		
		System.out.println("url : "+url); // url : /ex/list.ab

		ModelAndView mav = new ModelAndView();
		/*${map1.param1 }*/
		
		
		Paging pageInfo 
		= new Paging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
		// url 예시 ==>  http://localhost:8989/MyServlet/list.do
		// 맨 끝에 null은 안넣어도 될듯함, 생성자에서 사용안함
		/*
		처음에 pageSize에 null넣어서 Paging 생성자로 넘어가면,
		null이 넘어가니까 생성자에서 _pageSize="10"을하고 그것을 limit에 넣는다. 
		아래에서 getLimit() 하면 10이 나온다.
		 */
		System.out.println();
		System.out.println( "offset : " + pageInfo.getOffset() + ", " ) ; //  offset : 0, 3페이지 클릭하면 offset : 4
		System.out.println( "limit : " + pageInfo.getLimit() + ", " ) ;  //limit : 2, 항상 2로 나온다. 한페이지에 나오는 레코드 갯수
		System.out.println( "url : " + pageInfo.getUrl() + ", " ) ; // url : /ex/list.ab
		System.out.println("pageInfo.getPageNumber() : "+pageInfo.getPageNumber());
		List<AlbumBean> albumLists = albumDao.GetAlbumList( pageInfo, map );
		// map에는 whatColumn, keyword가 담겨있다.

		System.out.println("조회된 건수: " + albumLists.size());
		mav.addObject( "albumLists", albumLists );	//albumLists:한페이지에 나올 레코드	
		mav.addObject( "pageInfo", pageInfo ); // pageInfo를 가져가므로 
		// System.out.println("pageInfo:"+pageInfo); // pageInfo:utility.Paging@164807d
		mav.setViewName(getPage); // "AlbumList"
		return mav;



	}
}



