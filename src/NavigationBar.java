import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Extends HBox because we want this navbar to be a Horizontal box
 */
public class NavigationBar extends HBox {

    // Create the FileChooser
    private FileChooser fileChooser = new FileChooser();

    /**
     * Constructor to construct the navigation bar
     * @param view is the webview
     * @param homePageUrl is the homePageUrl. When the home button is clicked it leads to this URL
     * @param goToHomePage boolean to tell if the application should go to the homepage when running
     * @param browser stage is passed in to change the stage title after searching an URL
     * @param tab1 tab is passed in to change the tab title after searching an URL
     */
    public NavigationBar(WebView view, String homePageUrl, boolean goToHomePage, Stage browser, Tab tab1)
    {
        this.setSpacing(4);

        // Set the Style-properties of the Navigation Bar
        this.setStyle("-fx-padding: 3;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: #F8F5F5;");

        // Configure the FileChooser
        fileChooser.setTitle("Open Web Content");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HTML Files", "*.html", "*.htm"));

        // Create the WebEngine and load the homepage if boolean is set to true
        WebEngine engine = view.getEngine();
        if (goToHomePage)
        {
            engine.load(homePageUrl);
        }

        TextField addressBar = new TextField();
        addressBar.setPromptText("URL Address");
        addressBar.setText("http://www.google.com");
        addressBar.setMinWidth(150);
        addressBar.setMinHeight(32);

        // Create the Buttons
        Image refreshIcon = new Image(getClass().getResourceAsStream("/resources/images/refreshicon24.png"));
        Button refreshButton = new Button("Refresh", new ImageView(refreshIcon));
        Image goIcon = new Image(getClass().getResourceAsStream("/resources/images/goicon24.png"));
        Button goButton = new Button("Go", new ImageView(goIcon));
        Button homeButton = new Button();
        homeButton.setId("home-button");
        Image htmlIcon = new Image(getClass().getResourceAsStream("/resources/images/htmlicon24.png"));
        Button openButton = new Button("HTML Viewer", new ImageView(htmlIcon));

        // Set goButton as default button so it will trigger the event when pressing enter
        goButton.setDefaultButton(true);

        // Go to URL when button has been clicked
        goButton.setOnAction( e -> {
            String s = addressBar.getText();
            engine.load("http://"+s);

            //Format title
            String title = s.substring(0, s.indexOf("."));
            String output = title.substring(0, 1).toUpperCase() + title.substring(1);
            browser.setTitle(output);

            tab1.setText(output);
        });

        refreshButton.setOnAction( e -> {
            engine.reload();
        });

        homeButton.setOnAction( e -> {
           engine.load(homePageUrl);
        });

        // Let the TextField grow horizontallly
        HBox.setHgrow(addressBar, Priority.ALWAYS);

        // Add an ActionListener for the HTML viewer
        openButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());

            if (selectedFile != null)
            {
                try
                {
                    engine.load(selectedFile.toURI().toURL().toExternalForm());
                }
                catch(MalformedURLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        // Add the Children (nodes) to the Navigation Bar
        this.getChildren().addAll(refreshButton, addressBar, goButton, homeButton, openButton);
    }
}
