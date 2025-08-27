package com.example.ra.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    private String TMP_URL = "/";
    private Long maxFileSize = 50 * 1024 * 1024L;
    private Long maxRequestSize = 40 * maxFileSize;
    private Integer fileThreshold = 0;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{CloudinaryConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        super.customizeRegistration(registration);
        registration.setMultipartConfig(new MultipartConfigElement(TMP_URL, maxFileSize, maxRequestSize, fileThreshold));
    }
}
