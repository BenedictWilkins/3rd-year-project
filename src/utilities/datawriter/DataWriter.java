package utilities.datawriter;

import utilities.autowarn.AutoWarnContinue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class DataWriter {

  private boolean warnExists = true;

  private BufferedWriter writer;
  private File file;
  private FileFormat format;
  private String write;

  /**
   * Constructor.
   * 
   * @param filename
   *          of the file to write to
   * @param format
   *          of the file to be written
   */
  public DataWriter(String filename, FileFormat format) {
    // TODO check the format again the filename
    try {
      this.file = createFile(filename);
      this.format = format;
      writer = createWriter(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Appends the given array to the end of the file that should be written to by
   * this writer. The given array should be appropriately structured for the
   * file format of the file to be written to.
   * 
   * @param toWrite
   *          to be written
   */
  public void write(String[] toWrite) {
    write = this.format.construct(toWrite);
    try {
      writer.write(write);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private File createFile(String filename) throws Exception {
    File file = new File(filename);
    if (file.exists()) {
      if (warnExists) {
        AutoWarnContinue warning = new AutoWarnContinue("File: " + filename
            + " already exists and will be over written.", null);
        warning.warn();
      }
    }
    file.createNewFile();
    return file;
  }

  private BufferedWriter createWriter(File file) {
    try {
      return new BufferedWriter(new PrintWriter(file));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Getter for overwrite warning flag.
   * 
   * @return whether a warning will be given if a file will be overwritten. True
   *         - yes, false - no.
   */
  public boolean isWarnExists() {
    return warnExists;
  }

  /**
   * Setter for overwrite warning flag.
   * 
   * @param warnExists
   *          true if warning should be given, false if it not
   */
  public void setWarnExists(boolean warnExists) {
    this.warnExists = warnExists;
  }

  /**
   * Getter for the {@link File} that is being written to.
   * 
   * @return the {@link File}
   */
  public File getFile() {
    return this.file;
  }

}
