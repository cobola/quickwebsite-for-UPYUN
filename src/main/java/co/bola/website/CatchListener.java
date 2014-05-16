package co.bola.website;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

/**
 * Created by xiangxiang on 14-5-16.
 */
public class CatchListener implements ServletContextListener {


    private Timer timer = null;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        timer = new Timer(true);
        timer.schedule(new JsoupTask(), 0, 1000 * 60 * 20);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

