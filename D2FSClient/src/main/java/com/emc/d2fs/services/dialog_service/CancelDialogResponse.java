
package com.emc.d2fs.services.dialog_service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.emc.d2fs.models.dialog.Dialog;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dialog" type="{http://www.emc.com/d2fs/models/dialog}dialog"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dialog"
})
@XmlRootElement(name = "cancelDialogResponse")
public class CancelDialogResponse {

    @XmlElement(required = true)
    protected Dialog dialog;

    /**
     * Gets the value of the dialog property.
     * 
     * @return
     *     possible object is
     *     {@link Dialog }
     *     
     */
    public Dialog getDialog() {
        return dialog;
    }

    /**
     * Sets the value of the dialog property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dialog }
     *     
     */
    public void setDialog(Dialog value) {
        this.dialog = value;
    }

}
