# AI Study Assistant

A Java web application where students ask technical questions and get simple AI-generated answers.

## Technologies
Java, Servlet, JDBC, MySQL, Gemini API, Gson, HTML, CSS, Apache Tomcat 9

## Features
- Ask a technical question and get an AI answer
- Saves every question and answer in MySQL
- History page to view previous questions

## Project structure
- com.util: JDBC_Connection (database connection) and GeminiUtil (Gemini API call)
- com.dao: JDBC_Operations (all JDBC methods)
- com.model: QA class
- com.servlets: AskServlet

## How to run
1. Run database.sql in MySQL
2. Put your MySQL password in JDBC_Connection.java
3. Put your Gemini API key in GeminiUtil.java
4. Run on Apache Tomcat 9 and open index.html
