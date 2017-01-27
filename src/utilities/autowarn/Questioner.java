package utilities.autowarn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Questioner {

  private String question;
  private BufferedReader reader;

  public Questioner(String question) {
    this.question = question;
    reader = new BufferedReader(new InputStreamReader(System.in));
  }

  /**
   * Asks the question represented by this {@link Questioner}.
   * 
   * @return the answer given by the user
   * @throws IOException
   *           if the users answer cannot be read
   */
  public String ask() throws IOException {
    System.out.println(question);
    return reader.readLine();
  }

  /**
   * Closes this {@link Questioner}.
   */
  public void close() {
    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

}
