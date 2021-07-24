package com.longz.thss.github.jpa.factory;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.longz.thss.github.jpa.entity.Abn;

public class AbnDataSerializableFactory implements DataSerializableFactory {
    /*private static final String ENTITY_NAME = "Abn";*/
    public static final int FACTORY_ID = 11102; /*( new FactoryIdAccessor()).getFactoryIdByEntityName(ENTITY_NAME);*/
    /*public static int ABN_TYPE = FACTORY_ID;*/

    @Override
    public IdentifiedDataSerializable create(int typeId) {
        if (typeId == 1102) {
            return new Abn();
        } else {
            return null;
        }
    }
}
