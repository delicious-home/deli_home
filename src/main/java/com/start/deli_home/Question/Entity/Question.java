package com.start.deli_home.Question.Entity;

import com.start.deli_home.Member.Entity.Member;

//import com.start.deli_home.MultiPhoto.Entity.MultiPhoto;
import com.start.deli_home.Review.Entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String shopName;

    @Column
    private String foodType;

    @Column
    private String address;

    @Column
    private String introduce;

    @Column
    private String time;

    @Column
    private String menu;

    @Column
    private String phone;

    @ManyToOne
    private Member author;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    public void setCreateDate(LocalDateTime now) {
    }

    private String category;

    @Column(name = "create_date")
    private LocalDateTime createDate;

//    private String thumbnailImg;

//    public void thumbnailImg(String thumbnailRelPath) {
//        this.thumbnailImg = thumbnailRelPath;
//    }

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToMany
    private Set<Member> likers;

    private boolean likedByCurrentUser;

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
    }
}
