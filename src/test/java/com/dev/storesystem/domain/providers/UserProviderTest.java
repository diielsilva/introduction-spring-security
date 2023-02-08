package com.dev.storesystem.domain.providers;

import com.dev.storesystem.domain.providers.impl.UserProviderImpl;
import com.dev.storesystem.domain.repositories.UserRepository;
import com.dev.storesystem.domain.factories.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
class UserProviderTest {
    @InjectMocks
    private UserProviderImpl provider;
    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        var activeUser = UserFactory.generateActiveUserEntityWithId();
        var inactiveUser = UserFactory.generateInactiveUserEntityWithId();
        var activeUsers = new PageImpl<>(List.of(activeUser));
        var inactiveUsers = new PageImpl<>(List.of(inactiveUser));

        BDDMockito.when(repository.save(ArgumentMatchers.any()))
                .thenReturn(UserFactory.generateActiveUserEntityWithId());
        BDDMockito.when(repository.findByDeletedAtIsNull(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(activeUsers);
        BDDMockito.when(repository.findByDeletedAtIsNotNull(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(inactiveUsers);
        BDDMockito.when(repository.findByIdAndDeletedAtIsNull(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(activeUser));
        BDDMockito.when(repository.findByIdAndDeletedAtIsNotNull(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(inactiveUser));
        BDDMockito.when(repository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(activeUser));
    }

    @Test
    void save_AssertDoesNotThrowsException_WhenUserIsSaved() {
        var user = UserFactory.generateActiveUserEntityWithoutId();
        assertDoesNotThrow(() -> provider.save(user));
    }

    @Test
    void findAllActive_AssertGetFilledPage_WhenHaveActiveUsers() {
        var activeUsers = provider.findAllActive(PageRequest.of(0, 20));
        assertNotNull(activeUsers);
        assertFalse(activeUsers.getContent().isEmpty());
        assertEquals(1, activeUsers.getContent().size());
        assertEquals(1L, activeUsers.getContent().get(0).getId());
        assertEquals("diel", activeUsers.getContent().get(0).getUsername());
        assertNull(activeUsers.getContent().get(0).getDeletedAt());
    }

    @Test
    void findAllActive_AssertGetEmptyPage_WhenHaveNotActiveUsers() {
        BDDMockito.when(repository.findByDeletedAtIsNull(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        var activeUsers = provider.findAllActive(PageRequest.of(0, 20));
        assertNotNull(activeUsers);
        assertTrue(activeUsers.getContent().isEmpty());
    }

    @Test
    void findAllInactive_AssertGetFilledPage_WhenHaveInactiveUsers() {
        var inactiveUsers = provider.findAllInactive(PageRequest.of(0, 20));
        assertNotNull(inactiveUsers);
        assertFalse(inactiveUsers.isEmpty());
        assertEquals(1, inactiveUsers.getContent().size());
        assertEquals(1L, inactiveUsers.getContent().get(0).getId());
        assertEquals("diel", inactiveUsers.getContent().get(0).getUsername());
        assertNotNull(inactiveUsers.getContent().get(0).getDeletedAt());
    }

    @Test
    void findAllInactive_AssertGetEmptyPage_WhenHaveNotInactiveUsers() {
        BDDMockito.when(repository.findByDeletedAtIsNotNull(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        var inactiveUsers = provider.findAllInactive(PageRequest.of(0, 20));
        assertNotNull(inactiveUsers);
        assertTrue(inactiveUsers.getContent().isEmpty());
    }

    @Test
    void findActiveById_AssertGetUser_WhenUserIdIsFound() {
        var activeUser = provider.findActiveById(1L);
        assertNotNull(activeUser);
        assertEquals(1L, activeUser.getId());
        assertEquals("diel", activeUser.getUsername());
        assertNull(activeUser.getDeletedAt());
    }

    @Test
    void findActiveById_AssertThrowsException_WhenUserIdIsNotFound() {
        BDDMockito.when(repository.findByIdAndDeletedAtIsNull(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> provider.findActiveById(1L));
    }

    @Test
    void findInactiveById_AssertGetUser_WhenUserIdIsFound() {
        var inactiveUser = provider.findInactiveById(1L);
        assertNotNull(inactiveUser);
        assertEquals(1L, inactiveUser.getId());
        assertEquals("diel", inactiveUser.getUsername());
        assertNotNull(inactiveUser.getDeletedAt());
    }

    @Test
    void findInactiveById_AssertThrowsException_WhenUserIdIsNotFound() {
        BDDMockito.when(repository.findByIdAndDeletedAtIsNotNull(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> provider.findInactiveById(1L));
    }

    @Test
    void findByUsername_AssertGetUser_WhenUsernameIsFound() {
        var user = provider.findByUsername("diel");
        assertNotNull(user);
        assertFalse(user.isEmpty());
        assertEquals("diel", user.get().getUsername());
    }

    @Test
    void findByUsername_AssertNotGetUser_WhenUsernameIsNotFound() {
        BDDMockito.when(repository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        var user = provider.findByUsername("diel");
        assertNotNull(user);
        assertTrue(user.isEmpty());
    }

}