package com.incsteps.nextflow.cachebrowser.mn
import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters
import groovy.transform.CompileStatic

@Parameters(optionPrefixes = "--")
@CompileStatic
class WebServerConfig {

    @Parameter(names = '--help', description='help', help=true)
    boolean help

    @Parameter(names = '--port', description='web server port')
    int port = 9999

    @Parameter(names = '--directory', description='nextflow directory')
    String workdir = ''

    @Parameter(names = '--duckdb', description='a jdbc url to duckdb')
    String duckdbJdbc = ''

    @Parameter(names = '--postgres', description='a jdbc url to postgres')
    String postgresJdbc = ''

    @Parameter(names = '--user', description='a jdbc user')
    String jdbcuser = ''

    @Parameter(names = '--password', description='a jdbc password')
    String jdbcpassword = ''

    @Parameter(description = 'args')
    List<String> args


    String[] toMicronautProperties(){
        def map = [
                "--micronaut.server.port" : "${port}".toString()
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

}
