module com.longz.thss.ThssHzLibModule {
    requires java.logging;
    /*requires com.hazelcast.core;*/
    requires org.objectweb.asm.tree.analysis;

    /*exports com.longz.thss.lib.listener;*/
    /*exports com.hazelcast.core to jdk.jlink, jdk.jshell;*/
    exports com.longz.thss.lib.utils;
}