package cn.eva.mini.application.dto.product.function;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 用于功能的数据类型。
 * 包括：boolean － 布尔型，value － 数值型，enum - 枚举型，string － 字符型。
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BooleanType.class, name = FunctionDataTypeValues.BOOLEAN_TYPE),
    @JsonSubTypes.Type(value = BooleanType.ValueType.class, name = FunctionDataTypeValues.VALUE_TYPE),
    @JsonSubTypes.Type(value = EnumType.class, name = FunctionDataTypeValues.ENUM_TYPE),
    @JsonSubTypes.Type(value = StringType.class, name = FunctionDataTypeValues.STRING_TYPE),
})
public interface FunctionDataType {

}
