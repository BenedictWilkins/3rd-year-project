package machinelearning.agent;

import java.io.Serializable;

public interface DataFrameRow extends Serializable {

  public Object[] getValues();

  public <T> T getValue(int column, Class<T> type);

  public int getSize();
}
