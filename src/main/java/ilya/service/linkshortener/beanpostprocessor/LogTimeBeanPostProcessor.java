package ilya.service.linkshortener.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class LogTimeBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, BeanInfo> beansWithLogTimeAnnotation = new HashMap<>();

    private record BeanInfo(Class<?> clazz, ArrayList<Method> methods) {
    }

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
            boolean isAnnotated = beanInfo.methods().stream()
                    .anyMatch(annotatedMethod -> methodEquals(annotatedMethod, method));

            if (isAnnotated) {
                long start = System.currentTimeMillis();

                try {
                    return method.invoke(bean, args);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    long executionTime = System.currentTimeMillis() - start;
                    log.info("Метод {} выполнился за {} ms", method.getName(), executionTime);
                }
            }

            try {
                return method.invoke(bean, args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        };

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanInfo.clazz());
        enhancer.setCallback(interceptor);

        return enhancer.create();
    }

    private boolean methodEquals(Method m1, Method m2) {
        String methodName1 = m1.getName();
        String methodName2 = m2.getName();

        if (methodName1.equals(methodName2)) {
            return equalParamTypes(m1.getParameterTypes(), m2.getParameterTypes());
        }

        return false;
    }

    private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }
}
