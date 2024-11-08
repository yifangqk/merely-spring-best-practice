package com.project.telegram.application;

import com.project.telegram.auth.AppUserDetails;
import com.project.telegram.auth.account.Account;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestContext implements InitializingBean {

    @Getter
    private static RequestContext instance;

    private final ThreadLocal<Map<String, Object>> userData = ThreadLocal.withInitial(HashMap::new);

    public Account getCurrentUser() {
        return getUserDetails().getAccount();
    }

    private AppUserDetails getUserDetails() {
        try {
            return (AppUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception ex) {
            return new AppUserDetails(new Account());
        }
    }

    public <T> T putUserData(String key, T value) {
        var currentThreadUserData = userData.get();
        return (T) currentThreadUserData.computeIfAbsent(key, (k) -> value);
    }

    @Nullable
    public <T> T getUserData(String key) {
        return (T) userData.get().get(key);
    }

    public void clearUserData() {
        userData.remove();
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

}
