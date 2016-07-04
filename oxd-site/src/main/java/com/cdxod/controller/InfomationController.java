package com.cdxod.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 资讯中心Controller
 * @author chaiying
 *
 */
@Controller
@RequestMapping("/info")
public class InfomationController {
	
	@RequestMapping(value = {"", "/index"})
	public String index(Model model) {
		return "forward:/info/news";
	}
	
	@RequestMapping("/news")
	public String news(Model model) {
		return "xod/news";
	}
}