package com.routdoo.dailyroutine.module.chat.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.routdoo.dailyroutine.module.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

	private final ChatService chatService;
	
	/**
	 * 방 전체 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/rooms")
	public ModelAndView rooms() throws Exception {
		
		ModelAndView mv = new ModelAndView("chat/rooms");

	    mv.addObject("list", chatService.findAllRooms());
		
		return mv;
	}
	
	/**
	 * 방생성
	 * @param name
	 * @param rttr
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/room")
	public String create(@RequestParam String name, RedirectAttributes rttr) throws Exception {
		
		rttr.addFlashAttribute("roomName",chatService.createChatRoomDto(name));
		return "redirect:/chat/rooms";
	}
	
	//채팅방 조회
	@GetMapping("/room")
	public void getRoom(String roomId, Model model) {
		model.addAttribute("room",chatService.findRoomById(roomId));
	}
}
