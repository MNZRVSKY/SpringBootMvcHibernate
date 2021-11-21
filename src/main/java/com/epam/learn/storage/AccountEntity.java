package com.epam.learn.storage;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
@DynamicUpdate
public class AccountEntity {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_money")
    private float userMoney;

    public AccountEntity() {
        this.userId = -1;
        this.userMoney = 0;
    }

    public AccountEntity(int userId, float userMoney) {
        this.userId = userId;
        this.userMoney = userMoney;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(float userMoney) {
        this.userMoney = userMoney;
    }
}
