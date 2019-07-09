package src;

import com.github.guillaumederval.javagrading.GradeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(JailRunner.class)
@GradeClass(totalValue = 1, defaultCpuTimeout = 2000)
public class StudentTests {

@Test
public void testNameMkyong() {

   MessageBuilder obj = new MessageBuilder();
   assertEquals("Hello mkyong", obj.getMessage("mkyong"));

}

}
