import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebView;

/**
 * represents the "Options button in the navbar"
 * @author Ferran
 */
public class WebMenu extends MenuButton {

    public WebMenu(WebView webView) {
        // Set the Text of the WebMenu
        this.setText("Options");
        this.setMinHeight(32);

        // Create the Menu Items
        CheckMenuItem ctxMenu = new CheckMenuItem("Enable Context Menu");
        ctxMenu.setSelected(true);

        MenuItem normalFontMenu = new MenuItem("Normal");
        MenuItem biggerFontMenu = new MenuItem("10% Bigger");
        MenuItem smallerFontMenu = new MenuItem("10% Smaller");

        MenuItem normalZoomMenu = new MenuItem("Normal");
        MenuItem biggerZoomMenu = new MenuItem("10% Bigger");
        MenuItem smallerZoomMenu = new MenuItem("10% Smaller");

        // Create the RadioMenuItems
        RadioMenuItem grayMenu = new RadioMenuItem("GRAY");
        grayMenu.setSelected(true);
        RadioMenuItem lcdMenu = new RadioMenuItem("LCD");

        // Create the Menus
        Menu scalingMenu = new Menu("Font Scale");
        scalingMenu.textProperty().bind(new SimpleStringProperty("Font Scale ").concat(webView.fontScaleProperty().multiply(100.0)).concat("%"));

        Menu smoothingMenu = new Menu("Font Smoothing");

        Menu zoomMenu = new Menu("Zoom");
        zoomMenu.textProperty().bind(new SimpleStringProperty("Zoom ").concat(webView.zoomProperty().multiply(100.0)).concat("%"));

        // Add the Items to the corresponding Menu
        scalingMenu.getItems().addAll(normalFontMenu, biggerFontMenu, smallerFontMenu);
        smoothingMenu.getItems().addAll(grayMenu, lcdMenu);
        zoomMenu.getItems().addAll(normalZoomMenu, biggerZoomMenu, smallerZoomMenu);

        // Create the ToggleGroup
        new ToggleGroup().getToggles().addAll(lcdMenu, grayMenu);

        // Define the Event Handler
        normalFontMenu.setOnAction(event -> webView.setFontScale(1.0));

        biggerFontMenu.setOnAction(event -> webView.setFontScale(webView.getFontScale() + 0.10));

        smallerFontMenu.setOnAction(event -> webView.setFontScale(webView.getFontScale() - 0.10));

        grayMenu.setOnAction(event -> webView.setFontSmoothingType(FontSmoothingType.GRAY));

        lcdMenu.setOnAction(event -> webView.setFontSmoothingType(FontSmoothingType.LCD));

        normalZoomMenu.setOnAction(event -> webView.setZoom(1.0));

        biggerZoomMenu.setOnAction(event -> webView.setZoom(webView.getZoom() + 0.10));

        smallerZoomMenu.setOnAction(event -> webView.setZoom(webView.getZoom() - 0.10));

        webView.contextMenuEnabledProperty().bind(ctxMenu.selectedProperty());

        // Enabled JavaScript option
        CheckMenuItem scriptMenu = new CheckMenuItem("Enable JavaScript");
        scriptMenu.setSelected(true);
        webView.getEngine().javaScriptEnabledProperty().bind(scriptMenu.selectedProperty());

        // Add Menus to the WebMenu
        this.getItems().addAll(ctxMenu, scalingMenu, smoothingMenu, zoomMenu, new SeparatorMenuItem(), scriptMenu);
    }

}
