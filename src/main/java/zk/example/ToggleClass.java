package zk.example;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;

import java.util.HashMap;
import java.util.Map;

public class ToggleClass implements Composer<Component> {
    private String cssClass;

    static Map<String, ToggleClass> cache = new HashMap<>();

    public ToggleClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public static ToggleClass instanceFor(String cssClass) {
        return cache.computeIfAbsent(cssClass, ToggleClass::new);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        comp.setWidgetListener("onMouseOver", String.format("jq(this).toggleClass('%s', true);", cssClass));
        comp.setWidgetListener("onMouseOut", String.format("jq(this).toggleClass('%s', false);", cssClass));
    }
}
