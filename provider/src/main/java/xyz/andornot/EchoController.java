package xyz.andornot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟服务提供者提供响应数据
 *
 * @author igaozp
 * @since 2020/12/13
 */
@RestController
public class EchoController {

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        return "Hello Discovery " + string;
    }
}
