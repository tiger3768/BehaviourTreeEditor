package com.bteditor;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Controller class for the Behaviour Tree Editor application.
 */
public class Controller implements Initializable {

    private MultipleSelectionModel<TreeItem<Entry>> model;
    private TreeItem<Entry> root;
    private TreeItem<Entry> selected;
    private String[] nodeTypes = {
            "Selector",
            "Sequence",
            "Loop",
            "Condition",
            "Action"
    };

    @FXML
    private TreeView<Entry> behaviourTreeView;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private MenuButton nodeTypeMenuButton;
    @FXML
    private TextField nodeTypeTextField;
    @FXML
    private TextField nodeNameTextField;
    @FXML
    private TextField lambdaNameTextField;
    @FXML
    private Button exitButton;
    @FXML
    private Button addNodeButton;
    @FXML
    private Button deleteNodeButton;
    @FXML
    private Button newTreeButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button loadTreeButton;

    /**
     * Initializes the controller.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = behaviourTreeView.getSelectionModel();
        model.selectedItemProperty().addListener(
            (ObservableValue<? extends TreeItem<Entry>> changed,
             TreeItem<Entry> oldValue,
             TreeItem<Entry> newValue) -> { selected = newValue; }
        );

        // Create menu items for node types
        MenuItem[] menuItems = new MenuItem[nodeTypes.length];
        int i = 0;
        for (String ntype : nodeTypes ) {
            menuItems[i] = new MenuItem(ntype);
            menuItems[i].setOnAction((event) -> nodeTypeTextField.setText(ntype));
            i++;
        }
        nodeTypeMenuButton.getItems().setAll(Arrays.asList(menuItems));

        // Set event handlers for buttons
        exitButton.setOnAction(this::exitHandler);
        loadTreeButton.setOnAction(this::loadTreeHandler);
        addNodeButton.setOnAction(this::addNodeHandler);
        deleteNodeButton.setOnAction(this::deleteNodeHandler);
        newTreeButton.setOnAction(this::newTreeHandler);
        clearButton.setOnAction(this::clearHandler);
    }
    
    /**
     * Loads a pre-defined behavior tree from a table and sets it as the root of the tree view.
     *
     * @param event The ActionEvent triggered by the load tree button click.
     */
    @FXML
    private void loadTreeHandler(ActionEvent event) {
        behaviourTreeView.setRoot(loadFromTable());
        messageTextArea.setText("Load: Tree Loaded");
    }

    /**
     * Adds a new node to the selected parent node in the tree view.
     *
     * @param event The ActionEvent triggered by the add node button click.
     */
    @FXML
    private void addNodeHandler(ActionEvent event) {
        TreeItem<Entry> selectedNode = behaviourTreeView.getSelectionModel().getSelectedItem();
        if (selectedNode == null) {
            messageTextArea.setText("Add: No parent node selected");
            return;
        }
        String type = nodeTypeTextField.getText().trim();
        String name = nodeNameTextField.getText().trim();
        String behavior = lambdaNameTextField.getText().trim();
        if (type.isEmpty() || name.isEmpty() || ((type.equals("Condition") || type.equals("Action")) && behavior.isEmpty())) {
            messageTextArea.setText("Add: Incomplete data provided");
            return;
        }

        Entry newNodeData;
        if (behavior.isEmpty()) {
            newNodeData = new Entry(type, name);
        } else {
            newNodeData = new Entry(type, name, behavior);
        }
        TreeItem<Entry> newNode = new TreeItem<>(newNodeData);

        selectedNode.getChildren().add(newNode);
        selectedNode.setExpanded(true);

        messageTextArea.setText("Add: Node added");
        
        nodeTypeTextField.clear();
        nodeNameTextField.clear();
        lambdaNameTextField.clear();
    }

    /**
     * Deletes the selected node from the tree view.
     *
     * @param event The ActionEvent triggered by the delete node button click.
     */
    @FXML
    private void deleteNodeHandler(ActionEvent event) {
        TreeItem<Entry> selected = behaviourTreeView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageTextArea.setText("Delete: No node selected");
            return;
        }

        TreeItem<Entry> parent = selected.getParent();

        if (parent != null) {
            parent.getChildren().remove(selected);
            messageTextArea.setText("Delete: Node Deleted");
        }
        
        else {
        	messageTextArea.setText("Delete: Can't delete root node");
        	return;
        }
    }

    /**
     * Creates a new behavior tree with a root node and sets it as the root of the tree view.
     *
     * @param event The ActionEvent triggered by the new tree button click.
     */
    @FXML
    private void newTreeHandler(ActionEvent event) {
        behaviourTreeView.setRoot(new TreeItem<>( new Entry("Root", "root" )));
        messageTextArea.setText("New Tree Created");
    }

    /**
     * Clears the tree view and input fields.
     *
     * @param event The ActionEvent triggered by the clear button click.
     */
    @FXML
    private void clearHandler(ActionEvent event) {
        behaviourTreeView.setRoot(null);

        messageTextArea.clear();
        nodeTypeTextField.clear();
        nodeNameTextField.clear();
        lambdaNameTextField.clear();
    }

    /**
     * Loads a pre-defined behavior tree from a table.
     *
     * @return The root TreeItem containing the loaded behavior tree.
     */
    private TreeItem<Entry> loadFromTable() {
        String[][] nodes = {
            { "Condition", "c1", "isNotFull" },
            { "Action",    "a1", "detect"    },
            { "Condition", "c2", "pickable"  },
            { "Action",    "a2", "advance"   },
            { "Action",    "a3", "grip"      },
            { "Action",    "a4", "retract"   },
            { "Condition", "c3", "picked"    },
            { "Action",    "a5", "release"   },
            { "Sequence",  "s1", ""          },
            { "Loop",      "l1", ""          },
            { "Root",      "r0", ""          }
        };
        int[] subnodes = { 
            0, 0, 0, 0, 0, 0, 0, 0, 7, 2, 1
        };

        Stack<TreeItem<Entry>> s = new Stack<>();
        for ( int i = 0; i < nodes.length; i++ ) {
            int n = subnodes[i];
            Entry e = new Entry( nodes[i][0], nodes[i][1], nodes[i][2], n );
            TreeItem<Entry> item = new TreeItem<>( e );
            if ( n == 0 ) {
                s.push( item );
                continue;
            }
            for ( int j = 0; j < n; j++ )
                item.getChildren().add( 0, s.pop() );
            e.setNumberOfChildren( 0 );
            s.push( item );   
        }
        return s.pop();
    }

    /**
     * Handles the exit button click event and exits the application.
     *
     * @param event The ActionEvent triggered by the exit button click.
     */
    @FXML
    private void exitHandler(ActionEvent event) {
        System.exit(0);
    }
}

