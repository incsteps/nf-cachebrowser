package com.incsteps.nextflow.cachebrowser


import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import com.incsteps.nextflow.cachebrowser.mn.StorageService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.runtime.Micronaut
import jakarta.inject.Singleton

@Slf4j
@CompileStatic
@Singleton
class RenameWorkdirCmd {

    RenameWorkdirCmd(){
    }

    static void run(List<String>args, ClassLoader classLoader) {
        RenameConfig config = new RenameConfig()

        try {
            JCommander jc = new JCommander()
            jc.addObject(config)
            jc.parse(args as String[])

            if (config.help) {
                jc.usage()
                return
            }

            if( config.args.size() != 2){
                throw new ParameterException("oldPath newPath are required")
            }
        }catch (e){
            System.err.println(e.toString())
            return
        }

        run( config, classLoader )
    }

    static void run(RenameConfig config, ClassLoader classLoader) {

        def mn = Micronaut.build(config.toMicronautProperties())
                .classLoader(classLoader)
                .mainClass(RenameWorkdirCmd)
                .start()

        def storage = mn.getBean(StorageService)

        storage.renameWorkDir(config.args.first, config.args.last, config.task)

        mn.stop()
    }

    static void main(String[]args){
        System.setProperty("micronaut.environments","dev")
        run( args as List<String>, RenameWorkdirCmd.classLoader )
    }
}
