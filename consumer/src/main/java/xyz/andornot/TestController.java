package xyz.andornot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 模拟调用服务提供者
 *
 * @author igaozp
 * @since 2020/12/13
 */
@RefreshScope
@RestController
public class TestController {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Lazy
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancerClient loadBalancerClient;
    @Value("${testConfig:false}")
    private boolean testConfig;

    @GetMapping("/echo/{str}")
    public String echo(@PathVariable String str) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
        String url = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), str);
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("config")
    public boolean config() {
        return testConfig;
    }
}
