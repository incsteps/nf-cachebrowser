package com.incsteps.nextflow.cachebrowser
import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters
import groovy.transform.CompileStatic

@Parameters(optionPrefixes = "--")
@CompileStatic
class RenameConfig {

    @Parameter(names = '--help', description='help', help=true)
    boolean help

    @Parameter(names = '--directory', description='nextflow directory')
    String workdir = ''

    @Parameter(names = '--duckdb', description='a jdbc url to duckdb')
    String duckdbJdbc = ''

    @Parameter(names = '--postgres', description='a jdbc url to postgres')
    String postgresJdbc = ''

    @Parameter(names = '--task', description='replace in specified task')
    String task

    @Parameter(description = 'new path to use', required = true)
    List<String> args


    String[] toMicronautProperties(){
        adjustWorkDir()
        def map = [
                "--micronaut.server.port" : "-1" // random port
        ]
        map.putAll([
                "--storage": "local", // by default
                "--storage.dir" : workdir
        ])
        if( duckdbJdbc.length() ){
            map.putAll([
                    "--storage": "duckdb",
                    "--duckdb.jdbc" : duckdbJdbc
            ])
        }
        map.entrySet().collect{ "$it.key=$it.value".toString()} as String[]
    }

    private void adjustWorkDir(){
        if( workdir?.length() == 0 && System.getenv("NXF_CACHE_DIR")){
            workdir = System.getenv("NXF_CACHE_DIR")
        }
    }
}
