package com.example.demo.controler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.form.UploadForm;

@Controller
public class UploadController {

	private String getExtension(String filename) {
		int dot = filename.lastIndexOf(".");
		if(dot > 0) {
			return filename.substring(dot).toLowerCase();
		}
		return "";
	}

	private String getUploadFileName(String fileName) {
		return fileName + "_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
		.format(LocalDateTime.now())
				+ getExtension(fileName);
	}

	private void createDirectory() {
		Path path = Paths.get("./file");
		if(!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch(Exception e) {
				System.out.println("エラー");
			}
		}
	}

	private void savefile(MultipartFile file) {
		String filename = getUploadFileName(file.getOriginalFilename());
		Path uploadfile = Paths.get("./file/" + filename);
		try(OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)){
			byte[] bytes = file.getBytes();
			os.write(bytes);
		} catch(IOException e) {
			System.out.println("エラー1");
		}
	}

	private void savefiles(List<MultipartFile> multipartFiles) {
		createDirectory();
		for(MultipartFile file : multipartFiles) {
			savefile(file);
		}
	}


	@GetMapping("/upload")
	String uploadview(Model model) {
		model.addAttribute("form", new UploadForm());
		return "upload";
	}

	@PostMapping("/upload")
	String upload(Model model, UploadForm form) {
		if(form.getFile() == null || form.getFile().isEmpty()) {
			System.out.println("エラー2");
		}
		savefiles(form.getFile());
		return "redirect:/upload";
	}
}
