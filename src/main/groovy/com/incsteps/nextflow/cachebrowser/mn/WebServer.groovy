package com.incsteps.nextflow.cachebrowser.mn

import ch.qos.logback.classic.util.ContextInitializer
import com.beust.jcommander.JCommander
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Value
import io.micronaut.context.event.ShutdownEvent
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton

@Slf4j
@CompileStatic
@Singleton
class WebServer  {

    int port

    WebServer(@Value('${micronaut.server.port}')int port){
        this.port = port
    }

    @EventListener
    void onApplicationStartupEvent(StartupEvent event) {
        println "Server started at port ${port}"
    }

    @EventListener
    void onApplicationShutdownEvent(ShutdownEvent event) {
        println "Server shutdown"
    }

    static void run(List<String>args, ClassLoader classLoader) {
        WebServerConfig config = new WebServerConfig()

        JCommander jc = new JCommander()
        //jc.programName =" nf-cachebrowser"
        jc.addObject(config)
        jc.parse( args as String[])

        if( config.help ){
            jc.usage()
            return
        }

        run( config, classLoader )
    }

    static void run(WebServerConfig config, ClassLoader classLoader) {

        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY,"/home/jorge/nextflow/nf-cachebrowser/src/main/resources/logback.xml")
        def mn = Micronaut.build(config.toMicronautProperties())
                .classLoader(classLoader)
                .mainClass(WebServer)
                .start()
        while(true){
            sleep(1000)
        }
    }

    static void main(String[]args){
        System.setProperty("micronaut.environments","dev")
        run( args as List<String>, WebServer.classLoader )
    }
}
