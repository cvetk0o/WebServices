//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.18 at 03:16:30 PM CEST 
//


package rs.xml.ws.netflixzuulgatewayserver.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleNameXML.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleNameXML"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ROLE_USER"/&gt;
 *     &lt;enumeration value="ROLE_AGENT"/&gt;
 *     &lt;enumeration value="ROLE_ADMIN"/&gt;
 *     &lt;enumeration value="ROLE_MESSAGE"/&gt;
 *     &lt;enumeration value="ROLE_ADVERTISE"/&gt;
 *     &lt;enumeration value="ROLE_APPOINT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RoleNameXML")
@XmlEnum
public enum RoleNameXML {

    ROLE_USER,
    ROLE_AGENT,
    ROLE_ADMIN,
    ROLE_MESSAGE,
    ROLE_ADVERTISE,
    ROLE_APPOINT;

    public String value() {
        return name();
    }

    public static RoleNameXML fromValue(String v) {
        return valueOf(v);
    }

}
