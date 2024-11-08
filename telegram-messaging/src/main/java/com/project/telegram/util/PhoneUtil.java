package com.project.telegram.util;

import org.apache.commons.lang3.StringUtils;

public final class PhoneUtil {
    private PhoneUtil() {
    }

    public static String standardizePhoneNumber(String phoneNumber) {
        return "0" + StringUtils.right(phoneNumber, 9);
    }
}
