package com.lucene.shopping.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerReviewModel {

    /**
     * 리뷰 ID
     */
    @CsvBindByPosition(position = 0)
    private String reviewId;

    /**
     * 상품 ID
     */
    @CsvBindByPosition(position = 1)
    private String clothingId;

    /**
     * 리뷰어 나이
     */
    @CsvBindByPosition(position = 2)
    private String age;

    /**
     * 상품 후기 제목
     */
    @CsvBindByPosition(position = 3)
    private String title;

    /**
     * 상품 후기
     */
    @CsvBindByPosition(position = 4)
    private String reviewText;

    /**
     * 상품 후기 점수
     */
    @CsvBindByPosition(position = 5)
    private String rating;

    /**
     * 리뷰어 상품 추천 여부
     */
    @CsvBindByPosition(position = 6)
    private String recommendedInd;

    /**
     * 리뷰에 대한 '좋아요' 카운트
     */
    @CsvBindByPosition(position = 7)
    private String positiveFeedbackCount;

    /**
     * 상품의 분류명
     */
    @CsvBindByPosition(position = 8)
    private String divisionName;

    /**
     * 상품 부서명
     */
    @CsvBindByPosition(position = 9)
    private String departmentName;

    /**
     * 상품 종류
     */
    @CsvBindByPosition(position = 10)
    private String className;
}
