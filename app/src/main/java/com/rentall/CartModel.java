package com.rentall;



public class CartModel
{
    String Name,Price,Refund,ImageUrl;
<<<<<<< HEAD
//    CartModel()
//    {
//    }
=======
    CartModel()
    {
    }
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
    public CartModel(String Name, String Price, String Refund, String ImageUrl) {
        this.Name = Name;
        this.Price = Price;
        this.Refund = Refund;
        this.ImageUrl = ImageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRefund() {
        return Refund;
    }

    public void setRefund(String refund) {
        Refund = refund;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
