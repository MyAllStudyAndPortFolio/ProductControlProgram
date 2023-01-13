package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="orders")
@Getter@Setter
public class Order {

    @Id @GeneratedValue
    @Column( name = "order_id")
    private Long id;
    //ManyToOne은 무조건 lazy로 바꾸어야 한다.

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "number_id")
    private Member member;

    //oneToMany는 lazy 가 default 이기 때문에 없애도 된다.
    @OneToMany (mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**CaseCadeType.All 하면 밑의 내용들과 같은 상황이 된다.
     * persist(orderItemA);
    persist(orderItemB);
    persist(orderItemC);
    persist(order)*/

    // order 만 persist 를 하면 이미 다 세팅을 해놓았기 때문에 다 persist 가 된다.
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn (name = "delivery_id")
    private Delivery delivery;

    //order_date -> 거의 다 언더바 캐멀 케이스로 바꾸어 버린다.
    private LocalDateTime orderDate;  // 주문시간

    @Enumerated (EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL];

    //== 연관관계 메서드 =//
    // 연관관계 편입 메서드라고 불린다. 양방향으로 되는걸 확인
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
