package com.temporalis.io.directives;

import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.junit.jupiter.api.Test;

import static graphql.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class ScopedTest {

    @Test
    public void onObject()
    {
        var scoped = new Scoped();
        assertNull(scoped.onObject(mock(SchemaDirectiveWiringEnvironment.class)));
    }
}
