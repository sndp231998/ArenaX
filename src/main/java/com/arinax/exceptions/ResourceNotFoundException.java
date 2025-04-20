package com.arinax.exceptions;

import com.arinax.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	Object fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(Class<?> resourceClass, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceClass.getSimpleName(), fieldName, fieldValue));
        this.resourceName = resourceClass.getSimpleName();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
