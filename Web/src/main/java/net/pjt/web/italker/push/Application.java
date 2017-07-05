package net.pjt.web.italker.push;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.pjt.web.italker.push.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Created by pjt on 2017/7/5.
 */
public class Application extends ResourceConfig {


    public Application() {

        //扫描以下的包，多个包用逗号隔开

        packages(AccountService.class.getPackage().getName());

        //注册log
        register(Logger.class);
        //注册 json
        register(JacksonJsonProvider.class);
    }

}
