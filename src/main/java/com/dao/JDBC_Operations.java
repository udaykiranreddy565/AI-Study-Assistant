package com.dao;

import com.model.QA;
import com.util.JDBC_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JDBC_Operations {

    // save question and answer in the database
    public void saveQuestionAnswer(String question, String answer) throws Exception {
        Connection con = JDBC_Connection.createConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO qa(question, answer) VALUES(?,?)");
        ps.setString(1, question);
        ps.setString(2, answer);
        ps.executeUpdate();
        con.close();
    }

    // get all previous questions from the database
    public ArrayList<QA> getAllQuestions() throws Exception {
        ArrayList<QA> list = new ArrayList<QA>();

        Connection con = JDBC_Connection.createConnection();
        PreparedStatement ps = con.prepareStatement("SELECT question, answer FROM qa ORDER BY id DESC");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            QA qa = new QA(rs.getString("question"), rs.getString("answer"));
            list.add(qa);
        }

        con.close();
        return list;
    }
}