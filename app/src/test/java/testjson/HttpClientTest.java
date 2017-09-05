package testjson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import pojo.Record;
import pojo.User;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by Jinffee on 2017/8/24.
 */
public class HttpClientTest {
    //conversation test
    HttpClient httpClient1;
    HttpClient httpClient2;
    //HttpClient httpClient;
    @Before
    public void setUp() throws Exception {
        //conversation test
        httpClient1 = new HttpClient();
        httpClient2 = new HttpClient();
      // httpClient = new HttpClient();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void tougunJudge() throws Exception {
        //conversation test
        httpClient1.connectionInit(1);
        httpClient2.connectionInit(2);
        sleep(6000);
        Connection connection1 = httpClient1.getConection();
        Connection connection2 = httpClient2.getConection();
        connection1.initChat();
        sleep(6000);
        connection1.selectDoctor(2);
        sleep(3000);
        connection1.send("hello1");
        connection2.send("hello2");
        sleep(3000);
        connection1.closeChat();
        sleep(3000);
//        httpClient.insert(new User(456,"1","normal","1"));


    }

}