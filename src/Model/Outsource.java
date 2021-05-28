/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author brysa
 */
/**
FUTURE ENHANCEMENT: I would like to see this inherited class have maybe one or two more unique variables. I am not sure how inventory tracking truly works, but surely
there are quite a few things that can separate an InHouse part from an Outsourced one.
* 
* RUNTIME-ERROR:
This class was straightforward from the UML diagram reference. I did not receive errors in this class.
* */


public class Outsource extends Part{
    
     private String companyName;
    
    public Outsource(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
    
    
}
