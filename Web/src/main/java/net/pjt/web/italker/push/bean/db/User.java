package net.pjt.web.italker.push.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pjt on 2017/7/16.
 * <p>
 * 用户的model，对应数据库
 */

@Entity
@Table(name = "TB_USER")
public class User {

    //表明这个entity的主key
    @Id
    @PrimaryKeyJoinColumn
    //主键生成储存的类型为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid生成器定义为uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    //用户名唯一
    @Column(nullable = false, unique = true, length = 128)
    private String name;

    //电话注册的，所以必须唯一
    @Column(length = 62, nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    //头像
    @Column
    private String portrait;

    @Column
    private String description;

    //性别有初始值，所有不为空
    @Column(nullable = false)
    private int sex = 0;

    @Column(unique = true)
    private String token;

    //用于推送的设备ID
    @Column
    private String pushId;

    @CreationTimestamp//定义为创建时间戳
    @Column(nullable = false)
    private LocalDateTime crateAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //最后一次收到消息的时间
    @Column(nullable = false)
    private LocalDateTime lastReceivedAt = LocalDateTime.now();


    // 我关注的人的列表方法
    // 对应的数据库表字段为TB_USER_FOLLOW.originId
    @JoinColumn(name = "originId")
    // 定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 1对多，一个用户可以有很多关注人，每一次关注都是一个记录
    // 1指本类user，多指userfollow
    // CascadeType all表示删除的话，会连同删除相关
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<>();


    // 关注我的人的列表
    // 对应的数据库表字段为TB_USER_FOLLOW.targetId
    @JoinColumn(name = "targetId")
    // 定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 1对多，一个用户可以被很多人关注，每一次关注都是一个记录
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();



    // 我所有创建的群
    // 对应的字段为：Group.ownerId
    @JoinColumn(name = "ownerId")
    // 懒加载集合方式为尽可能的不加载具体的数据，
    // 当访问groups.size()仅仅查询数量，不加载具体的Group信息
    // 只有当遍历集合的时候才加载具体的数据
    @LazyCollection(LazyCollectionOption.EXTRA)
    // FetchType.LAZY：懒加载，加载用户信息时不加载这个集合
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();

    public Set<UserFollow> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserFollow> following) {
        this.following = following;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public LocalDateTime getCrateAt() {
        return crateAt;
    }

    public void setCrateAt(LocalDateTime crateAt) {
        this.crateAt = crateAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getLastReceivedAt() {
        return lastReceivedAt;
    }

    public void setLastReceivedAt(LocalDateTime lastReceivedAt) {
        this.lastReceivedAt = lastReceivedAt;
    }
}
