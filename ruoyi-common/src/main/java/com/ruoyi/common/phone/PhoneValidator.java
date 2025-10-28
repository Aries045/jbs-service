package com.ruoyi.common.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    // 用于编译正则表达式的Pattern
    private Pattern pattern;
    // 初始化方法：获取@Phone注解中的regexp参数
    @Override
    public void initialize(Phone phoneAnnotation) {
        // 编译注解中指定的正则表达式（默认或自定义）
        pattern = Pattern.compile(phoneAnnotation.regexp());
    }

    // 核心校验方法：返回true表示校验通过，false表示失败
    @Override
    public boolean isValid(String phoneValue, ConstraintValidatorContext context) {
        // 注意：如果字段允许为null，需要先判断（非空校验建议用@NotNull）
        if (phoneValue == null) {
            // 此处返回true，因为非空校验交给@NotNull，当前只负责格式
            return true;
        }
        // 用正则匹配手机号
        return pattern.matcher(phoneValue).matches();
    }
}
