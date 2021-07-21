package com.longz.thss.github.jpa.factory;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.longz.thss.github.jpa.entity.Apply;

public class ApplyDataSerializableFactory implements DataSerializableFactory {
    public static int FACTORY_ID = 11132;
    /*public static int APPLY_TYPE = FACTORY_ID;*/

    @Override
    public IdentifiedDataSerializable create(int typeId) {
        if (typeId == 1132) {
            return new Apply();
        } else {
            return null;
        }
    }
}
