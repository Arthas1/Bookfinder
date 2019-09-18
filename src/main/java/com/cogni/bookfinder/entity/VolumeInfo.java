package com.cogni.bookfinder.entity;


import java.util.List;


// This tells Hibernate to make a table out of this class
public class VolumeInfo {

    private List<String> isbns;
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

    private List<String> authors;
    private List<String> categories;


    public VolumeInfo(List<String> isbns, String title, String subtitle, String publisher, String publishedDate, String description, int pageCount, String thumbnailUrl, String language, String previewLink, double averageRating, List<String> authors, List<String> categories) {

        super();

  //  if (isbn != null) isbns = Arrays.asList(isbn);
         this.isbns = isbns;
  //      this.isbns=isbns;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnailUrl = thumbnailUrl;
        this.language = language;
        this.previewLink = previewLink;
        this.averageRating = averageRating;
        // if (author != null) authors = Arrays.asList(author);
        // if (categorie != null) categories = Arrays.asList(categorie);
        this.authors = authors;
        this.categories = categories;

    }



    public List<String> getIsbns() {
        return isbns;
    }

//     public List<Isbn> getIsbns() {
//        return isbns;
//    }
    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getCategories() {
        return categories;

    }
    @Override
    public String toString() {


        if (isbns!=null)    {}


            return '\n' +
                "   {" + '\n' +
                    "       isbns :" + isbns + '\n'  +
                "       title :" + title + '\n' +
                "       subtitle :" + subtitle + '\n' +
                "       publisher :" + publisher + '\n' +
                "       publishedDate :" + publishedDate + '\n' +
                "       description :" + description + '\n' +
                "       pageCount :" + pageCount + '\n' +
                "       thumbnailUrl :" + thumbnailUrl + '\n' +
                "       language :" + language + '\n' +
                "       previewLink :" + previewLink + '\n' +
                "       averageRating :" + averageRating +'\n' +
                "       authors :" + authors + '\n' +
                "       categories :" + categories + '\n' +
                "   }" +'\n';
    }
}
