package com.lucene.shopping.example;

import com.lucene.shopping.model.CustomerReviewModel;
import com.lucene.shopping.service.IndexService;
import com.lucene.shopping.service.SearchService;
import com.lucene.shopping.util.CsvLoader;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.List;

public class ClothingReviewExample {

    public static void main(String[] args) throws IOException {
        List<CustomerReviewModel> reviewList = CsvLoader.readReview();

        Directory directory = new ByteBuffersDirectory();

        IndexService indexService = new IndexService();
        indexService.indexCustomerReview(directory, reviewList);

        SearchService searchService = new SearchService();

        String userQuery = "shirt";
        String fieldName = "reviewText";
        int maxHitCount = 10;
        searchService.search(directory, fieldName, userQuery, maxHitCount);
    }

}
