package com.gindho.client.dragdrop;

import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager for drag and drop functionality in a GridPane.
 */
public class DragAndDropManager {
    
    private static final String DRAG_FORMAT = "widget-index";
    private GridPane grid;
    private final Map<Node, Integer> nodeToIndexMap = new HashMap<>();
    private final Map<Integer, Node> indexToNodeMap = new HashMap<>();
    
    public DragAndDropManager() {
    }
    
    /**
     * Sets the grid for this manager and initializes the maps.
     * @param grid The GridPane to manage
     */
    public void setGrid(GridPane grid) {
        this.grid = grid;
        initializeMaps();
    }
    
    private void initializeMaps() {
        nodeToIndexMap.clear();
        indexToNodeMap.clear();
        if (grid == null) {
            return;
        }
        int index = 0;
        for (Node child : grid.getChildren()) {
            nodeToIndexMap.put(child, index);
            indexToNodeMap.put(index, child);
            index++;
        }
    }
    
    /**
     * Makes a node draggable within the grid.
     * @param node The node to make draggable
     */
    public void makeDraggable(Node node) {
        node.setOnDragDetected(event -> {
            // Allow dragging only if the node is in the grid
            if (!nodeToIndexMap.containsKey(node)) {
                return;
            }
            
            Dragboard db = node.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(DRAG_FORMAT + ":" + nodeToIndexMap.get(node));
            db.setContent(content);
            
            // Visual feedback: reduce opacity
            node.setOpacity(0.5);
            event.consume();
        });
        
        node.setOnDragDone(event -> {
            // Restore opacity
            node.setOpacity(1.0);
            event.consume();
        });
        
        node.setOnMousePressed(event -> {
            // Prepare for dragging
            if (!nodeToIndexMap.containsKey(node)) {
                return;
            }
            // Store initial position for potential snap-back
            node.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
            event.consume();
        });
        
        node.setOnMouseDragged(event -> {
            if (!nodeToIndexMap.containsKey(node)) {
                return;
            }
            double[] start = (double[]) node.getUserData();
            if (start == null) return;
            
            double offsetX = event.getSceneX() - start[0];
            double offsetY = event.getSceneY() - start[1];
            node.setTranslateX(offsetX);
            node.setTranslateY(offsetY);
            event.consume();
        });
        
        node.setOnMouseReleased(event -> {
            if (!nodeToIndexMap.containsKey(node)) {
                return;
            }
            // Snap back to original position
            node.setTranslateX(0);
            node.setTranslateY(0);
            
            // Check if we dropped on another node to swap
            Node target = pickNode(event.getSceneX(), event.getSceneY());
            if (target != null && target != node) {
                swapNodes(node, target);
            }
            event.consume();
        });
    }
    
    /**
     * Sets up a node as a drop target (for visual feedback during drag).
     * @param node The node to set as drop target
     */
    public void setupDropTarget(Node node) {
        node.setOnDragOver(event -> {
            if (event.getGestureSource() != node && 
                event.getDragboard().hasString() && 
                event.getDragboard().getString().startsWith(DRAG_FORMAT)) {
                event.acceptTransferModes(TransferMode.MOVE);
                // Visual feedback: highlight the target
                node.setStyle("-fx-border-color: #FF6B6B; -fx-border-width: 2px;");
            }
            event.consume();
        });
        
        node.setOnDragEntered(event -> {
            if (event.getGestureSource() != node && 
                event.getDragboard().hasString() && 
                event.getDragboard().getString().startsWith(DRAG_FORMAT)) {
                node.setStyle("-fx-background-color: rgba(255,107,107,0.1); -fx-border-color: #FF6B6B; -fx-border-width: 2px;");
            }
            event.consume();
        });
        
        node.setOnDragExited(event -> {
            node.setStyle(""); // Reset style
            event.consume();
        });
        
        node.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            
            if (db.hasString() && db.getString().startsWith(DRAG_FORMAT)) {
                String[] parts = db.getString().split(":");
                if (parts.length == 2) {
                    int sourceIndex = Integer.parseInt(parts[1]);
                    Node sourceNode = indexToNodeMap.get(sourceIndex);
                    
                    Node targetNode = node;
                    if (sourceNode != null && targetNode != null && sourceNode != targetNode) {
                        swapNodes(sourceNode, targetNode);
                        success = true;
                    }
                }
            }
            
            event.setDropCompleted(success);
            event.consume();
            
            // Reset styles of all nodes
            resetStyles();
        });
    }
    
    /**
     * Picks the node at the given scene coordinates.
     * @param sceneX X coordinate in scene
     * @param sceneY Y coordinate in scene
     * @return The node at the coordinates, or null if none
     */
    private Node pickNode(double sceneX, double sceneY) {
        for (Node child : grid.getChildren()) {
            if (child.contains(child.sceneToLocal(sceneX, sceneY))) {
                return child;
            }
        }
        return null;
    }
    
    /**
     * Swaps two nodes in the grid.
     * @param node1 First node
     * @param node2 Second node
     */
    private void swapNodes(Node node1, Node node2) {
        Integer index1 = nodeToIndexMap.get(node1);
        Integer index2 = nodeToIndexMap.get(node2);
        
        if (index1 == null || index2 == null) return;
        
        // Swap in the grid
        grid.getChildren().set(index1, node2);
        grid.getChildren().set(index2, node1);
        
        // Update maps
        nodeToIndexMap.put(node1, index2);
        nodeToIndexMap.put(node2, index1);
        indexToNodeMap.put(index1, node2);
        indexToNodeMap.put(index2, node1);
    }
    
    /**
     * Resets the styles of all nodes in the grid.
     */
    private void resetStyles() {
        if (grid == null) return;
        for (Node child : grid.getChildren()) {
            child.setStyle("");
        }
    }
    
    /**
     * Adds a node to the grid at the specified position and makes it draggable.
     * @param node The node to add
     * @param column Column index
     * @param row Row index
     * @param columnSpan Column span (default 1)
     * @param rowSpan Row span (default 1)
     */
    public void addDraggableNode(Node node, int column, int row, int columnSpan, int rowSpan) {
        if (grid == null) {
            throw new IllegalStateException("Grid not set. Call setGrid() first.");
        }
        grid.add(node, column, row, columnSpan, rowSpan);
        initializeMaps(); // Update maps after adding
        makeDraggable(node);
        setupDropTarget(node); // Also make it a drop target
    }
}