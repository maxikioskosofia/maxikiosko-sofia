package com.ecommerce.ecommerce.dto.errors;


public record ErrorDtoNotFound(
    String path,
    
    String message
) {

}