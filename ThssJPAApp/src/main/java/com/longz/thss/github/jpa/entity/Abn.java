package com.longz.thss.github.jpa.entity;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.longz.thss.github.jpa.factory.AbnDataSerializableFactory;
import com.longz.thss.thsshzlibmodule.utils.ThssLibUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@javax.persistence.Entity(name = "Abn")
@Table(name = "abn", catalog = "ozssc", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Abn.findAll", query = "SELECT a FROM Abn a"),
        @NamedQuery(name = "Abn.findByAbn", query = "SELECT a FROM Abn a WHERE a.abn = :abn"),
        @NamedQuery(name = "Abn.findByName", query = "SELECT a FROM Abn a WHERE a.name = :name"),
        @NamedQuery(name = "Abn.findByType", query = "SELECT a FROM Abn a WHERE a.type = :type"),
        @NamedQuery(name = "Abn.findByLocation", query = "SELECT a FROM Abn a WHERE a.location = :location"),
        @NamedQuery(name = "Abn.findByStatus", query = "SELECT a FROM Abn a WHERE a.status = :status"),
        @NamedQuery(name = "Abn.findByCreated", query = "SELECT a FROM Abn a WHERE a.created = :created"),
        @NamedQuery(name = "Abn.findByContacted", query = "SELECT a FROM Abn a WHERE a.contacted = :contacted"),
        @NamedQuery(name = "Abn.findByQuoted", query = "SELECT a FROM Abn a WHERE a.quoted = :quoted"),
        @NamedQuery(name = "Abn.findByViewed", query = "SELECT a FROM Abn a WHERE a.viewed = :viewed"),
        @NamedQuery(name = "Abn.findByModified", query = "SELECT a FROM Abn a WHERE a.modified = :modified"),
        @NamedQuery(name = "Abn.findByEntityTypeCode", query = "SELECT a FROM Abn a WHERE a.entityTypeCode = :entityTypeCode"),
        @NamedQuery(name = "Abn.findByEntityType", query = "SELECT a FROM Abn a WHERE a.entityType = :entityType"),
        @NamedQuery(name = "Abn.findByRegoStatus", query = "SELECT a FROM Abn a WHERE a.regoStatus = :regoStatus"),
        @NamedQuery(name = "Abn.findByRegoEffectFrom", query = "SELECT a FROM Abn a WHERE a.regoEffectFrom = :regoEffectFrom"),
        @NamedQuery(name = "Abn.findByLastInquiried", query = "SELECT a FROM Abn a WHERE a.lastInquiried = :lastInquiried"),
        @NamedQuery(name = "Abn.findByRegoState", query = "SELECT a FROM Abn a WHERE a.regoState = :regoState"),
        @NamedQuery(name = "Abn.findByRegoPostcode", query = "SELECT a FROM Abn a WHERE a.regoPostcode = :regoPostcode"),
        @NamedQuery(name = "Abn.findAllKeys",query = "SELECT a.abn from Abn a order by a.abn desc"),
        @NamedQuery(name = "Abn.findByTradeAs", query = "SELECT a FROM Abn a WHERE a.tradeAs = :tradeAs")})
