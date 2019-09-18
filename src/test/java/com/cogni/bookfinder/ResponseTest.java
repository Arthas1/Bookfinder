package com.cogni.bookfinder;

import com.cogni.bookfinder.api.BookFinderApi;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class ResponseTest {

 //Check response for non existing Category

    BookFinderApi t = new BookFinderApi();
    String c, is, p;

    @Test
    public void findByCategoryTest() {
        c="Polska";
        p="src/main/resources/public/books.json";
        assertEquals("No result found", t.findByCategoryMapping(c,p));
    }

    @Test
    public void findByIsbnTest() {
        is="12345";

        assertEquals("No result found", t.findByIsbnMapping(is,p));
    }




}