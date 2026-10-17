package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.SubVersionRange;
import com.viaversion.viaversion.api.protocol.version.VersionType;

import java.util.Comparator;

public class RedirectProtocolVersion extends ProtocolVersion {
    private final ProtocolVersion origin;

    public RedirectProtocolVersion(int version, String name, ProtocolVersion origin) {
        this(version, -1, name, null, origin);
    }

    public RedirectProtocolVersion(int version, int snapshotVersion, String name, SubVersionRange subVersionRange, ProtocolVersion origin) {
        super(VersionType.SPECIAL, version, snapshotVersion, name, subVersionRange);
        this.origin = origin;
    }

    @Override
    protected Comparator<ProtocolVersion> customComparator() {
        return (o1, o2) -> {
            ProtocolVersion v1 = (o1 instanceof RedirectProtocolVersion r) ? r.origin : o1;
            ProtocolVersion v2 = (o2 instanceof RedirectProtocolVersion r) ? r.origin : o2;
            return v1.compareTo(v2);
        };
    }

    public ProtocolVersion getOrigin() {
        return this.origin;
    }

    public ProtocolVersion getBaseProtocolVersion() {
        return this.origin;
    }
}
