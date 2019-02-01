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
	목록보기(AlbumList.jsp)에서 넘어올 때에는 get방식으로 넘어오고
	수정 폼(AlbumUpdateForm.jsp)에서 넘어올 때에는 post방식으로 넘어오므로
	2가지 방식 처리를 모두 해야한다.
	*/
	@Autowired
	private AlbumDao albumDao;
	//ServletContext application;
	
	/*
	처음 목록보기에서 수정버튼 클릭시 번호를 가지고 get방식으로 넘어오고
	수정 폼에서 수정한 후 수정하기 버튼을 클릭하면 post방식으로 넘어온다.
	*/
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String doActionGet(@RequestParam(value="num",required=true) int num,
			@RequestParam(value="pageNumber",required=true) int pageNumber,
			@RequestParam(value="pageSize",required=true) int pageSize,
			Model model){
		
		System.out.println("AlbumUpdateController Get 방식 들어옴");
		
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
		
		// 위의 매개변수 자리에 유효성 검사 코드와 @RequestParam을 같이 쓸 수 없다. => 쓸수 있다고 하는데 다시 테스트 해보자.
		// @RequestParam을 쓸거면 HttpServletRequest를 사용하자.
		
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
			){ //3 레코드 수정 후 보고 있던 페이지로 다시 가기위해 pageNumber, pageSize까지 넘겨봤는데 꼭 위처럼 모두 @RequestParam으로 받아야 하나?
			일부는 AlbumBean으로 받고 일부는 @RequestParam으로 받을수는 없나?
			그리고 위처럼 해도 보고 있던 페이지로 안가는데 어떻게 해야할까???
	*/		
		
		System.out.println("AlbumUpdateController Post 방식 들어옴");

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
			위에서 gotoPage =  "/list.ab"; 로 쓰면 
			결과창의 url에 /ex/WEB-INF/album/list.ab.jsp 이런식으로 나오므로
			redirect를 꼭 붙여야 한다.
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
	map.put("param1", "나의파람1");
	map.put("param2", "나의파람2");
	//redirectAttr.addFlashAttribute("param1", map);
	redirectAttr.addFlashAttribute("map1", map);
	return "redirect:/c2";
}
*/