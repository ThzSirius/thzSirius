package kernel.Common;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

public class TypeUtil {

    /**
     * @param preValue   需要转化的值
     * @param convertTo  需要转化成的类型
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T convert(Object preValue,Class<T>  convertTo) throws Exception{
        if(preValue == null || StringUtils.isBlank(preValue.toString())){
            return  null;
        }

        if(convertTo.equals(String.class)){
            return convertTo.cast(preValue.toString());
        }else if(isBasicType(convertTo)){
          String simpleName = convertTo.getSimpleName();
          simpleName = simpleName.equals("Integer")? "Int" : simpleName;
          Method method = convertTo.getMethod("parse"+simpleName,String.class);
          return convertTo.cast(method.invoke(null,preValue.toString()));

        }

        return  convertTo.cast(preValue);


    }


    public static boolean isBasicType(Object object){
        return object != null && isBasicType(object.getClass());
    }

    public static boolean isBasicType(Class clazz){
        return Integer.class.equals(clazz) || Float.class.equals(clazz) || Double.class.equals(clazz) || Byte.class.equals(clazz) || Long.class.equals(clazz) || Short.class.equals(clazz) || Boolean.class.equals(clazz)
                || String.class.equals(clazz);
    }

    private TypeUtil(){}

}
