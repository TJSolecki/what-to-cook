package com.what.to.cook.models;

import com.what.to.cook.json.NutritionJson;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Nutrition {
    @Id
    private Integer id;
    private String calories;
    private String carbohydrateContent;
    private String proteinContent;
    private String fatContent;
    private String saturatedFatContent;
    private String cholesterolContent;
    private String sodiumContent;
    private String fiberContent;
    private String sugarContent;
    private String unsaturatedFatContent;
    private String servingSize;

    public static Nutrition fromJson(NutritionJson json) {
        return builder()
                .calories(json.calories())
                .carbohydrateContent(json.carbohydrateContent())
                .proteinContent(json.proteinContent())
                .fatContent(json.fatContent())
                .saturatedFatContent(json.saturatedFatContent())
                .cholesterolContent(json.cholesterolContent())
                .sodiumContent(json.sodiumContent())
                .fiberContent(json.fiberContent())
                .sugarContent(json.sugarContent())
                .unsaturatedFatContent(json.unsaturatedFatContent())
                .servingSize(json.servingSize())
                .build();
    }
}
