package pl.pwn.reaktor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.pwn.reaktor.model.Article;
import pl.pwn.reaktor.service.ArticleService;

@Controller
public class BlogController {

	private ArticleService articleService;

	@Autowired
	public BlogController(ArticleService articleService) {
		super();
		this.articleService = articleService;
	}

	@GetMapping("/")
	public String index(Model model, Pageable pageable) {

		Page<Article> pageArticle = articleService.getAll(pageable);
		model.addAttribute("articles", pageArticle);
		PageWrapper<Article> page = new PageWrapper<>(pageArticle, "/");
		model.addAttribute("page", page);

		return "index";
	}

	@GetMapping("/about")
	public String about(Model model) {
		return "about";
	}

	@GetMapping("/contact")
	public String post(Model model) {
		return "contact";
	}

}
