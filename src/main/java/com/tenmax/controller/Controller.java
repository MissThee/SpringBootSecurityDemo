package com.tenmax.controller;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tenmax.tool.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public JSONObject admin() {
        JSONObject jO = new JSONObject();
        jO.put("result", "/admin");
        return jO;
    }

    @RequestMapping(value = "/provider", method = RequestMethod.GET)
    public JSONObject provider() {
        JSONObject jO = new JSONObject();
        jO.put("result", "/provider");
        return jO;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public JSONObject user() {
        JSONObject jO = new JSONObject();
        jO.put("result", "/user");
        return jO;
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public JSONObject about() {
        JSONObject jO = new JSONObject();
        jO.put("result", "/usabouter");
        return jO;
    }

    //==================== method annotation ====================
    @DenyAll
    @RequestMapping(value = "/deny", method = RequestMethod.GET)
    public JSONObject deny() {
        System.out.println("all user cant get this!");
        JSONObject jO = new JSONObject();
        jO.put("result", "all user cant get this!");
        return jO;
    }

    @RolesAllowed({"ADMIN", "PROVIDER"})
    @RequestMapping(value = "/adminAT", method = RequestMethod.GET)
    public JSONObject adminAT() {
        System.out.println("!!authentication!!:" + SecurityContextHolder.getContext().getAuthentication());
        JSONObject jO = new JSONObject();
        jO.put("result", "/adminAT");
        jO.put("result-getAuthentication", SecurityContextHolder.getContext().getAuthentication());
        return jO;
    }

    @PermitAll
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JSONObject all() {
        JSONObject jO = new JSONObject();
        jO.put("result", "everyone can login !");
        return jO;
    }

    @PermitAll
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public JSONObject error() {
        JSONObject jO = new JSONObject();
        jO.put("result", "error page");
        return jO;
    }

    @RequestMapping(value = "/logout1", method = RequestMethod.GET)
    public JSONObject logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("!!auth-BEFORE!!:" + SecurityContextHolder.getContext().getAuthentication());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            System.out.println("!!auth-AFTER!!:" + SecurityContextHolder.getContext().getAuthentication());
        }
        JSONObject jO = new JSONObject();
        jO.put("result", "LogoutSuccess。default redirect:/login?logout");
        return jO;
    }
}
