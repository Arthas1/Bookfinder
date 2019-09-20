package com.cogni.bookfinder.api;

import com.cogni.bookfinder.entity.VolumeInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BookFinderApi {

    private String jsonFileInString;
    private List<String> authorsArray;
    private List<String> categoriesArray;
    private String title;
    private String subtitle;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private String thumbnailUrl;
    private String language;
    private String previewLink;
    private double averageRating;

    private JsonArray isbnJsonArray;
    private JsonObject s;
    private JsonArray authorsJsonArray;
    private JsonArray categoriesJsonArray;
    private JsonObject j;

    private List<String> Isbn;
    private VolumeInfo volumeFormatted;
    private String jsonInString;
    private String isbnJ;
    private ArrayList<String> isbnArray;

    private boolean ratingRequestByUser;

    private Stream<String> stringStream;
    private List<VolumeInfo> optStringStream;
    private List<VolumeInfo> parsedChains;
    private CharSequence queryByUserToLowerCase;
    private Map<String, Double> rating;

    //BY URL
    private String is;
    private String c;
    private String q;

    //QUERY BINDING
    private String isbnByUser;
    private String queryByUser;
    private String categoryByUser;
    private String pathByUser;
    private String finalJson;
    private String parsedString;
    private String parsed;
    private String chain;
    private String chainPart;
    private String objectToSave;
    private String tempObject;
    private int counter;

    @Autowired
    public void api() throws IOException {

    }

    @GetMapping("api/book")
    public String findByIsbnMapping(@RequestParam String is, String p) {
        isbnByUser = is;
        pathByUser = p;
        processingFile();
        response();
        return finalJson;
    }

    @GetMapping("api/category")
    public String findByCategoryMapping(@RequestParam String c, String p) {

        categoryByUser = c;
        pathByUser = p;
        processingFile();
        response();
        return finalJson;
    }

    @GetMapping("api/search")
    public String findByQueryMapping(@RequestParam String q, String p) {
        queryByUser = q;
        pathByUser = p;
        processingFile();
        response();
        return finalJson;
    }

    @GetMapping("api/rating")
    public void findByRatingMapping() {
    }

    public String response() {
        counter = 0;
        if (chain != null) {
            finalJson = chain.replaceAll("\\\\\"", "").replace("}{", "},{");
        } else {
            finalJson = "No result found";
        }
        return finalJson;
    }

    // READING & PROCESSING JSON FILE  ----------------------------------------------------------------------------------------

    public String processingFile() {

// path for file
        System.out.println(pathByUser);

        if (pathByUser == null) {

            pathByUser = "src/main/resources/public/books.json";
        }

        FileInputStream fileInputStream = null;

        try {

            fileInputStream = new FileInputStream(pathByUser);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;

            try {

                while ((line = bufferedReader.readLine()) != null) {

                    sb.append(line);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            jsonFileInString = sb.toString();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

// JSON FILE - FINDING volumeInfo FOR STREAM


        JsonObject jsonObject = new JsonParser().parse(jsonFileInString).getAsJsonObject();

        JsonArray itemsJsonArray = jsonObject.getAsJsonArray("items");

        int size = itemsJsonArray.size();

        for (int i = 0; i < size; i++) {

            j = (JsonObject) itemsJsonArray.get(i);

            s = (JsonObject) j.get("volumeInfo");

            isbnJsonArray = s.getAsJsonArray("industryIdentifiers");

            authorsJsonArray = s.getAsJsonArray("authors");

            categoriesJsonArray = s.getAsJsonArray("categories");

// COMPOSE BOOK REPRESENTATION ------------------------------------------------

            try {
                int isbnSize = isbnJsonArray.size();
                isbnArray = new ArrayList<String>();

                for (int in = 0; in < isbnSize; in++) {

                    JsonObject identifierList = (JsonObject) isbnJsonArray.get(in);
                    String isbnType = identifierList.get("type").toString();
                    isbnJ = new String(identifierList.get("identifier").toString());

                    isbnArray.add(isbnJ);
                }

            } catch (Exception ne1) {
            }
            try {
                title = s.get("title").toString();
            } catch (Exception ne1) {
            }
            try {
                subtitle = s.get("subtitle:").toString();
            } catch (Exception ne1) {
            }
            try {
                publisher = s.get("publisher").toString();
            } catch (Exception ne1) {
            }
            try {
                publishedDate = s.get("publishedDate").toString();
            } catch (Exception ne1) {
            }
            try {
                description = s.get("description").toString();
            } catch (Exception ne1) {
            }
            try {
                pageCount = s.get("pageCount").getAsInt();
            } catch (Exception ne1) {
            }
            try {
                thumbnailUrl = s.get("thumbnailUrl").toString();
            } catch (Exception ne1) {
            }
            try {
                language = s.get("language").toString();
            } catch (Exception ne1) {
            }
            try {
                previewLink = s.get("previewLink").toString();
            } catch (Exception ne1) {
            }
            try {
                averageRating = s.get("averageRating").getAsDouble();
            } catch (NullPointerException ne) {
            }
            try {
                int authorsSize = authorsJsonArray.size();
                authorsArray = new ArrayList<String>();
                for (int id = 0; id < authorsSize; id++) {

                    String authorListed = authorsJsonArray.get(id).toString();
                    authorsArray.add(authorListed);
                }
            } catch (Exception ne1) {
            }
            try {

                int categoriesSize = categoriesJsonArray.size();
                categoriesArray = new ArrayList<String>();
                for (int ic = 0; ic < categoriesSize; ic++) {
                    String categoriesListed = categoriesJsonArray.get(ic).toString();
                    categoriesArray.add(categoriesListed);

                }
            } catch (Exception ne1) {
            }

// MAKING AN EXPECTED FORM OF OBJECT

            volumeFormatted = new VolumeInfo(isbnArray, title, subtitle, publisher, publishedDate, description, pageCount,
                    thumbnailUrl, language, previewLink, averageRating, authorsArray, categoriesArray);

// PERFORMING REQUESTS ------------------------------------------------

            if (isbnByUser != null) {
                findByIsbn();
            }

            if (categoryByUser != null) {
                findByCategory();
            }

            if (queryByUser != null) {
                queryByUserToLowerCase = queryByUser.toLowerCase();
                findByQuery();
            }

            if (ratingRequestByUser == true) {
                findByRating();
            }

            // PREPARING NEW JSON TO RESPONSE

            if (optStringStream != null && optStringStream.size() > 0) {

                Gson gson = new Gson();

                for (int inj = 0; inj < optStringStream.size(); inj++) {
                    counter++;
                    chainPart = gson.toJson(optStringStream.get(inj));
                    if (tempObject != null) {
                        objectToSave = tempObject + chainPart;
                    } else {
                        objectToSave = chainPart;
                    }
                    tempObject = objectToSave;
                }
            }
        }
        // check if Json should be clamped because of multiple parts;
        if (counter > 1) {
            chain = "[" + tempObject + "]";
        } else {
            chain = tempObject;
        }
        tempObject = null;
        counter = 0;

        queryByUser = null;
        categoryByUser = null;
        isbnByUser = null;

        return chain;
    }

    // STREAM OPERATIONS  ------------------------------------------------

    private List<VolumeInfo> findByIsbn() {

        try {
            Stream<VolumeInfo> streamOfBookData = Stream.of(volumeFormatted);
            optStringStream = streamOfBookData.filter(VolumeInfo -> VolumeInfo.getIsbns().toString()
                    .contains(isbnByUser))
                    .sorted().collect(Collectors.toList());
            if (optStringStream.size() > 0) {
                return optStringStream;
            }
        } catch (Exception e) {
        }
        return null;
    }

    private List<VolumeInfo> findByCategory() {

        try {
            Stream<VolumeInfo> streamOfBookData = Stream.of(volumeFormatted);
            optStringStream = streamOfBookData.filter(VolumeInfo -> VolumeInfo.getCategories().toString()
                    .contains(categoryByUser))
                    .sorted().collect(Collectors.toList());
            if (optStringStream.size() > 0) {
                return optStringStream;
            }
        } catch (Exception e) {
        }
        return null;
    }

    private List<VolumeInfo> findByQuery() {
        try {
            Stream<VolumeInfo> streamOfBookData = Stream.of(volumeFormatted);
            optStringStream = streamOfBookData.filter(VolumeInfo -> VolumeInfo.toString().toLowerCase()
                    .contains(queryByUserToLowerCase))
                    .sorted().collect(Collectors.toList());
            if (optStringStream.size() > 0) {

                return optStringStream;
            }
        } catch (Exception e) {
        }
        return null;
    }

    private List<VolumeInfo> findByRating() {
// TODO
        return null;
    }
}
