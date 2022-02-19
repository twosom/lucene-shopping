package com.lucene.shopping.util;

import com.lucene.shopping.model.CustomerReviewModel;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvLoader {

    /**
     * resources 디렉토리에 있는 리뷰데이터 cvs를 읽어옵니다.<br/>
     * 읽은 리뷰데이터는 bean으로 변환되어 리스트로 반환됩니다.
     *
     * @return bean으로 변환된 리뷰 데이터 리스트
     */
    public static List<CustomerReviewModel> readReview() throws IOException {
        String filePath = new ClassPathResource("WomensClothingSample.csv").getFile().getAbsolutePath();
        List<CustomerReviewModel> reviewList = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            var csvToBean = new CsvToBeanBuilder<CustomerReviewModel>(reader)
                    .withType(CustomerReviewModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            reviewList = csvToBean.parse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewList;
    }
}
