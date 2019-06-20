package zk.example.popupref;

import org.zkoss.zel.LambdaExpression;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Popup;

import java.util.function.Function;

public class PopupReferenceControl<C extends Component, D> implements Composer<Popup> {
    public static final String POPUP_REFERENCE_ATTR = "popupReference";

    private final Function<C, D> componentDataMapper;
    private Popup popup;

    public PopupReferenceControl() {
        //by default no mapping, return the reference component unchanged
        this(comp -> (D) comp);
    }

    public PopupReferenceControl(Function<C, D> componentDataMapper) {
        this.componentDataMapper = componentDataMapper;
    }

    public static <C extends Component, D> PopupReferenceControl<C, D> forMapper(Function<C, D> mapper) {
        return new PopupReferenceControl<>(mapper);
    }

    public static <D> PopupReferenceControl<Component, D> forLambda(LambdaExpression lambdaExpression) {
        return new PopupReferenceControl<>(comp -> (D) lambdaExpression.invoke(comp));
    }

    @Override
    public void doAfterCompose(Popup comp) {
        this.popup = comp;
        popup.addEventListener(Events.ON_OPEN, (DeferrableListener<OpenEvent>) event -> {
            if(event.isOpen()) {
                D referenceData = componentDataMapper.apply((C) event.getReference());
                popup.setAttribute(POPUP_REFERENCE_ATTR, referenceData);
            } else {
                popup.removeAttribute(POPUP_REFERENCE_ATTR);
            }
        });
    }

    public D getReference() {
        return (D) popup.getAttribute(POPUP_REFERENCE_ATTR);
    }

    interface DeferrableListener<E extends Event> extends EventListener<E>, Deferrable {
        @Override
        public default boolean isDeferrable() {
            return true;
        }
    }
}
