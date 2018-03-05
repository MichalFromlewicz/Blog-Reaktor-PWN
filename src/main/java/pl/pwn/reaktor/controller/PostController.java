package pl.pwn.reaktor.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.pwn.reaktor.model.Article;
import pl.pwn.reaktor.service.ArticleService;
import pl.pwn.reaktor.service.TagService;

@Controller
@RequestMapping("/post")
public class PostController {

	private ArticleService articleService;
	private TagService tagService;

	@Autowired
	public PostController(TagService tagService, ArticleService articleService) {
		this.tagService = tagService;
		this.articleService = articleService;
	}

	@GetMapping
	public String post(Model model) {
		
		model.addAttribute("article", articleService.getFirst());
		return "post";
	}

	@GetMapping("/{id}")
	public String viewPost(@PathVariable long id, Model model) {

		model.addAttribute("article", articleService.findById(id));
		return "post";
	}

	@GetMapping("/article")
	public String article(Model model) {

		model.addAttribute("article", new Article());
		model.addAttribute("allTags", tagService.listAllTags());
		return "article/article";
	}

	@PostMapping("/article")
	public String article(@Valid @ModelAttribute Article article, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "article/article";
		}
		articleService.save(article);
		return "post";
	}

}
