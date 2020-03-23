import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Webbrowser + HTML Viewer
 * @author Ferran
 */
public class Main extends Application {

    VBox vBox = new VBox();

    public void start(Stage browser) throws Exception{

        WebView view = new WebView();

        String homePageUrl = "http://www.google.com";

        TabBar tabPane = new TabBar(view);

        NavigationBar navigationBar = new NavigationBar(view, homePageUrl, true, browser, tabPane.getFirstTab());

        // Create the options button (WebMenu) and add it to the navigationBar
        WebMenu options = new WebMenu(view);
        options.setId("options-dropdown");

        // Create the browser history
        BrowserHistory browserHistory = new BrowserHistory(view, browser, tabPane.getFirstTab());
        browserHistory.setId("browser-dropdown");

        // Add all the menus to the navbar
        navigationBar.getChildren().addAll(browserHistory, options);

        // Add toolbar and tabpane to the Vbox
        vBox.getChildren().addAll(navigationBar, tabPane);

        browser.setTitle("Google");

        Scene scene = new Scene(vBox);

        scene.getStylesheets().add("/resources/styles/main.css");

        browser.setScene(scene);

        browser.sizeToScene();

        browser.setMaximized(true);

        browser.getIcons().add(new Image("/resources/images/appicon.png"));
        browser.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
