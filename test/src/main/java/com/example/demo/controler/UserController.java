package com.example.demo.controler;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Mailaddress;
import com.example.demo.entity.Telephone;
import com.example.demo.entity.User;
import com.example.demo.entity.User.Role;
import com.example.demo.service.MailaddressService;
import com.example.demo.service.TelephoneService;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	TelephoneService telephoneService;

	@Autowired
	MailaddressService mailaddressService;

	@Autowired
    ResourceLoader resourceLoader;


//	<<------- ユーザー一覧 ------------->>
	@GetMapping("/users")
	public ModelAndView index(ModelAndView mav,
			@PageableDefault(page=0, size=5)
			Pageable pageable) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/index::user_contents");
		mav.addObject("title", "ユーザー一覧");
		Page<User> list = userService.getAll(pageable);
		mav.addObject("list", list);
		return mav;
	}

//  <<------------ユーザー新規作成画面表示----------------->>
	@GetMapping("/users/new")
	public ModelAndView usernew(@ModelAttribute User user,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/new::user_contents");
		mav.addObject("title", "ユーザー新規作成");
		return mav;
	}

//  <<------------ユーザー新規作成----------------->>
//	@Transactional(readOnly = false)
	@PostMapping("users/new")
	public ModelAndView save(
			MultipartFile file,
			@RequestParam("mailKind") String mailKind,
			@RequestParam("mailAddr") String mailAddr,
			@RequestParam("phoneKind") String phoneKind,
			@RequestParam("phoneNumber") String phoneNumber,
			@ModelAttribute("user")
			@Validated User user,
			BindingResult result,
			ModelAndView mav) throws IOException {

	ModelAndView res = null;

	try {
	if(!result.hasErrors()) {
		if(!file.isEmpty()) {
			byte[] bytefile = file.getBytes();
			user.setFilename(file.getOriginalFilename());
			user.setImage(bytefile);
		}
		List<Telephone> list = new ArrayList<Telephone>();
		Telephone telephone = new Telephone();
		telephone.setPhoneKind(phoneKind);
		telephone.setPhoneNumber(phoneNumber);
		list.add(telephone);
		user.setTelephoneList(list);
		userService.saveUser(user);
		List<Mailaddress> maillist = new ArrayList<Mailaddress>();
		Mailaddress mailAddress = new Mailaddress();
		mailAddress.setMailKind(mailKind);
		mailAddress.setMailAddr(mailAddr);
		mailAddress.setUser(user);
		mailaddressService.saveMailaddress(mailAddress);
		maillist.add(mailAddress);
		user.setMailList(maillist);
		res = new ModelAndView("redirect:/users/" + user.getId());
	} else {
		mav.setViewName("layout");
		mav.addObject("msg", "sorry, error is occured...");
		mav.addObject("contents", "user/new::user_contents");
		mav.addObject("title", "ユーザー新規作成");
		res = mav;
	}
	} catch(UnexpectedRollbackException | DataIntegrityViolationException e) {
		mav.setViewName("layout");
		mav.addObject("msg", "sorry, error is occured...");
		mav.addObject("contents", "user/new::user_contents");
		mav.addObject("title", "ユーザー新規作成");
		return new ModelAndView("redirect:/users/new");
	}
		return res;
	}

// <<------------ユーザー詳細---------------->>
	@GetMapping("/users/{id}")
	public ModelAndView show(@PathVariable Integer id,
			ModelAndView mav) throws IOException {
		User user = userService.find(id);
		if(user.getImage() != null) {
			byte[] image = user.getImage();
			String encodedImage = Base64.getEncoder().encodeToString(image);
			mav.addObject("image", encodedImage);
		}
		mav.setViewName("layout");
		mav.addObject("contents", "user/show::user_contents");
		mav.addObject("user", user);
		mav.addObject("title", "ユーザー詳細");

		return mav;
	}

// <<-----------ユーザー編集画面表示------------->>
	@GetMapping("/users/{id}/edit")
	public ModelAndView edit(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/edit::user_contents");
		mav.addObject("title", "ユーザー編集");
		mav.addObject("user", userService.find(id));
		return mav;
	}

