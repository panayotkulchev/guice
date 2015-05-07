package server;

import adapter.shedule.SessionCleanUpScheduler;
import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.swing.*;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class JettyRunner {
    static Server server = null;

    public static void main(String[] args) {

        try {

            new SessionCleanUpScheduler().start();

            server = new Server(8088);
            ServletHolder jspSH = new ServletHolder(JspServlet.class);

            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/");
            webapp.setWar("src/main/webapp");
            server.setHandler(webapp);

            server.start();
            server.join();


        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Something went wrong\n Probably server is already started");
        }
    }
}
