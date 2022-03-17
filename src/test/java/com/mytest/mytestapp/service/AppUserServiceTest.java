package com.mytest.mytestapp.service;

import com.mytest.mytestapp.domain.AppUser;
import com.mytest.mytestapp.repository.AppUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppUserServiceTest {

    @Mock
    private AppUserRepository mockAppUserRepository;

    private AppUserService appuserServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        studentServiceUnderTest = new AppUserService(mockAppUserRepository);

        final Student appuser1 =
                AppUser.builder()
                        .id(1)
                        .username("user 1")
						.password("pass 1")
						.firstName("First Name 1")
                        .lastName("Last Name 1")
                        .email("a@a.com")
						.phoneNumber("123456789")
						.lastLogindate("2022-01-01")
                        .build();

        Mockito.when(mockAppUserRepository.findById(1))
                .thenReturn(Optional.of(appuser1));
    }

    @Test
    public void testGetAppUserById() {

        // Run the test
        final AppUser result = appuserServiceUnderTest.getStudentById(1);

        // Verify the results
        assertEquals("appuser 1", result.getName());
        assertEquals(true, result.isActive());

    }

}
