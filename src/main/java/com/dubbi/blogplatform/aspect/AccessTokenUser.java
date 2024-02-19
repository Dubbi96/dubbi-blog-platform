package com.dubbi.blogplatform.aspect;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessTokenUser {
}
