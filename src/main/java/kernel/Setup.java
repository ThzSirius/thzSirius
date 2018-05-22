package kernel;


import kernel.config.DataSource;
import kernel.exception.SetupInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class Setup {

    private static final Logger logger = LoggerFactory.getLogger(Setup.class);

    private static List<Method> initMethods = new ArrayList<>();

    private static List<Method> exceptionHandleMethods = new ArrayList<>();

    private static List<Method> aopMethods = new ArrayList<>();

    private static Map<String, Method> webMethods = new HashMap<>();

    private static Map<Method, Set<Method>> methodInterceptors = new HashMap<>();

    private static DataSource dataSource;

    private static List<Class> scanClasses;

    static void init() throws SetupInitException{

    }

    private static void initProperties(){
        List<String> propertiesList = new ArrayList<>();


    }


}
