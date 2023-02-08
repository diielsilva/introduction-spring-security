package com.dev.storesystem.domain.helpers;

import com.dev.storesystem.domain.factories.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class DateManagerTest {
    @InjectMocks
    private DateManager dateManager;

    @Test
    void setSaveTime_AssertGetFilledDates_WhenSuccessful() {
        var user = UserFactory.generateEmptyUserEntity();
        dateManager.setSaveTime(user);
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void setUpdateTime_AssertGetFilledDates_WhenSuccessful() {
        var user = UserFactory.generateEmptyUserEntity();
        dateManager.setUpdateTime(user);
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void setDeleteTime_AssertGetFilledDates_WhenSuccessful() {
        var user = UserFactory.generateEmptyUserEntity();
        dateManager.setDeleteTime(user);
        assertNotNull(user.getDeletedAt());
    }
}