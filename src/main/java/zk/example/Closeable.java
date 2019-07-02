package zk.example;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Composer;

public class Closeable implements Composer<Component> {

    public final static Closeable instance = new Closeable();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        comp.addEventListener(Events.ON_CLICK,
                e -> Events.postEvent(Events.ON_CLOSE, comp, null));
        comp.addEventListener(-1000, Events.ON_CLOSE,
                e -> comp.detach());
    }
}
