package org.mikufans.core.impl.support;

import java.lang.annotation.Annotation;

public abstract class AnnotationClassTemplate extends ClassTemplate
{
    protected final Class<? extends Annotation> annotationClass;

    public AnnotationClassTemplate(String packageName, Class<? extends Annotation> annotationClass)
    {
        super(packageName);
        this.annotationClass = annotationClass;
    }
}
