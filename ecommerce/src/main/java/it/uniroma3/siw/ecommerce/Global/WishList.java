package it.uniroma3.siw.ecommerce.Global;

import it.uniroma3.siw.ecommerce.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class WishList {
    public  static List<Product> wishlist;
    static{
        wishlist = new ArrayList<Product>();
    }
}
