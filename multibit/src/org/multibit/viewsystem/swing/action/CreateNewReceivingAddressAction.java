package org.multibit.viewsystem.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.multibit.controller.ActionForward;
import org.multibit.controller.MultiBitController;
import org.multibit.model.AddressBookData;
import org.multibit.model.DataProvider;
import org.multibit.model.MultiBitModel;

import com.google.bitcoin.core.ECKey;

/**
 * This {@link Action} represents an action to create a receiving address
 */
public class CreateNewReceivingAddressAction extends AbstractAction {

    private static final long serialVersionUID = 200152235465875405L;

    private MultiBitController controller;

    /**
     * Creates a new {@link CreateNewReceivingAddressAction}.
     */
    public CreateNewReceivingAddressAction(MultiBitController controller) {
        super(controller.getLocaliser().getString("createOrEditAddressAction.createReceiving.text"));
        this.controller = controller;

        putValue(SHORT_DESCRIPTION, controller.getLocaliser().getString("createOrEditAddressAction.createReceiving.tooltip"));
        putValue(MNEMONIC_KEY, controller.getLocaliser().getMnemonic("createOrEditAddressAction.createReceiving.mnemonicKey"));
    }

    /**
     * create new receiving address
     */
    public void actionPerformed(ActionEvent e) {
        ECKey newKey = new ECKey();
        controller.getModel().getWallet().keychain.add(newKey);
        controller.getModel().saveWallet();
        
        String addressString = newKey.toAddress(controller.getMultiBitService().getNetworkParameters()).toString();
        controller.getModel().getAddressBook().addReceivingAddress(new AddressBookData("", addressString), false);
        
        controller.getModel().setUserPreference(MultiBitModel.RECEIVE_ADDRESS, addressString);
        controller.getModel().setUserPreference(MultiBitModel.RECEIVE_LABEL, "");

        controller.setActionForwardToSibling(ActionForward.FORWARD_TO_SAME); 

    }
}