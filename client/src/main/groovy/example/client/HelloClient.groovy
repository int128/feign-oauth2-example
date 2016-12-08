package example.client

import example.feign.HelloClientConfiguration
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = 'hello', url = '${hello.service.url}', configuration = HelloClientConfiguration)
interface HelloClient {
    @RequestMapping(value = '/hello', method = RequestMethod.GET)
    Hello hello()

    @RequestMapping(value = '/hello/{name}', method = RequestMethod.GET)
    Hello hello(@PathVariable('name') String name)
}
