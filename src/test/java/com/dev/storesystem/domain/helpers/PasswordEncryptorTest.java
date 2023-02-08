package com.dev.storesystem.domain.helpers;

import com.dev.storesystem.domain.factories.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
class PasswordEncryptorTest {
    @InjectMocks
    private PasswordEncryptor passwordEncryptor;

    @Test
    void encryptUserPassword_AssertPasswordIsEncrypted_WhenSuccessful() {
        var user = UserFactory.generateActiveUserEntityWithoutId();
        var password = user.getPassword();
        passwordEncryptor.encryptUserPassword(user);
        assertNotEquals(user.getPassword(), password);
    }
}