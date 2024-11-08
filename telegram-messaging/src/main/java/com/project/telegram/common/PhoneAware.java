package com.project.telegram.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.telegram.util.PhoneUtil;

public interface PhoneAware {

    String getPhone();

    @JsonIgnore
    default String getStandardizedPhone() {
        return PhoneUtil.standardizePhoneNumber(getPhone());
    }

}
