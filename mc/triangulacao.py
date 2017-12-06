import math
import numpy as np

xi = [1.55, -4.02, -4.40, 9.27, 9.15] # Coordenada x dos receptores
yi = [17.63, 0, 9.6, 4.64, 12] # Coordenada y dos receptores
zi = [1.35, 1.35, 1.35, 1.35, 1.35] # Coordenada z dos receptores

ro0 = [-26, -33.8, -29.8, -31.2, -33]
lk = [2.1, 1.8, 1.3, 1.4, 1.5]

#rok = [-48.4, -50.6, -32.2, -47.4, -46.3] # Caso 1
#xreal = 0 # Caso 1
#yreal = 9 # Caso 1
rok = [-46.9, -46.4, -41.2, -45.8, -48.7] # Caso 2
xreal = 3 # Caso 2
yreal = 3 # Caso 2

zreal = 1.24 # Caso1 e Caso2
dk = []
w = []
resultado = []
A = []
B = []

def distancia (ro0, rok, lk):
    dk = pow (10,(ro0 - rok)/(10*lk))
    return dk

def calculow (xi, yi, dk):
    wi = pow( xi,2) + pow(yi,2)  - pow(dk,2)
    return wi

for i in range(5):
    dk.append(distancia(ro0[i], rok[i], lk[i]))
    w.append(calculow(xi[i], yi[i], dk[i]))


A = np.matrix([[xi[1] - xi[0], yi[1] - yi[0]],
               [xi[2] - xi[1], yi[2] - yi[1]],
               [xi[3] - xi[2], yi[3] - yi[2]],
               [xi[4] - xi[3], yi[4] - yi[3]],
               [xi[0] - xi[4], yi[0] - yi[4]]])

B = np.matrix([[w[1] - w[0]],
               [w[2] - w[1]],
               [w[3] - w[2]],
               [w[4] - w[3]],
               [w[0] - w[4]]])

resultado = (A.T * A).I * A.T * B

print("Resultado")
print("x = ", resultado[0])
print("y = ", resultado[1])

import matplotlib.pyplot as plt
circle1 = plt.Circle((xi[0], yi[0]), dk[0], color='black', clip_on= True, fill = False)
circle2 = plt.Circle((xi[1], yi[1]), dk[1], color='black', clip_on= True, fill = False)
circle3 = plt.Circle((xi[2], yi[2]), dk[2], color='black', clip_on= True, fill = False)
circle4 = plt.Circle((xi[3], yi[3]), dk[3], color='black', clip_on= True, fill = False)
circle5 = plt.Circle((xi[4], yi[4]), dk[4], color='black', clip_on= True, fill = False)

fig, ax = plt.subplots()
ax = plt.gca()
ax.cla()

ax.set_xlim((-15,25))
ax.set_ylim((-15,30))
ax.plot(xreal, yreal, 'o', color='red')
ax.plot(resultado[0], resultado[1], 'o', color='blue')

ax.add_patch(circle1)
ax.add_patch(circle2)
ax.add_patch(circle3)
ax.add_patch(circle4)
ax.add_patch(circle5)

plt.show()
