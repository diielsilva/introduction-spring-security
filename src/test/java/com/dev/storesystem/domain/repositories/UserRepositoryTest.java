package com.dev.storesystem.domain.repositories;

import com.dev.storesystem.domain.enums.UserPermission;
import com.dev.storesystem.domain.factories.UserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(value = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void save_AssertUserIsSaved_WhenUserIsValid() {
        var user = repository.save(UserFactory.generateActiveUserEntityWithoutId());
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(1L, user.getId());
        assertEquals("Daniel Silva", user.getName());
        assertEquals("diel", user.getUsername());
        assertEquals("12345", user.getPassword());
        assertEquals(UserPermission.ROLE_ADMIN, user.getPermission());
        assertNull(user.getDeletedAt());
    }

    @Test
    void save_AssertUserIsUpdated_WhenUserIsValid() {
        var user = repository.save(UserFactory.generateActiveUserEntityWithoutId());
        Long id = user.getId();
        String name = user.getName();
        user.setName("Daniel Silva de Sousa");
        repository.save(user);
        assertEquals(id, user.getId());
        assertNotEquals(user.getName(), name);
    }

    @Test
    void save_AssertThrowsException_WhenUserIsInvalid() {
        var user = UserFactory.generateEmptyUserEntity();
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(user));
    }

    @Test
    void findByDeletedAtIsNull_AssertGetFilledPage_WhenHaveActiveUsers() {
        var user = UserFactory.generateActiveUserEntityWithoutId();
        repository.save(user);
        var activeUsers = repository.findByDeletedAtIsNull(PageRequest.of(0, 20));
        assertNotNull(activeUsers);
        assertEquals(1, activeUsers.getContent().size());
        assertEquals(1L, activeUsers.getContent().get(0).getId());
        assertEquals(user.getUsername(), activeUsers.getContent().get(0).getUsername());
        assertNull(activeUsers.getContent().get(0).getDeletedAt());
    }

    @Test
    void findByDeletedAtIsNull_AssertGetEmptyPage_WhenHaveNotActiveUsers() {
        repository.save(UserFactory.generateInactiveUserEntityWithoutId());
        var activeUsers = repository.findByDeletedAtIsNull(PageRequest.of(0, 20));
        assertNotNull(activeUsers);
        assertTrue(activeUsers.getContent().isEmpty());
    }

    @Test
    void findByDeletedAtIsNotNull_AssertGetFilledPage_WhenHaveInactiveUsers() {
        var user = UserFactory.generateInactiveUserEntityWithoutId();
        repository.save(user);
        var inactiveUsers = repository.findByDeletedAtIsNotNull(PageRequest.of(0, 20));
        assertNotNull(inactiveUsers);
        assertEquals(1, inactiveUsers.getContent().size());
        assertEquals(1L, inactiveUsers.getContent().get(0).getId());
        assertEquals(user.getUsername(), inactiveUsers.getContent().get(0).getUsername());
        assertNotNull(inactiveUsers.getContent().get(0).getDeletedAt());
    }

    @Test
    void findByDeletedAtIsNotNull_AssertGetEmptyPage_WhenHaveNotInactiveUsers() {
        repository.save(UserFactory.generateActiveUserEntityWithoutId());
        var inactiveUsers = repository.findByDeletedAtIsNotNull(PageRequest.of(0, 20));
        assertNotNull(inactiveUsers);
        assertTrue(inactiveUsers.getContent().isEmpty());
    }

    @Test
    void findByIdAndDeletedAtIsNull_AssertGetUser_WhenUserIdIsFound() {
        repository.save(UserFactory.generateActiveUserEntityWithoutId());
        var databaseUser = repository.findByIdAndDeletedAtIsNull(1L);
        assertNotNull(databaseUser);
        assertFalse(databaseUser.isEmpty());
        assertEquals(1, databaseUser.get().getId());
        assertNull(databaseUser.get().getDeletedAt());
    }

    @Test
    void findByIdAndDeletedAtIsNull_AssertDoNotGetUser_WhenUserIdIsNotFound() {
        repository.save(UserFactory.generateInactiveUserEntityWithoutId());
        var databaseUser = repository.findByIdAndDeletedAtIsNull(1L);
        assertNotNull(databaseUser);
        assertTrue(databaseUser.isEmpty());
    }

    @Test
    void findByIdAndDeletedAtIsNotNull_AssertGetUser_WhenUserIdIsFound() {
        repository.save(UserFactory.generateInactiveUserEntityWithoutId());
        var databaseUser = repository.findByIdAndDeletedAtIsNotNull(1L);
        assertNotNull(databaseUser);
        assertFalse(databaseUser.isEmpty());
        assertEquals(1, databaseUser.get().getId());
        assertNotNull(databaseUser.get().getDeletedAt());
    }

    @Test
    void findByIdAndDeletedAtIsNotNull_AssertDoNotGetUser_WhenUserIdIsNotFound() {
        repository.save(UserFactory.generateActiveUserEntityWithoutId());
        var databaseUser = repository.findByIdAndDeletedAtIsNotNull(1L);
        assertNotNull(databaseUser);
        assertTrue(databaseUser.isEmpty());
    }

    @Test
    void findByUsername_AssertGetUser_WhenUsernameIsFound() {
        repository.save(UserFactory.generateActiveUserEntityWithoutId());
        var databaseUser = repository.findByUsername("diel");
        assertNotNull(databaseUser);
        assertFalse(databaseUser.isEmpty());
        assertEquals("diel", databaseUser.get().getUsername());
    }

    @Test
    void findByUsername_AssertDoNotGetUser_WhenUsernameIsNotFound() {
        var databaseUser = repository.findByUsername("diel");
        assertNotNull(databaseUser);
        assertTrue(databaseUser.isEmpty());
    }
}