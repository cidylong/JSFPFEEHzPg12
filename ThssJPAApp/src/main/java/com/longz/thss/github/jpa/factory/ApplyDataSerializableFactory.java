package com.longz.thss.github.jpa.factory;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.longz.thss.github.jpa.entity.Apply;

public class ApplyDataSerializableFactory implements DataSerializableFactory {
    public static int FACTORY_ID = 1132;
    public static int APPLY_TYPE = FACTORY_ID;

    @Override
    public IdentifiedDataSerializable create(int typeId) {
        if (typeId == APPLY_TYPE) {
            return new Apply();
        } else {
            return null;
        }
    }
}
