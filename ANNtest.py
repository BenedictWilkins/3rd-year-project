import numpy as np
import matplotlib.pyplot as plt


# Simple implementation of an ANN, provides graphs showing
# change in total error per epoch and change in output per epoch.

def sigmoid(x):
    return 1 / (1 + np.exp(-x))


WO = np.array([[0.1, 0.3], [0.4, 0.1], [0.5, 0.2]])
WH = np.array([[0.1, 0.2, 0.4], [0.3, 0.1, 0.5]])
I = np.array([0.1, 0.2, 0.3]).T
O = np.array([0.0, 0.5, 1.0])
a = 0.5
EM = list()

EPOCHS = 1000
OUT = np.empty([3, EPOCHS])

for i in range(0, EPOCHS):
    netH = np.array([np.dot(I, WH[0]), np.dot(I, WH[1])])
    # print("netH: ", netH)
    outH = sigmoid(netH)
    # print("outH: ", outH)
    netO = np.array([np.dot(outH, WO[0]), np.dot(outH, WO[1]), np.dot(outH, WO[2])])
    # print("netO", netO)
    outO = sigmoid(netO)
    # print("outO", outO)
    sub = O - outO
    E = (1 / 2) * np.dot(sub.T, sub)
    print("TOTAL ERROR: ", E)
    EM.append(E)
    OUT[:, i] = outO

    # do output layer
    dOutOdNetO = outO * (1 - outO)
    dEdOutO = (outO - O)
    dNetOdWo = outH
    dEdWo = dEdOutO * dOutOdNetO
    dEdWo = np.outer(dEdWo, dNetOdWo)
    # print("dEdWo: ", dEdWo)

    # do hidden layer
    dEdNetO = dEdOutO * dOutOdNetO
    dNetOdOutH = WO
    dEdOutH = np.dot(dEdNetO, dNetOdOutH)
    dOutHdNetH = outH * (1 - outH)
    dNetHdWh = I
    dEdWh = dEdOutH * dOutHdNetH
    dEdWh = np.outer(dEdWh, dNetHdWh)

    # update weights
    WO -= (a * dEdWo)
    WH -= (a * dEdWh)

print(OUT)
plt.plot(EM)
plt.xlabel("Epoch")
plt.ylabel("Total Error")
plt.show()

plt.plot(OUT[0, :EPOCHS])
plt.plot(OUT[1, :EPOCHS])
plt.plot(OUT[2, :EPOCHS])
plt.xlabel("Epoch")
plt.ylabel("Output")
plt.axhline(0.5, color='0.8', linestyle='--')

plt.show()
