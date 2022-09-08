package ru.vsu.app.webapp.sequences;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResourceIdGenerator extends CustomIdGenerator{
    @Override
    protected Boolean existsId(Statement statement, Long id) throws SQLException {
        String query = "select exists(select * from vsu_java.resource where id = " + id + ");";
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next() && resultSet.getBoolean(1);
    }
}
