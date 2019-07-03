package zk.example;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ScopeParam;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;

import java.util.Locale;

import static zk.example.PopupReference.POPUP_REFERENCE_ATTR;
import static zk.example.PopupReference.forMapper;

public class PopupReferenceListboxVM {
    private ListModelList<Locale> model = new ListModelList(new Locale[]{Locale.TAIWAN, Locale.GERMANY, Locale.FRANCE});

    private PopupReference<Listitem, Locale> popupReference = forMapper(Listitem::getValue);

    @Command
    public void handleSelection() {
        Clients.log("Selection changed: " + model.getSelection());
    }

    @Command
    public void contextClick1(@BindingParam("contextLocale") Locale contextLocale) {
        Clients.log("Menuitem 1: clicked for " + contextLocale);
    }

    @Command
    public void contextClick2(@ScopeParam(POPUP_REFERENCE_ATTR) Locale popupReference) {
        Clients.log("Menuitem 2: clicked for " + popupReference);
    }

    @Command
    public void contextClick3() {
        Locale popupReference = this.popupReference.getReference();
        Clients.log("Menuitem 3: clicked for " + popupReference);
    }

    public ListModelList<Locale> getModel() {
        return model;
    }

    public PopupReference<Listitem, Locale> getPopupReference() {
        return popupReference;
    }
}
