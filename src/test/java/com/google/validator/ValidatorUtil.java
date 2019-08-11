package com.google.validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.log4j.Log4j;

@Log4j
public class ValidatorUtil {
	
	public <T>  List<String> validate( final Object expectedObj,  final Object actualObj,final Class<T> clazz) throws Exception {
		List<Field> fieldsToVerify = getFieldsToVerify(clazz);
		List<String> errorMessageList = new ArrayList<String>();
		for(Field field : fieldsToVerify) {
			String errorMessage = equalsCheck(field, expectedObj, actualObj);
			if(errorMessage != null && !errorMessage.isEmpty())
				errorMessageList.add(errorMessage);
		}
		return errorMessageList;
	}

	private  <T> List<Field> getFieldsToVerify(final Class<T> clazz) {
		List<Field> fieldNameList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            Validator fieldProperty = field.getAnnotation(Validator.class);
            if (Objects.nonNull(fieldProperty) && fieldProperty.validate() == Boolean.TRUE.booleanValue()) {
                fieldNameList.add(field);
            }
        }
        return fieldNameList;
	}
	
	private String equalsCheck(final Field fieldName, final Object expected, final Object actual) throws Exception {
		
		Object expectedFieldValue = fieldName.get(expected);
		Object actualFieldValue = fieldName.get(actual);
		
		if(expectedFieldValue.equals(actualFieldValue))
			return null;
		else {
			String errorMessage = String.format("Expected value %s in field %s, but actual value obtained %s", 
					expectedFieldValue.toString(), fieldName.getName(),  actualFieldValue.toString());
			log.error(errorMessage);
			return errorMessage;
		}
	}
}
