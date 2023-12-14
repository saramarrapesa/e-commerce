package it.uniroma3.siw.ecommerce.Email;

import it.uniroma3.siw.ecommerce.Model.Contact;
import it.uniroma3.siw.ecommerce.Model.Newsletter;
import it.uniroma3.siw.ecommerce.Repository.ContactRepository;
import it.uniroma3.siw.ecommerce.Repository.NewsletterRepository;
import it.uniroma3.siw.ecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private NewsletterRepository newsletterRepository;
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value={"/contact"})
    public  String sendMail(@ModelAttribute("contact") Contact contact, ModelMap modelMap, Model model){

        try {
            String name = contact.getName();
            this.emailSenderService.sendMail(
                    name,
                    contact.getEmail(),
                    contact.getSubject(),
                    contact.getBody()
            );
            model.addAttribute("contact", contact);
            contactRepository.save(contact);
        }catch (Exception e){
            modelMap.put("errors", e.getMessage());
        }
        return "contact";
    }

    @PostMapping(value = "/newsletter")
    public String sendnewsLetter(@ModelAttribute("newsletter") Newsletter newsletter, ModelMap modelMap , Model model){
        try {
            String name = newsletter.getName();
            this.emailSenderService.sendMailNewsLetter(name , newsletter.getEmail());
            model.addAttribute("newsletter", newsletter);
            newsletterRepository.save(newsletter);
        }catch (Exception e){
            modelMap.put("errors", e.getMessage());
        }
        model.addAttribute("categories", categoryService.getAllCategories() );
        return "index";
    }

}
