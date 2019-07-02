package zk.example;

import org.zkoss.zel.LambdaExpression;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Popup;

import java.util.function.Function;

public class PopupReference<Comp extends Component, Data> implements Composer<Popup> {
    public static final String POPUP_REFERENCE_ATTR = "popupReference";

    private final Function<Comp, Data> componentDataMapper;
    private Popup popup;

    public PopupReference() {
        //by default no mapping, return the reference component unchanged
        this(comp -> (Data) comp);
    }

    public PopupReference(Function<Comp, Data> componentDataMapper) {
        this.componentDataMapper = componentDataMapper;
    }

    public static <Comp extends Component, Data> PopupReference<Comp, Data> forMapper(Function<Comp, Data> mapper) {
        return new PopupReference<>(mapper);
    }

    public static <Data> PopupReference<Component, Data> forLambda(LambdaExpression lambdaExpression) {
        return new PopupReference<>(comp -> (Data) lambdaExpression.invoke(comp));
    }

    @Override
    public void doAfterCompose(Popup comp) {
        this.popup = comp;
        popup.addEventListener(Events.ON_OPEN, (DeferrableListener<OpenEvent>) event -> {
            if(event.isOpen()) {
                Data referenceData = componentDataMapper.apply((Comp) event.getReference());
                popup.setAttribute(POPUP_REFERENCE_ATTR, referenceData);
            } else {
                popup.removeAttribute(POPUP_REFERENCE_ATTR);
            }
        });
    }

    public Data getReference() {
        return (Data) popup.getAttribute(POPUP_REFERENCE_ATTR);
    }


    interface DeferrableListener<E extends Event> extends EventListener<E>, Deferrable {
        @Override
        public default boolean isDeferrable() {
            return true;
        }
    }
}
