package src;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;

import org.junit.Assert;

import templates.*;

@RunWith(GradingRunner.class)
public class InginiousTests {
    int observerHasBeenNotified;

    @Test
    @Grade(value = 1, cpuTimeout=1000)
    @GradeFeedback(message="Balance should be correct after multiple deposits and withdrawals", onFail=true)
    public void testBalance() {
        ObservableAccount account=new ObservableAccount();

        account.deposit(1500);
        account.deposit(500);
        account.withdraw(400);
        Assert.assertEquals(1600,account.getBalance());
    }

    @Test
    @Grade(value = 1, cpuTimeout=1000)
    @GradeFeedback(message="Observer should be notified of large deposits and withdrawals", onFail=true)
    public void testObserver() {
        ObservableAccount account=new ObservableAccount();
        account.addObserver(() -> observerHasBeenNotified++, 1000);

        observerHasBeenNotified=0;
        account.deposit(1500);
        Assert.assertEquals(1, observerHasBeenNotified);
    }

    @Test
    @Grade(value = 1, cpuTimeout=1000)
    @GradeFeedback(message="Withdrawals should not be executed if there is not enough money in the account", onFail=true)
    public void testNotExecutedWithdrawal() {
        ObservableAccount account=new ObservableAccount();
        account.addObserver(() -> observerHasBeenNotified++, 1000);

        observerHasBeenNotified=0;
        account.deposit(500);
        account.withdraw(1000);
        Assert.assertEquals(0, observerHasBeenNotified);
    }

    @Test
    @Grade(value = 1, cpuTimeout=1000)
    @GradeFeedback(message="Multiple observers with different maximums should be possible", onFail=true)
    public void testMultipleObserver() {
        ObservableAccount account=new ObservableAccount();
        account.addObserver(() -> observerHasBeenNotified++, 1000);
        account.addObserver(() -> observerHasBeenNotified++, 500);

        observerHasBeenNotified=0;
        account.deposit(800);
        Assert.assertEquals(1, observerHasBeenNotified);
        observerHasBeenNotified=0;
        account.deposit(1500);
        Assert.assertEquals(2, observerHasBeenNotified);
    }

    @Test
    @Grade(value = 1, cpuTimeout=1000)
    @GradeFeedback(message="The maximum for a observer can be modified by calling addObserver() again", onFail=true)
    public void testModifyObserver() {
        ObservableAccount account=new ObservableAccount();
        ObservableAccount.AccountObserver observer= () -> observerHasBeenNotified++;

        observerHasBeenNotified=0;
        account.addObserver(observer, 1000);
        account.deposit(900);
        account.addObserver(observer, 800);
        account.deposit(900);
        Assert.assertEquals(1, observerHasBeenNotified);
        account.deposit(2000);
        Assert.assertEquals(2, observerHasBeenNotified);
    }
}
