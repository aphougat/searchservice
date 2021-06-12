package com.cosmoneural.search.searchservice.tenant;

public class InvalidTenantException extends Exception
{
    private final Object tenant;

    public InvalidTenantException(String message, Object tenant)
    {
        super(message);
        this.tenant = tenant;
    }

    public Object getTenant()
    {
        return tenant;
    }
}
