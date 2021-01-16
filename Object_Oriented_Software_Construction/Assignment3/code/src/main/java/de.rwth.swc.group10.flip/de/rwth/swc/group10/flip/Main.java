package de.rwth.swc.group10.flip;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.OSXApplication;
import org.jhotdraw.app.SDIApplication;
import org.jhotdraw.samples.draw.DrawView;

public class Main {
    public static void main(String[] args) {
        Application app;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("mac")) {
            app = new OSXApplication();
        } else if (os.startsWith("win")) {
            app = new SDIApplication();
        } else {
            app = new SDIApplication();
        }

        // Create own model
        FlipApplicationModel model = new FlipApplicationModel();
        model.setName("Group 10 - Flip");

        // Set view to DrawView of the JHotDraw sample
        model.setViewFactory(DrawView::new);
        app.setModel(model);
        app.launch(args);
    }
}
