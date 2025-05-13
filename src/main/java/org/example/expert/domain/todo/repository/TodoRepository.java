package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(
        @Param("todoId") Long todoId
    );

    // 레벨 3 - weather로 검색
    @Query("select t from Todo t where t.weather like %:weather% ")
    Page<Todo> findByWeather(
        @Param("weather") String weather,
        Pageable pageable
    );

    // 레벨 3 - 수정일 기준 기간 검색 ( 기간 설정 가능 )
    @Query("select t from Todo t where t.modifiedAt between :start and :end")
    Page<Todo> findByModifiedAt(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        Pageable pageable
    );
}
