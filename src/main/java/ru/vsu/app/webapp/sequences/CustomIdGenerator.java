package ru.vsu.app.webapp.sequences;

import lombok.SneakyThrows;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import ru.vsu.app.webapp.entity.BaseEntity;

import java.io.Serializable;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class CustomIdGenerator extends SequenceStyleGenerator {
    private final static SecureRandom RND = new SecureRandom();

    @SneakyThrows
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        Connection connection = session.connection();
        Statement statement = connection.createStatement();
        long random = RND.nextLong() & Long.MAX_VALUE;
        while(Boolean.TRUE.equals(existsId(statement, random))){
            random = RND.nextLong();
        }
        if ((obj instanceof BaseEntity)) {
            BaseEntity entity = (BaseEntity) obj;
            if ((entity.getId() == null)) {
                return random;
            }
            return entity.getId();
        }
        return random;
    }

    protected abstract Boolean existsId(Statement statement, Long id) throws SQLException;
}