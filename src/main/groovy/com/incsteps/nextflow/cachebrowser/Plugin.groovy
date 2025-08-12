/*
 * Copyright 2021, Seqera Labs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.incsteps.nextflow.cachebrowser


import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import nextflow.cli.PluginAbstractExec
import nextflow.plugin.BasePlugin
import org.pf4j.PluginWrapper

/**
 * Implements the Hello plugins entry point
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
@CompileStatic
@Slf4j
class Plugin extends BasePlugin implements PluginAbstractExec{

    Plugin(PluginWrapper wrapper) {
        super(wrapper)
        initPlugin()
    }

    private void initPlugin(){
        log.info "${this.class.name} plugin initialized"
    }

    @Override
    List<String> getCommands() {
        return ['run', 'rename']
    }

    @Override
    int exec(String cmd, List<String> args) {
        return switch (cmd){
            case 'run'-> run(args)
            case 'rename'-> rename(args)
            default -> -1
        }
    }

    int run(List<String> args){
        try {
            WebServer.run(args,wrapper.pluginClassLoader)
            0
        }catch(Throwable t){
            log.error("Error running web server",t)
            -1
        }
    }

    int rename(List<String> args){
        try {
            RenameWorkdirCmd.run(args,wrapper.pluginClassLoader)
            0
        }catch(Throwable t){
            log.error("Error running rename workdir command",t)
            -1
        }
    }

}
