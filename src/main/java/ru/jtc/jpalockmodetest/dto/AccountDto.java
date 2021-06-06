package ru.jtc.jpalockmodetest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccountDto {
    private String id;
    private String accountNumber;
    private String bic;
    private Long version;
}
