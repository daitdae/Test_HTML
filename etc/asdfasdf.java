@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
	String file_id;
	String board_status;
	String board_no;
	String file_origin;
	String file_saved;
}
이건 file vo 코드이고

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Used {
	String used_id;
	String user_email;
	String category_id;
	String used_title;
	String used_date;
	int used_price;
	String used_content;
	String used_quality;
	String used_status;
	String uloc_id;
	String sloc_id;
}
이건 used vo코드이고
/**
	 * 중고거래 팝니다 글 수정(사용자 측)
	 **/
	@GetMapping("usedSellBoardUpdate")
	public String usedSellBoardUpdate(
			Model model
			,@RequestParam(name="used_id", defaultValue="0") String used_id
			
			) {
		
		Used used = service.usedSellBoardRead(used_id);
		
		//글정보를 모델에 저장
		model.addAttribute("used",used);
		
		//수정을 html로 포워딩
		return "used/usedSellBoardUpdate.html";
	}
	
	//수정폼에서 보낸 내용 처리
	@PostMapping("usedSellBoardUpdate")
	public String usedSellBoardUpdate(
	    @ModelAttribute Used used,
	    @RequestParam(name = "upload") ArrayList<MultipartFile> upload,
	    @RequestParam(name = "uploadOne") MultipartFile uploadOne
	) {
	    String used_id = service.usedSellBoardUpdate(used);
	    
	    if (used_id == null) {
	        return "redirect:/";
	    }
	    
	    // 추가된 사진 처리
	    if (!uploadOne.isEmpty()) {
	        String filename = FileService.saveFile(uploadOne, uploadPath);
	        File savedFile = new File();
	        savedFile.setFile_origin(uploadOne.getOriginalFilename());
	        savedFile.setFile_saved(filename);
	        savedFile.setBoard_no(used_id);
	        savedFile.setBoard_status("중고 거래");
	        service.insertFile(savedFile);
	    }
	    
	    for (int i = 0; i < upload.size(); ++i) {
	        MultipartFile file = upload.get(i);
	        if (!file.isEmpty()) {
	            String filename = FileService.saveFile(file, uploadPath);
	            File savedFile = new File();
	            savedFile.setFile_origin(file.getOriginalFilename());
	            savedFile.setFile_saved(filename);
	            savedFile.setBoard_no(used_id);
	            savedFile.setBoard_status("중고 거래");
	            service.insertFile(savedFile);
	        }
	    }
	    
	    return "redirect:/";
	} 이건 수정하기 위한 컨트롤러 코드이고

	public interface UsedService {
	//파는 글 수정
	public String usedSellBoardUpdate(Used used);
	} 이건 service 코드


	public class UsedServiceImpl implements UsedService {
	
	@Autowired
	UsedDAO dao;
	//파는 글 수정
	@Override
	public String usedSellBoardUpdate(Used used) {
		int n = dao.usedSellBoardUpdate(used);
		Used one = dao.usedSellBoardRead(used.getUsed_id());
		String user_id = one.getUsed_id();
		return user_id;
	}
}	이건 serviceImpl 코드

	/**
	 * 중고거래 파는 글 수정*/
	public int usedSellBoardUpdate(Used used);
	이건 dao코드이고

	<update id="usedSellBoardUpdate" parameterType="Used">
    UPDATE used
    SET
        category_id = #{category_id},
        used_title = #{used_title},
        used_date = #{used_date},
        used_price = #{used_price},
        used_content = #{used_content},
        used_quality = #{used_quality},
        used_status = #{used_status},
        uloc_id = #{uloc_id},
        sloc_id = #{sloc_id}
    WHERE
        used_id = #{used_id}
</update>

이건 sql문이야 근데 이걸 실행하면 글 수정과 사진 수정은 되는데 수정 전 사진이 db에서
지워지지를 않아. 이걸 어떻게 처리하면 file db에 저장되어 있는 수정 전 사진을 지울수있어?


