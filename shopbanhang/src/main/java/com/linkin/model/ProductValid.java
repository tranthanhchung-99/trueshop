package com.linkin.model;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
@Component
public class ProductValid implements org.springframework.validation.Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ProductDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ProductDTO productDTO= (ProductDTO) target;
		if(productDTO.getName()==null||productDTO.getName().length()==0) {
			errors.rejectValue("name", "report.name");
		}
		if(productDTO.getDescription()==null||productDTO.getDescription().length()==0) {
			errors.rejectValue("description", "report.de");
		}
		MultipartFile multipartFile= productDTO.getMultipartFile();
		if (multipartFile.getSize() == 0) {
            errors.rejectValue("multipartFile", "myapplication.missing.file");
        }
		if(productDTO.getPriceIn()==null) {
			errors.rejectValue("priceIn", "report.priceIn");
		}
		else if (productDTO.getPriceIn() < 0) {
			errors.rejectValue("priceIn", "report.priceOut1");
        }
		if(productDTO.getPriceOut()==null) {
			errors.rejectValue("priceOut", "report.priceOut");
		}
		else if (productDTO.getPriceOut() < 0) {
			errors.rejectValue("priceOut", "report.priceOut1");
        }
		if(productDTO.getDescription()==null) {
			errors.rejectValue("description", "report.de");
		}
		if(productDTO.getSoLuong()==null ) {
			errors.rejectValue("soLuong", "report.soluong");
		}else if (productDTO.getSoLuong() < 0) {
			errors.rejectValue("soLuong", "report.soluong1");
        }
		
	}
	
}
