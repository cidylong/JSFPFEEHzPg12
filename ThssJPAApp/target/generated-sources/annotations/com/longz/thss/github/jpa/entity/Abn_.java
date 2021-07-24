package com.longz.thss.github.jpa.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2021-07-23T17:02:14", comments="EclipseLink-2.7.7.v20201029-rNA")
@StaticMetamodel(Abn.class)
public class Abn_ { 

    public static volatile SingularAttribute<Abn, LocalDateTime> contacted;
    public static volatile SingularAttribute<Abn, LocalDate> lastInquiried;
    public static volatile SingularAttribute<Abn, LocalDateTime> created;
    public static volatile SingularAttribute<Abn, String> entityType;
    public static volatile SingularAttribute<Abn, String> regoPostcode;
    public static volatile SingularAttribute<Abn, String> abn;
    public static volatile SingularAttribute<Abn, String> type;
    public static volatile SingularAttribute<Abn, Integer> quoted;
    public static volatile SingularAttribute<Abn, String> regoState;
    public static volatile SingularAttribute<Abn, String> entityTypeCode;
    public static volatile SingularAttribute<Abn, Integer> viewed;
    public static volatile SingularAttribute<Abn, String> tradeAs;
    public static volatile SingularAttribute<Abn, String> name;
    public static volatile SingularAttribute<Abn, LocalDateTime> modified;
    public static volatile SingularAttribute<Abn, String> location;
    public static volatile SingularAttribute<Abn, String> regoStatus;
    public static volatile SingularAttribute<Abn, String> status;
    public static volatile SingularAttribute<Abn, LocalDate> regoEffectFrom;

}