// <<----------ユーザー画像ダウンロード----------->>
	@GetMapping("/users/{id}/download")
    public void imageDownload(@PathVariable Integer id,
    		HttpServletResponse response, ModelAndView mav) {

        try (OutputStream os = response.getOutputStream();) {

            byte[] bytefile = userService.find(id).getImage();
            String filename = userService.find(id).getFilename();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentLength(bytefile.length);
            os.write(bytefile);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// <<----------ユーザー編集--------------------->>
	@PostMapping("/users/{id}/edit")
	public ModelAndView edited(@PathVariable Integer id,
			@RequestParam("role") Role role,
			@RequestParam("name") String name,
			@RequestParam("password") String password,
			@RequestParam(name = "mailKind", required = false) String[] mailKinds,
			@RequestParam(name = "mailAddr", required = false) String[] mailAddrs,
			@RequestParam(name = "phoneKind", required = false) String[] phoneKinds,
			@RequestParam(name = "phoneNumber", required = false) String[] phoneNumbers,
			@Validated User user,
			BindingResult result,
			ModelAndView mav) throws IOException {
		if(!result.hasErrors()) {
			user = userService.find(id);
			user.setId(id);
			user.setRole(role);
			user.setName(name);
			user.setPassword(password);
			if(!user.getMailList().isEmpty()) {
				List<Mailaddress> mails = user.getMailList();
				for(int i = 0; i < mails.size(); i++) {
					mails.get(i).setMailKind(mailKinds[i]);
					mails.get(i).setMailAddr(mailAddrs[i]);
				}
			}
			if(!user.getTelephoneList().isEmpty()) {
				List<Telephone> tels = user.getTelephoneList();
				for(int i = 0; i < tels.size(); i++) {
					tels.get(i).setPhoneKind(phoneKinds[i]);
					tels.get(i).setPhoneNumber(phoneNumbers[i]);
				}
			}
		} else {
			mav.setViewName("layout");
			mav.addObject("contents", "user/edit::user_contents");
			mav.addObject("title", "ユーザー編集");
			mav.addObject("user", userService.find(id));
			return mav;
		}
		userService.saveUser(user);

		return new ModelAndView("redirect:/users/" + user.getId());
	}

// <<-----------ユーザーイメージ編集画面表示------------->>
	@GetMapping("/users/{id}/imgedit")
	public ModelAndView imgedit(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/imgedit::user_contents");
		mav.addObject("title", "ユーザー編集");
		mav.addObject("user", userService.find(id));
		return mav;
	}

// <<----------ユーザーイメージ編集--------------------->>
	@PostMapping("/users/{id}/imgedit")
	public ModelAndView imgedited(@PathVariable Integer id,
			@RequestParam("file") MultipartFile file,
			ModelAndView mav) throws IOException {
		User user = userService.find(id);
		user.setId(id);
		if(!file.isEmpty()) {
			byte[] bytefile = file.getBytes();
			user.setImage(bytefile);
			user.setFilename(file.getOriginalFilename());
		} else {
			user.setImage(null);
			user.setFilename(null);
		}
		userService.saveUserImage(user);

		return new ModelAndView("redirect:./");
	}

//	<<--------------ユーザー削除------------------>>
	@PostMapping("/users/{id}/delete")
	public ModelAndView delete(@PathVariable Integer id,
			ModelAndView mav) {
		userService.delete(id);
		return new ModelAndView("redirect:/users");
	}


//	<<------- ユーザー検索 ------------->>
	@GetMapping("/users/search")
	public ModelAndView fsearch(
			@RequestParam("name") String name,
			@PageableDefault(page=0, size=5) Pageable pageable) {
		Page<User> list = userService.search(name, name, name,  pageable);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("layout");
		mav.addObject("contents", "user/index::user_contents");
		mav.addObject("title", "ユーザー検索");
		mav.addObject("list", list);
		return mav;
	}

// <<-----------ユーザーメールアドレス新規画面------------->>
	@GetMapping("users/{id}/mailnew")
	public ModelAndView usermailaddressnew(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "mailaddress/usermailaddressnew::usermailaddress_contents");
		mav.addObject("title", "ユーザーメールアドレス新規登録");
		mav.addObject("user", userService.find(id));
		return mav;
	}

// <<-----------ユーザーメールアドレス新規作成------------->>
	@PostMapping("/users/{id}/mailnew")
	public ModelAndView usermailaddresssave(@PathVariable Integer id,
		@RequestParam(name = "mailKind", required = false) String mailKind,
		@RequestParam(name = "mailAddr", required = false) String mailAddr,
		ModelAndView mav) {
			User user = userService.find(id);
			Mailaddress mailAddress = new Mailaddress();
			List<Mailaddress> mails = user.getMailList();
			mailAddress.setMailKind(mailKind);
			mailAddress.setMailAddr(mailAddr);
			mailAddress.setUser(user);
			mails.add(mailAddress);
			mailaddressService.saveMailaddress(mailAddress);
			user.setMailList(mails);
			userService.saveUserImage(user);
			mav.setViewName("layout");
			mav.addObject("contents", "mailaddress/usermailaddressnew::mailaddress_contents");
			mav.addObject("title", "ユーザーメールアドレス新規登録");
			mav.addObject("user", user);
			return new ModelAndView("redirect:/users/{id}");
	}

// <<-----------ユーザー電話新規画面------------->>
	@GetMapping("users/{id}/telnew")
	public ModelAndView usertelephonenew(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "telephone/usertelephonenew::usertelephone_contents");
		mav.addObject("title", "ユーザー連絡先新規登録");
		mav.addObject("user", userService.find(id));
		return mav;
	}

