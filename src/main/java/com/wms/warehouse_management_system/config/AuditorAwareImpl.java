package com.wms.warehouse_management_system.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

@Override
    public Optional<String> getCurrentAuditor(){
        return Optional.of("SYSTEM");// later replace with logged-in user
    }
}
