package com.demo.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;


/**
 * Author:  Hardy
 * Date:    2018/7/18 20:05
 * Description:
 **/
public class JsonTool {

    private static final ValueFilter bigDecimalValueFilter = new BigDecimalValueFilter();

    public static String toJson(Object o) {
        if (o == null) return null;
        return JSON.toJSONString(o,
                bigDecimalValueFilter,
                SerializerFeature.WriteEnumUsingName,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteBigDecimalAsPlain);
    }

    public static String toJsonExclude(Object o, String... excludes) {
        if (o == null) return null;

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();

        for (String exclude : excludes) {
            filter.getExcludes().add(exclude);
        }

        return JSON.toJSONString(o, new SerializeFilter[]{filter, bigDecimalValueFilter},
                SerializerFeature.WriteEnumUsingName,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteDateUseDateFormat);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        json = json == null ? "" : json.trim();

        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, clazz, Feature.IgnoreNotMatch);
    }

    static class BigDecimalValueFilter implements ValueFilter {
        @Override
        public Object process(Object o, String name, Object value) {
            if (value instanceof BigDecimal) {
                return ((BigDecimal) value).toPlainString();
            }
            return value;
        }
    }
}
