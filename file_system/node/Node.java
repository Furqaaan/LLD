package file_system.node;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.Collection;

public abstract class Node {
    private String name;
    private Map<String, Node> children;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Node(String name) {
        this.name = name;
        this.children = new HashMap<>();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void addChild(String name, Node child) {
        this.children.put(name, child);
        this.modifiedAt = LocalDateTime.now();
    }

    public boolean hasChild(String name) {
        return this.children.containsKey(name);
    }

    public Node getChild(String name) {
        return this.children.get(name);
    }

    public boolean removeChild(String name) {
        if (hasChild(name)) {
            children.remove(name);
            return true;
        }
        return false;
    }

    public abstract boolean isFile();

    public abstract void display(int depth);

    public String getName() {
        return name;
    }

    public Collection<Node> getChildren() {
        return children.values();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    protected void updateModifiedTime() {
        this.modifiedAt = LocalDateTime.now();
    }
}