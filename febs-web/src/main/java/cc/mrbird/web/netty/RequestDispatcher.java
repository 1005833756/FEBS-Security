package cc.mrbird.web.netty;


import cc.mrbird.common.constant.NettyConstant;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RequestDispatcher implements ApplicationContextAware {
    private ExecutorService executorService = Executors.newFixedThreadPool(NettyConstant.getMaxThreads());
    private ApplicationContext app;

    /**
     * 发送
     *
     * @param ctx
     * @param msg
     */
    public void dispatcher(final ChannelHandlerContext ctx, final String msg) {
        executorService.submit(() -> {
            ChannelFuture f = null;
            try {

                if (msg == null) {
                    f = ctx.writeAndFlush(ObjectUtils.NULL);
                } else {
                    f = ctx.writeAndFlush(msg);
                }
            } catch (Exception e) {

                f = ctx.writeAndFlush("");
            }
        });
    }

    /**
     * 加载当前application.xml
     *
     * @param ctx
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.app = ctx;
    }
}