package com.c.refactoring.movie;

import com.c.refactoring.StringUtils;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

public class Movie {

    private static final List<String> VALID_B_RATINGS_LIST
            = Arrays.asList("B1","B2","B3","B4");

    private String rating;

    public Movie(String rating) {
        super();
        this.rating = rating;
    }

    /*Axx or By
    Where x represents any digit between 0 and 9, and y represents 
    any digit between 1 and 4*/
    public boolean isValidRating() {
        if (isNull(this.getRating()))
            return false;

        //hmm??
        if(!isRatingNumeric())
            return false;

        if(isValidA())
            return true;

        if (isValidB()) {
            return true;
        }

        return false;
    }

    private boolean isRatingNumeric() {
        String ratingWithoutFirstChar = this.rating.substring(1);
        return StringUtils.isNumeric(ratingWithoutFirstChar);
    }

    private boolean isValidA() {
        String firstChar = this.rating.substring(0, 1);
        return firstChar.equalsIgnoreCase("A")
                && this.rating.length() == 3
                && isRatingNumeric();
    }

    private boolean isValidB() {
        return VALID_B_RATINGS_LIST.contains(rating);
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
