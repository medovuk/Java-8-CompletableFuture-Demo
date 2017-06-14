import org.junit.Test;
import org.junit.Ignore;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopTest {

  private BestPriceFinder mFinder = new BestPriceFinder();
  private String mProduct = "BestProduct";  // "NA"
  private String mShop = "BestShop";

  @Test
  public void runFindPrice() {
    say("> Calling findPrice");
    long startTime = getTime();
    String price = mFinder.findPrice(mShop, mProduct);
    say("< findPrice returns after " + (getTime() - startTime) + " milliseconds");
    say("I have been blocked until now :(");
    say("The price is " + price);
  }

  @Test
  public void runFindPriceAsync() {
    say("> Calling findPriceAsync");
    long startTime = getTime();
    Future<String> futurePrice = mFinder.findPriceAsync(mShop, mProduct);
    say("< findPriceAsync returns a Future after " + (getTime() - startTime) + " milliseconds");

    say("I can now dow anything I want :)");
    doSomethingElse();

    String price;
    try {
      price = futurePrice.get(10, TimeUnit.SECONDS);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    say("The Future is ready after " + (getTime() - startTime) + " milliseconds, price is " + price);
  }

  @Test
  public void runFindAllPrices() {
    say("> Calling findAllPrices");
    long startTime = getTime();
    List<String> prices = mFinder.findAllPrices(mProduct);
    say("< findAllPrices returns after " + (getTime() - startTime) + " milliseconds");
    prices.stream().forEach(this::say);
  }

  @Test
  public void runFindAllPricesParallel() {
    say("> Calling findAllPricesParallel");
    long startTime = getTime();
    List<String> prices = mFinder.findAllPrices(mProduct);
    say("< findAllPricesParallel returns after " + (getTime() - startTime) + " milliseconds");
    prices.stream().forEach(this::say);
  }

  @Test
  public void runFindAllPricesAsync() {
    say("> Calling findAllPricesAsync");
    long startTime = getTime();
    List<String> prices = mFinder.findAllPricesAsync(mProduct);
    say("< findAllPricesAsync returns after " + (getTime() - startTime) + " milliseconds");
    prices.stream().forEach(this::say);
    
  }

  private void say(String str) {
    System.out.println(str);
  }

  private long getTime() {
    return System.nanoTime() / 1_000_000;
  }

  private void doSomethingElse() {
    delay();
    say("doodelidum..");
    delay();
    say("doodelidum....");
  }

  private void delay() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
