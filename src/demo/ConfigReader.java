package demo;

import org.jfree.util.StringUtils;

import utilities.Pair;
import agent.general.AgentStructure;
import agent.general.AgentType;
import housemodels.House;
import housemodels.HouseFactory;
import housemodels.HouseModelFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigReader {

  private static Map<String, StringBuilder> KEYWORDS = new HashMap<>();
  private static String TIMEGAP = "timegap", AGENTS = "agents",
      ERROR = "error", PREDICTOR = "predictor", GROUP = "group",
      ACORNU = "acornu", COMFORTABLE = "comfortable", AFFLUENT = "affluent",
      ADVERSITY = "adversity";
  private static Map<String, AgentStructureGetter> AGENTKEYWORDMAP = new HashMap<>();
  private static Map<String, HouseGetter> HOUSEKEYWORDMAP = new HashMap<>();

  static {
    // base keywords
    KEYWORDS.put(AGENTS, new StringBuilder());
    KEYWORDS.put(ERROR, new StringBuilder());
    KEYWORDS.put(TIMEGAP, new StringBuilder());
    // agent keywords
    doAgentGetters();
    // house keywords
    doHouseGetters();
  }

  private BufferedReader reader;
  private Double error;

  public ConfigReader(String filepath) throws IOException {
    File file = new File(filepath);
    if (file.exists()) {
      reader = new BufferedReader(new FileReader(file));
    }
    // read the file
    String line;
    StringBuilder builder = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      builder.append(line.toLowerCase().replace("\t", "").replace(" ", ""));
    }
    Map<Integer, String> indexmap = new HashMap<>();
    KEYWORDS.keySet().forEach((String s) -> {
      indexmap.put(builder.indexOf(s), s);
    });
    List<Integer> indexs = new ArrayList<>(indexmap.keySet());
    Collections.sort(indexs);
    System.out.println(indexs);
    indexs.add(null);
    for (int i = 0; i < indexs.size() - 1; i++) {
      KEYWORDS.get(indexmap.get(indexs.get(i))).append(
          builder.substring(
              indexs.get(i),
              (indexs.get(i + 1) != null) ? indexs.get(i + 1) : builder
                  .length()).split(":")[1]);
    }
  }

  public AgentStructure[] getAgentStructure() {
    this.error = Double.valueOf(KEYWORDS.get(ERROR).toString());
    String input = KEYWORDS.get(AGENTS).toString();
    int c1 = input.length() - input.replace("{", "").length();
    int c2 = input.length() - input.replace("}", "").length();
    if (c1 != c2) {
      System.err.println("INVALID AGENT STRUCTURE");
      return null;
    }
    return recurseStructure(input);
  }

  private AgentStructure[] recurseStructure(String input) {
    List<Pair<String, String>> nodes = new ArrayList<>();
    if (input.contains("{")) {
      String next = "";
      String nextInput = input;
      int total = 0;
      while (!nextInput.isEmpty()) {
        Pair<Pair<String, String>, String> result = findNode(nextInput);
        if (result != null) {
          nodes.add(result.getO1());
          nextInput = result.getO2();
        }
      }
      List<AgentStructure> agents = new ArrayList<>();
      for (Pair<String, String> p : nodes) {
        agents.add(AGENTKEYWORDMAP.get(p.getO1()).getAgent(
            recurseStructure(p.getO2())));
      }
      return agents.toArray(new AgentStructure[]{});
    } else {
      String[] shouses = input.split(",");
      House[] houses = new House[shouses.length];
      for (int i = 0; i < shouses.length; i++) {
        houses[i] = HOUSEKEYWORDMAP.get(shouses[i]).getHouse(this.error);
      }
      return createSmartMeter(houses);
    }
  }

  private Pair<Pair<String, String>, String> findNode(String input) {
    int start = input.indexOf("{");
    if (start < 0) {
      return null;
    }
    int l = input.length();
    int count = 0;
    int end = -1;
    String next = null;
    if (start >= 0) {
      for (int i = start; i < l; i++) {
        if (input.charAt(i) == '{') {
          count++;
        } else if (input.charAt(i) == '}') {
          count--;
          if (count == 0) {
            end = i;
            break;
          }
        }
      }
    }
    String key = input.substring(0, start).replace(",", "");
    System.out.println(key);
    // return ((key,output),next)
    return new Pair<Pair<String, String>, String>(new Pair<String, String>(key,
        input.substring(start + 1, end)), input.substring(end + 1));
  }

  private AgentStructure[] createSmartMeter(House[] houses) {
    AgentStructure[] astructs = new AgentStructure[houses.length];
    for (int i = 0; i < houses.length; i++) {
      astructs[i] = new AgentStructure(houses[i], new AgentStructure[] {});
    }
    return astructs;
  }

  public Integer getTimeGap() {
    return Integer.valueOf(KEYWORDS.get(TIMEGAP).toString());
  }

  private interface AgentStructureGetter {
    public AgentStructure getAgent(AgentStructure... children);
  }

  private interface HouseGetter {
    public House getHouse(Double error);
  }

  public final static void doHouseGetters() {
    HOUSEKEYWORDMAP.put(ACORNU, new HouseGetter() {
      @Override
      public House getHouse(Double error) {
        return HouseFactory.getFactory().createAcornUHouse(error);
      }
    });
    HOUSEKEYWORDMAP.put(COMFORTABLE, new HouseGetter() {
      @Override
      public House getHouse(Double error) {
        return HouseFactory.getFactory().createComfortableHouse(error);
      }
    });
    HOUSEKEYWORDMAP.put(ADVERSITY, new HouseGetter() {
      @Override
      public House getHouse(Double error) {
        return HouseFactory.getFactory().createAdversityHouse(error);
      }
    });
    HOUSEKEYWORDMAP.put(AFFLUENT, new HouseGetter() {
      @Override
      public House getHouse(Double error) {
        return HouseFactory.getFactory().createAffluentHouse(error);
      }
    });
  }

  private static final void doAgentGetters() {
    AGENTKEYWORDMAP.put(PREDICTOR, new AgentStructureGetter() {
      @Override
      public AgentStructure getAgent(AgentStructure... children) {
        return new AgentStructure(AgentType.PREDICTOR, children);
      }
    });
    AGENTKEYWORDMAP.put(GROUP, new AgentStructureGetter() {
      @Override
      public AgentStructure getAgent(AgentStructure... children) {
        return new AgentStructure(AgentType.NEIGHBOURHOOD, children);
      }
    });
  }
}
