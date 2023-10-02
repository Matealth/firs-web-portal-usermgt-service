package com.firs.wps.usermgt.converter;

import com.firs.wps.usermgt.enums.RoleEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {
    @Override
    public String convertToDatabaseColumn(RoleEnum role) {
        if (role == null) {
            return null;
        }
        return role.getValue();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(RoleEnum.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
