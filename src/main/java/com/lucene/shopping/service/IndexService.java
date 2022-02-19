package com.lucene.shopping.service;

import com.lucene.shopping.model.CustomerReviewModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class IndexService {

    /**
     * 회원 리뷰 데이터를 색인합니다.
     *
     * @param directory  색인하려는 디렉토리
     * @param reviewList 색인하려는 리뷰 목록
     */
    public void indexCustomerReview(Directory directory, List<CustomerReviewModel> reviewList) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            reviewList.forEach(review -> {
                // Document 생성
                addDocument(indexWriter, review);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteCustomerReview(Directory index, String word) {
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter indexWriter = new IndexWriter(index, config)) {
            System.out.println("before delete. numDocs = " + indexWriter.getDocStats().numDocs);

            Query q = new QueryParser("reviewText", analyzer).parse(word);

            indexWriter.deleteDocuments(q);

            System.out.println("after delete. numDocs = " + indexWriter.getDocStats().numDocs);

            indexWriter.flush();

            System.out.println("after delete and flush. numDocs = " + indexWriter.getDocStats().numDocs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원리뷰 문서를 추가합니다.
     *
     * @param indexWriter 인덱스라이터
     * @param review      회원리뷰
     */
    private static void addDocument(IndexWriter indexWriter, CustomerReviewModel review) {
        Document doc = new Document();
        doc.add(new StringField("reviewId", review.getReviewId(), Field.Store.YES));
        doc.add(new TextField("clothingId", review.getClothingId(), Field.Store.YES));
        doc.add(new TextField("title", review.getTitle(), Field.Store.YES));
        doc.add(new TextField("reviewText", review.getReviewText(), Field.Store.YES));
        doc.add(new StringField("age", review.getAge(), Field.Store.YES));
        doc.add(new StringField("systemYear", DateTools.dateToString(new Date(), DateTools.Resolution.DAY), Field.Store.YES));

        try {
            indexWriter.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateCustomerReview(Directory index, String reviewId, Document document) {
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter writer = new IndexWriter(index, config)) {

            // 수정하기 전 색인된 도큐먼트 수를 확인한다.
            System.out.println("before update. numDocs=" + writer.getDocStats().numDocs);

            // 수정할 도큐먼트를 설정한다.
            Term updateDocumentReview = new Term("reviewId", reviewId);

            // 수정할 도큐먼트를 매개변수로 받은 도큐먼트로 교체한다.
            writer.updateDocument(updateDocumentReview, document);

            // 수정 후 도큐먼트 수를 확인한다.
            System.out.println("after update. numDocs=" + writer.getDocStats().numDocs);

            // 수정 작업에 대한 플러시를 수행한다.
            writer.flush();

            // 플러시 후 도큐먼트 수를 확인한다.
            System.out.println("after update and flush. numDocs=" + writer.getDocStats().numDocs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
