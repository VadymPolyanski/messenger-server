import com.softgroup.common.loger.LoggerConfig;
import com.softgroup.common.protocol.ActionHeader;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.router.api.AbstractRequestHandler;
import com.softgroup.common.router.api.factory.RequestHandlerFactory;
import com.softgroup.common.router.impl.FirstRouterHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Author: vadym
 * Date: 04.03.17
 * Time: 15:58
 */
@ContextConfiguration(classes={LoggerConfig.class})
@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerFactoryTest {
    @InjectMocks
    private RequestHandlerFactory requestHandlerFactory;

    @Mock
    private AbstractRequestHandler handlerFirst;
    @Mock
    private AbstractRequestHandler handlerSecond;
    @Mock
    private Request firstRequest;
    @Mock
    private Request secondRequest;
    @Mock
    private ActionHeader firstHeader;
    @Mock
    private ActionHeader secondHeader;

    @Spy
    private List<AbstractRequestHandler> handlerList = new ArrayList<>();
    @Spy
    private Logger log = LoggerFactory.getLogger(FirstRouterHandler.class);

    @Before
    public void init() {
        when(handlerFirst.getName()).thenReturn("handlerFirst");
        when(handlerSecond.getName()).thenReturn("handlerSecond");

        handlerList.add(handlerFirst);
        handlerList.add(handlerSecond);


        when(firstRequest.getHeader()).thenReturn(firstHeader);
        when(secondRequest.getHeader()).thenReturn(secondHeader);

        when(firstHeader.getCommand()).thenReturn("handlerFirst");
        when(secondHeader.getCommand()).thenReturn("handlerSecond");

        requestHandlerFactory.init();
    }

    @Test
    public void testGetHandler() {
        assertThat(requestHandlerFactory.getHandler(firstRequest), is(handlerFirst));
        assertThat(requestHandlerFactory.getHandler(secondRequest), is(handlerSecond));
    }


}
