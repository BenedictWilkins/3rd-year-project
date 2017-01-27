package utilities.autowarn;

import java.io.IOException;

public class YesNoQuestioner extends Questioner {

  private static final String[] ANSWERS = { "yes", "no" };

  private Runnable yes;
  private Runnable no;

  public YesNoQuestioner(String question, Runnable yes, Runnable no) {
    super(question);
    this.yes = yes;
    this.no = no;
  }

  @Override
  public String ask() throws IOException {
    String answer = super.ask();
    answer = answer.toLowerCase();
    while (!processAnswer(answer)) {
      answer = super.ask();
    }
    return answer;
  }

  private boolean processAnswer(String answer) {
    if (!yes(answer)) {
      if (!no(answer)) {
        return false;
      }
    }
    return true;
  }

  private boolean yes(String answer) {
    if (answer.charAt(0) == ANSWERS[0].charAt(0)) {
      if (yes != null) {
        yes.run();
      }
      return true;
    }
    return false;
  }

  private boolean no(String answer) {
    if (answer.charAt(0) == ANSWERS[1].charAt(0)) {
      if (no != null) {
        no.run();
      }
      return true;
    }
    return false;
  }
}
