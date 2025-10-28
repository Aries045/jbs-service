package com.ruoyi.common.phone;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// 注解作用目标：字段、方法参数等
@Target({ElementType.FIELD, ElementType.PARAMETER})
// 注解保留周期：运行时（因为需要在运行时校验）
@Retention(RetentionPolicy.RUNTIME)
// 标记为约束注解（JSR 303规范），指定校验器
@Constraint(validatedBy = PhoneValidator.class)
// 允许在JavaDoc中显示该注解
@Documented
public @interface Phone {
    // 校验失败的默认提示信息
    String message() default "手机号格式错误";

    // 分组校验（用于不同场景下的校验分组，如新增/更新）
    Class<?>[] groups() default {};

    // 负载信息（通常用于携带额外元数据）
    Class<? extends Payload>[] payload() default {};

    // 可选：支持自定义手机号正则表达式（默认国内手机号规则）
    String regexp() default "^1[3-9]\\d{9}$";
}
