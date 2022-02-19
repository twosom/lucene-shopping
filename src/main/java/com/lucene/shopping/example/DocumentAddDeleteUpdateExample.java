package com.lucene.shopping.example;

import com.lucene.shopping.model.CustomerReviewModel;
import com.lucene.shopping.service.IndexService;
import com.lucene.shopping.service.SearchService;
import com.lucene.shopping.util.CsvLoader;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.List;

public class DocumentAddDeleteUpdateExample {

    public static void main(String[] args) throws IOException {
        DocumentAddDeleteUpdateExample example = new DocumentAddDeleteUpdateExample();

        List<CustomerReviewModel> reviewList = example.collectData();

        Directory directory = new ByteBuffersDirectory();

        IndexService indexService = new IndexService();
        indexService.indexCustomerReview(directory, reviewList);

        example.searchResult(directory);

        indexService.deleteCustomerReview(directory, "love");

        example.searchResult(directory);

        // 변경할 문서를 생성한다.
        Document doc = new Document();
        doc.add(new StringField("reviewId", "66", Field.Store.YES));
        doc.add(new TextField("clothingId", "862", Field.Store.YES));
        doc.add(new TextField("title", "Super cute and unique top_modified", Field.Store.YES));
        doc.add(new TextField("reviewText", "Just received this in the mail, tried it on and am smitten. i'm usually a l, but sometimes i'm a xl (if no stretch), in retailer tops. i bought this one in l and i'm sure glad i did. very flowy, stretchy and comfortable. i also bought the meda lace top from one september and they are very similar expect this is more of a t-shirt and the other is more of a blouse. i almost think i could've gotten a m in this because there is a lot of extra fabric at the chest which is usually never the issue for me", Field.Store.YES));
        doc.add(new TextField("rating", "4", Field.Store.YES));

        indexService.updateCustomerReview(directory, "66", doc);
        example.searchResult(directory);
    }

    private void searchResult(Directory directory) {
        SearchService searchService = new SearchService();
        searchService.search(directory, "reviewText", "shirt", 10);
    }

    private List<CustomerReviewModel> collectData() throws IOException {
        return CsvLoader.readReview().subList(0, 100);
    }
}
