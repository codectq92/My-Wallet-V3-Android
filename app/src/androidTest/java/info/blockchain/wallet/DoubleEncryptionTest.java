package info.blockchain.wallet;

import android.content.Context;

import info.blockchain.wallet.crypto.AESUtil;
import info.blockchain.wallet.util.CharSequenceX;
import info.blockchain.wallet.util.DoubleEncryptionFactory;

public class DoubleEncryptionTest extends BlockchainTest {

    /**
     * @param String name
     * @param Context ctx
     */
    public DoubleEncryptionTest(String name, Context ctx) {
        super(name);
        context = ctx;
    }

    /**
     * @param String name
     */
    public DoubleEncryptionTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test something
     */
    public void test() {

        String cleartext = "test data";
        CharSequenceX pw = new CharSequenceX("password");
        int iterations = AESUtil.DoubleEncryptionPBKDF2Iterations;
        String sharedKey = "524b5e9f-72ea-4690-b28c-8c1cfce65ca0";

        DoubleEncryptionFactory def = DoubleEncryptionFactory.getInstance();
        assertTrue(def != null);

        String hash = def.getHash(sharedKey, pw.toString(), iterations);
        assertTrue(def.validateSecondPassword(hash, sharedKey, pw, iterations));

        String encrypted = def.encrypt(cleartext, sharedKey, pw.toString(), iterations);
        assertTrue(encrypted != null);

        String decrypted = def.decrypt(encrypted, sharedKey, pw.toString(), iterations);
        assertTrue(cleartext.equals(decrypted));

        String decrypted2 = def.decrypt(encrypted, sharedKey, "bogus", iterations);
        assertTrue(!cleartext.equals(decrypted2));

        String decrypted3 = def.decrypt(encrypted, sharedKey, pw.toString(), iterations + 1);
        assertTrue(!cleartext.equals(decrypted3));

    }

}