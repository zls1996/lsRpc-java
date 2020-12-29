package org.ls.rpc.server.model.registry;

import lombok.Getter;

/**
 * Created By 朱立松 on 2020/12/21
 */
@Getter
public enum RegistryTypeEnum {
    Zookeeper("zookeeper");
    private String type;

    RegistryTypeEnum(String type) {
        this.type = type;
    }

    public static RegistryTypeEnum getRegistryType(String type) {
        for (RegistryTypeEnum registryTypeEnum : RegistryTypeEnum.values()) {
            if (registryTypeEnum.getType().equals(type)) {
                return registryTypeEnum;
            }
        }

        return null;
    }
}
