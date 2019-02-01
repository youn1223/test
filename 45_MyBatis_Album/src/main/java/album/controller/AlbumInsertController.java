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
	// AlbumList.jsp���� �Ѿ�� ������ get���� �Ѿ����
	// AlbumInsertForm.jsp���� �Ѿ�� ������ post�� �Ѿ�´�.
	
	private static final String getPage = "AlbumInsertForm";
	private static final String gotoPage = "redirect:/list.ab";
	private static final String command = "/insert.ab";
	/*
	������ gotoPage = "/list.ab" �� �ϸ� InsertForm.jsp���� �߰��ϱ� Ŭ������ ��
	���� �ּ�â��	ex/WEB-INF/album/list.ab.jsp �̷�������
	�տ� /WEB-INF/album/�� �ٰ� �ڿ� .jsp�� �ٴ� ���·� �����Ƿ� 
	redirect: �� �� ��� �Ѵ�.
	*/
	
	@Autowired
	private AlbumDao albumDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET) // ��Ϻ��⿡�� �߰���ư Ŭ���� �� 
	public String doActionGet(){
		System.out.println(this.getClass() + " Get ��� ����"); 
		
		return getPage;		
	}
	
	
	/*
	@RequestMapping(value=command, method=RequestMethod.GET) // ��Ϻ��⿡�� �߰���ư Ŭ���� �� 
	public String doActionGet(HttpServletRequest req,Model model){
		System.out.println(this.getClass() + " Get ��� ����"); 
		String pn = req.getParameter("pageNumber");
		model.addAttribute("pn",pn);
		return getPage;		
	}
	*/
	
	@RequestMapping(value=command, method=RequestMethod.POST) // ���������� �߰���ư Ŭ���� ��
	public ModelAndView doActionGet(
			@ModelAttribute("album") @Valid AlbumBean album, 
			BindingResult bindingResult){
		// ���� �Ű����� �ڸ��� ��ȿ�� �˻� �ڵ�� @RequestParam�� ���� �� �� ����. @ModelAttribute("album") �� �־ ���  @RequestParam�� �� �� ����.  
		// @RequestParam�� ���Ÿ� HttpServletRequest�� �������.
		
		System.out.println(this.getClass() + " POST ��� ����"); 
		// BindingResult �������̽� : ���� �ڵ�κ��� ���� �޽����� ���� �´�.

		//valid�� �� ModelAttribute�� ����Ѵ�?? �Ƚᵵ �Ǵ°� ������..
		
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()){
			System.out.println("��ȿ�� �˻� �����Դϴ�");
			mav.setViewName(getPage);
			return mav;
		}
		
		// �����ͺ��̽��� �߰��ϴ� �ڵ�
		int cnt = -1;
		cnt = albumDao.InsertAlbum(album);
		mav.setViewName(gotoPage); // redirect�� get��� ��û�̴�.
		return mav;		
	}
	/*
	@RequestMapping(value=command, method=RequestMethod.POST) // ���������� �߰���ư Ŭ���� ��
	public ModelAndView doActionGet(HttpServletRequest req,Model model,
			@ModelAttribute("album") @Valid AlbumBean album, 
			BindingResult bindingResult){
		
		// BindingResult �������̽� : ���� �ڵ�κ��� ���� �޽����� ���� �´�.

		//valid�� �� ModelAttribute�� ����Ѵ�?? �Ƚᵵ �Ǵ°� ������..
		
		System.out.println(this.getClass() + " POST ��� ����"); 
		String pn = req.getParameter("pageNumber");
		model.addAttribute("pn",pn);
		
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()){
			System.out.println("��ȿ�� �˻� �����Դϴ�");
			mav.setViewName(getPage);
			return mav;
		}
		
		// �����ͺ��̽��� �߰��ϴ� �ڵ�
		int cnt = -1;
		cnt = albumDao.InsertAlbum(album);
		mav.setViewName(gotoPage);
		return mav;		
	}
	*/
}


