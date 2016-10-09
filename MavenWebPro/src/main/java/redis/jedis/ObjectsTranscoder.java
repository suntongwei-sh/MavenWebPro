package redis.jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectsTranscoder {

	private static final Logger log = LoggerFactory.getLogger(ObjectsTranscoder.class);

	public static byte[] serialize(Object value) {
		if (value == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] rv = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(value);
			os.close();
			bos.close();
			rv = bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally {
			closeOutput(os);
			closeOutput(bos);
		}
		return rv;
	}

	public static Object deserialize(byte[] in) {
		Object rv = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				rv = is.readObject();
				is.close();
				bis.close();
			}
		} catch (IOException e) {
			log.warn("Caught IOException decoding %d bytes of data", in == null ? 0 : in.length, e);
		} catch (ClassNotFoundException e) {
			log.warn("Caught CNFE decoding %d bytes of data", in == null ? 0 : in.length, e);
		} finally {
			closeInput(is);
			closeInput(bis);
		}
		return rv;
	}

	private static void closeOutput(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void closeInput(InputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
