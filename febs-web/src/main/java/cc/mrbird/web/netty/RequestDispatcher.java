package cc.mrbird.web.netty;


import cc.mrbird.common.NettyConstant;
import cc.mrbird.common.domain.MethodInvokeMeta;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
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
     * @param invokeMeta
     */
    public void dispatcher(final ChannelHandlerContext ctx, final MethodInvokeMeta invokeMeta) {
        executorService.submit(() -> {
            ChannelFuture f = null;
            try {
                Class<?> interfaceClass = invokeMeta.getInterfaceClass();
                String name = invokeMeta.getMethodName();
                Object[] args = invokeMeta.getArgs();
                Class<?>[] parameterTypes = invokeMeta.getParameterTypes();
                Object targetObject = app.getBean(interfaceClass);
                Method method = targetObject.getClass().getMethod(name, parameterTypes);
                Object obj = method.invoke(targetObject, args);
                if (obj == null) {
                    f = ctx.writeAndFlush(ObjectUtils.NULL);
                } else {
                    f = ctx.writeAndFlush(obj);
                }
                f.addListener(ChannelFutureListener.CLOSE);
            } catch (Exception e) {

                f = ctx.writeAndFlush("");
            } finally {
                f.addListener(ChannelFutureListener.CLOSE);
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