// <<-----------ユーザー電話新規作成------------->>
	@PostMapping("/users/{id}/telnew")
	public ModelAndView usertelephonesave(@PathVariable Integer id,
		@RequestParam(name = "phoneKind", required = false) String phoneKind,
		@RequestParam(name = "phoneNumber", required = false) String phoneNumber,
		ModelAndView mav) {
			User user = userService.find(id);
			Telephone tel = new Telephone();
			List<Telephone> tels = user.getTelephoneList();
			tel.setPhoneKind(phoneKind);
			tel.setPhoneNumber(phoneNumber);
			tels.add(tel);
			user.setTelephoneList(tels);
			userService.saveUserImage(user);
			mav.setViewName("layout");
			mav.addObject("contents", "telephone/usertelephonenew::telephone_contents");
			mav.addObject("title", "ユーザー連絡先新規登録");
			mav.addObject("user", user);
			return new ModelAndView("redirect:/users/{id}");
	}

// <<------------ユーザー電話削除---------------->>
	@PostMapping("/users/{uid}/teldelete/{tid}")
	public ModelAndView usertelephonedeleted(
			@PathVariable Integer uid,
			@PathVariable int tid,
			ModelAndView mav) {
		User user = userService.find(uid);
		List<Telephone> tels = user.getTelephoneList();
		Telephone tel = telephoneService.find(tid);
		tels.remove(tel);
		user.setTelephoneList(tels);
		userService.saveUserImage(user);
		telephoneService.delete(tid);
		return new ModelAndView("redirect:/users/{uid}");
	}

// <<------------ユーザーメール削除---------------->>
	@PostMapping("/users/{uid}/maildelete/{mid}")
	public ModelAndView userMaildeleted(
			@PathVariable Integer uid,
			@PathVariable int mid,
			ModelAndView mav) {
		User user = userService.find(uid);
		List<Mailaddress> mails = user.getMailList();
		Mailaddress mailAddress = mailaddressService.find(mid);
		mails.remove(mailAddress);
		user.setMailList(mails);
		userService.saveUserImage(user);
		mailaddressService.delete(mid);
		return new ModelAndView("redirect:/users/{uid}");
	}

// <<-----------マイアカウント表示------------------>>
	@GetMapping("/users/myaccount")
	public ModelAndView getmyaccount(
			@AuthenticationPrincipal Principal principal,
			ModelAndView mav) {
		User user = userService.findByName(principal.getName());
		if(user.getImage() != null) {
			byte[] image = user.getImage();
			String encodedImage = Base64.getEncoder().encodeToString(image);
			mav.addObject("image", encodedImage);
		}
		mav.setViewName("layout");
		mav.addObject("contents", "user/show::user_contents");
		mav.addObject("user", user);
		mav.addObject("title", "ユーザー詳細");

		return mav;
	}

// <<-----------マイアカウント編集画面表示------------->>
	@GetMapping("/users/myaccountedit")
	public ModelAndView myaccountedit(
			@AuthenticationPrincipal Principal principal,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/myaccountedit::user_contents");
		mav.addObject("title", "ユーザー編集");
		mav.addObject("user", userService.findByName(principal.getName()));
		return mav;
	}

// <<----------マイアカウント編集--------------------->>
	@PostMapping("/users/myaccountedit")
	public ModelAndView myaccountedited(
			@AuthenticationPrincipal Principal principal,
			@RequestParam("id") Integer id,
			@RequestParam("role") Role role,
			@RequestParam("name") String name,
			@RequestParam("password") String password,
			@RequestParam(name = "mailKind", required = false) String[] mailKinds,
			@RequestParam(name = "mailAddr", required = false) String[] mailAddrs,
			@RequestParam(name = "phoneKind", required = false) String[] phoneKinds,
			@RequestParam(name = "phoneNumber", required = false) String[] phoneNumbers,
			@Validated User user,
			BindingResult result,
			ModelAndView mav) throws IOException {
		if(!result.hasErrors()) {
			user = userService.findByName(principal.getName());
			user.setId(id);
			user.setRole(role);
			user.setName(name);
			user.setPassword(password);
			if(!user.getMailList().isEmpty()) {
				List<Mailaddress> mails = user.getMailList();
				for(int i = 0; i < mails.size(); i++) {
					mails.get(i).setMailKind(mailKinds[i]);
					mails.get(i).setMailAddr(mailAddrs[i]);
				}
			}
			if(!user.getTelephoneList().isEmpty()) {
				List<Telephone> tels = user.getTelephoneList();
				for(int i = 0; i < tels.size(); i++) {
					tels.get(i).setPhoneKind(phoneKinds[i]);
					tels.get(i).setPhoneNumber(phoneNumbers[i]);
				}
			}
		} else {
			mav.setViewName("layout");
			mav.addObject("contents", "user/edit::user_contents");
			mav.addObject("title", "ユーザー編集");
			mav.addObject("user", userService.find(id));
			return mav;
		}
		userService.saveUser(user);

		return new ModelAndView("redirect:/users/myaccount");
	}

}
