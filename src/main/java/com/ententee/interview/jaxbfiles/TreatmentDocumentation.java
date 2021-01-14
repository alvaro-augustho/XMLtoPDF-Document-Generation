package com.ententee.interview.jaxbfiles;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TreatmentDocumentation {
    private int id;
    private String description;
    private String diseases;
    private String sideEffects;

    public TreatmentDocumentation() {}
    public TreatmentDocumentation(int id, String description, String diseases, String sideEffects) {
        super();
        this.id = id;
        this.description = description;
        this.diseases = diseases;
        this.sideEffects = sideEffects;
    }
    @XmlAttribute
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @XmlElement
    public String getDiseases() {
        return diseases;
    }
    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }
    @XmlElement
    public String getSideEffects() {
        return sideEffects;
    }
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
}
