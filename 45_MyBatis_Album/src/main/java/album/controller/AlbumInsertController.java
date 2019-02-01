package album.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;

@Controller
public class AlbumInsertController { 
	// AlbumList.jsp에서 넘어올 때에는 get으로 넘어오고
	// AlbumInsertForm.jsp에서 넘어올 때에는 post로 넘어온다.
	
	private static final String getPage = "AlbumInsertForm";
	private static final String gotoPage = "redirect:/list.ab";
	private static final String command = "/insert.ab";
	/*
	위에서 gotoPage = "/list.ab" 로 하면 InsertForm.jsp에서 추가하기 클릭했을 때
	위의 주소창에	ex/WEB-INF/album/list.ab.jsp 이런식으로
	앞에 /WEB-INF/album/가 붙고 뒤에 .jsp가 붙는 형태로 나오므로 
	redirect: 를 꼭 써야 한다.
	*/
	
	@Autowired
	private AlbumDao albumDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET) // 목록보기에서 추가버튼 클릭할 때 
	public String doActionGet(){
		System.out.println(this.getClass() + " Get 방식 들어옴"); 
		
		return getPage;		
	}
	
	
	/*
	@RequestMapping(value=command, method=RequestMethod.GET) // 목록보기에서 추가버튼 클릭할 때 
	public String doActionGet(HttpServletRequest req,Model model){
		System.out.println(this.getClass() + " Get 방식 들어옴"); 
		String pn = req.getParameter("pageNumber");
		model.addAttribute("pn",pn);
		return getPage;		
	}
	*/
	
	@RequestMapping(value=command, method=RequestMethod.POST) // 삽입폼에서 추가버튼 클릭할 때
	public ModelAndView doActionGet(
			@ModelAttribute("album") @Valid AlbumBean album, 
			BindingResult bindingResult){
		// 위의 매개변수 자리에 유효성 검사 코드와 @RequestParam을 같이 쓸 수 없다. @ModelAttribute("album") 이 있어도 없어도  @RequestParam을 쓸 수 없다.  
		// @RequestParam을 쓸거면 HttpServletRequest를 사용하자.
		
		System.out.println(this.getClass() + " POST 방식 들어옴"); 
		// BindingResult 인터페이스 : 에러 코드로부터 에러 메시지를 가져 온다.

		//valid쓸 때 ModelAttribute꼭 써야한다?? 안써도 되는것 같은데..
		
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()){
			System.out.println("유효성 검사 오류입니다");
			mav.setViewName(getPage);
			return mav;
		}
		
		// 데이터베이스에 추가하는 코드
		int cnt = -1;
		cnt = albumDao.InsertAlbum(album);
		mav.setViewName(gotoPage); // redirect는 get방식 요청이다.
		return mav;		
	}
	/*
	@RequestMapping(value=command, method=RequestMethod.POST) // 삽입폼에서 추가버튼 클릭할 때
	public ModelAndView doActionGet(HttpServletRequest req,Model model,
			@ModelAttribute("album") @Valid AlbumBean album, 
			BindingResult bindingResult){
		
		// BindingResult 인터페이스 : 에러 코드로부터 에러 메시지를 가져 온다.

		//valid쓸 때 ModelAttribute꼭 써야한다?? 안써도 되는것 같은데..
		
		System.out.println(this.getClass() + " POST 방식 들어옴"); 
		String pn = req.getParameter("pageNumber");
		model.addAttribute("pn",pn);
		
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()){
			System.out.println("유효성 검사 오류입니다");
			mav.setViewName(getPage);
			return mav;
		}
		
		// 데이터베이스에 추가하는 코드
		int cnt = -1;
		cnt = albumDao.InsertAlbum(album);
		mav.setViewName(gotoPage);
		return mav;		
	}
	*/
}


