package com.ingsw.provatab.com.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private int productID;
    private String name;
    private String description;
    private double price;
    private List<String> path_img;
    private boolean onSale;
    private int rating;
    private String category;
    private List<String> tag;
    private List<String> colors;
    private List<Integer> feedback;
    private boolean isDeleted;

    public Item() {
    }

    public Item(int productID, String name, String description, double price, ArrayList<String> path_imgs, boolean onSale,
                int rating, String category, ArrayList<String> tag, ArrayList<String> colors, ArrayList<Integer> feedback) {
        setID(productID);
        setName(name);
        setDescription(description);
        setPrice(price);
        setPath(path_imgs);
        setOnSale(onSale);
        setRating(rating);
        setCategory(category);
        setTag(tag);
        setColors(colors);
        setFeedback(feedback);
    }

    public Item(int productID, String name, String description, double price, ArrayList<String> path_imgs, boolean onSale,
                int rating, String category, ArrayList<String> tag, ArrayList<String> colors, ArrayList<Integer> feedback, Boolean isDeleted) {
        setID(productID);
        setName(name);
        setDescription(description);
        setPrice(price);
        setPath(path_imgs);
        setOnSale(onSale);
        setRating(rating);
        setCategory(category);
        setTag(tag);
        setColors(colors);
        setFeedback(feedback);
        setIsDeleted();
    }

    public void setID(int productID) {
        this.productID = productID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPath(ArrayList<String> path) {
        if (path != null) {
            this.path_img.addAll(path);
        }else{
            new ArrayList<String>().addAll(path);
        }
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(ArrayList<String> tags) {
        if (tags != null) {
            this.tag.addAll(tags);
        }else{
            new ArrayList<String>().addAll(tags);
        }


    }
    public void setColors(ArrayList<String> colors) {
        if (colors != null) {
            this.colors.addAll(colors);
        }else{
            new ArrayList<String>().addAll(colors);
        }
    }

    public void setFeedback(ArrayList<Integer> feedback) {
        if (feedback != null) {
            this.feedback.addAll(feedback);
        }else{
            new ArrayList<Integer>().addAll(feedback);
        }

    }

    public int getID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getPath() {
        return path_img;
    }

    public boolean getOnSale() {
        return onSale;
    }

    public int getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getTag() {
        return tag;
    }

    public List<String> getColors() {
        return colors;
    }

    public List<Integer> getFeedback() {
        if (feedback==null)
            feedback=new ArrayList<Integer>();
        return feedback;
    }
    public String getDescriptionHTML() {
        return "<html><body style=\"text-align:justify\">"+description.replaceAll("(\r\n|\n)","<br/>")+"</body></html>";
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public Boolean setIsDeleted() {
        return isDeleted;
    }

}