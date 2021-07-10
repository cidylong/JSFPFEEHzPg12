package com.longz.thss.github.jpa.entity;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.longz.thss.github.jpa.factory.ApplyDataSerializableFactory;
import com.longz.thss.github.jpa.utils.ThssLibUtils;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "Apply")
@Table(name = "apply",catalog = "ozssc", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Apply.findAll", query = "SELECT a FROM Apply a"),
        @NamedQuery(name = "Apply.findAllIds",query = "select a.applyId from Apply a order by a.applyId desc"),
        @NamedQuery(name = "Apply.findByApplyId", query = "SELECT a FROM Apply a WHERE a.applyId = :applyId"),
        @NamedQuery(name = "Apply.findByApplicant", query = "SELECT a FROM Apply a WHERE a.applicant = :applicant"),
        @NamedQuery(name = "Apply.findByTarget", query = "SELECT a FROM Apply a WHERE a.target = :target"),
        @NamedQuery(name = "Apply.findByComment", query = "SELECT a FROM Apply a WHERE a.comment = :comment"),
        @NamedQuery(name = "Apply.findByCategory", query = "SELECT a FROM Apply a WHERE a.category = :category"),
        @NamedQuery(name = "Apply.findByCreated", query = "SELECT a FROM Apply a WHERE a.created = :created"),
        @NamedQuery(name = "Apply.findByStatus", query = "SELECT a FROM Apply a WHERE a.status = :status"),
        @NamedQuery(name = "Apply.findByResponsible", query = "SELECT a FROM Apply a WHERE a.responsible = :responsible"),
        @NamedQuery(name = "Apply.findByProcessed", query = "SELECT a FROM Apply a WHERE a.processed = :processed"),
        @NamedQuery(name = "Apply.findByTargetOwner", query = "SELECT a FROM Apply a WHERE a.targetOwner = :targetOwner")})
public class Apply implements IdentifiedDataSerializable, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apply_id")
    private String applyId;

    @Size(max = 2147483647)
    @Column(name = "applicant")
    private String applicant;

    @Size(max = 2147483647)
    @Column(name = "target")
    private String target;

    @Size(max = 2147483647)
    @Column(name = "comment")
    private String comment;

    @Size(max = 2147483647)
    @Column(name = "category")
    private String category;

    /*@Temporal(TemporalType.TIMESTAMP)*/
    @Column(name = "created")
    private LocalDateTime created;

    @Size(max = 2147483647)
    @Column(name = "status")
    private String status;

    @Size(max = 2147483647)
    @Column(name = "responsible")
    private String responsible;

    /*@Temporal(TemporalType.TIMESTAMP)*/
    @Column(name = "processed")
    private LocalDateTime processed;

    @Size(max = 2147483647)
    @Column(name = "target_owner")
    private String targetOwner;

    public Apply() {
        initial();
    }

    public Apply(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public LocalDateTime getProcessed() {
        return processed;
    }

    public void setProcessed(LocalDateTime processed) {
        this.processed = processed;
    }

    public String getTargetOwner() {
        return targetOwner;
    }

    public void setTargetOwner(String targetOwner) {
        this.targetOwner = targetOwner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applyId != null ? applyId.hashCode() : 0);
        hash += (applicant != null ? applicant.hashCode() : 0);
        hash += (target != null ? target.hashCode() : 0);
        hash += (comment != null ? comment.hashCode() : 0);
        hash += (category != null ? category.hashCode() : 0);

        hash += (created != null ? created.hashCode() : 0);
        hash += (status != null ?  status.hashCode() : 0);
        hash += (responsible != null ? responsible.hashCode() : 0);
        hash += (processed != null ? processed.hashCode() : 0);
        hash += (targetOwner != null ? targetOwner.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apply)) {
            return false;
        }
        Apply other = (Apply) object;
        if ((this.applyId == null && other.applyId != null) || (this.applyId != null && !this.applyId.equals(other.applyId))) { return false; }
        if ((this.applicant == null && other.applicant != null) || (this.applicant != null && !this.applicant.equals(other.applicant))) { return false; }
        if ((this.target == null && other.target != null) || (this.target != null && !this.target.equals(other.target))) { return false; }
        if ((this.comment == null && other.comment != null) || (this.comment != null && !this.comment.equals(other.comment))) { return false; }
        if ((this.category == null && other.category != null) || (this.category != null && !this.category.equals(other.category))) { return false; }

        if ((this.created == null && other.created != null) || (this.created != null && !this.created.equals(other.created))) { return false; }
        if ((this.status == null && other.status != null) || (this.status != null && !this.status.equals(other.status))) { return false; }
        if ((this.responsible == null && other.responsible != null) || (this.responsible != null && !this.responsible.equals(other.responsible))) { return false; }
        if ((this.processed == null && other.processed != null) || (this.processed != null && !this.processed.equals(other.processed))) { return false; }
        if ((this.targetOwner == null && other.targetOwner != null) || (this.targetOwner != null && !this.targetOwner.equals(other.targetOwner))) { return false; }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append("Id: " + this.applyId + NEW_LINE);
        result.append("Applicant: " + this.applicant + NEW_LINE);
        result.append("Target position: " + this.target + NEW_LINE);
        result.append("Comment: " + this.comment + NEW_LINE);
        result.append("Category: " + this.category + NEW_LINE);

        result.append("Created: " + this.created + NEW_LINE);
        result.append("Status: " + this.status + NEW_LINE);
        result.append("Responsible party: " + this.responsible + NEW_LINE);
        result.append("Processed time: " + this.processed + NEW_LINE);
        result.append("Target position owner: " + this.targetOwner + "}");
        return  result.toString();
    }

    @Override
    public int getFactoryId() {
        return ApplyDataSerializableFactory.FACTORY_ID;
    }

    @Override
    public int getClassId() {
        return ApplyDataSerializableFactory.APPLY_TYPE;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeString(applyId);
        out.writeString(applicant);
        out.writeString(target);
        out.writeString(comment);
        out.writeString(category);

        out.writeLong(ThssLibUtils.getLongFromLocalDateTime(created));
        out.writeString(status);
        out.writeString(responsible);
        out.writeLong(ThssLibUtils.getLongFromLocalDateTime(processed));
        out.writeString(targetOwner);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        setApplyId(in.readString());
        setApplicant(in.readString());
        setTarget(in.readString());
        setComment(in.readString());
        setCategory(in.readString());

        setCreated(ThssLibUtils.getLocalDateTimeFromLong(in.readLong()));
        setStatus(in.readString());
        setResponsible(in.readString());
        setProcessed(ThssLibUtils.getLocalDateTimeFromLong(in.readLong()));
        setTargetOwner(in.readString());
    }

    protected void initial(){
        if(applyId == null || applyId.trim().length() == 0){
            setApplyId(ThssLibUtils.getEntityId("APPLY"));
            setCreated(LocalDateTime.now());
            setStatus("new");
        }
    }
}
