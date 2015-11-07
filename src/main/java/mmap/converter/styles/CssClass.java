package mmap.converter.styles;

public class CssClass extends CssSelector {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = "xmap-" + id;
    }

    public String getName() {
        return "." + id;
    }

}
