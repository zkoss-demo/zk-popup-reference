package zk.example;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Popup;

public class PopupReferenceSimple<T extends Popup> implements Composer<T> {
    @Override
    public void doAfterCompose(T popup) {
        popup.addEventListener(Events.ON_OPEN, (OpenEvent event) -> {
            if (event.isOpen()) {
                popup.setAttribute("popupReference", event.getReference());
            } else {
                popup.removeAttribute("popupReference");
            }
        });
    }
}
