package album.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import album.model.AlbumDao;

@Controller
public class AlbumDeleteController {

	private static final String gotoPage = "redirect:/list.ab";
	private static final String command = "/delete.ab";
	
	@Autowired
	private AlbumDao albumDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String doActionGet(
			@RequestParam(value="num",required=true) int num,
			@RequestParam(value="pageNumber",required=true) int pageNumber){
		
		System.out.println("AlbumDeleteController Get 방식 들어옴");
		int cnt=-1;
		cnt = albumDao.DeleteAlbum(num);
		
		return gotoPage+"?pageNumber="+pageNumber; // redirect는 get방식 요청이다.
	}
	
}
