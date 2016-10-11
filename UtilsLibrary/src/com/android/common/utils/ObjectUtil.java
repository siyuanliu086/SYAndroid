package com.android.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

/**
 * @author LuoJ
 * @date 2014-1-9
 * @package com.lidroid.xutils.ext.util -- ObjectUtil.java
 * @Description 对象工具类
 */
public class ObjectUtil {

	/**
	 * 判断对象中是否存在某个类型的属性
	 * @return
	 */
	public static boolean fieldExist(Object obj,Class<?> field){
		if (null==obj)throw new NullPointerException("被检查的对象不能为空，请检查参数");
		boolean is=false;
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String typeString = fields[i].getType().toString();
			typeString=typeString.substring(typeString.lastIndexOf(".")+1);
			if (field.getSimpleName().equals(typeString)) {
				is=true;
				break;
			}
		}
		return is;
	}
	
	/**
	 * 检测参数是否合法
	 */
	public static void checkFieldName(Object obj,String fieldName){
		if(null==obj)throw new NullPointerException("obj参数不能为空");
		checkFieldName(obj.getClass().getDeclaredFields(), fieldName, true);
	}
	
	public static boolean checkFieldName(Field[] fields,String fieldName,boolean throwException){
		if(null==fields||fields.length==0)throw new NullPointerException("对象中没有任何属性");
		boolean isHave=false;
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				isHave=true;
				break;
			}
		}
		if (!isHave) {
			if(throwException)throw new NullPointerException("注解中声明的属性名称在实体类中不存在，请检查是否输入正确(建议直接在实体类中复制然后粘贴)");
		}
		return isHave;
	}
	
	/**
	 * 克隆一个对象(深拷贝)
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
    public static Object deepClone(Object obj) throws IOException, ClassNotFoundException{
        //将对象写到流里
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        //从流里读回来
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
	
}
