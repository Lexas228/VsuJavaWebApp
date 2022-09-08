package ru.vsu.app.webapp.sequences;

import ru.vsu.app.webapp.pojo.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemIdGenerator extends CustomIdGenerator{
    @Override
    protected Boolean existsId(Statement statement, Long id) throws SQLException {
        //actually Long doesn't dangerous
        String query = "select exists(select * from vsu_java.item where id = " + id + ");";
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next() && resultSet.getBoolean(1);
    }
}
