package it.uniroma3.siw.ecommerce.Email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class EmailSenderService {

    @Autowired
   JavaMailSender mailSender;

    void sendMail(String name ,String from, String subject , String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("sara.marrapesaa@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        this.mailSender.send(message);
        System.out.println(("Mail sent successfully!"));
    }

    public void sendMailNewsLetter(String name , String to ){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom("sara.marrapesaa@mail.com");
                helper.setTo(to);
                helper.setSubject("MAKE (YOU) UP");

                /*
                * Add an inline resource. Use the true flag to indicate you need a multipart message*/
                helper.setText(
                        "<html><body><p>Cara/o " + name + "</p><br>>img src = 'cid:newsletter>'</body></html>",
                        true);
                helper.addInline("newsletter", new ClassPathResource("static/documenti/newsletter_page-0001.jpg"));
            }
        };
        try{
            mailSender.send(preparator);
            System.out.println("Message Sent");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
