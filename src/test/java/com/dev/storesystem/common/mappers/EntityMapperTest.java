package com.dev.storesystem.common.mappers;

import com.dev.storesystem.common.mappers.impl.EntityMapperImpl;
import com.dev.storesystem.domain.factories.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EntityMapperTest {
    @InjectMocks
    private EntityMapperImpl entityMapper;

    @Test
    void mapUser_AssertGetCorrectUser_WhenMapIsSuccessful() {
        var dto = UserFactory.generateValidSaveUserDto();
        var entity = entityMapper.fromSaveUserDtoToUserEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getUsername(), entity.getUsername());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getPermission(), entity.getPermission().name());
    }

    @Test
    void mapUser_AssertGetCorrectDto_WhenMapIsSuccessful() {
        var entity = UserFactory.generateActiveUserEntityWithId();
        var dto = entityMapper.fromUserEntityToShowUserDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getPermission(), dto.getPermission());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(entity.getDeletedAt(), dto.getDeletedAt());
    }

    @Test
    void mapUser_AssertThrowsException_WhenMapFail() {
        var dto = UserFactory.generateSaveUserDtoWithInvalidPermission();
        assertThrows(InvalidUserPermissionException.class, () -> entityMapper.fromSaveUserDtoToUserEntity(dto));
    }
}