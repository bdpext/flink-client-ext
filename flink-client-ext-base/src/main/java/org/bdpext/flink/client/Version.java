package org.bdpext.flink.client;

import static org.bdpext.flink.client.Version.DeclaredVersionsHolder.DECLARED_VERSIONS;

import org.joor.Reflect;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Version implements Comparable<Version> {

    public static final Version V_1_0_0 = new Version(10000);
    public static final Version V_1_0_1 = new Version(10001);
    public static final Version V_1_0_2 = new Version(10002);
    public static final Version V_1_0_3 = new Version(10003);

    public static final Version V_1_1_0 = new Version(10100);
    public static final Version V_1_1_1 = new Version(10101);
    public static final Version V_1_1_2 = new Version(10102);
    public static final Version V_1_1_3 = new Version(10103);
    public static final Version V_1_1_4 = new Version(10104);
    public static final Version V_1_1_5 = new Version(10105);

    public static final Version V_1_2_0 = new Version(10200);
    public static final Version V_1_2_1 = new Version(10201);

    public static final Version V_1_3_0 = new Version(10300);
    public static final Version V_1_3_1 = new Version(10301);
    public static final Version V_1_3_2 = new Version(10302);
    public static final Version V_1_3_3 = new Version(10303);

    public static final Version V_1_4_0 = new Version(10400);
    public static final Version V_1_4_1 = new Version(10401);
    public static final Version V_1_4_2 = new Version(10402);

    public static final Version V_1_5_0 = new Version(10500);
    public static final Version V_1_5_1 = new Version(10501);
    public static final Version V_1_5_2 = new Version(10502);
    public static final Version V_1_5_3 = new Version(10503);
    public static final Version V_1_5_4 = new Version(10504);
    public static final Version V_1_5_5 = new Version(10505);
    public static final Version V_1_5_6 = new Version(10506);

    public static final Version V_1_6_0 = new Version(10600);
    public static final Version V_1_6_1 = new Version(10601);
    public static final Version V_1_6_2 = new Version(10602);
    public static final Version V_1_6_3 = new Version(10603);
    public static final Version V_1_6_4 = new Version(10604);

    public static final Version V_1_7_0 = new Version(10700);
    public static final Version V_1_7_1 = new Version(10701);
    public static final Version V_1_7_2 = new Version(10702);

    public static final Version V_1_8_0 = new Version(10800);
    public static final Version V_1_8_1 = new Version(10801);
    public static final Version V_1_8_2 = new Version(10802);
    public static final Version V_1_8_3 = new Version(10803);

    public static final Version V_1_9_0 = new Version(10900);
    public static final Version V_1_9_1 = new Version(10901);
    public static final Version V_1_9_2 = new Version(10902);
    public static final Version V_1_9_3 = new Version(10903);

    public static final Version V_1_10_0 = new Version(11000);
    public static final Version V_1_10_1 = new Version(11001);
    public static final Version V_1_10_2 = new Version(11002);
    public static final Version V_1_10_3 = new Version(11003);

    public static final Version V_1_11_0 = new Version(11100);
    public static final Version V_1_11_1 = new Version(11101);
    public static final Version V_1_11_2 = new Version(11102);
    public static final Version V_1_11_3 = new Version(11103);
    public static final Version V_1_11_4 = new Version(11104);
    public static final Version V_1_11_5 = new Version(11105);
    public static final Version V_1_11_6 = new Version(11106);

    public static final Version V_1_12_0 = new Version(11200);
    public static final Version V_1_12_1 = new Version(11201);
    public static final Version V_1_12_2 = new Version(11202);
    public static final Version V_1_12_3 = new Version(11203);
    public static final Version V_1_12_4 = new Version(11204);
    public static final Version V_1_12_5 = new Version(11205);
    public static final Version V_1_12_6 = new Version(11206);
    public static final Version V_1_12_7 = new Version(11207);

    public static final Version V_1_13_0 = new Version(11300);
    public static final Version V_1_13_1 = new Version(11301);
    public static final Version V_1_13_2 = new Version(11302);
    public static final Version V_1_13_3 = new Version(11303);
    public static final Version V_1_13_4 = new Version(11304);
    public static final Version V_1_13_5 = new Version(11305);

    public static final Version V_1_14_0 = new Version(11400);
    public static final Version V_1_14_1 = new Version(11401);
    public static final Version V_1_14_2 = new Version(11402);

    public static final Version OLDEST = V_1_0_0;
    public static final Version CURRENT = V_1_14_2;
    public static final Version NEXT = new Version(11500);

    private final int id;
    private final byte major;
    private final byte minor;
    private final byte revision;

    private static boolean test(Map.Entry<String, Reflect> entry) {
        return !entry.getKey().equals("NEXT")
            && !entry.getKey().equals("CURRENT")
            && !entry.getKey().equals("OLDEST");
    }

    private static Version apply(Map.Entry<String, Reflect> entry) {
        return entry.getValue().get();
    }

    protected static final class DeclaredVersionsHolder {
        static final List<Version> DECLARED_VERSIONS = Collections.unmodifiableList(getDeclaredVersions());
    }

    Version(int id) {
        this.id = id;
        this.major = (byte) ((id / 10000) % 100);
        this.minor = (byte) ((id / 100) % 100);
        this.revision = (byte) ((id) % 100);
    }

    /**
     * Parse version string to a Flink Version
     *
     * @param ver Flink version string. eg: v1.14.0 V1.14.0 1.14.0 or the id string: 10900
     * @return Version
     */
    public static Version fromString(String ver) {
        Objects.requireNonNull(ver);
        if (ver.contains(".")) {
            if (ver.toLowerCase().startsWith("v")) {
                ver = ver.substring(1);
            }
            String[] verArray = ver.split("\\.");
            if (verArray.length == 3) {
                int major = Integer.parseInt(verArray[0]);
                int minor = Integer.parseInt(verArray[1]);
                int revision = Integer.parseInt(verArray[2]);

                int id = major * 10000 + minor * 100 + revision;
                Version version = new Version(id);
                if (DECLARED_VERSIONS.contains(version)) {
                    return new Version(id);
                }
            }
        } else {
            int id = Integer.parseInt(ver);
            Version version = new Version(id);
            if (DECLARED_VERSIONS.contains(version)) {
                return new Version(id);
            }
        }
        throw new IllegalArgumentException("invalid Flink version: " + ver);
    }

    public boolean after(Version version) {
        return version.id < id;
    }

    public boolean onOrAfter(Version version) {
        return version.id <= id;
    }

    public boolean before(Version version) {
        return version.id > id;
    }

    public boolean onOrBefore(Version version) {
        return version.id >= id;
    }

    public Version next() {
        if (CURRENT.equals(this)) {
            return NEXT;
        }
        return DECLARED_VERSIONS.get(DECLARED_VERSIONS.indexOf(this) + 1);
    }

    public Version previous() {
        if (OLDEST.equals(this)) {
            return this;
        }
        return DECLARED_VERSIONS.get(DECLARED_VERSIONS.indexOf(this) - 1);
    }

    @Override
    public int compareTo(Version other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.join(".",
                           Objects.toString(major),
                           Objects.toString(minor),
                           Objects.toString(revision));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Version version = (Version) o;
        return id == version.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    private static List<Version> getDeclaredVersions() {
        return Reflect.onClass(Version.class).fields()
                      .entrySet().stream()
                      .filter(Version::test)
                      .map(Version::apply)
                      .collect(Collectors.toList());
    }

}
