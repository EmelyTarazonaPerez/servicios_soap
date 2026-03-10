//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v3.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2025.03.01 a las 11:46:15 PM COT 
//


package com.webservice.soap.generated.com.ejemplo.fondos;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mount" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="sourceaccount" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="destinationaccount" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "mount",
    "sourceaccount",
    "destinationaccount",
    "date",
    "code"
})
@XmlRootElement(name = "RequestPay")
public class RequestPay {

    protected float mount;
    @XmlElement(required = true)
    protected String sourceaccount;
    @XmlElement(required = true)
    protected String destinationaccount;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true)
    protected String code;

    /**
     * Obtiene el valor de la propiedad mount.
     * 
     */
    public float getMount() {
        return mount;
    }

    /**
     * Define el valor de la propiedad mount.
     * 
     */
    public void setMount(float value) {
        this.mount = value;
    }

    /**
     * Obtiene el valor de la propiedad sourceaccount.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceaccount() {
        return sourceaccount;
    }

    /**
     * Define el valor de la propiedad sourceaccount.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceaccount(String value) {
        this.sourceaccount = value;
    }

    /**
     * Obtiene el valor de la propiedad destinationaccount.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationaccount() {
        return destinationaccount;
    }

    /**
     * Define el valor de la propiedad destinationaccount.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationaccount(String value) {
        this.destinationaccount = value;
    }

    /**
     * Obtiene el valor de la propiedad date.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Obtiene el valor de la propiedad code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Define el valor de la propiedad code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

}
