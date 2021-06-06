package ru.jtc.jpalockmodetest.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jtc.jpalockmodetest.domain.Account;
import ru.jtc.jpalockmodetest.dto.AccountDto;
import ru.jtc.jpalockmodetest.dto.AccountUpdateRequest;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "accounts")
public class AccountResource {

    private final EntityManager entityManager;

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable("id") String id,
                              @RequestParam(value = "lockMode", required = false) LockModeType lockMode,
                              @RequestParam(value = "load", required = false) Long load) {
        Account account = entityManager.find(Account.class, id, resolveLockMode(lockMode));

        emulateLoad(load);

        return toDto(account);
    }

    @GetMapping("/{id}/for-update")
    public AccountDto getForUpdate(@PathVariable("id") String id,
                                   @RequestParam(value = "skip-locked", required = false) Boolean skipLocked,
                                   @RequestParam(value = "load", required = false) Long load) {
        String str = "select * from accounts where id = :id for update";
        if (skipLocked != null && skipLocked) {
            str += " skip locked";
        }

        Query query = entityManager.createNativeQuery(str, Account.class);
        query.setParameter("id", id);
        Account account = (Account) query.getSingleResult();

        emulateLoad(load);

        return toDto(account);
    }

    @PutMapping("/{id}")
    public AccountDto update(@PathVariable("id") String id,
                             @RequestBody AccountUpdateRequest request,
                             @RequestParam(value = "lockMode", required = false) LockModeType lockMode,
                             @RequestParam(value = "load", required = false) Long load) {
        Account account = entityManager.find(Account.class, id, resolveLockMode(lockMode));
        account.setAccountNumber(request.getAccountNumber());
        account.setBic(request.getBic());

        emulateLoad(load);

        entityManager.persist(account);

        return toDto(account);
    }

    private void emulateLoad(Long ms) {
        if (Objects.nonNull(ms)) {
            // need load emulation
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                // wrong, but ignore it :)
            }
        }
    }

    private LockModeType resolveLockMode(LockModeType userDefinedLockMode) {
        if (Objects.nonNull(userDefinedLockMode)) {
            return userDefinedLockMode;
        }
        return LockModeType.NONE;
    }

    private AccountDto toDto(Account account) {
        return new AccountDto()
                .setId(account.getId())
                .setBic(account.getBic())
                .setAccountNumber(account.getAccountNumber())
                .setVersion(account.getVersion());
    }
}
