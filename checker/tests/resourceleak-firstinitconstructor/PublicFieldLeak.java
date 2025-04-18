// Test: Field is public assigned in constructor and open().
// Expected: Leak in both constructor and open().

import java.io.FileInputStream;
import org.checkerframework.checker.calledmethods.qual.*;
import org.checkerframework.checker.mustcall.qual.*;

@InheritableMustCall({"close"})
class PublicFieldLeak {
  @Owning public FileInputStream f;

  public PublicFieldLeak() throws Exception {
    // :: error: (required.method.not.called)
    f = new FileInputStream("file.txt");
  }

  // :: error: (missing.creates.mustcall.for)
  public void open() {
    try {
      // :: error: (required.method.not.called)
      f = new FileInputStream("file.txt");
    } catch (Exception e) {
    }
  }

  @EnsuresCalledMethods(value = "this.f", methods = "close")
  public void close() {
    try {
      f.close();
    } catch (Exception e) {
    }
  }
}
