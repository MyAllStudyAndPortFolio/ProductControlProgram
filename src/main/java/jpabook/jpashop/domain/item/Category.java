package jpabook.jpashop.domain.item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name ="category_item",
            joinColumns = @JoinColumn(name = "Category_id"),
            inverseJoinColumns = @JoinColumn(name ="item_id"))
    private List<Item> items = new ArrayList<>();

    // 새롭게 양방향 연관관계를 한 것임
    // 다른 엔티티 매핑하는 방식과 같은 형식
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // ==연관관계 메서드 == //
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
