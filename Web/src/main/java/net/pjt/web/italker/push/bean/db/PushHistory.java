package net.pjt.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 消息推送历史记录表
 * Created by pjt on 2017/7/17.
 */

@Entity
@Table(name = "TB_PUSH_HISTORY")
public class PushHistory {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;


    // 推送的具体实体存储的都是JSON字符串
    // BLOB 是比TEXT更多的一个大字段类型
    @Lob
    @Column(nullable = false, columnDefinition = "BLOB")
    private String entity;


    // 推送的实体类型
    @Column(nullable = false)
    private int entityType;


    // 接收者
    // 接收者不允许为空
    // 一个接收者可以接收很多推送消息
    // FetchType.EAGER：加载一条推送消息的时候之间加载用户信息
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "receiverId")// 默认是：receiver_id
    private User receiver;
    @Column(nullable = false, updatable = false, insertable = false)
    private String receiverId;


    // 发送者
    // 发送者可为空，因为可能是系统消息
    // 一个发送者可以发送很多推送消息
    // FetchType.EAGER：加载一条推送消息的时候之间加载用户信息
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "senderId")
    private User sender;
    @Column(updatable = false, insertable = false)
    private String senderId;


    // 接收者当前状态下的设备推送ID
    // User.pushId 可为null
    @Column
    private String receiverPushId;

    // 定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义为更新时间戳，在创建时就已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    // 消息送达的时间，可为空
    @Column
    private LocalDateTime arrivalAt;
}
