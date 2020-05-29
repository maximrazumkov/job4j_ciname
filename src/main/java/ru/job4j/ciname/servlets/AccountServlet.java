package ru.job4j.ciname.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.ciname.models.Account;
import ru.job4j.ciname.models.Hall;
import ru.job4j.ciname.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer line = Integer.valueOf(req.getParameter("line"));
        Integer col = Integer.valueOf(req.getParameter("col"));
        String fullname = req.getParameter("username");
        String phone = req.getParameter("phone");
        Double amount = Double.valueOf(req.getParameter("amount"));
        Hall place = PsqlStore.instOf().findPlaceByLineAndRow(line, col);
        Integer result = PsqlStore.instOf().createAccount(new Account(place.getId(), amount, phone, fullname), line, col);
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setStatus(result == null ? 409 : 201);
    }
}
