package RPCServer;

import java.io.Serializable;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class HelloServiceImpl implements HelloService, Serializable {

    public String sayHi(String name) {
        return "Hi " + name;
    }

}
