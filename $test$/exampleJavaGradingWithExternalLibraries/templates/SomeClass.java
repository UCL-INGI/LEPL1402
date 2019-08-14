package templates;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class SomeClass {
    public static int someMethod(boolean flag){
		@ @student_code@@
    }

    // a example about using external libraries
    // Credits to https://www.baeldung.com/apache-commons-math for the code sample
    public static void main(String[] args) {
        double[] values = new double[] {65, 51 , 16, 11 , 6519, 191 ,0 , 98, 19854, 1, 32};
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (double v : values) {
            descriptiveStatistics.addValue(v);
        }

        double mean = descriptiveStatistics.getMean();
        double median = descriptiveStatistics.getPercentile(50);
        double standardDeviation = descriptiveStatistics.getStandardDeviation();

    }
}
