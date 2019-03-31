package cc.mrbird.web.controller.test;

import cc.mrbird.common.annotation.Limit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);
    /**
     * 测试限流注解，下面配置说明该接口 60秒内最多只能访问 10次，保存到 redis的键名为 limit_test，
     * 即 prefix + "_" + key，也可以根据 IP 来限流，需指定 limitType = LimitType.IP
     */
    @Limit(key = "test", period = 60, count = 10, name = "测试Limit注解", prefix = "limit")
    @GetMapping("/test")
    public int testLimiter() {
        return ATOMIC_INTEGER.incrementAndGet();
    }

    @RequestMapping(value = "/test/bison",method = RequestMethod.POST)
    public String testBison(@RequestBody String string){
        logger.info(string);
        return string;
    }
}
