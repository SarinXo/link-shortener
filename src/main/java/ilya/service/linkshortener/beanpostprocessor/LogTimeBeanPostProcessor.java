package ilya.service.linkshortener.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@ConditionalOnProperty(name = "logging.log-time.enabled", havingValue = "true")
public class LogTimeBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, BeanInfo> beansWithLogTimeAnnotation = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();

        for (var method : methods) {
            if (method.isAnnotationPresent(LogTime.class)) {
                beansWithLogTimeAnnotation.putIfAbsent(
                        beanName,
                        new BeanInfo(bean.getClass(), new ArrayList<>())
                );
                beansWithLogTimeAnnotation.get(beanName).methods().add(method);
            }
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        BeanInfo beanInfo = beansWithLogTimeAnnotation.get(beanName);

        if (Objects.isNull(beanInfo)) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }

        MethodInterceptor interceptor = (obj, method, args, proxy) -> {
            LogTime annotation = method.getAnnotation(LogTime.class);

            if (Objects.nonNull(annotation)) {
                long start = System.currentTimeMillis();

                try {
                    return method.invoke(bean, args);
                } catch (Exception e) {
                    throw e.getCause();
                } finally {
                    long executionTime = System.currentTimeMillis() - start;
                    log.info("Метод {} выполнился за {} ms", getMethodName(method, annotation), executionTime);
                }
            }

            try {
                return method.invoke(bean, args);
            } catch (Exception e) {
                throw e.getCause();
            }

        };

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanInfo.clazz());
        enhancer.setCallback(interceptor);

        return enhancer.create();
    }

    private String getMethodName(Method annotatedMethod, LogTime annotation) {
        String annotationMethodName = annotation.methodName();
        return StringUtils.hasText(annotationMethodName)
                ? annotationMethodName
                : annotatedMethod.getName();
    }

    private record BeanInfo(Class<?> clazz, List<Method> methods) {
    }
}
