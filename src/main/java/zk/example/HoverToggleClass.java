package zk.example;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HoverToggleClass implements Composer<Component> {
    private String cssClass;

    static Map<String, HoverToggleClass> cache = new ConcurrentHashMap<>();

    public HoverToggleClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public static HoverToggleClass instanceFor(String cssClass) {
        return cache.computeIfAbsent(cssClass, HoverToggleClass::new);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        comp.setWidgetListener("onMouseOver", String.format("jq(this).toggleClass('%s', true);", cssClass));
        comp.setWidgetListener("onMouseOut", String.format("jq(this).toggleClass('%s', false);", cssClass));
    }
}
