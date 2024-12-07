package org.powerimo.common.resstrings;

import org.junit.jupiter.api.Test;
import org.powerimo.common.resstrings.CacheableResStringProvider;
import org.powerimo.common.resstrings.InMemorySource;
import org.powerimo.common.resstrings.ResourceRecord;

import static org.junit.jupiter.api.Assertions.*;

public class CacheableProviderTest {

    @Test
    void test01() {
        var basic = new InMemorySource();
        basic.addResourceRecord( ResourceRecord.builder()
                        .code("CODE-0001")
                        .httpCode(400)
                        .text("en", "test en")
                        .text("de", "test de")
                .build() );

        var provider= new CacheableResStringProvider("en", basic);

        var res = provider.getResourceText("CODE-0001");

        assertNotNull(res);
        assertEquals("test en", res);
    }

    @Test
    void test02() {
        var basic = new InMemorySource();
        basic.addResourceRecord( ResourceRecord.builder()
                .code("CODE-0001")
                .httpCode(400)
                .text("en", "test en")
                .text("de", "test de")
                .build() );

        var provider= new CacheableResStringProvider("en", basic);

        var res = provider.getResourceRecord("CODE-0001");

        assertNotNull(res);
        assertEquals(400, res.getHttpCode());
    }

    @Test
    void stringIsNotExists() {
        var basic = new InMemorySource();
        var provider= new CacheableResStringProvider("en", basic);
        var res = provider.getResourceRecord("CODE-0002");

        assertNull(res);
    }

    @Test
    void cacheTest() {
        var basic = new InMemorySource();
        basic.addResourceRecord( ResourceRecord.builder()
                .code("CODE-0001")
                .httpCode(400)
                .text("en", "test en")
                .text("de", "test de")
                .build() );

        var provider= new CacheableResStringProvider("en", basic);
        var res = provider.getResourceRecord("CODE-0001");
        var res2 = provider.getResourceRecord("CODE-0001");

        assertNotNull(res);
        assertEquals(400, res.getHttpCode());
        assertEquals(400, res2.getHttpCode());
    }

}
