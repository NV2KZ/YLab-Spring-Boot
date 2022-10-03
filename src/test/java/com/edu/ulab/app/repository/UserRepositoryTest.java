package com.edu.ulab.app.repository;

import com.edu.ulab.app.config.SystemJpaTest;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты репозитория {@link UserRepository}.
 */
@SystemJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    @DisplayName("Сохранить пользователя. Числа select и insert должны равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void insertPerson_thenAssertDmlCount() {
        //Given
        UserEntity person = new UserEntity();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        //When
        UserEntity result = userRepository.saveAndFlush(person);

        //Then
        assertThat(result.getAge()).isEqualTo(111);
        assertSelectCount(1);
        assertInsertCount(1);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Обновить пользователя. Числа select и update должны равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updatePerson_thenAssertDmlCount() {
        //Given
        UserEntity updatedPerson = new UserEntity();
        updatedPerson.setId(1001L);
        updatedPerson.setAge(111);
        updatedPerson.setTitle("Update reader");
        updatedPerson.setFullName("Update Test");

        Long id = 1001L;

        //When

        UserEntity result = userRepository.findById(id)
                .map(user -> updatedPerson)
                .map(userRepository::saveAndFlush)
                .orElseThrow(() -> new NotFoundException("entity not found"));


        //Then
        assertThat(result.getAge()).isEqualTo(111);
        assertThat(result.getTitle()).isEqualTo("Update reader");
        assertThat(result.getFullName()).isEqualTo("Update Test");
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(1);
        assertDeleteCount(0);
    }

    @DisplayName("Получить пользователя по id. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getPerson_thenAssertDmlCount() {
        //Given

        Long id = 1001L;

        //When
        UserEntity result = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("entity not found"));

        //Then
        assertThat(result.getAge()).isEqualTo(55);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Удалить пользователя. Числа select и delete должны равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void deletePerson_thenAssertDmlCount() {
        //Given

        Long id = 1001L;

        //When
        userRepository.deleteById(id);
        userRepository.flush();

        //Then
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(1);
    }
}
