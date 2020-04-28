package org.mikufans.core.impl.support;


public abstract class SupperClassTemplate extends ClassTemplate
{

    protected final Class<?> superClass;

    public SupperClassTemplate(String packageName, Class<?> superClass)
    {
        super(packageName);
        this.superClass = superClass;
    }
}
