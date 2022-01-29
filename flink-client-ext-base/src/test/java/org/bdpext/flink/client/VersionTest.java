package org.bdpext.flink.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionTest {

    @org.junit.jupiter.api.Test
    void fromString() {
        String ver1 = "v1.14.2";

        Version version = Version.fromString(ver1);
        assertEquals(version, Version.V_1_14_2);

        ver1 = "V1.14.2";
        version = Version.fromString(ver1);
        assertEquals(version, Version.V_1_14_2);

        ver1 = "11100";
        version = Version.fromString(ver1);
        assertEquals(version, Version.V_1_11_0);

        ver1 = "10604";
        version = Version.fromString(ver1);
        assertEquals(version, Version.V_1_6_4);

        assertThrows(IllegalArgumentException.class, () -> Version.fromString("x1.14.2"));
        assertThrows(IllegalArgumentException.class, () -> Version.fromString("10699"));
    }

    @org.junit.jupiter.api.Test
    void after() {
        Version first = Version.V_1_1_2;
        Version second = Version.V_1_2_0;

        assertTrue(second.after(first));

        second = Version.V_1_1_1;
        assertFalse(second.after(first));
    }

    @org.junit.jupiter.api.Test
    void onOrAfter() {
        Version first = Version.V_1_1_2;
        Version second = Version.V_1_1_2;
        assertTrue(second.onOrAfter(first));

        second = Version.V_1_1_3;
        assertTrue(second.onOrAfter(first));
    }

    @org.junit.jupiter.api.Test
    void before() {
        Version first = Version.V_1_2_1;
        Version second = Version.V_1_1_2;

        assertFalse(first.before(second));

        second = Version.V_1_3_1;
        assertTrue(first.before(second));
    }

    @org.junit.jupiter.api.Test
    void onOrBefore() {
        Version first = Version.V_1_1_2;
        Version second = Version.V_1_1_2;

        assertTrue(first.onOrBefore(second));

        second = Version.V_1_1_3;
        assertTrue(first.onOrBefore(second));
    }

    @org.junit.jupiter.api.Test
    void next() {
        Version first = Version.V_1_12_4;
        assertEquals(first.next(), Version.V_1_12_5);

        first = Version.CURRENT;
        assertEquals(first.next(), Version.NEXT);
    }

    @org.junit.jupiter.api.Test
    void previous() {
        Version first = Version.V_1_8_3;
        assertEquals(first.previous(), Version.V_1_8_2);

        first = Version.OLDEST;
        assertEquals(first.previous(), first);
    }
}
