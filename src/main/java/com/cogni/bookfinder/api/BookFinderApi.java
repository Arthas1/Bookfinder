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

    private JsonObject volumeDataCompacted;
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
    private Double rate;
    private String id;

    //BY URL
    private String is;
    private String c;
    private String q;

    //QUERY BINDING
    private  String isbnByUser;
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
    int iter = 0;
    private int counter;

    @Autowired
    public void api() throws IOException {


        Map<String, Double> rating = new HashMap<String, Double>();

        // PATH FROM USER OR PREDEFINED

        if (pathByUser == null) {

            pathByUser = "src/main/resources/public/books.json";
        }
    }

    @GetMapping("api/book")
    public String findByIsbnMapping(@RequestParam String is) {
        isbnByUser=is;
        System.out.println("Na wejsciu z URL ----------------" + isbnByUser);
        processingFile();
        if (chain!=null) {  finalJson = chain.replaceAll("\\\\\"", ""); } else { chain = " No results";}
        chain=finalJson;
        return chain;
    }

    @GetMapping("api/category")
    public String findByCategoryMapping(@RequestParam String c) {
        categoryByUser = c.toString();
        processingFile();

      if (chain!=null) {  finalJson = chain.replaceAll("\\\\\"", ""); } else { chain = " No results";}
chain=finalJson;
        return chain;
    }

    @GetMapping("api/search")
    public String findByQueryMapping(@RequestParam String q) {
        queryByUser = q;
        processingFile();
        if (chain!=null) {  finalJson = chain.replaceAll("\\\\\"", ""); } else { chain = " No results";}
        chain=finalJson;
        return chain;

    }

    @GetMapping("api/rating")
    public void findByRatingMapping() {


    }

    // READING JSON FILE

    public String processingFile() {

        System.out.println("is na poczatku metody processing -----"+ is);
        System.out.println("ISBN na poczatku metody processing -----"+ isbnByUser);
        System.out.println("c na poczatku metody processing -----"+ is);
        System.out.println("categoryByuser na poczatku metody processing -----"+ categoryByUser);

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
            e.printStackTrace();
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

// COMPOSE BOOK REPRESENTATION

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


// PERFORMING REQUESTS

          //  categoryByUser = "Indonesia";
           // this.isbnByUser=isbnByUser;
          // isbnByUser="9780226";
           // queryByUser="IndoNesia";

         //   System.out.println("ISBN BY USER Przed streamem" + isbnByUser);
            System.out.println("CATEGORY BY USER Przed streamem" + categoryByUser);

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

                System.out.println("DO PARSOWANIA --------------" +" numer " + counter + " --" + optStringStream );
                Gson gson = new Gson();

                for (int inj = 0; inj < optStringStream.size(); inj++) {

                    chainPart = gson.toJson(optStringStream.get(inj));
                   if (tempObject!=null){ objectToSave = tempObject + chainPart ; } else {objectToSave= chainPart;}
                    tempObject = objectToSave;
                }
            }

        }
        chain = tempObject;
        tempObject=null;
        return chain;
    }


    private List<VolumeInfo> findByIsbn() {
        System.out.println(" W Stream - find by user =" + isbnByUser);

        try {
            Stream<VolumeInfo> streamOfBookData = Stream.of(volumeFormatted);
            optStringStream = streamOfBookData.filter(VolumeInfo -> VolumeInfo.getIsbns().toString().contains(isbnByUser)).sorted().collect(Collectors.toList());
            if (optStringStream.size() > 0) {
                System.out.println("Na wyjsciu z Isbn " + optStringStream.toString());

                return optStringStream;

            }
        } catch (Exception e) {
            System.out.println("No results");
        }
        return null;
    }


    private List<VolumeInfo> findByCategory() {
       // System.out.println(" W Stream - find by Categories =" + categoryByUser);

        try {
            Stream<VolumeInfo> streamOfBookData = Stream.of(volumeFormatted);
            optStringStream = streamOfBookData.filter(VolumeInfo -> VolumeInfo.getCategories().toString().contains(categoryByUser))
                    .sorted().collect(Collectors.toList());
            if (optStringStream.size() != 0) {
           //     System.out.println("Na wyjsciu z Category " + optStringStream.toString());
                counter++;

                return optStringStream;
            }
        } catch (Exception e) {
        }
        return null;
    }


    private List<VolumeInfo> findByQuery() {
        try {
            Stream<VolumeInfo> streamOfBookData = Stream.of(volumeFormatted);
            optStringStream = streamOfBookData.filter(VolumeInfo -> VolumeInfo.toString().toLowerCase().contains(queryByUserToLowerCase))
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
