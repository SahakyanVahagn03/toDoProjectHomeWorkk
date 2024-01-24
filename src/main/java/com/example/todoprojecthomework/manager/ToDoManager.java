package com.example.todoprojecthomework.manager;


import com.example.todoprojecthomework.dbConnectionProvider.DBConnectionProvider;
import com.example.todoprojecthomework.model.Status;
import com.example.todoprojecthomework.model.ToDo;
import com.example.todoprojecthomework.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoManager {
    private final Connection CONNECTION = DBConnectionProvider.getInstance().getConnection();

    private final UserManager USER_MANAGER = new UserManager();

    public void add(ToDo toDo) {
        String sql = "INSERT INTO to_do(title, created_date, finish_date,user_id,status) VALUES(?,?,?,?,?)";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, toDo.getTitle());
            preparedStatement.setDate(2, new Date(toDo.getCreatedDate().getTime()));
            preparedStatement.setDate(3, new Date(toDo.getFinishDate().getTime()));
            preparedStatement.setInt(4, toDo.getUser().getId());
            preparedStatement.setString(5, toDo.getStatus().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<ToDo> getOwnToDoList(User user) {
        String sql = "SELECT * FROM to_do WHERE user_id=" + user.getId();
        List<ToDo> toDoList = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                toDoList.add(ToDo.builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .createdDate(resultSet.getDate("created_date"))
                        .finishDate(resultSet.getDate("finish_date"))
                        .status(Status.valueOf(resultSet.getString("status")))
                        .user(user)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toDoList;
    }

    public void delete(int toDoId, int userId) {
        String sql = "DELETE FROM to_do WHERE id=" + toDoId + " AND user_id=" + userId;
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ToDo getToDoWithUserIdAndToDoId(int user_id, int toDoId) {
        String sql = "SELECT * FROM to_do WHERE id = " + toDoId + " AND " + "user_id=" + user_id;
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return ToDo.builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .createdDate(resultSet.getDate("created_date"))
                        .finishDate(resultSet.getDate("finish_date"))
                        .status(Status.valueOf(resultSet.getString("status")))
                        .user(USER_MANAGER.getUserById(user_id))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void changeToDoStatus(Status status, int toDoId) {
        String sql = "UPDATE to_do SET status= '" + status + "' WHERE id=" + toDoId;
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeToDo(ToDo toDo) {
        String sql = "UPDATE to_do SET title = ?,finish_date = ?";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql);) {
            preparedStatement.setString(1,toDo.getTitle());
            preparedStatement.setDate(2, new Date(toDo.getFinishDate().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
