import matplotlib.pyplot as plt
import numpy as np


# Run to show 3 different activation functions, linear, tanH and standard sigmoid.

def initplt(sub):
    sub.axhline(linewidth=1, color='0.6', linestyle='--')
    sub.axvline(linewidth=1, color='0.6', linestyle='--')


f, (sub1, sub2, sub3) = plt.subplots(nrows=1, ncols=3)
sub2.set_xlabel("X")
sub1.set_ylabel("Y")
sample = 1
x = np.arange(-sample, sample, 0.01)
y = x
initplt(sub1)
sub1.set_title("Linear")
sub1.plot(x, y)

x = np.arange(-sample * 10, sample * 10, 0.01)
y = 2 / (1 + np.exp(-2 * x)) - 1
initplt(sub2)
sub2.set_title("TanH")
sub2.plot(x, y)
sub2.set_ylim(-1.5, 1.5)

y = 1 / (1 + np.exp(-x)) - 0.5
initplt(sub3)
sub3.set_title("Standard Sigmoid")
sub3.plot(x, y)
sub3.set_ylim(-1, 1)

plt.show()
