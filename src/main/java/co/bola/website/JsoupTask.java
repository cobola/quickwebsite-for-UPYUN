package co.bola.website;

import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Created by xiangxiang on 14-5-16.
 */


public class JsoupTask extends TimerTask {

    private static Logger log = Logger.getAnonymousLogger();

    public JsoupTask() {
        log.info("start task at " + new Date());

    }

    public void run() {


        new SiteUtils().updateHuiHui();

        log.info("task run at " + new Date());
    }
}