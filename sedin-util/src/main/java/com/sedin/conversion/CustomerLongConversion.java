package com.sedin.conversion;

import com.sedin.util.Utility;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by lh on 2016/5/4.
 */
public class CustomerLongConversion implements Converter<String , Long> {
    @Override
    public Long convert(String source) {
        Long longObj = 0l;
        if(Utility.isInteger(source)){
            source = source.replace("," ,"");
            longObj = Long.parseLong(source);
        }
        return longObj;
    }
}
