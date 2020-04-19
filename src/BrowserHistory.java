import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BrowserHistory extends HBox {

    /**
     * Constructor to construct the BrowserHistory
     * @param webView
     * @param browser is passed in to change the browser title
     * @param tab1 is passed in to change the tab title
     */
    public BrowserHistory(WebView webView, Stage browser, Tab tab1)
    {
        this.setSpacing(4);

        WebHistory history = webView.getEngine().getHistory();

        Button backButton = new Button("Back");
        backButton.setMinHeight(32);
        backButton.setDisable(true);
        Button forwardButton = new Button("Forward");
        forwardButton.setDisable(true);
        forwardButton.setMinHeight(32);

        // Add an ActionListener to the Back and Forward Buttons
        backButton.setOnAction(event -> history.go(-1));

        forwardButton.setOnAction(event -> history.go(1));

        // Add an ChangeListener to the currentIndex property
        history.currentIndexProperty().addListener((ov, oldvalue, newvalue) -> {
            int currentIndex = newvalue.intValue();

            if (currentIndex <= 0)
            {
                backButton.setDisable(true);
            }
            else
            {
                backButton.setDisable(false);
            }

            if (currentIndex >= history.getEntries().size())
            {
                forwardButton.setDisable(true);
            }
            else
            {
                forwardButton.setDisable(false);
            }
        });

        // Create the ComboBox for the History List
        ComboBox<WebHistory.Entry> historyList = new ComboBox<>();
        historyList.setMinHeight(32);
        historyList.setPrefWidth(150);
        historyList.setPromptText("History");
        historyList.setItems(history.getEntries());

        // Set a cell factory to to show only the page title in the history list
        historyList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<WebHistory.Entry> call(ListView<WebHistory.Entry> list) {
                ListCell<WebHistory.Entry> cell = new ListCell<>() {
                    @Override
                    public void updateItem(WebHistory.Entry item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            this.setText(null);
                            this.setGraphic(null);
                        } else {
                            String pageTitle = item.getTitle();
                            this.setText(pageTitle);
                        }
                    }
                };

                return cell;
            }
        });

        // Let the user navigate to a page using the history list
        historyList.setOnAction(event -> {
            int currentIndex = history.getCurrentIndex();

            WebHistory.Entry selectedEntry = historyList.getValue();

            int selectedIndex = historyList.getItems().indexOf(selectedEntry);
            int offset = selectedIndex - currentIndex;

            browser.setTitle(historyList.getItems().get(selectedIndex).getTitle());
            tab1.setText(historyList.getItems().get(selectedIndex).getTitle());

            history.go(offset);
        });

        // Add the Children to the BrowserHistory
        this.getChildren().addAll(backButton, forwardButton, historyList);
    }
}
