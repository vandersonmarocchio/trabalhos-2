f1 = open("isMeshXY.txt", "r")
f2 = open("isOctagon.txt", "r")

mesh = []
oct = []

for x in f1:
    x = x.split()
    mesh.append(float(x[1]))

for y in f2:
    y = y.split()
    oct.append(float(y[1]))

for m, o in zip(mesh, oct):
    print(str((o-m)/m*100))
