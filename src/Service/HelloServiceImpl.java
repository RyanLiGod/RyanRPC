package Service;

import java.io.Serializable;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class HelloServiceImpl implements HelloService, Serializable {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
