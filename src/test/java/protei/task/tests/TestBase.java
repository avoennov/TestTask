package protei.task.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import protei.task.appmanager.ApplicationManager;

import java.lang.reflect.Method;

@Listeners(MyTestListener.class)
public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected final ApplicationManager app = new ApplicationManager();

    //public static final Logger logger = LoggerFactory.getLogger(TestBase.class);


    @BeforeSuite
    public void setUp(ITestContext context) throws Exception {
        app.init();
        context.setAttribute("app", app);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m) {
        logger.info("======[Начало теста " + m.getName() + "]======");
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("======[Конец теста " + m.getName() + "]======");
    }

    public ApplicationManager getApp() {
        return app;
    }


}