public class Abn implements IdentifiedDataSerializable, Serializable {
    /*private static final long serialVersionUID = 1L;*/
    private static final int CLASS_ID = 1102;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "abn")
    private String abn;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "type")
    private String type;
    @Size(max = 2147483647)
    @Column(name = "location")
    private String location;
    @Size(max = 2147483647)
    @Column(name = "status")
    private String status;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "contacted")
    private LocalDateTime contacted;
    @Column(name = "quoted")
    private Integer quoted;
    @Column(name = "viewed")
    private Integer viewed;
    @Column(name = "modified")
    private LocalDateTime modified;
    @Size(max = 2147483647)
    @Column(name = "entity_type_code")
    private String entityTypeCode;
    @Size(max = 2147483647)
    @Column(name = "entity_type")
    private String entityType;
    @Size(max = 2147483647)
    @Column(name = "rego_status")
    private String regoStatus;
    @Column(name = "rego_effect_from")
    private LocalDate regoEffectFrom;
    @Column(name = "last_inquiried")
    private LocalDate lastInquiried;
    @Size(max = 2147483647)
    @Column(name = "rego_state")
    private String regoState;
    @Size(max = 2147483647)
    @Column(name = "rego_postcode")
    private String regoPostcode;
    @Size(max = 2147483647)
    @Column(name = "trade_as")
    private String tradeAs;

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getContacted() {
        return contacted;
    }

    public void setContacted(LocalDateTime contacted) {
        this.contacted = contacted;
    }

    public Integer getQuoted() {
        return quoted;
    }

    public void setQuoted(Integer quoted) {
        this.quoted = quoted;
    }

    public Integer getViewed() {
        return viewed;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getEntityTypeCode() {
        return entityTypeCode;
    }

    public void setEntityTypeCode(String entityTypeCode) {
        this.entityTypeCode = entityTypeCode;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRegoStatus() {
        return regoStatus;
    }

    public void setRegoStatus(String regoStatus) {
        this.regoStatus = regoStatus;
    }

    public LocalDate getRegoEffectFrom() {
        return regoEffectFrom;
    }

    public void setRegoEffectFrom(LocalDate regoEffectFrom) {
        this.regoEffectFrom = regoEffectFrom;
    }

    public LocalDate getLastInquiried() {
        return lastInquiried;
    }

    public void setLastInquiried(LocalDate lastInquiried) {
        this.lastInquiried = lastInquiried;
    }

    public String getRegoState() {
        return regoState;
    }

    public void setRegoState(String regoState) {
        this.regoState = regoState;
    }

    public String getRegoPostcode() {
        return regoPostcode;
    }

    public void setRegoPostcode(String regoPostcode) {
        this.regoPostcode = regoPostcode;
    }

    public String getTradeAs() {
        return tradeAs;
    }

    public void setTradeAs(String tradeAs) {
        this.tradeAs = tradeAs;
    }

    public Abn() {
    }

    public Abn(String abn){
        this.abn = abn;
    }

    public Abn(String abn, String name, String type, String location, String status, LocalDateTime created, LocalDateTime contacted, int quoted, int viewed, LocalDateTime modified,
               String entityTypeCode, String entityType, String regoStatus, LocalDate regoEffectFrom, LocalDate lastInquiried, String regoState, String regoPostcode, String tradeAs){
        this.abn = abn;
        this.name = name;
        this.type = type;
        this.location = location;
        this.status = status;

        this.created = created;
        this.contacted = contacted;
        this.quoted = quoted;
        this.viewed = viewed;
        this.modified = modified;

        this.entityTypeCode = entityTypeCode;
        this.entityType = entityType;
        this.regoStatus = regoStatus;
        this.regoEffectFrom = regoEffectFrom;
        this.lastInquiried = lastInquiried;

        this.regoState = regoState;
        this.regoPostcode = regoPostcode;
        this.tradeAs = tradeAs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (abn != null ? abn.hashCode() : 0);
        hash += (name != null ? name.hashCode() : 0);
        hash += (type != null ? type.hashCode() : 0);
        hash += (location != null ? location.hashCode() : 0);
        hash += (status != null ? status.hashCode() : 0);

        hash += (created != null ? created.hashCode() : 0);
        hash += (contacted != null ? contacted.hashCode() : 0);
        hash += (quoted != null ? quoted.hashCode() : 0);
        hash += (viewed != null ? viewed.hashCode() : 0);
        hash += (modified != null ? modified.hashCode() : 0);

        hash += (entityTypeCode != null ? entityTypeCode.hashCode() : 0);
        hash += (entityType != null ? entityType.hashCode() : 0);
        hash += (regoStatus != null ? regoStatus.hashCode() : 0);
        hash += (regoEffectFrom != null ? regoEffectFrom.hashCode() : 0);
        hash += (lastInquiried != null ? lastInquiried.hashCode() : 0);

        hash += (regoState != null ? regoState.hashCode() : 0);
        hash += (regoPostcode != null ? regoPostcode.hashCode() : 0);
        hash += (tradeAs != null ? tradeAs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abn)) {
            return false;
        }
        Abn other = (Abn) object;
        if ((this.abn == null && other.abn != null) || (this.abn != null && !this.abn.equals(other.abn))) { return false; }
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) { return false; }
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) { return false; }
        if ((this.location == null && other.location != null) || (this.location != null && !this.location.equals(other.location))) { return false; }
        if ((this.status == null && other.status != null) || (this.status != null && !this.status.equals(other.status))) { return false; }

        if ((this.created == null && other.created != null) || (this.created != null && !this.created.equals(other.created))) { return false; }
        if ((this.contacted == null && other.contacted != null) || (this.contacted != null && !this.contacted.equals(other.contacted))) { return false; }
        if ((this.quoted == null && other.quoted != null) || (this.quoted != null && !this.quoted.equals(other.quoted))) { return false; }
        if ((this.viewed == null && other.viewed != null) || (this.viewed != null && !this.viewed.equals(other.viewed))) { return false; }
        if ((this.modified == null && other.modified != null) || (this.modified != null && !this.modified.equals(other.modified))) { return false; }

        if ((this.entityTypeCode == null && other.entityTypeCode != null) || (this.entityTypeCode != null && !this.entityTypeCode.equals(other.entityTypeCode))) { return false; }
        if ((this.entityType == null && other.entityType != null) || (this.entityType != null && !this.entityType.equals(other.entityType))) { return false; }
        if ((this.regoStatus == null && other.regoStatus != null) || (this.regoStatus != null && !this.regoStatus.equals(other.regoStatus))) { return false; }
        if ((this.regoEffectFrom == null && other.regoEffectFrom != null) || (this.regoEffectFrom != null && !this.regoEffectFrom.equals(other.regoEffectFrom))) { return false; }
        if ((this.lastInquiried == null && other.lastInquiried != null) || (this.lastInquiried != null && !this.lastInquiried.equals(other.lastInquiried))) { return false; }

        if ((this.regoState == null && other.regoState != null) || (this.regoState != null && !this.regoState.equals(other.regoState))) { return false; }
        if ((this.regoPostcode == null && other.regoPostcode != null) || (this.regoPostcode != null && !this.regoPostcode.equals(other.regoPostcode))) { return false; }
        if ((this.tradeAs == null && other.tradeAs != null) || (this.tradeAs != null && !this.tradeAs.equals(other.tradeAs))) { return false; }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append("Id: " + this.abn + NEW_LINE);
        result.append("Name: " + this.name + NEW_LINE);
        result.append("Type: " + this.type + NEW_LINE);
        result.append("Location: " + this.location + NEW_LINE);
        result.append("Status: " + this.status + NEW_LINE);

        result.append("Created: " + this.created + NEW_LINE);
        result.append("Contacted: " + this.contacted + NEW_LINE);
        result.append("Quoted: " + this.quoted + NEW_LINE);
        result.append("Viewed: " + this.viewed + NEW_LINE);
        result.append("Modified: " + this.modified + NEW_LINE);

        result.append("Entity Type code: " + this.entityTypeCode + NEW_LINE);
        result.append("Entity Type: " + this.entityType + NEW_LINE);
        result.append("Rego Status: " + this.regoStatus + NEW_LINE);
        result.append("Rego effective from: " + this.regoEffectFrom + NEW_LINE);
        result.append("Last Inquired: " + this.lastInquiried + NEW_LINE);

        result.append("Rego state: " + this.regoState + NEW_LINE);
        result.append("Rego Post code: " + this.regoPostcode + NEW_LINE);
        result.append("Trade As: " + this.tradeAs + "}");

        return result.toString();
    }

    @Override
    public int getFactoryId() {
        return AbnDataSerializableFactory.FACTORY_ID;
    }

    @Override
    public int getClassId() {
        return this.CLASS_ID;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeString(abn);
        out.writeString(name);
        out.writeString(type);
        out.writeString(location);
        out.writeString(status);

        out.writeLong(ThssLibUtils.getLongFromLocalDateTime(created));
        out.writeLong(ThssLibUtils.getLongFromLocalDateTime(contacted));
        out.writeInt(quoted == null ? 0 : quoted);/*(city.getName() == null) ? "N/A" : city.getName()*/
        out.writeInt(viewed == null ? 0 : viewed);
        out.writeLong(ThssLibUtils.getLongFromLocalDateTime(modified));

        out.writeString(entityTypeCode);
        out.writeString(entityType);
        out.writeString(regoStatus);
        out.writeLong(ThssLibUtils.getLongfromLocalDate(regoEffectFrom));
        out.writeLong(ThssLibUtils.getLongfromLocalDate(lastInquiried));

        out.writeString(regoState);
        out.writeString(regoPostcode);
        out.writeString(tradeAs);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        setAbn(in.readString());
        setName(in.readString());
        setType(in.readString());
        setLocation(in.readString());
        setStatus(in.readString());

        setCreated(ThssLibUtils.getLocalDateTimeFromLong(in.readLong()));
        setContacted(ThssLibUtils.getLocalDateTimeFromLong(in.readLong()));
        setQuoted(in.readInt());
        setViewed(in.readInt());
        setModified(ThssLibUtils.getLocalDateTimeFromLong(in.readLong()));

        setEntityTypeCode(in.readString());
        setEntityType(in.readString());
        setRegoStatus(in.readString());
        setRegoEffectFrom(ThssLibUtils.getLocalDatefromLong(in.readLong()));
        setLastInquiried(ThssLibUtils.getLocalDatefromLong(in.readLong()));

        setRegoState(in.readString());
        setRegoPostcode(in.readString());
        setTradeAs(in.readString());
    }
}
