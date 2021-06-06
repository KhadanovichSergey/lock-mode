package ru.jtc.jpalockmodetest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccountUpdateRequest {
    private String accountNumber;
    private String bic;
}
