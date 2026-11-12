package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import com.dao.JDBC_Operations;
import com.model.QA;
import com.util.GeminiUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/ask")
public class AskServlet extends HttpServlet {

	JDBC_Operations dao = new JDBC_Operations();
	String css = "<style>"
			+ "body{font-family:Arial,sans-serif;background-color:#f3f4f6;margin:0;}"
			+ ".header{background-color:#2563eb;color:white;text-align:center;padding:20px;}"
			+ ".container{max-width:700px;margin:30px auto;background-color:white;padding:25px;"
			+ "border-radius:10px;box-shadow:0 2px 8px rgba(0,0,0,0.15);}"
			+ ".question{background-color:#eff6ff;padding:12px;border-radius:6px;font-weight:bold;}"
			+ ".answer{background-color:#f9fafb;border-left:4px solid #2563eb;padding:12px;white-space:pre-wrap;}"
			+ "a{color:#2563eb;text-decoration:none;margin-right:10px;}"
			+ "</style>";

	// runs when the user clicks "Ask AI" (form method = post)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String question = req.getParameter("question");
		String answer = "";

		try {
			answer = GeminiUtil.askGemini(question);   // get answer from AI
			dao.saveQuestionAnswer(question, answer);  // save in database
		} catch (Exception e) {
			answer = "Error: " + e.getMessage();
		}

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>AI Answer</title>" + css + "</head><body>");
		out.println("<div class='header'><h1>AI Study Assistant</h1></div>");
		out.println("<div class='container'>");
		out.println("<h3>Question</h3>");
		out.println("<div class='question'>" + question + "</div>");
		out.println("<h3>AI Answer</h3>");
		out.println("<div class='answer'>" + answer + "</div>");
		out.println("<p><a href='index.html'>Ask another</a> <a href='ask'>History</a></p>");
		out.println("</div></body></html>");
	}

	// runs when the user clicks "View previous questions" (link = get)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>History</title>" + css + "</head><body>");
		out.println("<div class='header'><h1>Previous Questions</h1></div>");
		out.println("<div class='container'>");

		try {
			ArrayList<QA> list = dao.getAllQuestions();
			for (QA qa : list) {
				out.println("<div class='question'>Q: " + qa.getQuestion() + "</div>");
				out.println("<div class='answer'>" + qa.getAnswer() + "</div><br>");
			}
		} catch (Exception e) {
			out.println("Error: " + e.getMessage());
		}

		out.println("<p><a href='index.html'>Back</a></p>");
		out.println("</div></body></html>");
	}
}