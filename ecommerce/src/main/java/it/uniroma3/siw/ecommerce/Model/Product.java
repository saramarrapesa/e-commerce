package it.uniroma3.siw.ecommerce.Model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getName() {
        return name;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    private  Category category;
    private double price;
    private double weight;
    private String description;
    private String image;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
