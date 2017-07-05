package net.pjt.web.italker.push.service;

import net.pjt.web.italker.push.bean.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;

/**
 * Created by pjt on 2017/7/5.
 */


// http://localhost:8080/api/account...
@Path("/account")
public class AccountService {

    @GET
    @Path("/login")
    public String loginGet() {
        return "You are login";
    }


    //http://localhost:8080/api/account/login
    @POST
    @Path("/login")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User loginPost() {

        User user = new User();
        user.setGender(1);
        user.setName("pengjintu");

        return user;
    }


}
