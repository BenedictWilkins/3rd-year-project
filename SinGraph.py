import matplotlib.pyplot as plt
import numpy as np


# Run to show the difference between shifting and shrinking a sinusoidal function.
# Displayed as a graph.

def plot1(x, sample):
    y1 = np.sin(2 * np.pi * 3 * x / sample) + 1
    y2 = np.sin(2 * np.pi * 3 * x / sample) * 0.5 + 1
    plt.gca().set_color_cycle(['black', 'red'])
    p1 = plt.plot(x, y1, label='Original')
    p2 = plt.plot(x, y2, label='Reduced')
    plt.legend()
    plt.xlabel("Time")
    plt.ylabel('Consumption')
    plt.show()


def plot2(x, sample):
    y1 = np.sin((2 * np.pi * 3 * x / sample) - (3 * np.pi / 4))
    y2 = np.sin(2 * np.pi * 3 * x / sample)
    y3 = y1 + y2 + 1
    plt.gca().set_color_cycle(['red', 'green', 'blue'])
    p2 = plt.plot(x, y3, label='Combination')
    p3 = plt.plot(x, y1 + 1, label='Consumer1')
    p4 = plt.plot(x, y2 + 1, label='Consumer2')
    plt.legend()
    plt.xlabel("Time")
    plt.ylabel('Consumption')
    plt.show()


sample = 500
x = np.arange(sample)
plot1(x, sample)
plot2(x, sample)
