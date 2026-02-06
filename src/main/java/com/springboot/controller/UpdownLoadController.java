package com.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.dto.UploadFileDTO;
import com.springboot.dto.UploadResultDTO;

import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnailator;


@RestController("/file")
//@Log4j2
public class UpdownLoadController {

    private final BoardController boardController;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("${project.upload.path}")
	private String UPLOAD_PATH;

    UpdownLoadController(BoardController boardController) {
        this.boardController = boardController;
    }
	
	@ApiOperation(value = "Upload Post", notes = "POST 방식으로 파일 등록" )
	@PostMapping(value = "/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO) {
		log.info("post upload");
		if(uploadFileDTO.getFiles() != null) {
			
			final List<UploadResultDTO> list = new ArrayList<>();
			
			for(MultipartFile file  : uploadFileDTO.getFiles()) {
				log.info(file.getOriginalFilename());
				
				String uuid = UUID.randomUUID().toString();
				boolean img = false ;
				String fileName =  file.getOriginalFilename();
				Path savePath = Paths.get(UPLOAD_PATH , uuid+ "_" +fileName);
				log.info(savePath.toString());
				try {
					file.transferTo(savePath);
					
					if(Files.probeContentType(savePath).startsWith("image")) {
						img = true ;
						
						File thumFile = new File(UPLOAD_PATH ,  "s_"+uuid+"_" +file.getOriginalFilename());
						Thumbnailator.createThumbnail(savePath.toFile(), thumFile, 200, 200);
					}
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				list.add( 	UploadResultDTO.builder()
							.fileName(file.getOriginalFilename())
							.img(img)
							.uuid(uuid)
							.build()
						);
			}
			log.info(list.toString());
			
			return list;
		}
		return null;
	}
	
	
	@ApiOperation(value = "view file ", notes = "get 방식으로 파일 조회" )
	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> getMethodName(@PathVariable String fileName ) {
		
		Resource resource = new FileSystemResource(UPLOAD_PATH + File.separator + fileName);
		
		String resourceeName = resource.getFilename();
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Type" , Files.probeContentType(resource.getFile().toPath()));
			log.info(Files.probeContentType(resource.getFile().toPath()));
		} catch (IOException e) {
			log.error(e.toString());
			return ResponseEntity.internalServerError().build();
		}
		
		
		return ResponseEntity.ok().headers(headers).body(resource);
	}
	
	@ApiOperation(value = "delete file ", notes = "delete 방식으로 파일 삭제" )
	@DeleteMapping("/remove/{fileName}")
	public ResponseEntity<Map<String,Object>> removeFile(@PathVariable String fileName){
		
		Path deleteFilePath =  Paths.get( UPLOAD_PATH ,  File.separator , fileName);
		
		
		Resource resource = new FileSystemResource(deleteFilePath);
		
		boolean isRemoved =  false;
		
		try {
			String contentType = Files.probeContentType(deleteFilePath);
			isRemoved = resource.getFile().delete();
			
			log.info(contentType);
			log.info(contentType.startsWith("image")+"");
			log.info(Paths.get( UPLOAD_PATH ,  File.separator , fileName).toString());
			log.info(UPLOAD_PATH + File.separator + "s_" + fileName );
			
			
			if(contentType.startsWith("image")) {
				File thumFile = new File(UPLOAD_PATH + File.separator + "s_" + fileName );
				thumFile.delete();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", isRemoved);
		
		
		return ResponseEntity.ok().body(resultMap);
	}
	
	
	
}
