package com.start.deli_home.Question.QuestionForm;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;

    @NotEmpty(message="가게 이름은 필수항목입니다.")
    private String shopName;

    @NotEmpty(message="음식전문은 필수항목입니다.")
    private String foodType;

    @NotEmpty(message="카테고리는 필수항목입니다.")
    private String category;

    @NotEmpty(message = "매장 주소를 입력해 주세요")
    private String address;

    @NotEmpty(message = "소개정보를 입력해 주세요")
    private String introduce;

    @NotEmpty(message = "운영시간을 입력해 주세요")
    private String time;

    @NotEmpty(message = "대표 메뉴를 입력해 주세요")
    private String menu;

    @NotEmpty(message = "가게 전화번호를 입력해 주세요")
    private String phone;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private List<MultipartFile> images;

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
