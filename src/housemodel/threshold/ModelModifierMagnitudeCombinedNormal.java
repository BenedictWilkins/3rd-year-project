package housemodel.threshold;

import utilities.CombinedNormalDistribution;
import utilities.MathematicalFunction;

public class ModelModifierMagnitudeCombinedNormal extends
    ModelModifierMagnitude implements ModelModifierCombinedNormal {

  public ModelModifierMagnitudeCombinedNormal(Double scale) {
    super(scale);
  }

  private static final long serialVersionUID = 1L;

  @Override
  public CombinedNormalDistribution modify(MathematicalFunction<?> function) {
    CombinedNormalDistribution mod = (CombinedNormalDistribution) function;
    mod.getNormal1().setScale(mod.getNormal1().getScale() * this.getScale());
    mod.getNormal2().setScale(mod.getNormal2().getScale() * this.getScale());
    return mod;
  }

}
