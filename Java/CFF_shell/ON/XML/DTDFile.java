/**
 *@Project  :   cff_shell
 *@Package  :   ON.XML
 *@Revision :   1.0.1
 *@Created  :   04/04/2017 10:53
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON.XML;

// Import Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;


// Import Objects
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * <h1>Dtd file extractor.</h1>
 * <a>Class used by XMLReader class.</a>
 * <h2>Long description</h2>
 * <p>This class is used for extracting build-in dtd file out of the jar file.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
class DTDFile {

	private String DTD;
	/**
	 * Constructor of the DTDFile object.
	 * This constructor require the path/filename of the dtd configuration file of the xml files.
	 *
	 * @param File (String): filename of the DTD to use,
	 *             if null then it will use default dtd stored into jar file.
	 */
	public  DTDFile (String File) {
		if (File != null) {
			this.DTD = File ;
		} else {
			this.DTD = "/dtd/villes.xml.dtd";
		}
	}

	/**
	 * Function to extract dtd file.
	 *
	 * @param filename (String): filename where the dtd file will be extracted.
	 */
	public void extractDTDFileTo (String filename) {
		Reader DTDFile = null;
		boolean load = false;
		try {
			DTDFile = new InputStreamReader(getClass().getResourceAsStream(DTD));
			if (!DTDFile.ready())
				load = true;
		} catch (IOException e) {
			System.err.println("DTDFile: Unable Read ressources: " + getClass().getResourceAsStream(DTD).toString());
		} catch (NullPointerException e) {
			System.err.println("DTDFile: Unable Read ressources: " + DTD);
		}
		try {
			if (!load) {
				this.DTD = "lib/villes.xml.dtd";
				DTDFile = new InputStreamReader(new FileInputStream(DTD));
			}
			else {
				System.err.println("DTDFile: Unable to read file: " + DTD);
			}
		} catch (FileNotFoundException e) {
			System.err.println("DTDFile: Unable to read file: " + DTD);
			System.exit(-1);
		}
		String DTDContent = convertDTDToString(DTDFile, "\\A");
		try {
			try {
				File file = new File("./", filename);
				file.createNewFile();
				file.setWritable(true);
				file.setReadable(true);
			} catch (IOException e) {
				System.err.println("DTDFile: Unable to create file: " + filename);
				e.printStackTrace();
				System.exit(-1);
			}
			FileOutputStream DTDOutputStream = new FileOutputStream(filename);
			DTDOutputStream.write(DTDContent.getBytes());
		} /*catch (FileNotFoundException e) {
			System.err.println("DTDFile: Unable to write file: " + filename);
			System.exit(-1);
		}*/ catch (IOException e) {
			System.err.println("DTDFile: File \'" + filename + "\' not found" );
			System.exit(-1);
		}
	}

	/**
	 * Function to convert dtd file to string.
	 *
	 * @param is (Reader):
	 * @param delimiter (String):
	 * @return (String) - content of the Reader object.
	 */
	public String convertDTDToString(Reader is, String delimiter) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter(delimiter);
		return s.hasNext() ? s.next() : "";
	}
}
