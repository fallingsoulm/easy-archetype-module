package io.github.fallingsoulm.easy.archetype.data.file.filehost;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luyanan
 * @since 2021/6/6
 **/
public class HandleResult {
    public enum Type {
        /**
         * 创建新的序列化属性
         */
        NEW,
        /**
         * 覆盖原有属性值
         */
        REPLACE;
    }

    /**
     * 序列化属性名称
     */
    private final String fieldName;
    /**
     * 序列化属性值
     */
    private final Object value;
    /**
     * 序列化赋值方式
     */
    private final Type type;
    /**
     * 其他序列化属性集合，便于扩展适用于特殊场景
     */
    private Map<String, Object> fields;

    public HandleResult(String fieldName, Object value, Type type) {
        this.fieldName = fieldName;
        this.type = type;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public HandleResult addField(String name, Object value) {
        if (this.fields == null) {
            this.fields = new HashMap<>();
        }
        this.fields.put(name, value);
        return this;
    }

    public Map<String, Object> getFields() {
        return fields;
    }
}
