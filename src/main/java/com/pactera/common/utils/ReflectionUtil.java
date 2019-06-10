/**
 * 
 */
package com.pactera.common.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuqs
 * @version 
 * @since 2018年2月27日 下午4:48:51
 */
public class ReflectionUtil {

	public static void getObjectAllFileds(Class<?> clazz, List<Field> fields){
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		
		//过滤掉自动生成的 serialVersionUID 字段
		for(Field field:fields) {
			if(field.getName().equals("serialVersionUID")) {
				fields.remove(field);
				break;
			}
		}
		
		if(clazz.getSuperclass() != null && clazz.getSuperclass().getPackage().equals(clazz.getPackage())){
			getObjectAllFileds(clazz.getSuperclass(), fields);
		}
	}
	
	/************************************************/
	public static Object getValue(Object obj, String fieldName) {
		try {
			Class<? extends Object> clazz = obj.getClass();
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method getMethod = pd.getReadMethod(); // 获得get方法
			Object o = getMethod.invoke(obj); // 执行get方法返回一个Object
			return o;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setValue(Object obj, String fieldName, Object value) {
		Class<? extends Object> clazz = obj.getClass();
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method setMethod = pd.getWriteMethod(); // 获得set方法
			setMethod.invoke(obj, value);// 执行 set 方法
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Object> convertBean2Map(Object obj) {
		Map<String, Object> map = new HashMap<>();
		// 获取obj对象对应类中的所有属性域
		List<Field> fieldList = new ArrayList<>();
		getObjectAllFileds(obj.getClass(), fieldList);

		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		for (Field fd : fields) {
			String varName = fd.getName();
			try {
				if (!Modifier.isFinal(fd.getModifiers()) && !Modifier.isStatic(fd.getModifiers())
						&& Modifier.isPrivate(fd.getModifiers())) {

					// 获取原来的访问控制权限
					boolean accessFlag = fd.isAccessible();
					// 修改访问控制权限
					fd.setAccessible(true);
					// 获取在对象f中属性fields[i]对应的对象中的变量
					Object o = fd.get(obj);
					if (o != null){
						map.put(varName, o);
					}
					// 恢复访问控制权限
					fd.setAccessible(accessFlag);
				}
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}
	
}
