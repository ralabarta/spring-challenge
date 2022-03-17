package com.mytest.mytestapp.service;

import com.mytest.mytestapp.domain.AppRole;
import com.mytest.mytestapp.repository.AppRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppRoleServiceTest {

    @Mock
    private AppRoleRepository mockAppRoleRepository;

    private AppRoleService AppRoleServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        studentServiceUnderTest = new AppRoleService(mockAppRoleRepository);

        final Student AppRole1 =
                AppRole.builder()
                        .id(1)
                        .roleName("user 1")
						.build();

        Mockito.when(mockAppRoleRepository.findById(1))
                .thenReturn(Optional.of(AppRole1));
    }

    @Test
    public void testGetAppRoleById() {

        // Run the test
        final AppRole result = AppRoleServiceUnderTest.getStudentById(1);

        // Verify the results
        assertEquals("AppRole 1", result.getName());
        assertEquals(true, result.isActive());

    }

}
