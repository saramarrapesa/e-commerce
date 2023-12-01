package it.uniroma3.siw.ecommerce.Global;

import it.uniroma3.siw.ecommerce.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public  static List<Product> cart;
    static{
        cart= new ArrayList<Product>();
    }
}
