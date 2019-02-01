package album.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;

@Controller
public class AlbumDetailViewController {
	private static final String getPage = "AlbumDetailView";
	private static final String command = "/detail.ab";
	
	@Autowired
	private AlbumDao albumDao; // AlbumDao의 객체가 들어옴
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String doActionGet(@RequestParam(value="num", required=true) int num,
			@RequestParam(value="pageNumber", required=false) int pageNumber,
			Model model){
		System.out.println("Get 방식 들어옴");
		System.out.println("pageNumber:"+pageNumber);
		
		AlbumBean album = albumDao.GetAlbum(num);
		model.addAttribute("album",album);
		model.addAttribute("pageNumber",pageNumber);
		return getPage;		
	}
	/*위에서 pageNumber를 @RequestParam으로 받고 model로도 설정해야 보고있던 페이지로 넘어갈 수 있다.*/
	
	// 위의 코드도 되고, 아래 코드도 된다.
	/*
	@RequestMapping(value=command, method=RequestMethod.GET)
	public ModelAndView doActionGet(@RequestParam(value="num", required=true) int num){
		
		System.out.println("Get 방식 들어옴");
		AlbumBean album = albumDao.GetAlbum(num);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("album",album);
		mav.setViewName(getPage);
		return mav;
	}
	*/
	
}
