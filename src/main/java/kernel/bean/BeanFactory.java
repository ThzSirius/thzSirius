package kernel.bean;

public interface BeanFactory<Bean> {

    Object getBean(String beanName);
    boolean containsBean(String name);
    void registerBean(Bean baseBean);

    default <T> T getBean(String beanName,Class<T> requiredType){
        return requiredType.cast(getBean(beanName));
    }

    default <T> T getBean(Class<T> requiredType){

    }

}
