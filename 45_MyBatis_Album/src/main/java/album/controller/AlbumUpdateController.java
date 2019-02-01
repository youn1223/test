package album.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;

@Controller
public class AlbumUpdateController {

	private static final String getPage = "AlbumUpdateForm";
	private static final String gotoPage =  "redirect:/list.ab";
	private static final String command = "/update.ab";
	/*
	��Ϻ���(AlbumList.jsp)���� �Ѿ�� ������ get������� �Ѿ����
	���� ��(AlbumUpdateForm.jsp)���� �Ѿ�� ������ post������� �Ѿ���Ƿ�
	2���� ��� ó���� ��� �ؾ��Ѵ�.
	*/
	@Autowired
	private AlbumDao albumDao;
	//ServletContext application;
	
	/*
	ó�� ��Ϻ��⿡�� ������ư Ŭ���� ��ȣ�� ������ get������� �Ѿ����
	���� ������ ������ �� �����ϱ� ��ư�� Ŭ���ϸ� post������� �Ѿ�´�.
	*/
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String doActionGet(@RequestParam(value="num",required=true) int num,
			@RequestParam(value="pageNumber",required=true) int pageNumber,
			@RequestParam(value="pageSize",required=true) int pageSize,
			Model model){
		
		System.out.println("AlbumUpdateController Get ��� ����");
		
		AlbumBean album =  albumDao.GetAlbum(num);
		model.addAttribute("album" , album);
		model.addAttribute("num" , num);
		model.addAttribute("pageNumber" , pageNumber);
		model.addAttribute("pageSize" , pageSize);
		return getPage;
	}
	
	@RequestMapping(value=command, method=RequestMethod.POST)
	//public ModelAndView doActionGet(AlbumBean album,HttpServletRequest request){ // 1
	public ModelAndView doActionGet(
			@RequestParam(value="num",required=true) String num,
			@RequestParam(value="pageNumber",required=true) String pageNumber,
			@RequestParam(value="pageSize",required=true) String pageSize,
			@ModelAttribute("album") @Valid AlbumBean album, BindingResult result,
			HttpServletRequest request){ // 1
		
		// ���� �Ű����� �ڸ��� ��ȿ�� �˻� �ڵ�� @RequestParam�� ���� �� �� ����. => ���� �ִٰ� �ϴµ� �ٽ� �׽�Ʈ �غ���.
		// @RequestParam�� ���Ÿ� HttpServletRequest�� �������.
		
		String num2 = request.getParameter("num");
		String title = request.getParameter("title");
		String singer = request.getParameter("singer");
		System.out.println(num +"/"+title+"/"+ singer+"/"+pageNumber+"/"+pageSize);
		    
	/*	
	public ModelAndView doActionGet(@RequestParam(value="pageNumber",required=true) int pageNumber,
			@RequestParam(value="pageSize",required=true) int pageSize,AlbumBean album){ // 2
	*/
	/*
	public ModelAndView doActionGet(
			@RequestParam(value="pageNumber",required=true) int pageNumber,
			@RequestParam(value="pageSize",required=true) int pageSize,
			@RequestParam(value="num",required=true) int num,
			@RequestParam(value="title",required=true) String title,
			@RequestParam(value="singer",required=true) String singer,
			@RequestParam(value="price",required=true) int price,
			RedirectAttributes redirectAttr
			){ //3 ���ڵ� ���� �� ���� �ִ� �������� �ٽ� �������� pageNumber, pageSize���� �Ѱܺôµ� �� ��ó�� ��� @RequestParam���� �޾ƾ� �ϳ�?
			�Ϻδ� AlbumBean���� �ް� �Ϻδ� @RequestParam���� �������� ����?
			�׸��� ��ó�� �ص� ���� �ִ� �������� �Ȱ��µ� ��� �ؾ��ұ�???
	*/		
		
		System.out.println("AlbumUpdateController Post ��� ����");

		//System.out.println(pageNumber+","+pageSize);
		
		/*Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageNumber", pageNumber);
		map.put("pageSize", pageSize);
		redirectAttr.addFlashAttribute("map1", map);*/
		//String conPath = request.getContextPath();
		ModelAndView mav = new ModelAndView();
		
		//System.out.println(getPage+"?pageNumber="+pageNumber);
		
		
		
		if(result.hasErrors()){
			mav.addObject("pageNumber", pageNumber);  
			mav.addObject("pageSize", pageSize);  
			mav.setViewName(getPage);
			return mav;
		}
		
		
		//AlbumBean album = new AlbumBean(num,title,singer,price,null);
		
		int cnt = albumDao.updateAlbum(album);
		if(cnt != -1){
			mav.setViewName(gotoPage+"?pageNumber="+pageNumber+"&pageSize="+pageSize); // "redirect:/list.ab";
			//mav.setViewName(gotoPage);
			/*
			������ gotoPage =  "/list.ab"; �� ���� 
			���â�� url�� /ex/WEB-INF/album/list.ab.jsp �̷������� �����Ƿ�
			redirect�� �� �ٿ��� �Ѵ�.
			*/
		}else{
			mav.setViewName(getPage);
		}
		return mav;
	}
}
/*
@RequestMapping(value="/c1")
public String b1(RedirectAttributes redirectAttr){
	Map<String, Object> map = new HashMap<String,Object>();
	map.put("param1", "�����Ķ�1");
	map.put("param2", "�����Ķ�2");
	//redirectAttr.addFlashAttribute("param1", map);
	redirectAttr.addFlashAttribute("map1", map);
	return "redirect:/c2";
}
*/