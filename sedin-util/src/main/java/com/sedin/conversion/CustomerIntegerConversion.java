package com.sedin.conversion;

import com.sedin.util.Utility;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by lh on 2016/5/4.
 */
public class CustomerIntegerConversion implements Converter<String , Integer> {
        @Override
        public Integer convert(String source) {
            Integer longObj = 0;
            if(Utility.isInteger(source)){
                source = source.replace("," ,"");
                longObj = Integer.parseInt(source);
            }
            return longObj;
        }
}
