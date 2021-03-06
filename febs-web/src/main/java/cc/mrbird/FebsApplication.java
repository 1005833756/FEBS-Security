package cc.mrbird;

import cc.mrbird.common.properties.FebsProperies;
import cc.mrbird.security.properties.FebsSecurityProperties;
import cc.mrbird.web.config.NettyConfig;
import cc.mrbird.web.listener.NettyServerListener;
import org.apache.catalina.security.SecurityUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.social.autoconfigure.SocialWebAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication(exclude = {SocialWebAutoConfiguration.class})
@MapperScan({"cc.mrbird.*.dao"})
@EnableConfigurationProperties({FebsSecurityProperties.class, FebsProperies.class, NettyConfig.class})
@EnableCaching
@EnableAsync
@EnableTransactionManagement
public class FebsApplication implements CommandLineRunner {
    @Autowired
    private NettyServerListener nettyServerListener;
    public static void main(String[] args) {
        SpringApplication.run(FebsApplication.class, args);
        LoggerFactory.getLogger(FebsApplication.class).info(
                "《《《《《《 FEBS started up successfully at {} {} 》》》》》》", LocalDate.now(), LocalTime.now());
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(SecurityUtil.isPackageProtectionEnabled());
        nettyServerListener.start();
    }
}
