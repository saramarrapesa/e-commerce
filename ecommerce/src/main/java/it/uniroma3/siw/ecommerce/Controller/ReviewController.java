package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Model.Review;
import it.uniroma3.siw.ecommerce.Repository.ProductRepository;
import it.uniroma3.siw.ecommerce.Service.ProductService;
import it.uniroma3.siw.ecommerce.Service.ReviewService;
import it.uniroma3.siw.ecommerce.Validator.ReviewValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewValidator reviewValidator;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GlobalController globalController;

    @PostMapping("/user/uploadReview/{id}")
    public String newReview(@Valid @ModelAttribute("review") Review review, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        this.reviewValidator.validate(review, bindingResult);
        if (!bindingResult.hasErrors()) {
            Product product = this.productRepository.findProductById(id);
            if (this.globalController.getUser() != null && !product.getReviews().contains(review)) {
                review.setUsername(this.globalController.getUser().getUsername());
                this.reviewService.saveReview(review);
                product.getReviews().add(review);
            }
            this.productService.saveProdotto(product);
            model.addAttribute("product", productRepository.findProductById(id));
            this.productService.function(model, product, this.globalController.getUser().getUsername());
            System.out.println("recensione aggiunta");
            return "redirect:/shop/viewproduct/{id}";
        } else {
            return "index";
        }
    }

    @GetMapping("user/deleteReview/{id}/{revId}")
    public String removeReview(Model model, @PathVariable("id") Long id, @PathVariable("revId") Long reviewId) {
        Product prodotto = this.productRepository.findProductById(id);
        Review review = this.reviewService.findById(reviewId);

        prodotto.getReviews().remove(review);
        this.reviewService.deleteReview(review);
        this.productService.saveProdotto(prodotto);
        this.productService.function(model, prodotto, this.globalController.getUser().getUsername());
        return "redirect:/shop/viewproduct/{id}";
    }


}

