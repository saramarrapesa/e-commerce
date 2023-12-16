package it.uniroma3.siw.ecommerce.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Newsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsEmail() {
        return newsEmail;
    }

    public void setNewsEmail(String newsEmail) {
        this.newsEmail = newsEmail;
    }

    private String newsName;
    private String newsEmail;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
