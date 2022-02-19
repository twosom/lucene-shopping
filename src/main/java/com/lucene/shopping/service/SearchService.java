package com.lucene.shopping.service;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class SearchService {

    public void search(Directory directory, String field, String userQuery, int maxHitCount) {
        TermQuery termQuery = new TermQuery(new Term(field, userQuery));

        try (IndexReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(termQuery, maxHitCount);
            ScoreDoc[] hits = docs.scoreDocs;

            System.out.println(hits.length + " 개의 결과를 찾았습니다.");

            for (int i = 0; i < hits.length; i++) {
                ScoreDoc hit = hits[i];
                int docId = hit.doc;
                Document doc = searcher.doc(docId);
                System.out.println((i + 1) + ". ");
                System.out.println("[제목] " + "\t" + doc.get("title"));
                System.out.println("[리뷰아이디] " + "\t" + doc.get("reviewId"));
                System.out.println("[나이] " + "\t" + doc.get("age"));
                System.out.println("[내용] " + "\t" + doc.get("reviewText"));
                if (StringUtils.hasText(doc.get("systemYear"))) {
                    System.out.println("[시스템연도] " + "\t" + doc.get("systemYear"));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