---------------------------------------------------------------------------
@PostMapping("usedSellBoardUpdate")
	public String usedSellBoardUpdate(
	    @ModelAttribute Used used,
	    @RequestParam(name = "upload") ArrayList<MultipartFile> upload,
	    @RequestParam(name = "uploadOne") MultipartFile uploadOne
	) {
	    String used_id = service.usedSellBoardUpdate(used);
	    
	    if (used_id == null) {
	        return "redirect:/";
	    }
	    
	    // 기존 파일 삭제 처리
	    ArrayList<File> oldFiles = service.getFilesByBoardNo(used_id); // 해당 게시글의 기존 파일 목록 가져오기
	    for (File oldFile : oldFiles) {
	        String oldFilename = oldFile.getFile_saved();
	        FileService.deleteFile(uploadPath + "/" + oldFilename); // 기존 파일 삭제
	        service.deleteFile(oldFile.getFile_no()); // DB에서 파일 정보 삭제
	    }
	    
	    // 추가된 사진 처리
	    if (!uploadOne.isEmpty()) {
	        String filename = FileService.saveFile(uploadOne, uploadPath);
	        File savedFile = new File();
	        savedFile.setFile_origin(uploadOne.getOriginalFilename());
	        savedFile.setFile_saved(filename);
	        savedFile.setBoard_no(used_id);
	        savedFile.setBoard_status("중고 거래");
	        service.insertFile2(savedFile);
	    }
	    
	    for (int i = 0; i < upload.size(); ++i) {
	        MultipartFile file = upload.get(i);
	        if (!file.isEmpty()) {
	            String filename = FileService.saveFile(file, uploadPath);
	            File savedFile = new File();
	            savedFile.setFile_origin(file.getOriginalFilename());
	            savedFile.setFile_saved(filename);
	            savedFile.setBoard_no(used_id);
	            savedFile.setBoard_status("중고 거래");
	            service.insertFile2(savedFile);
	        }
	    }
	    
	    return "redirect:/";
	}
	-----------------------------------------------------------------
	@PostMapping("usedSellBoardUpdate")
public String usedSellBoardUpdate(
    @ModelAttribute Used used,
    @RequestParam(name = "upload") ArrayList<MultipartFile> upload,
    @RequestParam(name = "uploadOne") MultipartFile uploadOne
) {
    String used_id = service.usedSellBoardUpdate(used);
    
    if (used_id == null) {
        return "redirect:/";
    }
    
    // 기존 파일 삭제 처리
    List<File> oldFiles = service.getFilesByBoardNo(used_id); // 해당 게시글의 기존 파일 목록 가져오기
    for (File oldFile : oldFiles) {
        String oldFilename = oldFile.getFile_saved();
        FileService.deleteFile(uploadPath + "/" + oldFilename); // 기존 파일 삭제
        service.deleteFile(oldFile.getFile_no()); // DB에서 파일 정보 삭제
    }
    
    // 추가된 사진 처리
    if (!uploadOne.isEmpty()) {
        String filename = FileService.saveFile(uploadOne, uploadPath);
        File savedFile = new File();
        savedFile.setFile_origin(uploadOne.getOriginalFilename());
        savedFile.setFile_saved(filename);
        savedFile.setBoard_no(used_id);
        savedFile.setBoard_status("중고 거래");
        service.insertFile(savedFile);
    }
    
    for (int i = 0; i < upload.size(); ++i) {
        MultipartFile file = upload.get(i);
        if (!file.isEmpty()) {
            String filename = FileService.saveFile(file, uploadPath);
            File savedFile = new File();
            savedFile.setFile_origin(file.getOriginalFilename());
            savedFile.setFile_saved(filename);
            savedFile.setBoard_no(used_id);
            savedFile.setBoard_status("중고 거래");
            service.insertFile(savedFile);
        }
    }
    
    return "redirect:/";
} 이 코드에 맞는 public interface UsedService {}, public class UsedServiceImpl implements UsedService {}, public interface UsedDAO {}, <?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.anabada.dao.UsedDAO"> 이 코드들 만들어 줘




@GetMapping("usedBuyBoardDelete")	
	public String usedBuyBoardDelete(
	        @RequestParam(name="uBuy_id", defaultValue="0") String uBuy_id
	       ,@AuthenticationPrincipal UserDetails user    
	        ) {
		log.debug("오냐고  : {}", uBuy_id);
		String id = user.getUsername();
	    Used_buy used_buy = service.usedBuyBoardRead(uBuy_id);
	    if (used_buy == null) return "redirect:list";
	    // 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로   
	    if (!used_buy.getUser_email().equals(user.getUsername())) return "redirect:/";

	    int result = service.usedBuyBoardDelete(used_buy);

	    return "redirect:/";
	}