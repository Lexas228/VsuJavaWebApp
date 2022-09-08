package ru.vsu.app.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.app.webapp.entity.PlayerEntity;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    @Override
    Optional<PlayerEntity> findById(Long aLong);
}
