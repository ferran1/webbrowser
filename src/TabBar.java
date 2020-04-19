import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

/**
 * This class represents the tab pane
 * @author Ferran
 */
public class TabBar extends TabPane {

    private static int tabCounter = 1;
    private Tab tab1;

    /**
     * Create the tabpane with a tab
     * @param view passed in webview to view content
     */
    public TabBar(WebView view){
        tab1 = new Tab("Google");

        tab1.setContextMenu(createContextMenu());
        tab1.setGraphic(createTabButton("/resources/images/addtab16.png"));
        ((Button) tab1.getGraphic()).setOnAction(e -> {
            //Logic here to add another tab onclick
                
        });
        tab1.setContent(view);
        addTab(tab1);
    }

    private void addTab(Tab tab){
        this.getTabs().add(tab);
    }

    /**
     * Method to create the 'new tab' button
     * @param iconName
     * @return
     */
    private Button createTabButton(String iconName) {
        Button button = new Button();

        ImageView imageView = new ImageView(new Image(getClass().getResource(iconName).toExternalForm(),
                18, 18, false, true));
        button.setGraphic(imageView);
        button.getStyleClass().add("new-tab-button");
        return button;
    }

    //Creates the context menu
    private ContextMenu createContextMenu(){

        return new ContextMenu();
    }

    public Tab getFirstTab(){
        return tab1;
    }
}
