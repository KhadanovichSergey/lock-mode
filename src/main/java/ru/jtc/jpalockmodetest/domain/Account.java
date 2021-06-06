package ru.jtc.jpalockmodetest.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Accessors(chain = true)
public class Account {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "account_number")
    String accountNumber;

    @Column(name = "bic")
    String bic;

    @Version
    @Column(name = "version")
    Long version;
}
