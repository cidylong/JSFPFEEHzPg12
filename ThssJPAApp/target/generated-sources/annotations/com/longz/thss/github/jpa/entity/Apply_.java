package com.longz.thss.github.jpa.entity;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2021-07-21T15:52:19", comments="EclipseLink-2.7.7.v20201029-rNA")
@StaticMetamodel(Apply.class)
public class Apply_ { 

    public static volatile SingularAttribute<Apply, String> applyId;
    public static volatile SingularAttribute<Apply, LocalDateTime> processed;
    public static volatile SingularAttribute<Apply, LocalDateTime> created;
    public static volatile SingularAttribute<Apply, String> responsible;
    public static volatile SingularAttribute<Apply, String> comment;
    public static volatile SingularAttribute<Apply, String> category;
    public static volatile SingularAttribute<Apply, String> targetOwner;
    public static volatile SingularAttribute<Apply, String> applicant;
    public static volatile SingularAttribute<Apply, String> target;
    public static volatile SingularAttribute<Apply, String> status;

}