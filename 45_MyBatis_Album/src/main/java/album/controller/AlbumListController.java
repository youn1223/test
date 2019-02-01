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
	// /ex/list.ab �̷��� ���� ��������. 
	// ��� ȭ���� url�� �Ȱ��� �����µ� /ex/list.ab �̷��� �ϸ� ȭ���� �ȶ��. 

	@Autowired
	@Qualifier("myAlbumDao") // ��������-AlbumDao�� ��ӹ��� Ŭ������ ������ �ִ°��� �ƴϴϱ�..
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
			required=false�� �ϸ� �Ķ���Ͱ� �ʼ� ���� �ƴϴ� ��� ���̰�,
			defaultValue�δ� �Ķ���Ͱ� �Ѿ���� �ʴ��� �⺻ ���� �� �� �ִ�(defaultValue="kr" �̷�������)
			�⺻���� true �̴�.
		*/		
		
		System.out.println("method:"+request.getMethod());
		System.out.println("�˻��� �÷�(whatColumn) : " + whatColumn + ", ");
		System.out.println("�˻��� �ܾ�(keyword) : " + keyword + ", ");
		System.out.println("pageNumber : " + pageNumber + ", ");
		System.out.println("pageSize : " + pageSize + ", "); //�� �������� ������ �Ǽ�(���ڵ尹��)

		/*
		��ó���� list.ab�� ��û�Ǹ�
		�˻��� �÷�(whatColumn) : null, 
		�˻��� �ܾ�(keyword) : null, 
		pageNumber : null, 
		pageSize : null, 
		
		2������ Ŭ���ϸ�
		�˻��� �÷�(whatColumn) : null, 
		�˻��� �ܾ�(keyword) : null, 
		pageNumber : 2, 
		pageSize : 2, 

		���� a�ְ� 2������ Ŭ���ϸ�
		�˻��� �÷�(whatColumn) : title, 
		�˻��� �ܾ�(keyword) : a, 
		pageNumber : 2, 
		pageSize : 2, 
		*/
		
		Map<String, String> map = new HashMap<String, String>() ;	

		// �Ʒ� ���� ����
		/*if( whatColumn != null && whatColumn.equals("all")){
			whatColumn = null ; 
		}*/
		
		map.put("whatColumn", whatColumn ) ;
		map.put("keyword", "%" + keyword + "%" ) ;
		//map.put("keyword", "%����%" ) ;
		
		//���� ��� �Ʒ��ٵ� �ȴ�.
		//model.addAttribute("whatColumn", whatColumn ) ;
		//model.addAttribute("keyword", "%" + keyword + "%" ) ;
		
		int totalCount = albumDao.GetTotalCount( map );
		//���� ��� �Ʒ��ٵ� �ȴ�.
		//int totalCount = albumDao.GetTotalCount( model ); 

		System.out.println("��ü ���(totalCount) : " + totalCount + ", ");
		String url = request.getContextPath() +  this.command ;
		// request.getContextPath() + �� ����Ѵ�.(�Ⱦ��� ������)
		//String url = "/" +  this.command ;// ���� ��� ���ٷ� �ϸ� ������ Ŭ������ �� �ȳѾ��. 
											// �ٸ��۾����Ŀ� ��Ϻ���� �� �Ѿ���µ� ������ Ŭ������ ���� �ȳѾ���� ������ ����??
		
		System.out.println("url : "+url); // url : /ex/list.ab

		ModelAndView mav = new ModelAndView();
		/*${map1.param1 }*/
		
		
		Paging pageInfo 
		= new Paging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
		// url ���� ==>  http://localhost:8989/MyServlet/list.do
		// �� ���� null�� �ȳ־ �ɵ���, �����ڿ��� ������
		/*
		ó���� pageSize�� null�־ Paging �����ڷ� �Ѿ��,
		null�� �Ѿ�ϱ� �����ڿ��� _pageSize="10"���ϰ� �װ��� limit�� �ִ´�. 
		�Ʒ����� getLimit() �ϸ� 10�� ���´�.
		 */
		System.out.println();
		System.out.println( "offset : " + pageInfo.getOffset() + ", " ) ; //  offset : 0, 3������ Ŭ���ϸ� offset : 4
		System.out.println( "limit : " + pageInfo.getLimit() + ", " ) ;  //limit : 2, �׻� 2�� ���´�. ���������� ������ ���ڵ� ����
		System.out.println( "url : " + pageInfo.getUrl() + ", " ) ; // url : /ex/list.ab
		System.out.println("pageInfo.getPageNumber() : "+pageInfo.getPageNumber());
		List<AlbumBean> albumLists = albumDao.GetAlbumList( pageInfo, map );
		// map���� whatColumn, keyword�� ����ִ�.

		System.out.println("��ȸ�� �Ǽ�: " + albumLists.size());
		mav.addObject( "albumLists", albumLists );	//albumLists:���������� ���� ���ڵ�	
		mav.addObject( "pageInfo", pageInfo ); // pageInfo�� �������Ƿ� 
		// System.out.println("pageInfo:"+pageInfo); // pageInfo:utility.Paging@164807d
		mav.setViewName(getPage); // "AlbumList"
		return mav;



	}
}



