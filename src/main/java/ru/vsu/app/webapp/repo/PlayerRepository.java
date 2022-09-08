package ru.vsu.app.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.app.webapp.entity.PlayerEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByNickName(String nickName);
    Optional<PlayerEntity> removeById(Long id);
    Optional<PlayerEntity> removeByNickName(String nickName);
    List<PlayerEntity> removeAll();
}
