package src;

import com.github.guillaumederval.javagrading.GradeClass;
import com.github.guillaumederval.javagrading.TestSecurityManager;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import java.security.*;
import java.security.cert.Certificate;
import java.util.concurrent.TimeUnit;


/**
 * Custom runner that jails everything
 */
public class JailRunner extends BlockJUnit4ClassRunner {

    public JailRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }


    @Override
    protected Statement withPotentialTimeout(FrameworkMethod method, Object test, Statement next) {

        GradeClass annoGradeClass = method.getDeclaringClass().getAnnotation(GradeClass.class);

        long timeout = 0;

        if(annoGradeClass != null && timeout == 0 && annoGradeClass.defaultCpuTimeout() > 0)
            timeout = annoGradeClass.defaultCpuTimeout() * 3;

        if (timeout <= 0) {
            return next;
        }
        return FailOnTimeout.builder()
                .withTimeout(timeout, TimeUnit.MILLISECONDS)
                .build(next);
    }


    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        Statement base = super.methodInvoker(method, test);

        checkSecurity();

        PermissionCollection coll = new Permissions();

        ProtectionDomain pd = new ProtectionDomain(new CodeSource(null, (Certificate[]) null), coll);

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                Throwable ex = AccessController.doPrivileged(new PrivilegedExceptionAction<Throwable>() {
                    @Override
                    public Throwable run() throws Exception {
                        Throwable ex = null;
                        try {
                            base.evaluate();
                        } catch (Throwable throwable) {
                            ex = throwable;
                        }
                        return ex;
                    }
                }, new AccessControlContext(new ProtectionDomain[]{pd}));

                if(ex != null)
                    throw ex;
            }
        };
    }


    private static void checkSecurity() {
        if(!(System.getSecurityManager() instanceof TestSecurityManager)) {
            try {
                System.setSecurityManager(new TestSecurityManager());
            }
            catch (SecurityException e) {
                System.out.println("/!\\ WARNING: Cannot set a TestSecurityManager as the security manager. Tests may not be jailed properly.");
            }
        }
    }
}
