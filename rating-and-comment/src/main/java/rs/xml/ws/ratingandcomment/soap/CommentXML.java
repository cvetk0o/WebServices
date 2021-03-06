//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.06 at 02:26:55 AM CEST 
//


package rs.xml.ws.ratingandcomment.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommentXML complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommentXML"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="car" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="verified" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="isResponse" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="commentId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="createdAt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommentXML", propOrder = {
    "id",
    "user",
    "car",
    "comment",
    "verified",
    "isResponse",
    "commentId",
    "createdAt"
})
public class CommentXML {

    protected long id;
    protected long user;
    protected long car;
    @XmlElement(required = true)
    protected String comment;
    protected boolean verified;
    protected boolean isResponse;
    protected long commentId;
    @XmlElement(required = true)
    protected String createdAt;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the user property.
     * 
     */
    public long getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     */
    public void setUser(long value) {
        this.user = value;
    }

    /**
     * Gets the value of the car property.
     * 
     */
    public long getCar() {
        return car;
    }

    /**
     * Sets the value of the car property.
     * 
     */
    public void setCar(long value) {
        this.car = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the verified property.
     * 
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Sets the value of the verified property.
     * 
     */
    public void setVerified(boolean value) {
        this.verified = value;
    }

    /**
     * Gets the value of the isResponse property.
     * 
     */
    public boolean isIsResponse() {
        return isResponse;
    }

    /**
     * Sets the value of the isResponse property.
     * 
     */
    public void setIsResponse(boolean value) {
        this.isResponse = value;
    }

    /**
     * Gets the value of the commentId property.
     * 
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Sets the value of the commentId property.
     * 
     */
    public void setCommentId(long value) {
        this.commentId = value;
    }

    /**
     * Gets the value of the createdAt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the value of the createdAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedAt(String value) {
        this.createdAt = value;
    }

}
