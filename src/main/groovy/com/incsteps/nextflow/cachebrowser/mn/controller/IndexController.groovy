package com.incsteps.nextflow.cachebrowser.mn.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller
class IndexController {

    @Get("/")
    @Produces(MediaType.TEXT_HTML)
    MutableHttpResponse<String> index(){
        def indexHtml = getClass().getResourceAsStream("/frontend/index.html").text
        return HttpResponse.ok(indexHtml)
    }
}
