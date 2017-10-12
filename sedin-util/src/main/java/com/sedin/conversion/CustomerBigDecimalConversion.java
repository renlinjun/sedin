package com.sedin.conversion;

import com.sedin.util.Utility;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * Created by lh on 2016/5/4.
 */
public class CustomerBigDecimalConversion implements Converter<String , BigDecimal> {
    @Override
    public BigDecimal convert(String source) {
        BigDecimal bigDecimal = new BigDecimal(0);
        if(Utility.isNumber(source)){
            source = source.replace("," ,"");
            bigDecimal = new BigDecimal(source);
        }
        return bigDecimal;
    }
}
