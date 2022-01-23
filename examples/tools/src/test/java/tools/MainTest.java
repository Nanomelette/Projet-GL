package tools;

import static org.junit.Assert.*;
import org.junit.Test;

public class MainTest {

      @Test
      public void testMain() {
          Main mainprog = new Main();
          String[] args={"oui","non"};
          mainprog.main(args);
          args= new String[0];
          mainprog.main(args);
      }


}
